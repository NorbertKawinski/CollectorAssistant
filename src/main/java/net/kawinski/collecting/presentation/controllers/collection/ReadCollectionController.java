package net.kawinski.collecting.presentation.controllers.collection;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.Resources;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.category.CategoryService;
import net.kawinski.collecting.service.collection.CollectionService;
import net.kawinski.collecting.service.element.ElementService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;
import net.kawinski.utils.jee.JeeUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model
@Slf4j
public class ReadCollectionController {

    @Getter
    private final Collection collection;

    @Getter
    private final PagedResult<Element> elements;

    @Getter
    private final List<CollectionAttributeTemplate> attributeTemplates;

    @Getter
    @Setter
    private long selectedAddAttributeTemplateId = 0L;

    @Getter
    private List<Element> missingElements = new ArrayList<>();

    @Inject
    public ReadCollectionController(AccessController accessController,
                                    CollectionService collectionService,
                                    CategoryService categoryService,
                                    ElementService elementService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            final long collectionId = JeeUtils.getRequestParamLongOrThrow("id");
            int page = JeeUtils.getRequestParamInt("page").orElse(1);
            collection = collectionService.findByIdOrThrow(collectionId, true, true);
            elements = elementService.findByCollection(collection, new Page(page, Resources.DEFAULT_PAGE_SIZE));
            attributeTemplates = categoryService.getCollectionAttributeTemplates(collection.getCategory());
            if(collection.containsBase())
                missingElements = collectionService.getMissingElements(collection);

            accessController.permitCanSee(collection);
        }
    }
}
