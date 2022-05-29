package net.kawinski.collecting.data.model;

import lombok.Getter;
import net.kawinski.collecting.data.attribute.Barcode128AttributeType;
import net.kawinski.collecting.data.attribute.BaseAttributeType;
import net.kawinski.collecting.data.attribute.BooleanAttributeType;
import net.kawinski.collecting.data.attribute.DateTimeAttributeType;
import net.kawinski.collecting.data.attribute.DecimalAttributeType;
import net.kawinski.collecting.data.attribute.ImageAttributeType;
import net.kawinski.collecting.data.attribute.IntegerAttributeType;
import net.kawinski.collecting.data.attribute.LongStringAttributeType;
import net.kawinski.collecting.data.attribute.StringAttributeType;

import java.util.Date;

/**
 * Defines available data types for collection and element attributes
 * TODO: Not all of them are final yet, we might want to add some more types, or precisely define the datatypes and range for already existing ones.
 */
public enum AttributeType {

    /**
     * true/false ¯\_(ツ)_/¯
     */
    BOOLEAN("Typ logiczny", new BooleanAttributeType()),

    /**
     * Some integer.
     * Probably something closely representing Java's int
     */
    INTEGER("Liczba całkowita", new IntegerAttributeType()),

    /**
     * For premium users, better range than {@link AttributeType#INTEGER}
     * Probably implemented as something similar to Java's BigDecimal type constrained to integers only
     */
//    BIG_INTEGER("Duża liczba całkowita"),

    /**
     * Small precision but neat storage requirements
     * Probably something closely representing Java's float or double
     */
    DECIMAL("Liczba rzeczywista", new DecimalAttributeType()),

    /**
     * For premium users, better range than {@link AttributeType#DECIMAL}
     * Probably implemented as something similar to Java's BigDecimal type
     */
//    BIG_DECIMAL("Duża liczba rzeczywista"),

    /**
     * Useful for short attributes like names, producers, etc
     * Probably not longer than 255 character or something like that
     */
    STRING("Tekst", new StringAttributeType()),

    /**
     * Useful for longer attributes (like long descriptions, user-stories, etc)
     * Might store several thousands or more characters
     * Might be displayed as a text-area instead of text-field.
     *
     * Regular users might be limited to 1 attribute of this type per collection?
     */
    LONG_STRING("Długi tekst", new LongStringAttributeType()),

    /**
     * year, month, day
     */
//    DATE("Dzień"),

    /**
     * hour, minute, second, maybe millisecond
     */
//    TIME("Godzina"),

    /**
     * {@link AttributeType#DATE} + {@link AttributeType#TIME} o_O
     */
    DATETIME("Data", new DateTimeAttributeType()),

//    /**
//     * Similar to INTEGER, but with some constraints, possibly?
//     */
//    YEAR,

    /**
     * Any image file.
     *
     * Not all formats are supported.
     * Probably some standard formats like .jpg, .png, etc
     * Converted to .jpg automatically to save space?
     *
     * Probably some resolution constraints like 1024x1024 maximum size.
     * Downscaled if too big
     *
     * Probably some size contraint, like 1MB
     *
     * Probably limited to maximum of 3 images per collection element
     *
     * Probably stored in database as a path to image file
     *
     * Premium users might have less of above constraints
     */
    IMAGE("Obraz", new ImageAttributeType()),

    /**
     * A series of images.
     * Supported by some neat javascript plugin for displaying galleries
     *
     * Same limitations apply as for {@link AttributeType#IMAGE} type.
     *
     * Probably stored in database as a path to directory with images files
     */
//    GALLERY("Galeria"),

    /**
     *
     */
    BARCODE("Kod kreskowy", new Barcode128AttributeType())
    ;


    // TODO: COLLECTION_REFERENCE, ELEMENT_REFERENCE types.
    // Used for example for Pokemon types:  (fire, water, wind, grass, poison, bug, flying, dark, fairy, etc), or any other enum-like attribute.

    @Getter
    private final String label;

    @Getter
    private final BaseAttributeType<?> attributeType;

    AttributeType(final String label, final BaseAttributeType<?> attributeType) {
        this.label = label;
        this.attributeType = attributeType;
    }

    public BaseAttributeType<Boolean> getBooleanAttributeType() {
        if(this != BOOLEAN)
            throw new IllegalStateException("attribute type (" + this + ") is not of the expected type (" + BOOLEAN + ")");
        //noinspection unchecked
        return (BaseAttributeType<Boolean>) attributeType;
    }

    public BaseAttributeType<Integer> getIntegerAttributeType() {
        if(this != INTEGER)
            throw new IllegalStateException("attribute type (" + this + ") is not of the expected type (" + INTEGER + ")");
        //noinspection unchecked
        return (BaseAttributeType<Integer>) attributeType;
    }

    public BaseAttributeType<Double> getDecimalAttributeType() {
        if(this != DECIMAL)
            throw new IllegalStateException("attribute type (" + this + ") is not of the expected type (" + DECIMAL + ")");
        //noinspection unchecked
        return (BaseAttributeType<Double>) attributeType;
    }

    public BaseAttributeType<String> getStringAttributeType() {
        if(this != STRING)
            throw new IllegalStateException("attribute type (" + this + ") is not of the expected type (" + STRING + ")");
        //noinspection unchecked
        return (BaseAttributeType<String>) attributeType;
    }

    public BaseAttributeType<String> getLongStringAttributeType() {
        if(this != LONG_STRING)
            throw new IllegalStateException("attribute type (" + this + ") is not of the expected type (" + LONG_STRING + ")");
        //noinspection unchecked
        return (BaseAttributeType<String>) attributeType;
    }

    public BaseAttributeType<Date> getDatetimeAttributeType() {
        if(this != DATETIME)
            throw new IllegalStateException("attribute type (" + this + ") is not of the expected type (" + DATETIME + ")");
        //noinspection unchecked
        return (BaseAttributeType<Date>) attributeType;
    }
}
