package net.kawinski.collecting.data.search.filters.element;

import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.CollectionVisibility;
import net.kawinski.collecting.data.model.Collection_;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.Element_;
import net.kawinski.collecting.data.search.ElementSearchQuery;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

public class ElementCollectionVisibilityFilter implements BaseElementFilter {
    private final CollectionVisibility expectedVisibility;

    public ElementCollectionVisibilityFilter(final CollectionVisibility expectedVisibility) {
        this.expectedVisibility = expectedVisibility;
    }

    @Override
    public Predicate createPredicateFor(final ElementSearchQuery query) {
        final Join<Element, Collection> elementCollection = query.element.join(Element_.presentIn);
        return query.cb.equal(elementCollection.get(Collection_.VISIBILITY), expectedVisibility);
    }
}