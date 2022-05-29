package net.kawinski.collecting.data.search.filters.element;

import net.kawinski.collecting.data.search.ElementSearchQuery;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface BaseElementFilter {
    Predicate createPredicateFor(final ElementSearchQuery query);
}
