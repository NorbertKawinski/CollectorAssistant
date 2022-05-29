package net.kawinski.collecting.presentation.controllers.attribute;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.ElementAttribute;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.attribute.ElementAttributeService;
import net.kawinski.collecting.service.element.ElementService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class CreateElementAttributeController {

    private final ElementAttributeService elementAttributeService;

    @Getter
    private final ElementAttributeTemplate attributeTemplate;

    @Getter
    private final Element element;

    @Getter
    private final EditAttributeBaseController editAttributeBaseController;

    @Inject
    public CreateElementAttributeController(AccessController accessController,
                                            ElementService elementService,
                                            ElementAttributeService elementAttributeService,
                                            EditAttributeBaseController editAttributeBaseController) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.elementAttributeService = elementAttributeService;
            this.editAttributeBaseController = editAttributeBaseController;

            final long elementId = JeeUtils.getRequestParamLongOrThrow("element");
            final long templateId = JeeUtils.getRequestParamLongOrThrow("template");
            element = elementService.findByIdOrThrow(elementId, false);
            attributeTemplate = elementAttributeService.findTemplateByIdOrThrow(templateId);

            accessController.permitCanEdit(element);
        }
    }

    public void create() {
        try (final NkTrace trace = NkTrace.info(log)) {
            final String rawValue = editAttributeBaseController.convertToRawValue(attributeTemplate.getType());
            final ElementAttribute elementAttribute = new ElementAttribute(element, attributeTemplate, rawValue);
            elementAttributeService.create(elementAttribute);

            Messages.addFlashGlobalInfo("Pomyślnie dodano atrybut '" + attributeTemplate.getName() + "'.");
            JsfRedirectHelper.redirectReadElement(element);
        }
    }
}
