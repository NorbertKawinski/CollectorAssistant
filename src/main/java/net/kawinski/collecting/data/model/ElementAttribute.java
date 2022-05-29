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
@Entity(name = "ElementAttribute")
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class ElementAttribute extends GeneratedIdEntity<Long> {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER)
    @Getter
    private Element element;

    @ManyToOne(fetch = FetchType.EAGER)
    @Getter
    @ToString.Include
    private ElementAttributeTemplate attributeTemplate;

    @Embedded
    @NotNull
    @Getter
    @Setter
    @ToString.Include
    private AttributeValue value;

    public ElementAttribute(final Element element, final ElementAttributeTemplate attributeTemplate, @NotNull final String rawValue) {
        this.element = element;
        this.attributeTemplate = attributeTemplate;
        this.value = new AttributeValue(attributeTemplate.getType(), rawValue);
    }

    public void setElement(final Element element) {
        if(this.element != null)
            this.element.internalRemoveAttribute(this);
        this.element = element;
        if(this.element != null)
            this.element.internalAddAttribute(this);
    }

    public ElementAttribute copyWithoutElement() {
        return new ElementAttribute(null, attributeTemplate, value.getRaw());
    }
}
