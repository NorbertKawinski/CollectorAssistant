package net.kawinski.collecting.presentation.controllers.element;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.collection.CollectionService;
import net.kawinski.collecting.service.element.CreateElementForm;
import net.kawinski.collecting.service.element.ElementService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class CreateElementController {

    private final ElementService elementService;

    @Getter
    private final Collection collection;

    @Getter
    @Setter
    private CreateElementForm form = new CreateElementForm();

    @Inject
    public CreateElementController(AccessController accessController,
                                   CollectionService collectionService,
                                   ElementService elementService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.elementService = elementService;

            form.setCollectionId(JeeUtils.getRequestParamLongOrThrow("collection"));
            collection = collectionService.findByIdOrThrow(form.getCollectionId(), false, false);

            JeeUtils.getRequestParamLong("basedOn")
                    .map(baseId -> elementService.findByIdOrThrow(form.getBasedOnId(), false))
                    .ifPresent(base -> {
                        form.setBasedOnId(base.getId());
                        form.setName(base.getName());
                    });

            accessController.permitCanEdit(collection);
        }
    }

    public void create() {
        try (final NkTrace trace = NkTrace.info(log, "form: {}", form)) {
            final Element created = elementService.create(form);
            Messages.addFlashGlobalInfo("Pomyślnie utworzono element '" + form.getName() + "'.");
            JsfRedirectHelper.redirectReadElement(created);
        }
    }
}
