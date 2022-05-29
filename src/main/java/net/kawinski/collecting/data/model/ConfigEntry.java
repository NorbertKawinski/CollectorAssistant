package net.kawinski.collecting.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.omnifaces.persistence.model.GeneratedIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("java:S2160") // "override equals" --> Equals is already comparing IDs and that's fine for database entities.
@Entity(name = "ConfigEntry")
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class ConfigEntry extends GeneratedIdEntity<Long> {

    public static final int MIN_KEY_LENGTH = 1;
    public static final int MAX_KEY_LENGTH = 64;

    public static final int MIN_VALUE_LENGTH = 1;
    public static final int MAX_VALUE_LENGTH = 64;

    @NotNull
    @Size(min = MIN_KEY_LENGTH, max = MAX_KEY_LENGTH)
    @Column(length = MAX_KEY_LENGTH, nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private String theKey;

    @NotNull
    @Size(min = MIN_VALUE_LENGTH, max = MAX_VALUE_LENGTH)
    // MariaDB does not allow "value" column name so we're changing it to something else like "value_raw"
    @Column(length = MAX_VALUE_LENGTH, nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private String theValue;

    public ConfigEntry(final String key, final String value) {
        this.theKey = key;
        this.theValue = value;
    }

    public int getValueAsInt() {
        return Integer.parseInt(theValue);
    }

    public void setValueAsInt(final int value) {
        this.theValue = Integer.toString(value);
    }

}
