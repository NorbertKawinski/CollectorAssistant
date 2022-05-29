package net.kawinski.collecting.startup.helpers.base;

import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.service.media.CreateCaUploadForm;
import net.kawinski.collecting.startup.helpers.StartupInitializerBean;
import net.kawinski.collecting.startup.helpers.model.CatRoot;
import net.kawinski.collecting.startup.helpers.model.StartupCA;
import net.kawinski.utils.java.JavaUtils;

import java.util.Random;

public abstract class StartupObject {

    public static Random random = new Random();
    public static StartupInitializerBean srv = null;
    public static StartupCA ca = null;
    public static CatRoot root = null;

    public static CaUpload newUpload(final User owner, final String path_arg) {
        final String path = "uploads/" + path_arg;
        final byte[] uploadContent = JavaUtils.readCaResource(path);
        final String uploadExt = JavaUtils.getExtensionByStringHandling(path)
                .orElseThrow(() -> new RuntimeException("Cannot find resource at: " + path));
        final CreateCaUploadForm form = new CreateCaUploadForm(owner.getId(), uploadContent, uploadExt);
        return srv.getMediaService().create(form);
    }
}
