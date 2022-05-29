package net.kawinski.collecting.presentation.controllers.collection;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.collection.CollectionService;
import net.kawinski.collecting.service.common.DeleteByIdForm;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class DeleteCollectionController {

    private final CollectionService collectionService;

    @Getter
    private final Collection collection;

    @Getter
    @Setter
    private DeleteByIdForm form = new DeleteByIdForm();

    @Inject
    public DeleteCollectionController(AccessController accessController,
                                      CollectionService collectionService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.collectionService = collectionService;

            form.setId(JeeUtils.getRequestParamLongOrThrow("id"));
            collection = collectionService.findByIdOrThrow(form.getId(), false, false);

            accessController.permitCanEdit(collection);
            trace.setExitMsg("form: {}, collection: {}", form, collection);
        }
    }

    public void confirm() {
        try (final NkTrace trace = NkTrace.info(log)) {
            collectionService.delete(collection);

            Messages.addFlashGlobalInfo("Pomyślnie usunięto kolekcję '" + collection.getName() + "'.");
            JsfRedirectHelper.redirectIndex();
        }
    }
}
