package net.kawinski.collecting.service.attribute;

import lombok.NoArgsConstructor;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.repository.CollectionAttributeTemplateRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
public class CollectionAttributeTemplateService {

    @Inject
    private CollectionAttributeTemplateRepository collectionAttributeTemplateRepository;

    public void create(final CollectionAttributeTemplate attribute) {
        collectionAttributeTemplateRepository.persist(attribute);
    }

    public void delete(final CollectionAttributeTemplate attribute) {
        collectionAttributeTemplateRepository.delete(attribute);
    }

    public Optional<CollectionAttributeTemplate> findById(final long id) {
        return collectionAttributeTemplateRepository.findById(id);
    }

    public CollectionAttributeTemplate findByIdOrThrow(final long id) {
        return collectionAttributeTemplateRepository.findByIdOrThrow(id);
    }

    public void update(final CollectionAttributeTemplate attributeTemplate) {
        collectionAttributeTemplateRepository.update(attributeTemplate);
    }
}
