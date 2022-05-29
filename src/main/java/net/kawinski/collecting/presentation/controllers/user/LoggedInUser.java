package net.kawinski.collecting.presentation.controllers.user;

import net.kawinski.collecting.data.model.Group;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.service.auth.CaCallerPrincipal;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.Optional;

@Named
@SessionScoped
public class LoggedInUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SecurityContext securityContext;

    /**
     * We can cache logged user here as this bean is session scoped
     * and so this bean will be dropped when logged-out (invalidated session during logout)
     */
    private User loggedInUser = null;

    public Optional<User> getLoggedInUser() {
        if (loggedInUser == null) {
            loggedInUser = securityContext
                    .getPrincipalsByType(CaCallerPrincipal.class).stream()
                    .map(CaCallerPrincipal::getUser)
                    .findAny().orElse(null);
        }
        return Optional.ofNullable(loggedInUser);
    }

    public User getLoggedInUserOrThrow() {
        return getLoggedInUser()
                .orElseThrow(() -> new IllegalStateException("Not logged in"));
    }

    public boolean isLoggedIn() {
        return getLoggedInUser().isPresent();
    }

    public Long getId() {
        return getLoggedInUser()
                .map(User::getId)
                .orElse(0L);
    }

    public String getName() {
        return getLoggedInUser()
                .map(User::getName)
                .orElse("Guest");
    }

    public String getEmail() {
        return getLoggedInUser()
                .map(User::getEmail)
                .orElse("guest@ca.kawinski.net");
    }

    public boolean is(final Group group) {
        return getLoggedInUser()
                .map(user -> user.isInGroup(group))
                .orElse(false);
    }
}
