package net.kawinski.collecting.service.attribute;

import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.service.category.CategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {CategoryService.class})
public abstract class AttributeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "categoryId", target = "category")
    public abstract CollectionAttributeTemplate mapCollectionTemplate(CreateAttributeTemplateForm form);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "categoryId", target = "category")
    public abstract ElementAttributeTemplate mapElementTemplate(CreateAttributeTemplateForm form);

}
