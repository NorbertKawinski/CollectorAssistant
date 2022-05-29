package net.kawinski.collecting.data.search.filters.element;

import net.kawinski.collecting.data.model.Element_;
import net.kawinski.collecting.data.search.ElementSearchQuery;

import javax.persistence.criteria.Predicate;

public class ElementNameFilter implements BaseElementFilter {
    private final String expectedName;

    public ElementNameFilter(final String expectedName) {
        this.expectedName = expectedName;
    }

    @Override
    public Predicate createPredicateFor(final ElementSearchQuery query) {
        // TODO: Escape % and _ (and other characters too)
        return query.cb.like(query.element.get(Element_.NAME), "%" + expectedName + "%");
    }
}
