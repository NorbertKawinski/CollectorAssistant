package net.kawinski.collecting.presentation.controllers.element;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.common.DeleteByIdForm;
import net.kawinski.collecting.service.element.ElementService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class DeleteElementController {

    private final ElementService elementService;

    @Getter
    private final Element element;

    @Getter
    @Setter
    private DeleteByIdForm form = new DeleteByIdForm();

    @Inject
    public DeleteElementController(AccessController accessController,
                                   ElementService elementService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.elementService = elementService;

            form.setId(JeeUtils.getRequestParamLongOrThrow("id"));
            element = elementService.findByIdOrThrow(form.getId(), false);

            trace.setExitMsg("form: {}, element: {}", form, element);
            accessController.permitCanEdit(element);
        }
    }

    public void confirm() {
        try (final NkTrace trace = NkTrace.info(log)) {
            elementService.delete(element);

            Messages.addFlashGlobalInfo("Pomyślnie usunięto element '" + element.getName() + "'.");
            JsfRedirectHelper.redirectReadCollection(element.getPresentIn());
        }
    }
}
