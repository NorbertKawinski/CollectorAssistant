package net.kawinski.collecting.service.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.User;
import net.kawinski.utils.java.JavaUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCaUploadForm {

    @NotNull
    private Long ownerId;

    @NotNull
    @ToString.Exclude
    private byte[] content;

    @NotNull
    @Size(min = CaUpload.MIN_EXTENSION_LENGTH, max = CaUpload.MAX_EXTENSION_LENGTH)
    private String fileExtension;

    public CreateCaUploadForm(final User user, final String fileName, final byte[] content) {
        this(user.getId(), content, JavaUtils.getExtensionOrThrow(fileName));
    }

}