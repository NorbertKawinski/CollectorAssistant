package net.kawinski.collecting.data.search.filters.collection;

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

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

@Value
public class CollectionAttributeFilter implements BaseCollectionFilter {
    SearchAttributeForm form;

    @Override
    public Predicate createPredicateFor(final CollectionSearchQuery query) {
        final Join<Collection, CollectionAttribute> collectionAttributes = query.collection.join(Collection_.attributes, JoinType.LEFT);
        final Join<CollectionAttribute, CollectionAttributeTemplate> collectionAttributeTemplate = collectionAttributes.join(CollectionAttribute_.attributeTemplate);
        final Join<CollectionAttribute, AttributeValue> collectionAttributeValue = collectionAttributes.join(CollectionAttribute_.value);

        return query.cb.and(
                query.cb.equal(collectionAttributeTemplate.get(BaseAttributeTemplate_.name), form.getName()),
                AttributeFilter.createValuePredicateFor_s(query.cb, collectionAttributeValue, form)
        );
    }
}