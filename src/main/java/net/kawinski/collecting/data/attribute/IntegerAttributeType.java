package net.kawinski.collecting.data.attribute;

public class IntegerAttributeType implements BaseAttributeType<Integer> {

    @Override
    public Integer parse(final String value) {
        return Integer.parseInt(value);
    }

    @Override
    public String toString(final Integer value) {
        return value.toString();
    }
}
