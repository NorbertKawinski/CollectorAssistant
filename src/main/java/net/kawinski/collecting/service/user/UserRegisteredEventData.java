package net.kawinski.collecting.service.user;

import net.kawinski.collecting.data.model.User;

public class UserRegisteredEventData {
    private final User user;

    public UserRegisteredEventData(final User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
