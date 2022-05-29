package net.kawinski.collecting.data.attribute;

public class DecimalAttributeType implements BaseAttributeType<Double> {
    @Override
    public Double parse(final String value) {
        return Double.parseDouble(value);
    }

    @Override
    public String toString(final Double value) {
        return value.toString();
    }
}
