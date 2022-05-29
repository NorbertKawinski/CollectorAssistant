package net.kawinski.collecting.data.repository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.LoginToken;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.NkBaseRepository;
import net.kawinski.utils.jee.RepositoryUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class LoginTokenRepository extends NkBaseRepository<Long, LoginToken> {

    @Inject
    private EntityManager em;

    public Optional<LoginToken> findByToken(final String rawToken) {
        try (final NkTrace trace = NkTrace.info(log, "rawToken: {}", rawToken)) {
            final String tokenHash = LoginToken.hashToken(rawToken);
            return trace.returning(
                    em.createQuery("select lt from LoginToken lt " +
                            "where lt.tokenHash = :tokenHash", LoginToken.class)
                    .setParameter("tokenHash", tokenHash)
                    .getResultStream()
                    .findFirst()
            );
        }
    }

    public Optional<LoginToken> findByTokenAndType(final String rawToken, final LoginToken.TokenType tokenType) {
        try (final NkTrace trace = NkTrace.info(log, "rawToken: {}, tokenType: {}", rawToken, tokenType)) {
            final String tokenHash = LoginToken.hashToken(rawToken);
            return trace.returning(
                    em.createQuery("select lt from LoginToken lt " +
                            "where lt.tokenHash = :tokenHash and lt.type = :type", LoginToken.class)
                    .setParameter("tokenHash", tokenHash)
                    .setParameter("type", tokenType)
                    .getResultStream()
                    .findFirst()
            );
        }
    }

    public void deleteByToken(final String rawToken) {
        try (final NkTrace trace = NkTrace.info(log, "rawToken: {}", rawToken)) {
            final String tokenHash = LoginToken.hashToken(rawToken);
            RepositoryUtils.executeUpdateSingle(
                    em.createQuery("delete from LoginToken lt " +
                            "where lt.tokenHash = :tokenHash")
                    .setParameter("tokenHash", tokenHash)
            );
        }
    }
}
