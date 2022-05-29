package net.kawinski.collecting.data.repository;

import lombok.NoArgsConstructor;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.utils.jee.NkBaseRepository;

import javax.ejb.Stateless;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
public class CollectionAttributeTemplateRepository extends NkBaseRepository<Long, CollectionAttributeTemplate> {

}
