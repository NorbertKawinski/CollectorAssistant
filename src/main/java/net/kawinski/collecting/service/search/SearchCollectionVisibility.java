package net.kawinski.collecting.service.search;

import lombok.Getter;
import net.kawinski.collecting.data.model.CollectionVisibility;

import java.util.Optional;

public enum SearchCollectionVisibility {
    ALL("Wszystkie"),
    PUBLIC(CollectionVisibility.PUBLIC),
    PRIVATE(CollectionVisibility.PRIVATE);

    private final CollectionVisibility model;

    @Getter
    private final String label;

    SearchCollectionVisibility(String label) {
        this.model = null;
        this.label = label;
    }

    SearchCollectionVisibility(CollectionVisibility model) {
        this.model = model;
        this.label = model.getLabel();
    }

    public Optional<CollectionVisibility> getModel() {
        return Optional.ofNullable(model);
    }
}
