package net.kawinski.collecting.service.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kawinski.collecting.data.model.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCollectionForm {

    @NotNull
    private Long categoryId;

    private Long basedOnId;

    @NotNull
    @Size(min = Collection.MIN_NAME_LENGTH, max = Collection.MAX_NAME_LENGTH)
    private String name;

    @NotNull
    private boolean catalog;

    public CreateCollectionForm(final Long categoryId, final Collection basedOn, final String name, final boolean catalog) {
        this.categoryId = categoryId;
        this.basedOnId = basedOn == null ? null : basedOn.getId();
        this.name = name;
        this.catalog = catalog;
    }
}
