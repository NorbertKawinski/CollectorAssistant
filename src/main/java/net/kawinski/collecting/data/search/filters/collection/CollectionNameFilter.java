package net.kawinski.collecting.data.search.filters.collection;

import net.kawinski.collecting.data.model.Collection_;
import net.kawinski.collecting.data.search.CollectionSearchQuery;

import javax.persistence.criteria.Predicate;

public class CollectionNameFilter implements BaseCollectionFilter {
    private final String expectedName;

    public CollectionNameFilter(final String expectedName) {
        this.expectedName = expectedName;
    }

    @Override
    public Predicate createPredicateFor(final CollectionSearchQuery query) {
        // TODO: Escape % and _ (and other characters too)
        return query.cb.like(query.collection.get(Collection_.NAME), "%" + expectedName + "%");
    }
}
