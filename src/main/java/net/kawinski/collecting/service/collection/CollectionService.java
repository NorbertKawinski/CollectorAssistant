package net.kawinski.collecting.service.collection;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.CollectionAttribute;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.ImageModel;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.data.repository.CollectionRepository;
import net.kawinski.collecting.data.search.filters.collection.BaseCollectionFilter;
import net.kawinski.collecting.data.search.filters.collection.CollectionAttributeFilter;
import net.kawinski.collecting.data.search.filters.collection.CollectionCategoryFilter;
import net.kawinski.collecting.data.search.filters.collection.CollectionCategoryNameFilter;
import net.kawinski.collecting.data.search.filters.collection.CollectionNameFilter;
import net.kawinski.collecting.data.search.filters.collection.CollectionOwnerFilter;
import net.kawinski.collecting.data.search.filters.collection.CollectionVisibilityFilter;
import net.kawinski.collecting.service.category.CategoryHierarchyService;
import net.kawinski.collecting.service.category.CategoryService;
import net.kawinski.collecting.service.search.SearchAttributeForm;
import net.kawinski.collecting.service.search.SearchCollectionForm;
import net.kawinski.collecting.service.search.SearchCollectionVisibility;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class CollectionService implements Serializable {

    @Inject
    private CategoryService categoryService;

    @Inject
    private CategoryHierarchyService categoryHierarchyService;

    @Inject
    private CollectionRepository collectionRepository;

    public Collection create(final User owner, final CreateCollectionForm form) {
        try(final NkTrace trace = NkTrace.info(log, "form: {}", form)) {

            final Collection basedOn = Optional.ofNullable(form.getBasedOnId())
                    .map(catId -> collectionRepository.findByIdOrThrow(catId, false, true))
                    .orElse(null);

            final Category category = categoryService.findByIdOrThrow(form.getCategoryId(), false, false, false, false);
            if(!category.isCanContainCollections())
                throw new IllegalArgumentException("Category " + category + " cannot contain collections");

            final Collection collection = new Collection(form.getName(), owner, category, basedOn, form.isCatalog());
            if(basedOn != null) {
//                if(!category.equalsId(basedOn.getCategory()))
//                    throw new IllegalArgumentException("Requested categoryId(" + category.getId() + ") does not match catalog's categoryId(" + basedOn.getCategory().getId() + ")");
                collection.setImage(new ImageModel(basedOn.getImage()));
                for(final CollectionAttribute attribute : basedOn.getAttributes())
                    collection.addAttribute(attribute.copyWithoutCollection());
            }
            collectionRepository.persist(collection);

            return trace.returning(collection);
        }
    }

    public Optional<Collection> findById(final long id, final boolean joinElements, final boolean joinAttributes) {
        return collectionRepository.findById(id, joinElements, joinAttributes);
    }

    public Collection findByIdOrThrow(final long id, final boolean joinElements, final boolean joinAttributes) {
        return collectionRepository.findByIdOrThrow(id, joinElements, joinAttributes);
    }

    public PagedResult<Collection> findByFilters(final SearchCollectionForm form, Page page) {
        final List<BaseCollectionFilter> filters = new ArrayList<>();

        // Search by category ID
        // First of all, we need to narrow the search down to the current category (and subcategories).
        final Category searchRoot = categoryService.findByIdOrThrow(form.getCategoryId(), false, false, false, false);
        final List<Category> categoriesToSearch = categoryHierarchyService.getChildrenOf(searchRoot);
        categoriesToSearch.add(searchRoot);
        final List<Long> categoryIdsToSearch = categoriesToSearch.stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        filters.add(new CollectionCategoryFilter(categoryIdsToSearch));

        // Search by name
        Optional.ofNullable(form.getName())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(CollectionNameFilter::new)
                .ifPresent(filters::add);

        // Search by owner
        Optional.ofNullable(form.getOwner())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(CollectionOwnerFilter::new)
                .ifPresent(filters::add);

        // Search by category
        Optional.ofNullable(form.getCategory())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(CollectionCategoryNameFilter::new)
                .ifPresent(filters::add);

        // Search by visibility
        Optional.ofNullable(form.getVisibility())
                .flatMap(SearchCollectionVisibility::getModel)
                .map(CollectionVisibilityFilter::new)
                .ifPresent(filters::add);

        // Search by attributes
        for(final SearchAttributeForm saf : form.getAttributes()) {
            filters.add(new CollectionAttributeFilter(saf));
        }

        return collectionRepository.findByFilters(filters, page);
    }

    public PagedResult<Collection> findByCategory(final Category category, Page page) {
        return collectionRepository.findByCategory(category, page);
    }

    public List<Element> getMissingElements(final Collection collection) {
        final Collection baseCollection = collection.getBasedOn();
        final Set<Element> presentElements = collection.getElements();
        return collectionRepository.getMissingElements(baseCollection, presentElements);
    }

    public void delete(final Collection collection) {
        collectionRepository.delete(collection);
    }

    public void update(final Collection collection) {
        collectionRepository.update(collection);
    }
}
