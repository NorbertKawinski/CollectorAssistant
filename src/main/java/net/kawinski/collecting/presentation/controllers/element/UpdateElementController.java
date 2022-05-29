package net.kawinski.collecting.presentation.controllers.element;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.element.ElementService;
import net.kawinski.collecting.service.media.MediaService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;
import org.primefaces.model.file.UploadedFile;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class UpdateElementController {
    private final ElementService elementService;
    private final MediaService mediaService;

    @Getter
    private final Element element;

    @Getter
    @Setter
    private UploadedFile uploadedImage;

    @Getter
    @Setter
    private UploadedFile uploadedIcon;

    @Inject
    public UpdateElementController(AccessController accessController,
                                   ElementService elementService,
                                   MediaService mediaService) {
        try (final NkTrace ignored = NkTrace.info(log)) {
            this.elementService = elementService;
            this.mediaService = mediaService;

            final long elementId = JeeUtils.getRequestParamLongOrThrow("id");
            element = elementService.findByIdOrThrow(elementId, false);

            accessController.permitCanEdit(element);
        }
    }

    public void updateElement() {
        try (final NkTrace ignored = NkTrace.info(log)) {
            if(uploadedImage.getFileName() != null) {
                final CaUpload upload = mediaService.create(element.getPresentIn().getOwner(), uploadedImage.getFileName(), uploadedImage.getContent());
                elementService.updateImage(element, upload);
            }
            if(uploadedIcon.getFileName() != null) {
                final CaUpload upload = mediaService.create(element.getPresentIn().getOwner(), uploadedIcon.getFileName(), uploadedIcon.getContent());
                element.getImage().setIcon(upload);
            }

            elementService.update(element);
            Messages.addFlashGlobalInfo("Pomyślnie zaktualizowano element");
            JsfRedirectHelper.redirectReadElement(element);
        }
    }
}
