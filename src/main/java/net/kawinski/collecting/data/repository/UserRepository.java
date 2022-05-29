package net.kawinski.collecting.data.repository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.Collection_;
import net.kawinski.collecting.data.model.Group;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.data.search.CollectionSearchQuery;
import net.kawinski.collecting.data.search.UserSearchQuery;
import net.kawinski.collecting.data.search.filters.user.BaseUserFilter;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;
import net.kawinski.utils.jee.NkBaseRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class UserRepository extends NkBaseRepository<Long, User> {

    @Inject
    private EntityManager em;

    public Optional<User> findById(final long id, final boolean joinCollections, final boolean joinDetails) {
        final Optional<User> result = getEntityManager().createQuery("select user from User user " +
                (joinDetails ? "left join fetch user.userDetails " : "") +
                "where user.id = :id", User.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
        if(joinCollections)
            //noinspection ResultOfMethodCallIgnored
            result.ifPresent(user -> user.getCollections().size());
        return result;
    }

    public User findByIdOrThrow(final long id, final boolean joinCollections, final boolean joinDetails) {
        return findById(id, joinCollections, joinDetails)
                .orElseThrow(() -> new IllegalStateException("Couldn't find User with id: " + id));
    }

    /**
     * We support login by entering either email or username.
     *
     * In case of conflicts, the email takes precedence.
     * This kind of prioritization avoids 'trolling' like someone setting username 'davetheuser@example.com'
     * which could prevent the actual davetheuser from logging in using his email.
     * Had we prioritized username first, davetheuser wouldn't be able to log in by using his email.
     * Of course, this goes the other way around, but, let's be honest, who (besides trolls) picks 'davetheuser@example.com' as their username?
     */
    public Optional<User> findByNameOrEmail(final String nameOrEmail) {
        try (final NkTrace trace = NkTrace.info(log, "nameOrEmail: {}", nameOrEmail)) {

            final TypedQuery<User> query = em
                    .createQuery("select u from User u " +
                            "where u.name = :name or u.email = :email", User.class);
            query.setParameter("name", nameOrEmail);
            query.setParameter("email", nameOrEmail);
            final List<User> users = query.getResultList();

            // Prioritize the email first
            for (final User user : users) {
                if (user.getEmail().equalsIgnoreCase(nameOrEmail))
                    return trace.returning(Optional.of(user));
            }

            // Otherwise, return whatever is available
            return trace.returning(users.stream()
                    .findFirst());
        }
    }

    public void addGroups(final User userArg, final Group... groups) {
        try (final NkTrace trace = NkTrace.info(log, "userArg: {}, groups: {}", userArg, groups)) {
            final User user = manage(userArg);
            user.addGroups(groups);
        }
    }

    public PagedResult<User> findByFilters(List<BaseUserFilter> filters, Page page) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<User> searchQuery = cb.createQuery(User.class);
        final Root<User> searchQueryRoot = searchQuery.from(User.class);

        searchQuery.select(searchQueryRoot);

        final UserSearchQuery csq = new UserSearchQuery(cb, searchQueryRoot);
        final Predicate[] searchQueryPredicates = filters.stream()
                .map(filter -> filter.createPredicateFor(csq))
                .toArray(Predicate[]::new);
        searchQuery.where(searchQueryPredicates);

        searchQuery.distinct(true);

        List<User> list = getPage(page,
                getEntityManager().createQuery(searchQuery))
                .getResultList();

        CriteriaQuery<Long> recordsQuery = cb.createQuery(Long.class);
        Root<User> recordsQueryRoot = recordsQuery.from(User.class);
        recordsQuery.select(cb.count(recordsQueryRoot));

        final UserSearchQuery csq2 = new UserSearchQuery(cb, recordsQueryRoot);
        final Predicate[] recordsQueryPredicates = filters.stream()
                .map(filter -> filter.createPredicateFor(csq2))
                .toArray(Predicate[]::new);
        recordsQuery.where(recordsQueryPredicates);

        recordsQuery.distinct(true);
        long records = getEntityManager().createQuery(recordsQuery)
                .getSingleResult();

        return new PagedResult<>(list, page, records);
    }
}
