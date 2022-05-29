package net.kawinski.collecting.service.attribute;

import lombok.NoArgsConstructor;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.data.repository.ElementAttributeTemplateRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
public class ElementAttributeTemplateService {

    @Inject
    private ElementAttributeTemplateRepository elementAttributeTemplateRepository;

    public void create(final ElementAttributeTemplate attribute) {
        elementAttributeTemplateRepository.persist(attribute);
    }

    public void delete(final ElementAttributeTemplate attribute) {
        elementAttributeTemplateRepository.delete(attribute);
    }

    public Optional<ElementAttributeTemplate> findById(final long id) {
        return elementAttributeTemplateRepository.findById(id);
    }

    public ElementAttributeTemplate findByIdOrThrow(final long id) {
        return elementAttributeTemplateRepository.findByIdOrThrow(id);
    }

    public void update(final ElementAttributeTemplate attributeTemplate) {
        elementAttributeTemplateRepository.update(attributeTemplate);
    }
}
