package net.kawinski.collecting.data.attribute;

public class BooleanAttributeType implements BaseAttributeType<Boolean> {
    @Override
    public Boolean parse(final String value) {
        if(value.equalsIgnoreCase("true"))
            return true;
        if(value.equalsIgnoreCase("false"))
            return false;
        throw new IllegalArgumentException("Cannot parse '" + value + "'");
    }

    @Override
    public String toString(final Boolean value) {
        return value.toString();
    }
}
