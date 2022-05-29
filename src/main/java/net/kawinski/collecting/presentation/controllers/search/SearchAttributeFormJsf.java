package net.kawinski.collecting.presentation.controllers.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.AttributeValue;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.data.search.SearchAttributeComparator;
import net.kawinski.collecting.presentation.controllers.attribute.EditAttributeBaseController;
import net.kawinski.collecting.service.search.SearchAttributeForm;
import java.util.Arrays;
import java.util.List;

/**
 * Needed for proper JSF webpage rendering.
 * Contains extra fields:
 * - edit-attributes-controller for exposing specialized value placeholders
 * - attribute type
 * With above data JSF can generate proper HTML for the user
 * that nicely maps specialized values into raw strings used by the service-layer
 */
@AllArgsConstructor
public class SearchAttributeFormJsf {

    @Getter
    private final String id;

    @Getter
    private final String name;

    @Getter
    @Setter
    private SearchAttributeComparator comparator;

    @Getter
    private final EditAttributeBaseController valueControllerMain;

    @Getter
    private final EditAttributeBaseController valueControllerFrom;

    @Getter
    private final EditAttributeBaseController valueControllerTo;

    @Getter
    private final AttributeType type;

    public SearchAttributeFormJsf(Long id, String name, AttributeType type, EditAttributeBaseController baseController) {
        this(id.toString(), name, SearchAttributeComparator.EQ, baseController.copy(), baseController.copy(), baseController.copy(), type);
    }

    public boolean isNoValue() {
        return comparator.isNoValue();
    }

    public boolean isSingleValue() {
        return comparator.isSingleValue();
    }

    public boolean isMultiValue() {
        return comparator.isMultiValue();
    }

    public void setValue(SearchAttributeForm form) {
        List<String> values = form.getValues();

        switch(form.getComparator()) {
            case EMPTY:
            case NOT_EMPTY:
            case NULL:
            case NOT_NULL:
                break;

            case BETWEEN:
            case NOT_BETWEEN: {
                String from = values.get(0);
                String to = values.get(1);
                if (from != null && !from.isEmpty()){
                    valueControllerFrom.setValue(new AttributeValue(type, from));
                }
                if (to != null && !to.isEmpty()) {
                    valueControllerTo.setValue(new AttributeValue(type, to));
                }
                break;
            }

            case EQ:
            case NEQ:
            case LT:
            case LTE:
            case GT:
            case GTE:
            case CONTAINS:
            case NOT_CONTAINS:
            default:
                valueControllerMain.setValue(new AttributeValue(type, form.getValue()));
                break;
        }
    }

    public SearchAttributeForm getForm(User searchUploadUser) {
        switch (comparator) {
            case NULL:
            case NOT_NULL:
            case EMPTY:
            case NOT_EMPTY:
                return new SearchAttributeForm(name, comparator, null, null);

            case BETWEEN:
            case NOT_BETWEEN:
                if(valueControllerFrom.hasValue(type) && valueControllerTo.hasValue(type)) {
                    return new SearchAttributeForm(name, comparator, Arrays.asList(
                            valueControllerFrom.convertToRawValue(type, searchUploadUser),
                            valueControllerTo.convertToRawValue(type, searchUploadUser)
                    ));
                }
                break;

            case EQ:
            case NEQ:
            case LT:
            case LTE:
            case GT:
            case GTE:
            case CONTAINS:
            case NOT_CONTAINS:
            default:
                if(valueControllerMain.hasValue(type)) {
                    return new SearchAttributeForm(name, comparator, valueControllerMain.convertToRawValue(type, searchUploadUser));
                }
                break;
        }

        return null;
    }

    public SearchAttributeComparator[] getAvailableComparators() {
        switch (type) {
            case DECIMAL:
            case INTEGER:
            case BARCODE:
            case DATETIME:
                return new SearchAttributeComparator[]{
                        SearchAttributeComparator.EQ,
                        SearchAttributeComparator.NEQ,
                        SearchAttributeComparator.GT,
                        SearchAttributeComparator.GTE,
                        SearchAttributeComparator.LT,
                        SearchAttributeComparator.LTE,
//                        SearchAttributeComparator.IN,
//                        SearchAttributeComparator.NOT_IN,
                        SearchAttributeComparator.BETWEEN,
                        SearchAttributeComparator.NOT_BETWEEN,
                        SearchAttributeComparator.EMPTY,
                        SearchAttributeComparator.NOT_EMPTY,
                        SearchAttributeComparator.NULL,
                        SearchAttributeComparator.NOT_NULL
                };

            case STRING:
            case LONG_STRING:
                return new SearchAttributeComparator[]{
                        SearchAttributeComparator.EQ,
                        SearchAttributeComparator.NEQ,
//                        SearchAttributeComparator.IN,
//                        SearchAttributeComparator.NOT_IN,
                        SearchAttributeComparator.EMPTY,
                        SearchAttributeComparator.NOT_EMPTY,
                        SearchAttributeComparator.NULL,
                        SearchAttributeComparator.NOT_NULL
                };

            case BOOLEAN:
            case IMAGE:
            default:
                return new SearchAttributeComparator[]{
                        SearchAttributeComparator.EQ,
                        SearchAttributeComparator.EMPTY,
                        SearchAttributeComparator.NOT_EMPTY,
                        SearchAttributeComparator.NULL,
                        SearchAttributeComparator.NOT_NULL
                };
        }
    }
}
