package net.kawinski.collecting.presentation.controllers.attribute.template;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.attribute.AttributeService;
import net.kawinski.collecting.service.attribute.CreateAttributeTemplateForm;
import net.kawinski.collecting.service.category.CategoryService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class CreateAttributeTemplateController {

    private final AttributeService attributeService;

    @Getter
    private final Category category;

    @Getter
    @Setter
    private CreateAttributeTemplateForm form = new CreateAttributeTemplateForm();

    @Inject
    public CreateAttributeTemplateController(AccessController accessController,
                                             CategoryService categoryService,
                                             AttributeService attributeService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.attributeService = attributeService;
            accessController.permitAdmin();

            final long categoryId = JeeUtils.getRequestParamLongOrThrow("category");
            category = categoryService.findByIdOrThrow(categoryId, true, true, true, true);
            form.setCategoryId(categoryId);
        }
    }

    public void createNewAttributeTemplate() {
        try (final NkTrace trace = NkTrace.info(log, "form: {}", form)) {
            attributeService.create(form);
            Messages.addFlashGlobalInfo("Pomyślnie dodano atrybut '" + form.getName() + "'.");
            JsfRedirectHelper.redirectManageCategory(category);
        }
    }
}

