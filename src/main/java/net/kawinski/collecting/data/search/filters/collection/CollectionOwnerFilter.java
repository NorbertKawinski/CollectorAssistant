package net.kawinski.collecting.data.search.filters.collection;

import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.Collection_;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.data.model.User_;
import net.kawinski.collecting.data.search.CollectionSearchQuery;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

public class CollectionOwnerFilter implements BaseCollectionFilter {
    private final String expectedName;

    public CollectionOwnerFilter(final String expectedName) {
        this.expectedName = expectedName;
    }

    @Override
    public Predicate createPredicateFor(final CollectionSearchQuery query) {
        final Join<Collection, User> collectionOwner = query.collection.join(Collection_.owner);
        return query.cb.equal(collectionOwner.get(User_.NAME), expectedName);
    }
}