package net.kawinski.collecting.data.repository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.CollectionAttribute;
import net.kawinski.collecting.data.model.CollectionVisibility;
import net.kawinski.collecting.data.model.Collection_;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.search.CollectionSearchQuery;
import net.kawinski.collecting.data.search.filters.collection.BaseCollectionFilter;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;
import net.kawinski.utils.jee.NkBaseRepository;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class CollectionRepository extends NkBaseRepository<Long, Collection> {

    public Optional<Collection> findById(final long id, final boolean joinElements, final boolean joinAttributes) {
        final Optional<Collection> result = getEntityManager().createQuery("select col from Collection col " +
                (joinElements ? "left join fetch col.elements " : "") +
                "where col.id = :id", Collection.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
        if(joinAttributes)
            result.ifPresent(collection -> collection.getAttributes()
                    .sort(Comparator.comparingInt(attribute -> attribute.getAttributeTemplate().getDisplayOrder()))
            );
        return result;
    }

    public Collection findByIdOrThrow(final long id, final boolean joinElements, final boolean joinAttributes) {
        return findById(id, joinElements, joinAttributes)
                .orElseThrow(() -> new IllegalStateException("Couldn't find " + eClass + " with id: " + id));
    }

    public PagedResult<Collection> findByFilters(final List<? extends BaseCollectionFilter> filters, Page page) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Collection> searchQuery = cb.createQuery(Collection.class);
        final Root<Collection> searchQueryRoot = searchQuery.from(Collection.class);
        searchQueryRoot.fetch(Collection_.IMAGE, JoinType.LEFT);

        searchQuery.select(searchQueryRoot);

        final CollectionSearchQuery csq = new CollectionSearchQuery(cb, searchQueryRoot);
        final Predicate[] searchQueryPredicates = filters.stream()
                .map(filter -> filter.createPredicateFor(csq))
                .toArray(Predicate[]::new);
        searchQuery.where(searchQueryPredicates);

        searchQuery.distinct(true);

        List<Collection> list = getPage(page,
                getEntityManager().createQuery(searchQuery))
                .getResultList();

        CriteriaQuery<Long> recordsQuery = cb.createQuery(Long.class);
        Root<Collection> recordsQueryRoot = recordsQuery.from(Collection.class);
        recordsQuery.select(cb.count(recordsQueryRoot));

        final CollectionSearchQuery csq2 = new CollectionSearchQuery(cb, recordsQueryRoot);
        final Predicate[] recordsQueryPredicates = filters.stream()
                .map(filter -> filter.createPredicateFor(csq2))
                .toArray(Predicate[]::new);
        recordsQuery.where(recordsQueryPredicates);

        recordsQuery.distinct(true);
        long records = getEntityManager().createQuery(recordsQuery)
                .getSingleResult();

        return new PagedResult<>(list, page, records);
    }

    public PagedResult<Collection> findByCategory(Category category, Page page) {
        List<Collection> list = getPage(page, getEntityManager().createQuery("" +
                "select col from Collection col " +
                "where col.category.id = :id " +
                "    and col.visibility = :public", Collection.class)
                .setParameter("id", category.getId())
                .setParameter("public", CollectionVisibility.PUBLIC))
                .getResultList();

        long records = getEntityManager().createQuery("" +
                "select count(col) from Collection col " +
                "where col.category.id = :id " +
                "    and col.visibility = :public", Long.class)
                .setParameter("id", category.getId())
                .setParameter("public", CollectionVisibility.PUBLIC)
                .getSingleResult();

        return new PagedResult<>(list, page, records);
    }

    public void addAttribute(final Collection collection, final CollectionAttribute attribute) {
//        final Collection managed = manage(collection); // this wouldn't join attributes
        final Collection managed = findByIdOrThrow(collection.getId(), false, true);
        managed.addAttribute(attribute);
//        update(managed);
    }

    public List<Element> getMissingElements(final Collection baseCollection, final Set<Element> presentElements) {
        String query = "select element from Element element " +
                "where element.presentIn = :baseCollection";
        if(!presentElements.isEmpty()) {
            // We already have presentElements which hold basedOn_id values. Therefore I'd love to make it like this:
            // >>> basedOnIds = presentElements.stream().map(Element::getBasedOn).map(GeneratedIdEntity::getId).collect(Collectors.toList());
            // >>> query += " and element not in (:basedOnIds)";
            // But basedOn is not joined from the caller, and because of our beloved JPA, we can't work on IDs - We have to do it on objects.
            // So we're left with this monstrosity which creates cross-join instead of a simple where clause.
            // >>> and (element0_.id not in  (select element1_.basedOn_id from Element element1_ cross join Element element2_ where element1_.basedOn_id=element2_.id and (element1_.id in (?))))
            query += " and element not in (select presentElement.basedOn from Element presentElement where presentElement in :presentElements)";
            // Hell, even if we could work on IDs directly, this would be much better:
            // >>> and (element0_.id not in  (select element2_.basedOn_id from Element element2_ where element2_.id in (?)))
        }

        final TypedQuery<Element> q = getEntityManager()
                .createQuery(query, Element.class)
                .setParameter("baseCollection", baseCollection);
        if(!presentElements.isEmpty()) {
            final List<Element> presentElementsWithBase = presentElements.stream()
                    .filter(Element::containsBase)
                    .collect(Collectors.toList());
            q.setParameter("presentElements", presentElementsWithBase);
        }
        return q.getResultList();
    }
}
