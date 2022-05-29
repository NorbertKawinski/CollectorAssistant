package net.kawinski.collecting.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static javax.persistence.EnumType.STRING;

@Embeddable
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@Data
public class AttributeValue implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int MAX_RAW_LENGTH = 4096;

    /**
     * This field is a bit redundant, but it makes for much easier code than accessing the type from a template...
     * Now this class can hold any value independently from other entities.
     */
    @NotNull
    @Enumerated(STRING)
    @Column(nullable = false)
    private AttributeType type;

    @NotNull
    @Size(max = MAX_RAW_LENGTH)
    @Column(length = MAX_RAW_LENGTH, nullable = false)
    private String raw;

    public AttributeValue(final AttributeType type, final String raw) {
        this.type = type;
        checkRawValid(raw);
        this.raw = raw;
    }

    public void setRaw(final String raw) {
        checkRawValid(raw);
        this.raw = raw;
    }

    private void checkRawValid(final String raw) {
        final boolean valid = type.getAttributeType().canParse(raw);
        if(!valid)
            throw new IllegalArgumentException("passed value '" + raw + "' is invalid for type: '" + type + "'");
    }
}
