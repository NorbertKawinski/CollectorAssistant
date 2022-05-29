package net.kawinski.collecting.service.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kawinski.collecting.data.model.Category;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryForm {

    private Long parentId;

    @NotNull
    @Size(min = Category.MIN_NAME_LENGTH, max = Category.MAX_NAME_LENGTH)
    private String name;

    @NotNull
    private boolean canContainCollections;
}
