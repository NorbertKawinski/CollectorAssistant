package net.kawinski.collecting.service.auth;

import net.kawinski.collecting.data.model.User;

import javax.security.enterprise.CallerPrincipal;

/**
 * This principal is passed as authenticated user to the security API.
 * Using this class makes it easier to later retrieve the user back as opposed to keeping the user's login only
 */
public class CaCallerPrincipal extends CallerPrincipal {
    private final User user;

    public CaCallerPrincipal(final User user) {
        // We're passing user's ID instead of login/email.
        // Since users can change login/email, using IDs avoids principal names becoming outdated.
        super(user.getId().toString());
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
