package net.kawinski.collecting.data.search.filters.collection;

import lombok.AllArgsConstructor;
import lombok.Value;
import net.kawinski.collecting.data.model.AttributeValue;
import net.kawinski.collecting.data.model.AttributeValue_;
import net.kawinski.collecting.data.model.BaseAttributeTemplate_;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.CollectionAttribute;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.model.CollectionAttribute_;
import net.kawinski.collecting.data.model.Collection_;
import net.kawinski.collecting.data.search.CollectionSearchQuery;
import net.kawinski.collecting.data.search.SearchAttributeComparator;
import net.kawinski.collecting.service.search.SearchAttributeForm;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class AttributeFilter {

    public static Predicate createValuePredicateFor_s(CriteriaBuilder cb, Join<?, AttributeValue> attributeValue, SearchAttributeForm form) {
        String expectedValue = form.getValue();
        List<String> expectedValues = form.getValues();

        switch (form.getComparator()) {
            case EQ:
                return cb.equal(attributeValue.get(AttributeValue_.raw), expectedValue);
            case NEQ:
                return cb.notEqual(attributeValue.get(AttributeValue_.raw), expectedValue);
            case LT:
                return cb.lessThan(attributeValue.get(AttributeValue_.raw), expectedValue);
            case LTE:
                return cb.lessThanOrEqualTo(attributeValue.get(AttributeValue_.raw), expectedValue);
            case GT:
                return cb.greaterThan(attributeValue.get(AttributeValue_.raw), expectedValue);
            case GTE:
                return cb.greaterThanOrEqualTo(attributeValue.get(AttributeValue_.raw), expectedValue);
            case CONTAINS:
                return cb.like(attributeValue.get(AttributeValue_.raw), "%" + expectedValue + "%");
            case NOT_CONTAINS:
                return cb.notLike(attributeValue.get(AttributeValue_.raw), "%" + expectedValue + "%");
            case EMPTY:
                return cb.equal(attributeValue.get(AttributeValue_.raw), "");
            case NOT_EMPTY:
                return cb.notEqual(attributeValue.get(AttributeValue_.raw), "");
            case NULL:
                return cb.isNull(attributeValue.get(AttributeValue_.raw));
            case NOT_NULL:
                return cb.isNotNull(attributeValue.get(AttributeValue_.raw));
            case BETWEEN:
                return cb.between(attributeValue.get(AttributeValue_.raw), expectedValues.get(0), expectedValues.get(1));
            case NOT_BETWEEN:
                return cb.or(
                        cb.lessThanOrEqualTo(attributeValue.get(AttributeValue_.raw), expectedValues.get(0)),
                        cb.greaterThanOrEqualTo(attributeValue.get(AttributeValue_.raw), expectedValues.get(1))
                );
            default:
                throw new IllegalStateException("Unexpected comparator: '" + form.getComparator() + "'");
        }
    }
}