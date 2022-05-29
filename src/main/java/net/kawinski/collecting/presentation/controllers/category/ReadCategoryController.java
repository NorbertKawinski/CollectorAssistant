package net.kawinski.collecting.presentation.controllers.category;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.Resources;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.category.CategoryService;
import net.kawinski.collecting.service.collection.CollectionService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;
import net.kawinski.utils.jee.JeeUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Model
@Slf4j
public class ReadCategoryController {

    @Getter
    private final Category category;

    @Getter
    private final PagedResult<Collection> collections;

    @Inject
    public ReadCategoryController(AccessController accessController,
                                  CategoryService categoryService,
                                  CollectionService collectionService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            long categoryId = JeeUtils.getRequestParamLongOrThrow("id");
            int page = JeeUtils.getRequestParamInt("page").orElse(1);
            category = categoryService.findByIdOrThrow(categoryId, true, true, false, false);
            collections = collectionService.findByCategory(category, new Page(page, Resources.DEFAULT_PAGE_SIZE));
            List<Integer> neighborPages = collections.getNeighborPages();
            trace.returning(collections + " " + neighborPages);
            accessController.permitCanSeeCollections(category.getCollections());
        }
    }
}
