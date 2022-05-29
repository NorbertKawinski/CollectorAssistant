package net.kawinski.collecting.presentation.controllers.attribute.template;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.attribute.ElementAttributeTemplateService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class UpdateElementAttributeTemplateController {

    private final ElementAttributeTemplateService elementAttributeTemplateService;

    @Getter
    private final ElementAttributeTemplate attribute;

    @Inject
    public UpdateElementAttributeTemplateController(AccessController accessController,
                                                    ElementAttributeTemplateService elementAttributeTemplateService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.elementAttributeTemplateService = elementAttributeTemplateService;
            accessController.permitAdmin();

            final long attributeId = JeeUtils.getRequestParamLongOrThrow("id");
            attribute = elementAttributeTemplateService.findByIdOrThrow(attributeId);
        }
    }

    public void update() {
        try (final NkTrace ignored = NkTrace.info(log)) {
            elementAttributeTemplateService.update(attribute);
            Messages.addFlashGlobalInfo("Pomyślnie zaktualizowano atrybut");
            JsfRedirectHelper.redirectReadElementAttributeTemplate(attribute);
        }
    }
}
