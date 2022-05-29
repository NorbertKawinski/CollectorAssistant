package net.kawinski.collecting.presentation.controllers.attribute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.attribute.Barcode128AttributeType;
import net.kawinski.collecting.data.attribute.ImageAttributeType;
import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.AttributeValue;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.presentation.controllers.user.LoggedInUser;
import net.kawinski.collecting.service.media.MediaService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.java.StringUtils;
import org.primefaces.model.file.UploadedFile;

import javax.inject.Inject;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

@NoArgsConstructor
@Slf4j
public final class EditAttributeBaseController implements Serializable {

    @Inject
    private LoggedInUser loggedInUser;

    @Inject
    private MediaService mediaService;

    @Inject
    private AttributeTypeController attributeTypeController;

    @Getter
    @Setter
    private Boolean valueBoolean;

    @Getter
    @Setter
    private Integer valueInt;

    @Getter
    @Setter
    private Double valueDouble;

    @Getter
    @Setter
    private String valueString;

    @Getter
    @Setter
    private Date valueDateTime;

    @Getter
    @Setter
    private UploadedFile valueFile;

    public EditAttributeBaseController(final LoggedInUser loggedInUser, final MediaService mediaService,
                                       final AttributeTypeController attributeTypeController) {
        this.loggedInUser = loggedInUser;
        this.mediaService = mediaService;
        this.attributeTypeController = attributeTypeController;
    }

    public boolean hasValue(final AttributeType type) {
        switch (type) {
            case BOOLEAN:
                return valueBoolean != null;
            case INTEGER:
                return valueInt != null;
            case DECIMAL:
                return valueDouble != null;
            case STRING:
            case LONG_STRING:
                return !StringUtils.isNullOrWhitespace(valueString);
            case DATETIME:
                return valueDateTime != null;
            case IMAGE:
                return valueFile != null && valueFile.getFileName() != null;
            case BARCODE:
                return (!StringUtils.isNullOrWhitespace(valueString))
                        || (valueFile != null && valueFile.getFileName() != null);
            default:
                throw new IllegalArgumentException("Unsupported attribute type: " + type);
        }
    }

    public void setValue(final AttributeValue value) {
        switch (value.getType()) {
            case BOOLEAN:
                valueBoolean = attributeTypeController.getBOOLEAN_DRIVER().parse(value.getRaw());
                break;
            case INTEGER:
                valueInt = attributeTypeController.getINTEGER_DRIVER().parse(value.getRaw());
                break;
            case DECIMAL:
                valueDouble = attributeTypeController.getDECIMAR_DRIVER().parse(value.getRaw());
                break;
            case STRING:
            case LONG_STRING:
                valueString = attributeTypeController.getSTRING_DRIVER().parse(value.getRaw());
                break;
            case DATETIME:
                valueDateTime = attributeTypeController.getDATETIME_DRIVER().parse(value.getRaw());
                break;
            case IMAGE:
                valueString = attributeTypeController.getIMAGE_DRIVER().parse(value.getRaw());
                break;
            case BARCODE:
                valueString = attributeTypeController.getBARCODE_DRIVER().parse(value.getRaw());
                break;
            default:
                throw new IllegalStateException("Type '" + value.getType() + "' is not supported");
        }
    }

    public String convertToRawValue(final AttributeType type) {
        return convertToRawValue(type, loggedInUser.getLoggedInUserOrThrow());
    }

    public String convertToRawValue(final AttributeType type, User uploadOwner) {
        try (final NkTrace trace = NkTrace.info(log, "type: {}", type)) {

            switch (type) {
                case BOOLEAN:
                    return type.getBooleanAttributeType().toString(valueBoolean);
                case INTEGER:
                    return type.getIntegerAttributeType().toString(valueInt);
                case DECIMAL:
                    return type.getDecimalAttributeType().toString(valueDouble);
                case STRING:
                    return type.getStringAttributeType().toString(valueString);
                case LONG_STRING:
                    return type.getLongStringAttributeType().toString(valueString);
                case DATETIME:
                    return type.getDatetimeAttributeType().toString(valueDateTime);
                case IMAGE:
                    return convertImageToRawValue(uploadOwner);
                case BARCODE:
                    return convertBarcodeToRawValue();
                default:
                    throw new IllegalStateException("Type '" + type + "' is not supported");
            }
        }
    }

    private String convertImageToRawValue(final User user) {
        log.info("File detected. Saving...");
        final CaUpload upload = mediaService.create(user, valueFile.getFileName(), valueFile.getContent());
        setValueString(upload.getFileName());
        return new ImageAttributeType().toString(valueString);
    }

    /**
     * User can either upload barcode image, or enter the code manually.
     * This function detects such difference and acts accordingly.
     */
    private String convertBarcodeToRawValue() {
        if(valueFile != null && valueFile.getFileName() != null) {
            try {
                valueString = Barcode128AttributeType.scanImage(valueFile.getFileName(), valueFile.getContent());
            } catch (final Exception ex) {
                throw new RuntimeException("Failed to process uploaded barcode image", ex);
            }
        }
        return valueString;
    }

    public EditAttributeBaseController copy() {
        return new EditAttributeBaseController(loggedInUser, mediaService, attributeTypeController);
    }

}
