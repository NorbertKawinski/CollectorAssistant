package net.kawinski.collecting.presentation.controllers.user;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.service.user.UserLoginForm;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.java.JavaUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;

import static org.omnifaces.util.Faces.getFlash;
import static org.omnifaces.util.Faces.getRequest;
import static org.omnifaces.util.Faces.getRequestContextPath;
import static org.omnifaces.util.Faces.getResponse;
import static org.omnifaces.util.Faces.redirect;

@Model
@Slf4j
public class LoginController {

    private final FacesContext facesContext;
    private final SecurityContext securityContext;

    /**
     * Provides 'login to continue' feature.
     * #1. When displaying login form, its value is taken from the request query param in @PostConstruct.
     * #2. When submitting form, this value is also taken from the query param,
     *     which is always false, because submitting form don't copy query params.
     *     Then this value is immediately overwritten with the login's form value which equals to #1.
     */
    @Getter
    @Setter
    private boolean loginToContinue = false;

    @Getter
    @Setter
    private UserLoginForm loginCredentials;

    @Inject
    public LoginController(final FacesContext facesContext, final SecurityContext securityContext) {
        this.facesContext = facesContext;
        this.securityContext = securityContext;
    }

    @PostConstruct
    public void postConstruct() {
        final String param = getRequest().getParameter("continue");
        setLoginToContinue(param != null && param.equalsIgnoreCase("true"));

        resetForm();
    }

    public void resetForm() {
        loginCredentials = new UserLoginForm();
    }

    public void login() {
        try(final NkTrace trace = NkTrace.info(log, "login form: {}", loginCredentials)) {
            try {

                switch (continueAuthentication()) {
                    case SEND_CONTINUE:
                        facesContext.responseComplete();
                        break;
                    case SEND_FAILURE:
                        facesContext.addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logowanie nieudane", null));
                        break;
                    case SUCCESS:
                        facesContext.addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO, "Logowanie udane", null));

                        getFlash().setKeepMessages(true);
//                        redirect(getRequestContextPath() + "/index.xhtml?faces-redirect=true");
                        if(!isLoginToContinue())
                            redirect(getRequestContextPath() + "/index.xhtml");
                        break;
                    case NOT_DONE:
                }

                resetForm();
            } catch (final Exception e) {
                log.error("", e);
                final String errorMessage = JavaUtils.getRootErrorMessage(e);
                final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Unexpected error during the login process");
                facesContext.addMessage(null, m);
            }
        }
    }

    private AuthenticationStatus continueAuthentication() {
        try(final NkTrace trace = NkTrace.info(log, "login form: {}", loginCredentials)) {
            final AuthenticationParameters authParams = AuthenticationParameters.withParams()
                    .credential(new UsernamePasswordCredential(loginCredentials.getNameOrEmail(), loginCredentials.getPassword()))
                    .newAuthentication(!isLoginToContinue())
                    .rememberMe(loginCredentials.isRememberMe());
            log.info("auth params: {}", authParams);

            return trace.returning(securityContext.authenticate(getRequest(), getResponse(), authParams));
        }
    }

}
