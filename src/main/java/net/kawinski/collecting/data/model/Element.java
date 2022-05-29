package net.kawinski.collecting.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.omnifaces.persistence.model.GeneratedIdEntity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("java:S2160") // "override equals" --> Equals is already comparing IDs and that's fine for database entities.
@Entity(name = "Element")
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class Element extends GeneratedIdEntity<Long> {
    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 64;

    @NotNull
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    @Column(length = MAX_NAME_LENGTH, nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Category category;

    @OneToMany(mappedBy = "element", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<ElementAttribute> attributes = new ArrayList<>();

    /**
     * Catalogue-like feature
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Getter
    private Element basedOn;

    @ManyToOne(fetch = FetchType.EAGER)
    @Getter
    private Collection presentIn;

    @JsonbTransient
    @Embedded
    @Getter
    @Setter
    private ImageModel image = new ImageModel();

    public Element(@NotNull @Size(min = 1, max = 64) final String name, final Category category, final Element basedOn) {
        this.name = name;
        this.category = category;
        this.basedOn = basedOn;
    }

    public boolean containsBase() {
        return basedOn != null;
    }

    public void addAttribute(final ElementAttribute attribute) {
        attribute.setElement(this);
    }

    public void removeAttribute(final ElementAttribute attribute) {
        attribute.setElement(null);
    }

    public void internalAddAttribute(final ElementAttribute attribute) {
        attributes.add(attribute);
    }

    public void internalRemoveAttribute(final ElementAttribute attribute) {
        attributes.remove(attribute);
    }

    public void internalSetPresentIn(final Collection collection) {
        presentIn = collection;
    }
}
