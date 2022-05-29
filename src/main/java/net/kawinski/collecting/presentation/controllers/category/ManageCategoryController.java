package net.kawinski.collecting.presentation.controllers.category;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.category.CategoryService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class ManageCategoryController {

    @Getter
    private final Category category;

    @Inject
    public ManageCategoryController(AccessController accessController, CategoryService categoryService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            accessController.permitAdmin();

            final long categoryId = JeeUtils.getRequestParamLongOrThrow("id");
            category = categoryService.findByIdOrThrow(categoryId, true, false, true, true);
        }
    }
}

