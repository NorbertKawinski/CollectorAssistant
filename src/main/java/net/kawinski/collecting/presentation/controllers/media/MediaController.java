package net.kawinski.collecting.presentation.controllers.media;

import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.service.media.MediaService;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class MediaController {

    @Inject
    private MediaService mediaService;

    public String getUrlFor(final String fileName) {
        return mediaService.getUrlFor(fileName);
    }

    public String getUrlFor(final CaUpload upload) {
        return mediaService.getUrlFor(upload);
    }
}
