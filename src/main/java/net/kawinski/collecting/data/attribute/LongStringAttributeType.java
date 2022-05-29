package net.kawinski.collecting.data.attribute;

import net.kawinski.collecting.data.model.AttributeValue;

public class LongStringAttributeType implements BaseAttributeType<String> {
    public static final int MAX_LENGTH = AttributeValue.MAX_RAW_LENGTH;

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
