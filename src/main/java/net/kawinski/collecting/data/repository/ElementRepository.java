package net.kawinski.collecting.data.repository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.CollectionVisibility;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.ElementAttribute;
import net.kawinski.collecting.data.model.Element_;
import net.kawinski.collecting.data.search.ElementSearchQuery;
import net.kawinski.collecting.data.search.filters.element.BaseElementFilter;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;
import net.kawinski.utils.jee.NkBaseRepository;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class ElementRepository extends NkBaseRepository<Long, Element> {

    public Optional<Element> findById(final long id, final boolean joinAttributes) {
        final Optional<Element> result = getEntityManager().createQuery("select element from Element element " +
                (joinAttributes ? "left join fetch element.attributes " : "") +
                "where element.id = :id", Element.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
        if(joinAttributes)
            result.ifPresent(element -> element.getAttributes()
                    .sort(Comparator.comparingInt(attribute -> attribute.getAttributeTemplate().getDisplayOrder()))
            );
        return result;
    }

    public Element findByIdOrThrow(final long id, final boolean joinAttributes) {
        return findById(id, joinAttributes)
                .orElseThrow(() -> new IllegalStateException("Couldn't find Element with id: " + id));
    }

    public void addAttribute(final Element element, final ElementAttribute attribute) {
        final Element managed = findByIdOrThrow(element.getId(), true);
        managed.addAttribute(attribute);
    }

    // For ImgSearch
    public List<Element> findAfterId(final long id, final int limit) {
        return getEntityManager().createQuery("select element from Element element " +
            "join fetch element.presentIn " +
            "left join fetch element.image " +
            "where element.id > :id", Element.class)
            .setMaxResults(limit)
            .setParameter("id", id)
            .getResultList();
    }

    public PagedResult<Element> findByFilters(final List<? extends BaseElementFilter> filters, Page page) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Element> searchQuery = cb.createQuery(Element.class);
        final Root<Element> searchElementRoot = searchQuery.from(Element.class);
        searchElementRoot.fetch(Element_.presentIn);
        searchElementRoot.fetch(Element_.IMAGE, JoinType.LEFT);

        searchQuery.select(searchElementRoot);

        final ElementSearchQuery csq = new ElementSearchQuery(cb, searchElementRoot);
        final Predicate[] searchElementPredicates = filters.stream()
            .map(filter -> filter.createPredicateFor(csq))
            .toArray(Predicate[]::new);
        searchQuery.where(searchElementPredicates);

        searchQuery.distinct(true);

        List<Element> list = getPage(page,
            getEntityManager().createQuery(searchQuery))
            .getResultList();

        CriteriaQuery<Long> recordsQuery = cb.createQuery(Long.class);
        Root<Element> recordsQueryRoot = recordsQuery.from(Element.class);
        recordsQuery.select(cb.count(recordsQueryRoot));

        final ElementSearchQuery csq2 = new ElementSearchQuery(cb, recordsQueryRoot);
        final Predicate[] recordsQueryPredicates = filters.stream()
                .map(filter -> filter.createPredicateFor(csq2))
                .toArray(Predicate[]::new);
        recordsQuery.where(recordsQueryPredicates);

        recordsQuery.distinct(true);
        long records = getEntityManager().createQuery(recordsQuery)
                .getSingleResult();

        return new PagedResult<>(list, page, records);
    }

    public PagedResult<Element> findByCollection(Collection collection, Page page) {
        List<Element> list = getPage(page, getEntityManager().createQuery("" +
                "select ele from Element ele " +
                "where ele.presentIn.id = :id ", Element.class)
                .setParameter("id", collection.getId()))
                .getResultList();

        long records = getEntityManager().createQuery("" +
                "select count(ele) from Element ele " +
                "where ele.presentIn.id = :id ", Long.class)
                .setParameter("id", collection.getId())
                .getSingleResult();

        return new PagedResult<>(list, page, records);
    }

    public Stream<Element> findByUpload(Long uploadId) {
        return getEntityManager().createQuery("" +
                "select ele from Element ele " +
                "where ele.image.image.id = :uploadId ", Element.class)
                .setParameter("uploadId", uploadId)
                .getResultStream();
    }
}
