package net.kawinski.collecting.data.search.filters.element;

import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Category_;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.Element_;
import net.kawinski.collecting.data.search.ElementSearchQuery;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ElementCategoryFilter implements BaseElementFilter {
    private final List<Long> expectedIds;

    public ElementCategoryFilter(final List<Long> expectedIds) {
        this.expectedIds = new ArrayList<>(expectedIds);
    }

    @Override
    public Predicate createPredicateFor(final ElementSearchQuery query) {
        final Join<Element, Category> elementCategory = query.element.join(Element_.category);
        return elementCategory.get(Category_.id)
                .in(expectedIds);
//        final CriteriaBuilder.In<Comparable> inClause = query.cb.in(query.elementCategory.get(Category_.id));
//        for(final long expectedId : expectedIds)
//            inClause.value(expectedId);
//        return inClause;
    }
}
