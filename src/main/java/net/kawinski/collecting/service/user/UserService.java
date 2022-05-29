package net.kawinski.collecting.service.user;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Group;
import net.kawinski.collecting.data.model.LoginToken;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.data.repository.LoginTokenRepository;
import net.kawinski.collecting.data.repository.UserRepository;
import net.kawinski.collecting.data.search.filters.collection.BaseCollectionFilter;
import net.kawinski.collecting.data.search.filters.collection.CollectionAttributeFilter;
import net.kawinski.collecting.data.search.filters.collection.CollectionCategoryFilter;
import net.kawinski.collecting.data.search.filters.collection.CollectionCategoryNameFilter;
import net.kawinski.collecting.data.search.filters.collection.CollectionNameFilter;
import net.kawinski.collecting.data.search.filters.collection.CollectionOwnerFilter;
import net.kawinski.collecting.data.search.filters.collection.CollectionVisibilityFilter;
import net.kawinski.collecting.data.search.filters.user.BaseUserFilter;
import net.kawinski.collecting.data.search.filters.user.UserEmailFilter;
import net.kawinski.collecting.data.search.filters.user.UserEmailVerifiedFilter;
import net.kawinski.collecting.data.search.filters.user.UserNameFilter;
import net.kawinski.collecting.service.search.SearchAttributeForm;
import net.kawinski.collecting.service.search.SearchCollectionVisibility;
import net.kawinski.collecting.service.search.SearchUserForm;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private LoginTokenRepository loginTokenRepository;

    @Inject
    private Event<UserRegisteredEventData> userRegisteredEventSrc;

    public User register(final UserRegistrationForm registrationForm) {
        try(final NkTrace trace = NkTrace.info(log, "registration form: {}", registrationForm)) {
            final User user = new User(registrationForm.getName(), registrationForm.getEmail(), registrationForm.getPassword());
            // Actually, this group is kind of required for the application to work properly.
            // Otherwise, user wouldn't be able to access most of the application pages.
            user.addGroups(Group.USER);
            userRepository.persist(user);
            userRegisteredEventSrc.fire(new UserRegisteredEventData(user));
            return trace.returning(user);
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByPrincipal(final CallerPrincipal principal) {
        return findByPrincipalName(principal.getName());
    }

    public Optional<User> findByPrincipalName(final String principalName) {
        try(final NkTrace trace = NkTrace.info(log, "principalName: {}", principalName)) {
            final long userId = Long.parseLong(principalName);
            return trace.returning(userRepository.findById(userId));
        }
    }

    public Optional<User> findByNameOrEmail(final String nameOrEmail) {
        try(final NkTrace trace = NkTrace.info(log, "nameOrEmail: {}", nameOrEmail)) {
            return trace.returning(userRepository.findByNameOrEmail(nameOrEmail));
        }
    }

    public Optional<User> findByLoginToken(final String rawToken, final LoginToken.TokenType tokenType) {
        try (final NkTrace trace = NkTrace.info(log, "rawToken: {}, tokenType: {}", rawToken, tokenType)) {
            final Optional<LoginToken> token = loginTokenRepository.findByTokenAndType(rawToken, tokenType);
            final Optional<User> user = token.map(LoginToken::getUser);
            return trace.returning(user);
        }
    }

    public void addGroups(final User user, final Group... groups) {
        try (final NkTrace trace = NkTrace.trace(log, "user: {}, groups: {}", user, groups)) {
            userRepository.addGroups(user, groups);
        }
    }

    public Optional<User> findById(final long id, final boolean joinCollections, final boolean joinDetails) {
        return userRepository.findById(id, joinCollections, joinDetails);
    }

    public User findByIdOrThrow(final long id, final boolean joinCollections, final boolean joinDetails) {
        return userRepository.findByIdOrThrow(id, joinCollections, joinDetails);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public PagedResult<User> findByFilters(SearchUserForm form, Page page) {
        final List<BaseUserFilter> filters = new ArrayList<>();

        // Search by name
        Optional.ofNullable(form.getName())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(UserNameFilter::new)
                .ifPresent(filters::add);

        // Search by email
        Optional.ofNullable(form.getEmail())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(UserEmailFilter::new)
                .ifPresent(filters::add);

        // Search by verified
        Optional.ofNullable(form.getEmailVerified())
                .map(UserEmailVerifiedFilter::new)
                .ifPresent(filters::add);

        return userRepository.findByFilters(filters, page);
    }
}
