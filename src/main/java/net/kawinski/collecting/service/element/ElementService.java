package net.kawinski.collecting.service.element;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.ElementAttribute;
import net.kawinski.collecting.data.model.IssTask;
import net.kawinski.collecting.data.repository.ElementRepository;
import net.kawinski.collecting.data.search.filters.element.BaseElementFilter;
import net.kawinski.collecting.data.search.filters.element.ElementAttributeFilter;
import net.kawinski.collecting.data.search.filters.element.ElementCategoryFilter;
import net.kawinski.collecting.data.search.filters.element.ElementCategoryNameFilter;
import net.kawinski.collecting.data.search.filters.element.ElementCollectionFilter;
import net.kawinski.collecting.data.search.filters.element.ElementCollectionVisibilityFilter;
import net.kawinski.collecting.data.search.filters.element.ElementNameFilter;
import net.kawinski.collecting.data.search.filters.element.ElementOwnerFilter;
import net.kawinski.collecting.service.category.CategoryHierarchyService;
import net.kawinski.collecting.service.category.CategoryService;
import net.kawinski.collecting.service.collection.CollectionService;
import net.kawinski.collecting.service.imgsearch.ImageSearchResult;
import net.kawinski.collecting.service.imgsearch.ImageSearchService;
import net.kawinski.collecting.service.imgsearch.IssTaskService;
import net.kawinski.collecting.service.search.SearchAttributeForm;
import net.kawinski.collecting.service.search.SearchCollectionVisibility;
import net.kawinski.collecting.service.search.SearchElementForm;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class ElementService {

    @Inject
    private CategoryService categoryService;

    @Inject
    private CategoryHierarchyService categoryHierarchyService;

    @Inject
    private CollectionService collectionService;

    @Inject
    private ElementRepository elementRepository;

    @Inject
    private ImageSearchService imageSearchService;

    @Inject
    private IssTaskService issTaskService;

    public Element create(final CreateElementForm form) {
        try(final NkTrace trace = NkTrace.info(log, "form: {}", form)) {
            final Collection collection = collectionService.findByIdOrThrow(form.getCollectionId(), true, false);

            final Element basedOn = Optional.ofNullable(form.getBasedOnId())
                    .map(id -> elementRepository.findByIdOrThrow(id, true))
                    .orElse(null);

            final Element element = new Element(form.getName(), collection.getCategory(), basedOn);
            if(basedOn != null) {
                if(!collection.getBasedOn().equalsId(basedOn.getPresentIn()))
                    throw new IllegalArgumentException("Requested collectionId(" + collection.getId() + ") does not match base's collectionId(" + basedOn.getPresentIn().getId() + ")");
                element.getImage().setImage(basedOn.getImage().getImage());
                element.getImage().setIcon(basedOn.getImage().getIcon());
                for(final ElementAttribute attribute : basedOn.getAttributes())
                    element.addAttribute(attribute.copyWithoutElement());
            }
            collection.addElement(element);
            elementRepository.persist(element);

            return trace.returning(element);
        }
    }

    public Optional<Element> findById(final long id, final boolean joinAttributes) {
        return elementRepository.findById(id, joinAttributes);
    }

    public Element findByIdOrThrow(final long id, final boolean joinAttributes) {
        return elementRepository.findByIdOrThrow(id, joinAttributes);
    }

    public List<Element> findAfterId(final long id, final int limit) {
        return elementRepository.findAfterId(id, limit);
    }

    public void delete(final Element element) {
        elementRepository.delete(element);
        Optional.ofNullable(element.getImage().getImage())
                .ifPresent(issTaskService::newDeleteTask);
    }

    public void update(final Element element) {
        elementRepository.update(element);
    }

    public PagedResult<Element> findByFilters(final SearchElementForm form, Page page) {
        final List<BaseElementFilter> filters = new ArrayList<>();

        // Search by category ID
        // First of all, we need to narrow the search down to the current category (and subcategories).
        final Category searchRoot = categoryService.findByIdOrThrow(form.getCategoryId(), false, false, false, false);
        final List<Category> categoriesToSearch = categoryHierarchyService.getChildrenOf(searchRoot);
        categoriesToSearch.add(searchRoot);
        final List<Long> categoryIdsToSearch = categoriesToSearch.stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        filters.add(new ElementCategoryFilter(categoryIdsToSearch));

        // Search by name
        Optional.ofNullable(form.getName())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(ElementNameFilter::new)
                .ifPresent(filters::add);

        // Search by owner
        Optional.ofNullable(form.getOwner())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(ElementOwnerFilter::new)
                .ifPresent(filters::add);

        // Search by collection
        Optional.ofNullable(form.getCollection())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(ElementCollectionFilter::new)
                .ifPresent(filters::add);

        // Search by visibility
        Optional.ofNullable(form.getVisibility())
                .flatMap(SearchCollectionVisibility::getModel)
                .map(ElementCollectionVisibilityFilter::new)
                .ifPresent(filters::add);

        // Search by category
        Optional.ofNullable(form.getCategory())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(ElementCategoryNameFilter::new)
                .ifPresent(filters::add);

        // Search by attributes
        for (final SearchAttributeForm saf : form.getAttributes()) {
            filters.add(new ElementAttributeFilter(saf));
        }

        return elementRepository.findByFilters(filters, page);
    }

    public PagedResult<Element> findByFile(final String fileName, final String contentType, final byte[] content, Page page) {
        final List<ImageSearchResult> imgSearchResults = imageSearchService.search(fileName, contentType, content);
        AtomicInteger numResults = new AtomicInteger(imgSearchResults.size());
        List<Element> imgSearchResults2 = imgSearchResults.stream()

//                .skip(page.firstElementIndex())
//                .limit(page.getSize())
//                .map(result -> Long.valueOf(result.getMetadata().getElem_id()))
//                .map(id -> findById(id, false))
//                .filter(Optional::isPresent) // Database can have deleted elements. Let's just ignore them.
//                .map(Optional::get)

                .map(result -> Long.valueOf(result.getMetadata().getImg_id()))
                .flatMap(upload_id -> {
                    List<Element> byUpload = findByUpload(upload_id).collect(Collectors.toList());
                    if(byUpload.size() > 1) {
                        numResults.addAndGet(byUpload.size() - 1);
                    }
                    return byUpload.stream();
                })
                .skip(page.firstElementIndex())
                .limit(page.getSize())

                .collect(Collectors.toList());

//        if(numResults.get() == page.getSize()) {
//            numResults.incrementAndGet();
//        }
        return new PagedResult<>(imgSearchResults2, page, numResults.get());
    }

    private Stream<Element> findByUpload(Long uploadId) {
        return elementRepository.findByUpload(uploadId);
    }

    public PagedResult<Element> findByCollection(Collection collection, Page page) {
        return elementRepository.findByCollection(collection, page);
    }

    public void updateImage(Element element, CaUpload upload) {
        element.getImage().setImage(upload);
        if(upload != null) {
            issTaskService.newSynchronizeTask(upload, element);
        }
    }
}
