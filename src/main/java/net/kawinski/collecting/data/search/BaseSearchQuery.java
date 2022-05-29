package net.kawinski.collecting.data.search;

import javax.persistence.criteria.CriteriaBuilder;

public class BaseSearchQuery {

    public final CriteriaBuilder cb;

    public BaseSearchQuery(final CriteriaBuilder cb) {
        this.cb = cb;
    }
}
