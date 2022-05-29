package net.kawinski.utils.jee;

import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.Resources;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.data.search.filters.user.BaseUserFilter;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;
import org.omnifaces.persistence.model.BaseEntity;
import org.omnifaces.persistence.service.BaseEntityService;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Stream;

/**
 * Base class for project repositories. Provides:
 * - Helper methods common for CRUD repositories
 * - Logging for most "non-getter/non-finder" methods.
 * @param <I> ID type of the entity ('E' parameter)
 * @param <E> Entity type
 */
@Slf4j
public abstract class NkBaseRepository<I extends Comparable<I> & Serializable, E extends BaseEntity<I>> extends BaseEntityService<I, E> {

    protected final Class<I> iClass;
    protected final Class<E> eClass;

    @SuppressWarnings("unchecked")
    public NkBaseRepository() {
//        this.iClass = (Class<I>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        this.eClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

        Type genericSuperClass = getClass().getGenericSuperclass();

        ParameterizedType parametrizedType = null;
        while (parametrizedType == null) {
            if ((genericSuperClass instanceof ParameterizedType)) {
                parametrizedType = (ParameterizedType) genericSuperClass;
            } else {
                genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
            }
        }

        this.iClass = (Class<I>) parametrizedType.getActualTypeArguments()[0];
        this.eClass = (Class<E>) parametrizedType.getActualTypeArguments()[1];
    }

    public NkBaseRepository(final Class<I> iClass, final Class<E> eClass) {
        this.iClass = iClass;
        this.eClass = eClass;
    }

    @Override
    public I persist(final E entity) {
        log.info("persisting: {}", entity);
        return super.persist(entity);
    }

    @Override
    public E update(final E entity) {
        log.info("updating: {}", entity);
        return super.update(entity);
    }

    @Override
    public E save(final E entity) {
        log.info("saving: {}", entity);
        return super.save(entity);
    }

    @Override
    public void delete(final E entity) {
        log.info("deleting: {}", entity);
        super.delete(entity);
    }

    @Override
    public void softDelete(final E entity) {
        log.info("soft deleting: {}", entity);
        super.softDelete(entity);
    }

    @Override
    public void softUndelete(final E entity) {
        log.info("soft undeleting: {}", entity);
        super.softUndelete(entity);
    }

    @Override
    public void reset(final E entity) {
        log.info("resetting: {}", entity);
        super.reset(entity);
    }

    public E findByIdOrThrow(final I id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find '" + eClass + "' with ID: " + id));
    }

    public List<E> findAll() {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<E> query = cb.createQuery(eClass);
        final Root<E> root = query.from(eClass);
        query.select(root);
        return getEntityManager().createQuery(query).getResultList();
    }

    public <T> TypedQuery<T> getPage(Page page, TypedQuery<T> query) {
        return query
                .setFirstResult((page.getNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize());
    }
}
