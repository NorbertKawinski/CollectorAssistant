package net.kawinski.collecting.service.element;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kawinski.collecting.data.model.Element;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateElementForm {

    @NotNull
    private Long collectionId;

    private Long basedOnId;

    @NotNull
    @Size(min = Element.MIN_NAME_LENGTH, max = Element.MAX_NAME_LENGTH)
    private String name;

    public CreateElementForm(final Long collectionId, final Element basedOn, final String name) {
        this.collectionId = collectionId;
        this.basedOnId = basedOn == null ? null : basedOn.getId();
        this.name = name;
    }
}
