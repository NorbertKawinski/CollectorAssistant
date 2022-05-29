package net.kawinski.collecting.presentation.controllers.attribute;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.ElementAttribute;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.attribute.ElementAttributeService;
import net.kawinski.collecting.service.common.DeleteByIdForm;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class DeleteElementAttributeController {

    private final ElementAttributeService elementAttributeService;

    @Getter
    private final ElementAttribute attribute;

    @Getter
    @Setter
    private DeleteByIdForm form = new DeleteByIdForm();

    @Inject
    public DeleteElementAttributeController(AccessController accessController, ElementAttributeService elementAttributeService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.elementAttributeService = elementAttributeService;

            form.setId(JeeUtils.getRequestParamLongOrThrow("id"));
            attribute = elementAttributeService.findByIdOrThrow(form.getId());

            accessController.permitCanEdit(attribute);
            trace.setExitMsg("form: {}, attribute: {}", form, attribute);
        }
    }

    public void confirm() {
        try (final NkTrace trace = NkTrace.info(log)) {
            elementAttributeService.delete(attribute);

            Messages.addFlashGlobalInfo("Pomyślnie usunięto atrybut '" + attribute.getAttributeTemplate().getName() + "'.");
            JsfRedirectHelper.redirectReadElement(attribute.getElement());
        }
    }
}
