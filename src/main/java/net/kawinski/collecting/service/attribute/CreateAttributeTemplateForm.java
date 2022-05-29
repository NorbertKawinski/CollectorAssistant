package net.kawinski.collecting.service.attribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.BaseAttributeTemplate;
import net.kawinski.collecting.data.model.Category;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAttributeTemplateForm {

    @NotNull
    private Long categoryId;

    @NotNull
    private AttributeTarget attributeTarget;

    @NotNull
    @Size(min = BaseAttributeTemplate.MIN_NAME_LENGTH, max = BaseAttributeTemplate.MAX_NAME_LENGTH)
    private String name;

    @NotNull
    private AttributeType type;

    @NotNull
    private int displayOrder;

    @NotNull
    private boolean searchable;

    public CreateAttributeTemplateForm(@NotNull final Category category,
                                       @NotNull final AttributeTarget attributeTarget,
                                       @NotNull @Size(min = BaseAttributeTemplate.MIN_NAME_LENGTH, max = BaseAttributeTemplate.MAX_NAME_LENGTH) final String name,
                                       @NotNull final AttributeType type,
                                       @NotNull final int displayOrder,
                                       @NotNull final boolean searchable) {
        this(category.getId(), attributeTarget, name, type, displayOrder, searchable);
    }

    public AttributeTarget[] getAvailableTargets() {
        return AttributeTarget.values();
    }

    public AttributeType[] getAvailableTypes() {
        return AttributeType.values();
    }
}
