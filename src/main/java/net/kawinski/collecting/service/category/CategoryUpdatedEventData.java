package net.kawinski.collecting.service.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.kawinski.collecting.data.model.Category;

@Data
@AllArgsConstructor
public class CategoryUpdatedEventData {
    private final Category collection;
}
