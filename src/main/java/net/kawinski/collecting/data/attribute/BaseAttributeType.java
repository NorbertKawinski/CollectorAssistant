package net.kawinski.collecting.data.attribute;

import java.text.ParseException;

public interface BaseAttributeType<T> {

    default boolean canParse(final String value) {
        try {
            parse(value);
            return true;
        } catch(final Exception ex) {
            return false;
        }
    }

    T parse(String value) throws ParseException;
    String toString(T value);
}
