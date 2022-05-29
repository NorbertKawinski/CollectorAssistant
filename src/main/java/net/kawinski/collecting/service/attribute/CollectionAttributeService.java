package net.kawinski.collecting.service.attribute;

import lombok.NoArgsConstructor;
import net.kawinski.collecting.data.model.CollectionAttribute;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.repository.CollectionAttributeRepository;
import net.kawinski.collecting.data.repository.CollectionAttributeTemplateRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
public class CollectionAttributeService {

    @Inject
    private CollectionAttributeRepository collectionAttributeRepository;

    @Inject
    private CollectionAttributeTemplateRepository collectionAttributeTemplateRepository;

    public void create(final CollectionAttribute attribute) {
        collectionAttributeRepository.persist(attribute);
    }

    public void delete(final CollectionAttribute attribute) {
        collectionAttributeRepository.delete(attribute);
    }

    public Optional<CollectionAttribute> findById(final long id) {
        return collectionAttributeRepository.findById(id);
    }

    public CollectionAttribute findByIdOrThrow(final long id) {
        return collectionAttributeRepository.findByIdOrThrow(id);
    }

    public Optional<CollectionAttributeTemplate> findTemplateById(final Long id) {
        return collectionAttributeTemplateRepository.findById(id);
    }

    public CollectionAttributeTemplate findTemplateByIdOrThrow(final Long id) {
        return collectionAttributeTemplateRepository.findByIdOrThrow(id);
    }


    public void update(final CollectionAttribute attribute) {
        collectionAttributeRepository.update(attribute);
    }
}
