package net.kawinski.collecting.service.auth;

import lombok.extern.slf4j.Slf4j;
import net.kawinski.logging.NkTrace;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AutoApplySession // For "Is user already logged-in?"
@RememberMe(
        cookieSecureOnly = false, // Remove this when login is served over HTTPS.
        cookieMaxAgeSeconds = 60 * 60 * 24 * 14) // 14 days.
@LoginToContinue(
        loginPage = "/user/login.xhtml?continue=true",
        errorPage = "",
        useForwardToLogin = false)
@ApplicationScoped
@Slf4j
public class CaFormAuthenticationMechanismConfig implements HttpAuthenticationMechanism {

    private final IdentityStoreHandler identityStoreHandler;

    @Inject
    public CaFormAuthenticationMechanismConfig(final IdentityStoreHandler identityStoreHandler) {
        this.identityStoreHandler = identityStoreHandler;
    }

    @Override
    public AuthenticationStatus validateRequest(final HttpServletRequest request, final HttpServletResponse response, final HttpMessageContext context) {
        try(final NkTrace trace = NkTrace.trace(log, "Authenticating request for: {}", request.getRequestURI())) {
            final Credential credential = context.getAuthParameters().getCredential();
            log.trace("credential: {}", credential);

            if (credential != null) {
                return context.notifyContainerAboutLogin(identityStoreHandler.validate(credential));
            } else {
                return context.doNothing();
            }
        }
    }

}
