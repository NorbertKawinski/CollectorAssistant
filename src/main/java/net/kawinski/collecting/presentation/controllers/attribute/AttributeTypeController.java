package net.kawinski.collecting.presentation.controllers.attribute;

import lombok.Getter;
import net.kawinski.collecting.data.attribute.Barcode128AttributeType;
import net.kawinski.collecting.data.attribute.BooleanAttributeType;
import net.kawinski.collecting.data.attribute.DateTimeAttributeType;
import net.kawinski.collecting.data.attribute.DecimalAttributeType;
import net.kawinski.collecting.data.attribute.ImageAttributeType;
import net.kawinski.collecting.data.attribute.IntegerAttributeType;
import net.kawinski.collecting.data.attribute.LongStringAttributeType;
import net.kawinski.collecting.data.attribute.StringAttributeType;
import net.kawinski.collecting.data.model.AttributeType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class AttributeTypeController {
    @Getter
    private final AttributeType BOOLEAN = AttributeType.BOOLEAN;
    @Getter
    private final BooleanAttributeType BOOLEAN_DRIVER = new BooleanAttributeType();

    @Getter
    private final AttributeType INTEGER = AttributeType.INTEGER;
    @Getter
    private final IntegerAttributeType INTEGER_DRIVER = new IntegerAttributeType();

    @Getter
    private final AttributeType DECIMAL = AttributeType.DECIMAL;
    @Getter
    private final DecimalAttributeType DECIMAR_DRIVER = new DecimalAttributeType();

    @Getter
    private final AttributeType STRING = AttributeType.STRING;
    @Getter
    private final StringAttributeType STRING_DRIVER = new StringAttributeType();

    @Getter
    private final AttributeType LONG_STRING = AttributeType.LONG_STRING;
    @Getter
    private final LongStringAttributeType LONG_STRING_DRIVER = new LongStringAttributeType();

    @Getter
    private final AttributeType DATETIME = AttributeType.DATETIME;
    @Getter
    private final DateTimeAttributeType DATETIME_DRIVER = new DateTimeAttributeType();

    @Getter
    private final AttributeType IMAGE = AttributeType.IMAGE;
    @Getter
    private final ImageAttributeType IMAGE_DRIVER = new ImageAttributeType();

    @Getter
    private final AttributeType BARCODE = AttributeType.BARCODE;
    @Getter
    private final Barcode128AttributeType BARCODE_DRIVER = new Barcode128AttributeType();
}
