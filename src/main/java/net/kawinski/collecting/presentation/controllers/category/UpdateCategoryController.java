package net.kawinski.collecting.presentation.controllers.category;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.presentation.controllers.user.LoggedInUser;
import net.kawinski.collecting.service.category.CategoryService;
import net.kawinski.collecting.service.media.MediaService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;
import org.primefaces.model.file.UploadedFile;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class UpdateCategoryController {

    private final LoggedInUser loggedInUser;
    private final MediaService mediaService;
    private final CategoryService categoryService;

    @Getter
    private final Category category;

    @Getter
    @Setter
    private UploadedFile uploadedIcon;

    @Inject
    public UpdateCategoryController(AccessController accessController,
                                    LoggedInUser loggedInUser,
                                    MediaService mediaService,
                                    CategoryService categoryService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.loggedInUser = loggedInUser;
            this.mediaService = mediaService;
            this.categoryService = categoryService;
            accessController.permitAdmin();

            final long categoryId = JeeUtils.getRequestParamLongOrThrow("id");
            category = categoryService.findByIdOrThrow(categoryId, false, false, false, false);
        }
    }

    public void update() {
        try (final NkTrace trace = NkTrace.info(log, "category: {}", category)) {
            if(uploadedIcon.getFileName() != null) {
                final CaUpload upload = mediaService.create(loggedInUser.getLoggedInUserOrThrow(), uploadedIcon.getFileName(), uploadedIcon.getContent());
                category.getImage().setIcon(upload);
            }

            categoryService.update(category);
            Messages.addFlashGlobalInfo("Pomyślnie zaktualizowano kategorię '" + category.getName() + "'.");
            JsfRedirectHelper.redirectManageCategory(category);
        }
    }
}

