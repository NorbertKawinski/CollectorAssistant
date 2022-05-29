package net.kawinski.collecting.presentation.controllers.collection;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.collection.CollectionService;
import net.kawinski.collecting.service.media.MediaService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;
import org.primefaces.model.file.UploadedFile;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class UpdateCollectionController {

    private final CollectionService collectionService;
    private final MediaService mediaService;

    @Getter
    private final Collection collection;

    @Getter
    @Setter
    private UploadedFile uploadedImage;

    @Getter
    @Setter
    private UploadedFile uploadedIcon;

    @Inject
    public UpdateCollectionController(AccessController accessController,
                                      CollectionService collectionService,
                                      MediaService mediaService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.collectionService = collectionService;
            this.mediaService = mediaService;

            final long collectionId = JeeUtils.getRequestParamLongOrThrow("id");
            collection = collectionService.findByIdOrThrow(collectionId, true, true);

            accessController.permitCanEdit(collection);
        }
    }

    public void update() {
        try (final NkTrace ignored = NkTrace.info(log)) {

            if(uploadedImage.getFileName() != null) {
                final CaUpload upload = mediaService.create(collection.getOwner(), uploadedImage.getFileName(), uploadedImage.getContent());
                collection.getImage().setImage(upload);
            }
            if(uploadedIcon.getFileName() != null) {
                final CaUpload upload = mediaService.create(collection.getOwner(), uploadedIcon.getFileName(), uploadedIcon.getContent());
                collection.getImage().setIcon(upload);
            }

            collectionService.update(collection);
            Messages.addFlashGlobalInfo("Pomyślnie zaktualizowano kolekcję");
            JsfRedirectHelper.redirectReadCollection(collection);
        }
    }
}
