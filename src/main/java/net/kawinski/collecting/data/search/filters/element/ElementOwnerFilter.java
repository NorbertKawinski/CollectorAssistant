package net.kawinski.collecting.data.search.filters.element;

import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.Collection_;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.Element_;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.data.model.User_;
import net.kawinski.collecting.data.search.ElementSearchQuery;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

public class ElementOwnerFilter implements BaseElementFilter {
    private final String expectedName;

    public ElementOwnerFilter(final String expectedName) {
        this.expectedName = expectedName;
    }

    @Override
    public Predicate createPredicateFor(final ElementSearchQuery query) {
        final Join<Element, Collection> collection = query.element.join(Element_.presentIn);
        final Join<Collection, User> owner = collection.join(Collection_.owner);
        return query.cb.equal(owner.get(User_.NAME), expectedName);
    }
}