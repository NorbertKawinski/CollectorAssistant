package net.kawinski.collecting.data.search;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public enum SearchAttributeComparator {
    EQ("="),
    NEQ("!="),
    LT("<"),
    LTE("<="),
    GT(">"),
    GTE(">="),
    CONTAINS("zawiera"),
    NOT_CONTAINS("nie zawiera"),
    BETWEEN("w zakresie"),
    NOT_BETWEEN("poza zakresem"),
//    IN("jeden z"),
//    NOT_IN("żaden z"),
    EMPTY("pusty"),
    NOT_EMPTY("nie pusty"),
    NULL("NULL"),
    NOT_NULL("nie NULL")
    ;

    @Getter
    private final String displayName;

    SearchAttributeComparator(final String displayName) {
        this.displayName = displayName;
    }

    public static Optional<SearchAttributeComparator> fromString(final String str) {
        return Arrays.stream(values())
                .filter(sac -> sac.getDisplayName().equals(str))
                .findFirst();
    }

    public static SearchAttributeComparator fromStringOrThrow(final String str) {
        return fromString(str)
                .orElseThrow(() -> new IllegalArgumentException("Unrecognized value: '" + str + "'"));
    }

    public boolean isNoValue() {
        return this == NULL || this == NOT_NULL || this == EMPTY || this == NOT_EMPTY;
    }

    public boolean isMultiValue() {
        return this == BETWEEN || this == NOT_BETWEEN;
    }

    public boolean isSingleValue() {
        return !isNoValue() && !isMultiValue();
    }


}
