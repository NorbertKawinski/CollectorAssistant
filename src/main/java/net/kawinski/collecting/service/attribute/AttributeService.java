package net.kawinski.collecting.service.attribute;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.BaseAttributeTemplate;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.data.repository.CollectionAttributeTemplateRepository;
import net.kawinski.collecting.data.repository.ElementAttributeTemplateRepository;
import net.kawinski.collecting.service.category.CategoryService;

import javax.ejb.Stateless;
import javax.inject.Inject;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class AttributeService {

    @Inject
    private CategoryService categoryService;

    @Inject
    private ElementAttributeTemplateRepository elementAttributeTemplateRepository;

    @Inject
    private CollectionAttributeTemplateRepository collectionAttributeTemplateRepository;

    @Inject
    private AttributeMapper attributeMapper;

    public BaseAttributeTemplate create(final CreateAttributeTemplateForm form) {
        switch(form.getAttributeTarget()) {
            case Collection:
                return createForCollection(form);
            case Element:
                return createForElement(form);
            default:
                throw new IllegalStateException("Unknown target: " + form.getAttributeTarget());
        }
    }

    public CollectionAttributeTemplate createForCollection(final CreateAttributeTemplateForm form) {
        if(form.getAttributeTarget() != AttributeTarget.Collection) {
            throw new IllegalArgumentException("invalid attribute target");
        }
        final CollectionAttributeTemplate template = attributeMapper.mapCollectionTemplate(form);
        collectionAttributeTemplateRepository.persist(template);
        return template;
    }

    public ElementAttributeTemplate createForElement(final CreateAttributeTemplateForm form) {
        if(form.getAttributeTarget() != AttributeTarget.Element) {
            throw new IllegalArgumentException("invalid attribute target");
        }
        final ElementAttributeTemplate template = attributeMapper.mapElementTemplate(form);
        elementAttributeTemplateRepository.persist(template);
        return template;
    }
}
