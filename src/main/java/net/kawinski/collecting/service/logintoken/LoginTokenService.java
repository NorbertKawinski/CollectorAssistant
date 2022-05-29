package net.kawinski.collecting.service.logintoken;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.LoginToken;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.data.repository.LoginTokenRepository;
import net.kawinski.logging.NkTrace;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class LoginTokenService {

    @Inject
    private LoginTokenRepository loginTokenRepository;

    public String generateRememberMeToken(final User user) {
        return generate(user, LoginToken.TokenType.REMEMBER_ME, Duration.ofDays(365));
    }

    public String generate(final User user, final LoginToken.TokenType type, final Duration expiresIn) {
        try(final NkTrace trace = NkTrace.info(log, "user: {}, type: {}, expiresIn: {}", user, type, expiresIn)) {

            final String rawToken;
            while(true) {
                 final String tryToken = UUID.randomUUID().toString();
                 // Even though the chance is minuscule, we're playing it safe.
                 final Optional<LoginToken> existingToken = loginTokenRepository.findByToken(tryToken);
                 if(!existingToken.isPresent()) {
                     rawToken = tryToken;
                     break;
                 }
            }

            final LoginToken token = new LoginToken(user, type, rawToken, expiresIn);
            user.addLoginToken(token);
//            loginTokenRepository.persist(token);
            return trace.returning(rawToken);
        }
    }

    public Optional<LoginToken> findByRawTokenAndType(final String rawToken, final LoginToken.TokenType tokenType) {
        try(final NkTrace trace = NkTrace.info(log, "rawToken: {}, tokenType: {}", rawToken, tokenType)) {
            return trace.returning(loginTokenRepository.findByTokenAndType(rawToken, tokenType));
        }
    }

    public void remove(final String rawToken) {
        try(final NkTrace trace = NkTrace.info(log, "rawToken: {}", rawToken)) {
            loginTokenRepository.deleteByToken(rawToken);
        }
    }
}
