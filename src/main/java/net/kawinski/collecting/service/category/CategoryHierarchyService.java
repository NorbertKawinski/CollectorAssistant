package net.kawinski.collecting.service.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.logging.NkTrace;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This service holds (caches) the whole categories structure,
 * along with the ability to query for all parents/children of the category.
 *
 * The cache is automatically refreshed when category is created or updated.
 */
//@Named
//@ApplicationScoped
// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Singleton
@Slf4j
public class CategoryHierarchyService {

    @Inject
    private CategoryService categoryService;

    @Getter
    private Category rootCategory = null;

    private final Map<Category, List<Category>> parentsOfCategory = new HashMap<>();

    private final Map<Category, List<Category>> childrenOfCategory = new HashMap<>();

    @Inject
    private Event<CategoryHierarchyChangedEventData> categoryHierarchyChangedEventSrc;

    @Resource
    private TimerService timerService;

    private Optional<Timer> timerOpt = Optional.empty();

    @PostConstruct
    public void postConstruct() {
        try(final NkTrace trace = NkTrace.info(log, "Initializing...")) {
            // This one is pretty tricky and a little hackish!!!
            // We don't always need to run this on new transaction.
            // But whenever StartupInitializerBean finishes its job, CDI tries to call events regarding new categories.
            // Since we're not initialized yet, CDI first runs this postConstruct.
            // But since event is on AFTER_SUCCESS, the refreshCategories() would be called after transaction.
            // This would lead to the same problem as described in the events part of this class.
            // It's a bit crappy that CDI runs postConstruct after transaction in such situation, but we have to deal with it.
            categoryService.onNewTrx(this::refreshCategories);
        }
    }

    private void refreshCategories() {
        try(final NkTrace trace = NkTrace.info(log)) {
            rootCategory = categoryService.findRootJoinSubcategoriesRecursive();
            refreshCategoriesRecursive(rootCategory, new ArrayList<>());
        }
    }

    /**
     * Refreshes cache of the passed category regarding its children and parents.
     *
     * @param category category to refresh
     * @param parents parents of the category
     * @return list of all children of the category (recursive)
     */
    private List<Category> refreshCategoriesRecursive(final Category category, final List<Category> parents) {
        final List<Category> children = new ArrayList<>(category.getSubCategories());

        final List<Category> parentsWithMe = new ArrayList<>(parents);
        final List<Category> subChildren = new ArrayList<>();
        for(final Category child : children) {
            subChildren.addAll(refreshCategoriesRecursive(child, parentsWithMe));
        }
        children.addAll(subChildren);

        parentsOfCategory.put(category, parents);
        childrenOfCategory.put(category, children);
        return children;
    }

    public List<Category> getParentsOf(final Category category) {
        return new ArrayList<>(parentsOfCategory.get(category));
    }

    public List<Category> getChildrenOf(final Category category) {
        return new ArrayList<>(childrenOfCategory.get(category));
    }

    public void onCategoryCreated(@Observes(during = TransactionPhase.AFTER_SUCCESS) final CategoryCreatedEventData event) {
        try(final NkTrace trace = NkTrace.info(log, "event: {}", event)) {
            // Since we listen to AFTER_SUCCESS, then current transaction is already COMMITTED.
            // We have to do it 'onNewTrx', because WildFly seems to try to reuse old transaction
            // which results in nasty exception: 'IJ000609: Attempt to return connection twice'
            // and then another: 'javax.persistence.PersistenceException: org.hibernate.exception.GenericJDBCException: Unable to acquire JDBC Connection'
            // caused by: javax.resource.ResourceException: IJ000461: Could not enlist in transaction on entering meta-aware object
            // caused by: java.lang.IllegalStateException: Transaction Local transaction (xxx) is not active STATUS_COMMITTED
//            categoryService.onNewTrx(this::refreshCategories);
//            categoryHierarchyChangedEventSrc.fire(new CategoryHierarchyChangedEventData(rootCategory));
            scheduleCategoriesRefresh();
        }
    }

    public void onCategoryUpdated(@Observes(during = TransactionPhase.AFTER_SUCCESS) final CategoryUpdatedEventData event) {
        try(final NkTrace trace = NkTrace.info(log, "event: {}", event)) {
            // Similarly to 'created' event
//            categoryService.onNewTrx(this::refreshCategories);
//            categoryHierarchyChangedEventSrc.fire(new CategoryHierarchyChangedEventData(rootCategory));
            scheduleCategoriesRefresh();
        }
    }

    private void scheduleCategoriesRefresh() {
        // Sometimes we might create several categories very quickly (especially at startup).
        // Let's delay the refresh a bit to merge several pointless consecutive refreshes into a single one.
        timerOpt.ifPresent(Timer::cancel);
        final long duration = 1000;
        timerOpt = Optional.of(timerService.createSingleActionTimer(duration, new TimerConfig()));
    }

    @Timeout
    private void onRefreshSchedulerTimeout(final Timer timer) {
        try (final NkTrace trace = NkTrace.info(log, "timer: {}", timer)) {
            timerOpt = Optional.empty();
            refreshCategories();
            categoryHierarchyChangedEventSrc.fire(new CategoryHierarchyChangedEventData(rootCategory));
        }
    }
}
