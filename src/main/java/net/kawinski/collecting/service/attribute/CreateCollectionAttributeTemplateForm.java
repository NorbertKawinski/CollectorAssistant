package net.kawinski.collecting.service.attribute;

import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.BaseAttributeTemplate;
import net.kawinski.collecting.data.model.Category;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateCollectionAttributeTemplateForm extends CreateAttributeTemplateForm {
    public CreateCollectionAttributeTemplateForm(@NotNull final Category category,
                                                 @NotNull @Size(min = BaseAttributeTemplate.MIN_NAME_LENGTH, max = BaseAttributeTemplate.MAX_NAME_LENGTH) final String name,
                                                 @NotNull final AttributeType attributeType, @NotNull final int displayOrder, @NotNull final boolean searchable) {
        super(category, AttributeTarget.Collection, name, attributeType, displayOrder, searchable);
    }
}
