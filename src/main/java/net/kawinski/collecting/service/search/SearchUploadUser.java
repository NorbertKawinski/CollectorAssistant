package net.kawinski.collecting.service.search;

import lombok.Getter;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.service.user.ApplicationUser;
import net.kawinski.collecting.service.user.UserRegistrationForm;
import net.kawinski.collecting.service.user.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.UUID;

@ApplicationScoped
public class SearchUploadUser extends ApplicationUser {

    private static final long serialVersionUID = 1L;

    public SearchUploadUser() {
        super("ca_search_upload_user");
    }
}
