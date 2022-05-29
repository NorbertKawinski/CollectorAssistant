package net.kawinski.collecting.data.search.filters.user;

import net.kawinski.collecting.data.model.User_;
import net.kawinski.collecting.data.search.UserSearchQuery;

import javax.persistence.criteria.Predicate;

public class UserEmailVerifiedFilter implements BaseUserFilter {
    private final boolean expectedValue;

    public UserEmailVerifiedFilter(final boolean expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Override
    public Predicate createPredicateFor(final UserSearchQuery query) {
        // TODO: Escape % and _ (and other characters too)
        return query.cb.equal(query.user.get(User_.EMAIL_VERIFIED), expectedValue);
    }
}
