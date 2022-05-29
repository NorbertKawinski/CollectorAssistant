package net.kawinski.collecting.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.omnifaces.persistence.model.GeneratedIdEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

// "override equals" --> Equals is already comparing IDs and that's fine for database entities.
@SuppressWarnings({"java:S2160"})
@Entity(name = "CollectionAttribute")
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class CollectionAttribute extends GeneratedIdEntity<Long> {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER)
    @Getter
    private Collection collection;

    @ManyToOne(fetch = FetchType.EAGER)
    @Getter
    @ToString.Include
    private CollectionAttributeTemplate attributeTemplate;

    @Embedded
    @NotNull
    @Getter
    @Setter
    @ToString.Include
    private AttributeValue value;

    public CollectionAttribute(final Collection collection, final CollectionAttributeTemplate attributeTemplate, @NotNull final String rawValue) {
        this.collection = collection;
        this.attributeTemplate = attributeTemplate;
        this.value = new AttributeValue(attributeTemplate.getType(), rawValue);
    }

    public void setCollection(final Collection collection) {
        if(this.collection != null)
            this.collection.internalRemoveAttribute(this);
        this.collection = collection;
        if(this.collection != null)
            this.collection.internalAddAttribute(this);
    }

    public CollectionAttribute copyWithoutCollection() {
        return new CollectionAttribute(null, attributeTemplate, value.getRaw());
    }


}
