package net.kawinski.collecting.data.search.filters.collection;

import net.kawinski.collecting.data.model.CollectionVisibility;
import net.kawinski.collecting.data.model.Collection_;
import net.kawinski.collecting.data.search.CollectionSearchQuery;

import javax.persistence.criteria.Predicate;

public class CollectionVisibilityFilter implements BaseCollectionFilter {
    private final CollectionVisibility expectedVisibility;

    public CollectionVisibilityFilter(final CollectionVisibility expectedVisibility) {
        this.expectedVisibility = expectedVisibility;
    }

    @Override
    public Predicate createPredicateFor(final CollectionSearchQuery query) {
        return query.cb.equal(query.collection.get(Collection_.VISIBILITY), expectedVisibility);
    }
}