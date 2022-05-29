package net.kawinski.collecting.service.attribute;

public enum AttributeTarget {
    Collection("Kolekcja"),
    Element("Element");

    private final String label;

    AttributeTarget(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
