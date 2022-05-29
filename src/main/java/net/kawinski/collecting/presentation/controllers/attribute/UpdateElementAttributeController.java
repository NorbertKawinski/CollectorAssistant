package net.kawinski.collecting.presentation.controllers.attribute;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.ElementAttribute;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.attribute.ElementAttributeService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.text.ParseException;

@Model
@Slf4j
public class UpdateElementAttributeController {

    private final ElementAttributeService elementAttributeService;

    @Getter
    private final EditAttributeBaseController editAttributeBaseController;

    @Getter
    private final ElementAttribute attribute;

    @Inject
    public UpdateElementAttributeController(AccessController accessController,
                                            ElementAttributeService elementAttributeService,
                                            EditAttributeBaseController editAttributeBaseController) throws ParseException {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.elementAttributeService = elementAttributeService;
            this.editAttributeBaseController = editAttributeBaseController;

            final long attributeId = JeeUtils.getRequestParamLongOrThrow("id");
            attribute = elementAttributeService.findByIdOrThrow(attributeId);
            editAttributeBaseController.setValue(attribute.getValue());

            accessController.permitCanEdit(attribute);
        }
    }

    public void update() {
        try (final NkTrace ignored = NkTrace.info(log)) {
            final String newValue = editAttributeBaseController.convertToRawValue(attribute.getAttributeTemplate().getType());
            attribute.getValue().setRaw(newValue);
            elementAttributeService.update(attribute);
            Messages.addFlashGlobalInfo("Pomyślnie zaktualizowano atrybut");
            JsfRedirectHelper.redirectReadElementAttribute(attribute);
        }
    }
}
