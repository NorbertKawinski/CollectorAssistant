package net.kawinski.collecting.data.search.filters.collection;

import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Category_;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.Collection_;
import net.kawinski.collecting.data.search.CollectionSearchQuery;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

public class CollectionCategoryNameFilter implements BaseCollectionFilter {
    private final String expectedName;

    public CollectionCategoryNameFilter(final String expectedName) {
        this.expectedName = expectedName;
    }

    @Override
    public Predicate createPredicateFor(final CollectionSearchQuery query) {
        final Join<Collection, Category> collectionCategory = query.collection.join(Collection_.category);
        return query.cb.equal(collectionCategory.get(Category_.NAME), expectedName);
    }
}