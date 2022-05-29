package net.kawinski.collecting.data.search.filters.element;

import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Category_;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.Element_;
import net.kawinski.collecting.data.search.ElementSearchQuery;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

public class ElementCategoryNameFilter implements BaseElementFilter {
    private final String expectedName;

    public ElementCategoryNameFilter(final String expectedName) {
        this.expectedName = expectedName;
    }

    @Override
    public Predicate createPredicateFor(final ElementSearchQuery query) {
        final Join<Element, Category> elementCategory = query.element.join(Element_.category);
        return query.cb.equal(elementCategory.get(Category_.NAME), expectedName);
    }
}