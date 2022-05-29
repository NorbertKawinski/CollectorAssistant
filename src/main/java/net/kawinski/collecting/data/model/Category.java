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
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity(name = "Category")
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@SuppressWarnings("java:S2160") // "override equals" --> Equals is already comparing IDs and that's fine for database entities.
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class Category extends GeneratedIdEntity<Long> {
    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 64;

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    @Column(length = MAX_NAME_LENGTH, nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @Setter
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("name asc")
    @Getter
    private List<Category> subCategories = new ArrayList<>();

    /**
     * We don't want all categories (especially root ones) to contain collections
     */
    @NotNull
    @Column(nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private boolean canContainCollections;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @Getter
    private List<Collection> collections = new ArrayList<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @Getter
    private List<CollectionAttributeTemplate> collectionTemplate = new ArrayList<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @Getter
    private List<ElementAttributeTemplate> elementTemplate = new ArrayList<>();

    @JsonbTransient
    @Embedded
    @Getter
    @Setter
    private ImageModel image = new ImageModel();

    public Category(final Category parent, @NotNull @Size(min = 3, max = 64) final String name, @NotNull final boolean canContainCollections) {
        this.parent = parent;
        this.name = name;
        this.canContainCollections = canContainCollections;
    }

    public Optional<Category> getParent() {
        return Optional.ofNullable(parent);
    }

    public void addSubcategory(final Category subCategory) {
        subCategories.add(subCategory);
    }

    public void removeSubcategory(final Category subCategory) {
        subCategories.remove(subCategory);
    }

    public boolean equalsId(Category other) {
        return getId().equals(other.getId());
    }
}
