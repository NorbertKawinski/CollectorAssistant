package net.kawinski.collecting.service.auth;

import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.LoginToken;
import net.kawinski.logging.NkTrace;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import java.util.Set;

@ApplicationScoped
@Slf4j
public class CaRememberMeIdentityStore implements RememberMeIdentityStore {

    private final AuthService authService;

    @Inject
    public CaRememberMeIdentityStore(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public CredentialValidationResult validate(final RememberMeCredential credential) {
        try(final NkTrace trace = NkTrace.info(log, "credential: {}", credential)) {
            final String token = credential.getToken();
            return trace.returning(authService.validateToken(token, LoginToken.TokenType.REMEMBER_ME));
        }
    }

    @Override
    public String generateLoginToken(final CallerPrincipal callerPrincipal, final Set<String> groups) {
        try(final NkTrace trace = NkTrace.info(log, "callerPrincipal: {}, groups: {}", callerPrincipal, groups)) {
            return trace.returning(authService.generateRememberMeToken(callerPrincipal));
        }
    }

    @Override
    public void removeLoginToken(final String rawToken) {
        try(final NkTrace trace = NkTrace.info(log, "rawToken: {}", rawToken)) {
            authService.removeLoginToken(rawToken);
        }
    }

}
