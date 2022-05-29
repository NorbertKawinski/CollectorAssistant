package net.kawinski.collecting.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.omnifaces.persistence.model.GeneratedIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@SuppressWarnings("java:S2160") // "override equals" --> Equals is already comparing IDs and that's fine for database entities.
@Entity(name = "CaUpload")
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class CaUpload extends GeneratedIdEntity<Long> {
    public static final int MIN_EXTENSION_LENGTH = 1;
    public static final int MAX_EXTENSION_LENGTH = 8;
    public static final String EXTENSION_PATTERN = "^[a-zA-Z0-9]+$";

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    private User owner;

    @NotNull
    @Pattern(regexp = EXTENSION_PATTERN)
    @Size(min = MIN_EXTENSION_LENGTH, max = MAX_EXTENSION_LENGTH)
    @Column(length = MAX_EXTENSION_LENGTH, nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private String fileExtension;

    public CaUpload(final User owner, final String fileExtension) {
        this.owner = owner;
        this.fileExtension = fileExtension;
    }

    public String getFileName() {
        return "CAUP_" + getId().toString() + "." + getFileExtension();
    }
}
