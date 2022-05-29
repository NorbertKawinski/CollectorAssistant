package net.kawinski.collecting.data.search;

import net.kawinski.collecting.data.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

public class UserSearchQuery extends BaseSearchQuery {

    public final Root<User> user;

    public UserSearchQuery(final CriteriaBuilder cb, final Root<User> user) {
        super(cb);
        this.user = user;
    }
}
