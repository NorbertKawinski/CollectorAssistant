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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("java:S2160") // "override equals" --> Equals is already comparing IDs and that's fine for database entities.
@Entity(name = "Collection")
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class Collection extends GeneratedIdEntity<Long> {

    private static final long serialVersionUID = 1L;
    public static final int MIN_NAME_LENGTH = 3;
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
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Category category;

    @NotNull
    @Column(nullable = false)
    @Getter
    @ToString.Include
    private Instant created;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    @ToString.Include
    private CollectionVisibility visibility;

    @OneToMany(mappedBy = "presentIn", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private Set<Element> elements = new HashSet<>();

    @Getter
    @Setter
    @ToString.Include
    private boolean catalog;

    /**
     * Optional, Declares which catalog we're based on.
     * Note: Recursive catalogs are not supported.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Getter
    private Collection basedOn;

    @OneToMany(mappedBy = "collection", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<CollectionAttribute> attributes = new ArrayList<>();

    @OneToMany(mappedBy = "basedOn", fetch = FetchType.LAZY)
    @Getter
    private List<Collection> baseFor = new ArrayList<>();

    @JsonbTransient
    @Embedded
    @Getter
    @Setter
    private ImageModel image = new ImageModel();

    public Collection(@NotNull @Size(min = 3, max = 64) final String name, final User owner, final Category category, final Collection basedOn, final boolean catalog) {
        this.name = name;
        this.owner = owner;
        this.category = category;
        this.basedOn = basedOn;
        this.catalog = catalog;
        this.created = Instant.now();
        this.visibility = CollectionVisibility.PUBLIC;
    }

    public boolean containsBase() {
        return basedOn != null;
    }

    @PrePersist
    public void onPrePersist() {
        created = Instant.now();
    }

    public void addAttribute(final CollectionAttribute attribute) {
        attribute.setCollection(this);
    }

    public void removeAttribute(final CollectionAttribute attribute) {
        attribute.setCollection(null);
    }

    public void internalAddAttribute(final CollectionAttribute attribute) {
        attributes.add(attribute);
    }

    public void internalRemoveAttribute(final CollectionAttribute attribute) {
        attributes.remove(attribute);
    }

    public void addElement(final Element element) {
        internalAddElement(element);
        element.internalSetPresentIn(this);
    }

    public void removeElement(final Element element) {
        internalRemoveElement(element);
        element.internalSetPresentIn(null);
    }

    public void internalAddElement(final Element element) {
        elements.add(element);
    }

    public void internalRemoveElement(final Element element) {
        elements.remove(element);
    }

    public boolean equalsId(Collection other) {
        return getId().equals(other.getId());
    }
}
