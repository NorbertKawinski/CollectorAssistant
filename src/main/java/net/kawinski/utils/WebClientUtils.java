package net.kawinski.utils;

import lombok.experimental.UtilityClass;
import org.apache.http.entity.ContentType;

import java.io.File;

@UtilityClass
public class WebClientUtils {


    public static ContentType fromFile(final File imageFile) {
        return fromName(imageFile.getName());
    }

    public static ContentType fromName(final String name) {
        if(name.endsWith("jpg") || name.endsWith("jpeg"))
            return ContentType.IMAGE_JPEG;
        if(name.endsWith("png"))
            return ContentType.IMAGE_PNG;
        return ContentType.APPLICATION_OCTET_STREAM;
    }

    public static ContentType getContentType(String mimeType) {
        return ContentType.getByMimeType(mimeType);
    }
}
