package net.kawinski.collecting.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.omnifaces.persistence.model.GeneratedIdEntity;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static javax.persistence.EnumType.STRING;

/**
 * Represents common fields for {@link CollectionAttributeTemplate} and {@link ElementAttributeTemplate}
 */
@MappedSuperclass
@SuppressWarnings("java:S2160") // "override equals" --> Equals is already comparing IDs and that's fine for database entities.
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class BaseAttributeTemplate extends GeneratedIdEntity<Long> {

    private static final long serialVersionUID = 1L;

    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 64;

    @ManyToOne(fetch = FetchType.EAGER)
    @Getter
    @ToString.Include
    private Category category;

    @NotNull
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    @Column(length = MAX_NAME_LENGTH, nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private String name;

    @NotNull
    @Enumerated(STRING)
    @Column(nullable = false)
    @Getter
    @ToString.Include
    private AttributeType type;

    /**
     * Required for ordering in the UI.
     */
    @Getter
    @Setter
    @ToString.Include
    private int displayOrder = 0;

    @Getter
    @Setter
    @ToString.Include
    private boolean searchable;

//    TODO:
//    /**
//     * Some description of the field with examples how to use it
//     */
//    private String help;

    public BaseAttributeTemplate(final Category category, @NotNull @Size(min = 1, max = 32) final String name, final AttributeType type,
                                 final int displayOrder, final boolean searchable) {
        this.category = category;
        this.name = name;
        this.type = type;
        this.displayOrder = displayOrder;
        this.searchable = searchable;
    }
}
