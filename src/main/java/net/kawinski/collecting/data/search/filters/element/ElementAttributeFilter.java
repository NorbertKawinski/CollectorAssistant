package net.kawinski.collecting.data.search.filters.element;

import lombok.Value;
import net.kawinski.collecting.data.model.AttributeValue;
import net.kawinski.collecting.data.model.AttributeValue_;
import net.kawinski.collecting.data.model.BaseAttributeTemplate_;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.ElementAttribute;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.data.model.ElementAttribute_;
import net.kawinski.collecting.data.model.Element_;
import net.kawinski.collecting.data.search.ElementSearchQuery;
import net.kawinski.collecting.data.search.SearchAttributeComparator;
import net.kawinski.collecting.data.search.filters.collection.AttributeFilter;
import net.kawinski.collecting.service.search.SearchAttributeForm;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

@Value
public class ElementAttributeFilter implements BaseElementFilter {
    SearchAttributeForm form;

    @Override
    public Predicate createPredicateFor(final ElementSearchQuery query) {
        final Join<Element, ElementAttribute> elementAttributes = query.element.join(Element_.attributes, JoinType.LEFT);
        final Join<ElementAttribute, ElementAttributeTemplate> elementAttributeTemplate = elementAttributes.join(ElementAttribute_.attributeTemplate);
        final Join<ElementAttribute, AttributeValue> elementAttributeValue = elementAttributes.join(ElementAttribute_.value);

        return query.cb.and(
                query.cb.equal(elementAttributeTemplate.get(BaseAttributeTemplate_.name), form.getName()),
                AttributeFilter.createValuePredicateFor_s(query.cb, elementAttributeValue, form)
        );
    }
}