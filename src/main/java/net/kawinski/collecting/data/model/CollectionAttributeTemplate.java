package net.kawinski.collecting.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.kawinski.utils.Default;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "CollectionAttributeTemplate")
@SuppressWarnings({"java:S2160"}) // "override equals" --> Equals is already comparing IDs and that's fine for database entities.
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class CollectionAttributeTemplate extends BaseAttributeTemplate {

    private static final long serialVersionUID = 1L;

    @Default
    public CollectionAttributeTemplate(final Category category, @NotNull @Size(min = 1, max = 32) final String name, final AttributeType type,
                                       final int displayOrder, final boolean searchable) {
        super(category, name, type, displayOrder, searchable);
    }
}
