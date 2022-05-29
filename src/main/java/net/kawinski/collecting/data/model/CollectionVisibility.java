package net.kawinski.collecting.data.model;

import lombok.Getter;

public enum CollectionVisibility {
    PUBLIC("Publiczne"),
    PRIVATE("Prywatne");

    @Getter
    private final String label;

    CollectionVisibility(String label) {
        this.label = label;
    }
}
