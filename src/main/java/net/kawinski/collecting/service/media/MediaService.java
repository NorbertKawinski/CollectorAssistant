package net.kawinski.collecting.service.media;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.Resources;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.data.repository.CaUploadRepository;
import net.kawinski.collecting.service.user.UserService;
import net.kawinski.logging.NkTrace;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class MediaService {
    // TODO: Configurable (settings.properties? / database? / ???)
    public static final String UPLOADS_DIR = Resources.PROD ? "/ca_uploads" : "uploads";

    @Inject
    private CaUploadRepository caUploadRepository;

    @Inject
    private UserService userService;

    public CaUpload create(final User user, final String filename, final byte[] content) {
        return create(new CreateCaUploadForm(user, filename, content));
    }

    public CaUpload create(final CreateCaUploadForm form) {
        try(final NkTrace trace = NkTrace.info(log, "form: {}", form)) {
            final User owner = userService.findByIdOrThrow(form.getOwnerId(), false, false);
            final CaUpload upload = new CaUpload(owner, form.getFileExtension());
            caUploadRepository.persist(upload);

            // We're doing it after persisting to database to avoid dangling (unowned) files in the system.
            // In case file writing fails, the thrown exception will rollback the transaction.
            saveFile(upload, form.getContent());

            return trace.returning(upload);
        } catch (final IOException ex) {
            // Checked exceptions won't trigger rollback
            throw new RuntimeException(ex);
        }
    }

    public Optional<CaUpload> findById(Long id) {
        return caUploadRepository.findById(id);
    }

    private Path getUploadPath(final CaUpload upload) {
        return getUploadPath(upload.getFileName());
    }

    private Path getUploadPath(final String uploadFilename) {
        return Paths.get(UPLOADS_DIR, uploadFilename);
    }

    public File getUploadFile(final CaUpload upload) {
        return getUploadPath(upload).toFile();
    }

    private void saveFile(final CaUpload upload, @NotNull final byte[] content) throws IOException {
        Files.createDirectories(Paths.get(UPLOADS_DIR));

//        Files.write(path, content, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        // For debugging we quite often nuke the table resetting ids back to 1.
        // I don't want to manually delete these files so temporarily I enabled overwriting
        Files.write(getUploadPath(upload), content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    public byte[] getUploadContent(final String uploadFilename) throws IOException {
        final boolean filenameValid = uploadFilename.matches("^[a-zA-Z0-9._]+$");
        if(!filenameValid)
            throw new IllegalArgumentException("Invalid upload filename: " + uploadFilename);
        final Path uploadPath = getUploadPath(uploadFilename);
        return Files.readAllBytes(uploadPath);
    }

//    public String getUrlFor(final ImageModel image) {
//        return getUrlFor(image.getIconOrImage().getFileName());
//    }

    public String getUrlFor(final CaUpload upload) {
        return getUrlFor(upload.getFileName());
    }

    public String getUrlFor(final String fileName) {
        if(fileName == null)
            return "/resources/gfx/icons/error_16.png";
        return "/uploads?file=" + fileName;
    }


}
