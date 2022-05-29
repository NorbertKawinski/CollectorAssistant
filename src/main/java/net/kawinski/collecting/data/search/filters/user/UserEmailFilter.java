package net.kawinski.collecting.data.search.filters.user;

import net.kawinski.collecting.data.model.User_;
import net.kawinski.collecting.data.search.UserSearchQuery;

import javax.persistence.criteria.Predicate;

public class UserEmailFilter implements BaseUserFilter {
    private final String expectedValue;

    public UserEmailFilter(final String expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Override
    public Predicate createPredicateFor(final UserSearchQuery query) {
        // TODO: Escape % and _ (and other characters too)
        return query.cb.like(query.user.get(User_.EMAIL), "%" + expectedValue + "%");
    }
}
