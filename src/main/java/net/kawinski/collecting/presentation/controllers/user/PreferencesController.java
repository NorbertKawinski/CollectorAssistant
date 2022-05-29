package net.kawinski.collecting.presentation.controllers.user;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.Credentials;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.media.MediaService;
import net.kawinski.collecting.service.user.UserService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;
import org.primefaces.model.file.UploadedFile;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class PreferencesController {

    private final MediaService mediaService;
    private final UserService userService;

    @Getter
    private final User user;

    @Getter
    @Setter
    private String newEmail;

    @Getter
    @Setter
    private String oldPassword;

    @Getter
    @Setter
    private String newPassword;

    @Getter
    @Setter
    private UploadedFile uploadedImage;

    @Getter
    @Setter
    private UploadedFile uploadedIcon;

    @Inject
    public PreferencesController(AccessController accessController,
                                 MediaService mediaService,
                                 UserService userService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.mediaService = mediaService;
            this.userService = userService;

            final long userId = JeeUtils.getRequestParamLongOrThrow("id");
            user = userService.findByIdOrThrow(userId, true, true);
            newEmail = user.getEmail();

            accessController.permitCanEdit(user);
        }
    }

    public void update() {
        try (final NkTrace trace = NkTrace.info(log)) {
            // TODO: Move such validation to business layer
            final boolean emailChanged = newEmail != null && !newEmail.isEmpty() && !newEmail.equalsIgnoreCase(user.getEmail());
            final boolean passChanged = newPassword != null && !newPassword.isEmpty();
            final boolean passVerificationRequired = emailChanged || passChanged;
            if(passVerificationRequired) {
                if(!user.getCredentials().isValid(oldPassword)) {
                    throw new RuntimeException("Invalid old password");
                }
                if(emailChanged) {
                    user.setEmail(newEmail);
                    user.setEmailVerified(false);
                }
                if(passChanged) {
                    user.setCredentials(new Credentials(newPassword));
                }
            }

            if(uploadedImage.getFileName() != null) {
                final CaUpload upload = mediaService.create(user, uploadedImage.getFileName(), uploadedImage.getContent());
                user.getImage().setImage(upload);
            }
            if(uploadedIcon.getFileName() != null) {
                final CaUpload upload = mediaService.create(user, uploadedIcon.getFileName(), uploadedIcon.getContent());
                user.getImage().setIcon(upload);
            }

            userService.update(user);
            Messages.addFlashGlobalInfo("Pomyślnie zaktualizowano profil");
        }
    }
}
