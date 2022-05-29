package net.kawinski.collecting.data.search;

import net.kawinski.collecting.data.model.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

public class CollectionSearchQuery extends BaseSearchQuery {

    public final Root<Collection> collection;

    public CollectionSearchQuery(final CriteriaBuilder cb, final Root<Collection> collection) {
        super(cb);
        this.collection = collection;
    }
}
