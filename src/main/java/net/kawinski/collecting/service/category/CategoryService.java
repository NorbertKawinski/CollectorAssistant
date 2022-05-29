package net.kawinski.collecting.service.category;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.BaseAttributeTemplate;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.data.repository.CategoryRepository;
import net.kawinski.collecting.service.attribute.AttributeTarget;
import net.kawinski.logging.NkTrace;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class CategoryService implements Serializable {

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private Event<CategoryCreatedEventData> categoryCreatedEventSrc;

    @Inject
    private Event<CategoryUpdatedEventData> categoryUpdatedEventSrc;

    // TODO: Add to BaseClass?
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <R> R onNewTrx(final Function<? super CategoryService, R> function) {
        return function.apply(this);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> T onNewTrx(final Supplier<T> function) {
        return function.get();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void onNewTrx(final Runnable function) {
        function.run();
    }

    public Category findRoot(final boolean joinSubcategories) {
        return categoryRepository.findRoot(joinSubcategories);
    }

    public Category findRootJoinSubcategoriesRecursive() {
        return categoryRepository.findRootJoinSubcategoriesRecursive();
    }

    public Optional<Category> findById(final long id, final boolean joinSubcategories, final boolean joinCollections, final boolean joinCollectionTemplate, final boolean joinElementTemplate) {
        return categoryRepository.findById(id, joinSubcategories, joinCollections, joinCollectionTemplate, joinElementTemplate);
    }

    public Category findByIdOrThrow(final long id) {
        return findByIdOrThrow(id, false, false, false, false);
    }

    public Category findByIdOrThrow(final long id, final boolean joinSubcategories, final boolean joinCollections, final boolean joinCollectionTemplate, final boolean joinElementTemplate) {
        return categoryRepository.findByIdOrThrow(id, joinSubcategories, joinCollections, joinCollectionTemplate, joinElementTemplate);
    }

    public void update(final Category category) {
        try(final NkTrace trace = NkTrace.info(log, "category: {}", category)) {
            categoryRepository.update(category);
            categoryUpdatedEventSrc.fire(new CategoryUpdatedEventData(category));
        }
    }

    public Category create(final Category parent, final String name, final boolean canContainCollections) {
        return create(new CreateCategoryForm(parent.getId(), name, canContainCollections));
    }

    public Category create(final CreateCategoryForm form) {
        try(final NkTrace trace = NkTrace.info(log, "form: {}", form)) {
            final Category parent = Optional.ofNullable(form.getParentId())
                    .map(parentId -> findByIdOrThrow(parentId, true, false, false, false))
                    .orElse(null);
            final Category category = new Category(parent, form.getName(), form.isCanContainCollections());
            categoryRepository.persist(category);
            if(parent != null)
                // Will create subcategory thanks to cascade=ALL (but we ignore that and persist manually)
                // Automatic persisint would generate ID only at the end of transaction and we might need it earlier
                parent.addSubcategory(category);
            categoryCreatedEventSrc.fire(new CategoryCreatedEventData(category));
            return trace.returning(category);
        }
    }

    public List<? extends BaseAttributeTemplate> getAttributeTemplates(final Category category, AttributeTarget target) {
        switch(target) {
            case Collection: return getCollectionAttributeTemplates(category);
            case Element: return getElementAttributeTemplates(category);
            default: throw new IllegalArgumentException("Unknown target: " + target);
        }
    }

    public List<CollectionAttributeTemplate> getCollectionAttributeTemplates(final Category category) {
        return categoryRepository.getCollectionAttributeTemplates(category);
    }

    public List<ElementAttributeTemplate> getElementAttributeTemplates(final Category category) {
        return categoryRepository.getElementAttributeTemplates(category);
    }
}
