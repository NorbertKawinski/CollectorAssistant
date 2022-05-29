package net.kawinski.collecting.presentation.controllers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.service.category.CategoryHierarchyChangedEventData;
import net.kawinski.collecting.service.category.CategoryHierarchyService;
import net.kawinski.logging.NkTrace;
import org.omnifaces.model.tree.ListTreeModel;
import org.omnifaces.model.tree.TreeModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Shares between clients and provides caching for frequently used things (like categories list on the navbar)
 *
 * Thanks to the initialization being in @PostConstruct, we don't have to worry about two clients calling getCategories() simultaneously
 * as the bean will be available only once the @PostConstruct is done. Synchronization is automatically done by the JEE container.
 *
 * TODO: Add cache
 */
// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated}, force = true)
@Named
@ApplicationScoped
@Slf4j
public class GlobalLayoutController {

    private final CategoryHierarchyService categoryHierarchyService;

    /**
    * It's not a real thing. The OmniFaces 'tree' component requires some root, so we're providing it.
    */
    @Getter
    private TreeModel<Category> categoriesTree = new ListTreeModel<>();

    @Getter
    @Setter
    private String searchQuery = "";

    @Inject
    public GlobalLayoutController(final CategoryHierarchyService categoryHierarchyService) {
        this.categoryHierarchyService = categoryHierarchyService;
        refreshCategories();
    }

    private void refreshCategories() {
        try(final NkTrace trace = NkTrace.info(log)) {
            categoriesTree = new ListTreeModel<>();
            fillCategoryTree(categoriesTree, getRootCategory().getSubCategories());
        }
    }

    private void fillCategoryTree(final TreeModel<Category> parentCategory, final List<? extends Category> subCategories) {
        for(final Category subCategory : subCategories) {
            final TreeModel<Category> newParentCategory = parentCategory.addChild(subCategory);
            fillCategoryTree(newParentCategory, subCategory.getSubCategories());
        }
    }

    public Category getRootCategory() {
        return categoryHierarchyService.getRootCategory();
    }

    public void onCategoryHierarchyChanged(@Observes final CategoryHierarchyChangedEventData event) {
        try(final NkTrace trace = NkTrace.info(log, "event: {}", event)) {
            refreshCategories();
        }
    }
}
