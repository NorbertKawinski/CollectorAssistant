package net.kawinski.collecting.presentation.controllers.category;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.category.CategoryService;
import net.kawinski.collecting.service.category.CreateCategoryForm;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class CreateCategoryController {

    private final CategoryService categoryService;

    @Getter
    private final Category parent;

    @Getter
    @Setter
    private CreateCategoryForm form = new CreateCategoryForm();

    @Inject
    public CreateCategoryController(AccessController accessController,
                                    CategoryService categoryService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.categoryService = categoryService;
            accessController.permitAdmin();

            final long parentId = JeeUtils.getRequestParamLongOrThrow("parent");
            parent = categoryService.findByIdOrThrow(parentId, true, true, true, true);
            form.setParentId(parentId);
        }
    }

    public void createNewCategory() {
        try (final NkTrace trace = NkTrace.info(log, "form: {}", form)) {
            Category newCategory = categoryService.create(form);
            Messages.addFlashGlobalInfo("Pomyślnie dodano kategorię '" + form.getName() + "'.");
            JsfRedirectHelper.redirectManageCategory(newCategory);
        }
    }
}

