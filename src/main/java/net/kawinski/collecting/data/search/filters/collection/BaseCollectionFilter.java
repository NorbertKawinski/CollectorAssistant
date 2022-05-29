package net.kawinski.collecting.data.search.filters.collection;

import net.kawinski.collecting.data.search.CollectionSearchQuery;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface BaseCollectionFilter {
    Predicate createPredicateFor(final CollectionSearchQuery query);
}
