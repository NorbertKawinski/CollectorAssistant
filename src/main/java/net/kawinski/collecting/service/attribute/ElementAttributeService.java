package net.kawinski.collecting.service.attribute;

import lombok.NoArgsConstructor;
import net.kawinski.collecting.data.model.ElementAttribute;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.data.repository.ElementAttributeRepository;
import net.kawinski.collecting.data.repository.ElementAttributeTemplateRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
public class ElementAttributeService {

    @Inject
    private ElementAttributeRepository elementAttributeRepository;

    @Inject
    private ElementAttributeTemplateRepository elementAttributeTemplateRepository;

    public void create(final ElementAttribute attribute) {
        elementAttributeRepository.persist(attribute);
    }

    public void delete(final ElementAttribute attribute) {
        elementAttributeRepository.delete(attribute);
    }

    public Optional<ElementAttribute> findById(final long id) {
        return elementAttributeRepository.findById(id);
    }

    public ElementAttribute findByIdOrThrow(final long id) {
        return elementAttributeRepository.findByIdOrThrow(id);
    }

    public Optional<ElementAttributeTemplate> findTemplateById(final Long id) {
        return elementAttributeTemplateRepository.findById(id);
    }

    public ElementAttributeTemplate findTemplateByIdOrThrow(final Long id) {
        return elementAttributeTemplateRepository.findByIdOrThrow(id);
    }

    public void update(final ElementAttribute attribute) {
        elementAttributeRepository.update(attribute);
    }
}
