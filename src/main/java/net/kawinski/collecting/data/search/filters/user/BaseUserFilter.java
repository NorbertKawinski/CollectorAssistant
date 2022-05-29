package net.kawinski.collecting.data.search.filters.user;

import net.kawinski.collecting.data.search.UserSearchQuery;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface BaseUserFilter {
    Predicate createPredicateFor(final UserSearchQuery query);
}
