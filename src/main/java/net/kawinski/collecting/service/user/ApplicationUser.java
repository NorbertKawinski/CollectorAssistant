package net.kawinski.collecting.service.user;

import lombok.Getter;
import net.kawinski.collecting.data.model.User;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.UUID;

// Suppressed CdiManagedBeanInconsistencyInspection because this is abstract class, not a bean
@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
public abstract class ApplicationUser implements Serializable {

    private final String username;
    private final String email;

    @Inject
    private UserService userService;

    @Getter
    private User user;

    public ApplicationUser(String username) {
        this.username = username;
        this.email = username + "@ca.kawinski.net";
    }

    @PostConstruct
    public void registerIfNeeded() {
        user = userService.findByNameOrEmail(email)
                .orElseGet(this::register);
    }

    private User register() {
        String password = UUID.randomUUID().toString();
        User user = userService.register(new UserRegistrationForm(username, email, password, password, true));
        user.setEmailVerified(true);
        userService.update(user);
        return user;
    }

}
