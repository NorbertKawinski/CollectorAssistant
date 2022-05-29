package net.kawinski.collecting.data.search;

import net.kawinski.collecting.data.model.Element;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

public class ElementSearchQuery extends BaseSearchQuery {

    public final Root<Element> element;

    public ElementSearchQuery(final CriteriaBuilder cb, final Root<Element> element) {
        super(cb);
        this.element = element;
    }
}
