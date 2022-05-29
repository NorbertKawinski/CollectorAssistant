package net.kawinski.collecting.data.search.filters.collection;

import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Category_;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.Collection_;
import net.kawinski.collecting.data.search.CollectionSearchQuery;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CollectionCategoryFilter implements BaseCollectionFilter {
    private final List<Long> expectedIds;

    public CollectionCategoryFilter(final List<Long> expectedIds) {
        this.expectedIds = new ArrayList<>(expectedIds);
    }

    @Override
    public Predicate createPredicateFor(final CollectionSearchQuery query) {
        final Join<Collection, Category> collectionCategory = query.collection.join(Collection_.category);
        return collectionCategory.get(Category_.id)
                .in(expectedIds);
//        final CriteriaBuilder.In<Comparable> inClause = query.cb.in(query.collectionCategory.get(Category_.id));
//        for(final long expectedId : expectedIds)
//            inClause.value(expectedId);
//        return inClause;
    }
}
