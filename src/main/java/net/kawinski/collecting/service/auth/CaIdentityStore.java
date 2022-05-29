package net.kawinski.collecting.service.auth;

import lombok.extern.slf4j.Slf4j;
import net.kawinski.logging.NkTrace;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
@Slf4j
public class CaIdentityStore implements IdentityStore {

    private final AuthService authService;

    @Inject
    public CaIdentityStore(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public CredentialValidationResult validate(final Credential credential) {
        try(final NkTrace trace = NkTrace.info(log, "credential: {}", credential)) {
            if (credential instanceof UsernamePasswordCredential) {
                final UsernamePasswordCredential upCredential = (UsernamePasswordCredential) credential;
                final String login = upCredential.getCaller();
                final String password = upCredential.getPasswordAsString();
                return authService.validatePassword(login, password);
            }
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
    }

}
