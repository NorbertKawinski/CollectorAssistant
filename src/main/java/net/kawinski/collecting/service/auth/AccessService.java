package net.kawinski.collecting.service.auth;

import net.kawinski.collecting.data.model.Group;
import net.kawinski.collecting.data.model.User;

import java.io.Serializable;

public class AccessService implements Serializable {

    public boolean isAdmin(User user) {
        return user.isInGroup(Group.ADMIN);
    }

}
