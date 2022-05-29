package net.kawinski.collecting.presentation.controllers.search;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.service.attribute.AttributeTarget;
import net.kawinski.collecting.service.element.ElementService;
import net.kawinski.collecting.service.media.MediaService;
import net.kawinski.collecting.service.search.SearchElementForm;
import net.kawinski.collecting.service.search.SearchUploadUser;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;
import net.kawinski.utils.WebClientUtils;
import net.kawinski.utils.java.StringUtils;
import org.primefaces.model.file.NativeUploadedFile;
import org.primefaces.model.file.UploadedFile;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Model
@ViewScoped
@Slf4j
public class SearchElementsController extends SearchController<SearchElementForm, Element> implements Serializable {

    @Inject
    private ElementService elementService;

    @Inject
    private MediaService mediaService;

    @Inject
    private SearchUploadUser searchUploadUser;

    @Getter
    @Setter
    private UploadedFile searchImage = new NativeUploadedFile();

    public SearchElementsController() {
        super(SearchElementForm.class, AttributeTarget.Element);
    }

    @Override
    public void updateAttributes(SearchElementForm form, List<SearchAttributeFormJsf> attributesToSearch) {
        super.updateAttributes(form, attributesToSearch);

        if (searchImage.getFileName() != null) {
            final CaUpload upload = mediaService.create(searchUploadUser.getUser(), searchImage.getFileName(), searchImage.getContent());
            form.setImageFilename(upload.getFileName());
        }
    }

    @Override
    protected PagedResult<Element> findResults(Page page) throws IOException {
        PagedResult<Element> results;
        if (!StringUtils.isNullOrWhitespace(getForm().getImageFilename())) {
            String imageFilename = getForm().getImageFilename();
            String imageContentType = WebClientUtils.fromName(imageFilename).getMimeType();
            byte[] imageContent = mediaService.getUploadContent(imageFilename);
            results = elementService.findByFile(imageFilename, imageContentType, imageContent, page);
        } else {
            results = elementService.findByFilters(getForm(), page);
        }
        return accessController.filterCanSeeElements(results);
    }
}
