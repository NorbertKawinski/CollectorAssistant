package net.kawinski.collecting.presentation.controllers.attribute.template;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.attribute.ElementAttributeTemplateService;
import net.kawinski.collecting.service.common.DeleteByIdForm;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class DeleteElementAttributeTemplateController {

    private final ElementAttributeTemplateService elementAttributeTemplateService;

    @Getter
    private final ElementAttributeTemplate attribute;

    @Getter
    @Setter
    private DeleteByIdForm form = new DeleteByIdForm();

    @Inject
    public DeleteElementAttributeTemplateController(AccessController accessController,
                                                    ElementAttributeTemplateService elementAttributeTemplateService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.elementAttributeTemplateService = elementAttributeTemplateService;
            accessController.permitAdmin();

            form.setId(JeeUtils.getRequestParamLongOrThrow("id"));
            attribute = elementAttributeTemplateService.findByIdOrThrow(form.getId());
            trace.setExitMsg("form: {}, attribute: {}", form, attribute);
        }
    }

    public void confirm() {
        try (final NkTrace trace = NkTrace.info(log)) {
            elementAttributeTemplateService.delete(attribute);

            Messages.addFlashGlobalInfo("Pomyślnie usunięto szablon atrybutu elementu '" + attribute.getName() + "'.");
            JsfRedirectHelper.redirectManageCategory(attribute.getCategory());
        }
    }
}
