package net.kawinski.collecting.data.attribute;

public class StringAttributeType implements BaseAttributeType<String> {
    public static final int MAX_LENGTH = 200;

    @Override
    public boolean canParse(final String value) {
        return value.length() <= MAX_LENGTH;
    }

    @Override
    public String parse(final String value) {
        return value;
    }

    @Override
    public String toString(final String value) {
        return value;
    }
}
