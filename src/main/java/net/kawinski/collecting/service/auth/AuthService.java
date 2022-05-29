package net.kawinski.collecting.service.auth;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.LoginToken;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.service.exception.EmailNotVerifiedException;
import net.kawinski.collecting.service.logintoken.LoginTokenService;
import net.kawinski.collecting.service.user.UserService;
import net.kawinski.logging.NkTrace;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class AuthService {

    @Inject
    private UserService userService;

    @Inject
    private LoginTokenService loginTokenService;

    public CredentialValidationResult validateToken(final String token, final LoginToken.TokenType tokenType) {
        try(final NkTrace trace = NkTrace.info(log, "token: {}, tokenType: {}", token, tokenType)) {

            final Optional<User> userOpt = userService.findByLoginToken(token, LoginToken.TokenType.REMEMBER_ME);
            if (!userOpt.isPresent()) {
                return trace.returning(CredentialValidationResult.INVALID_RESULT, "token not found");
            }
            final User user = userOpt.get();

            return trace.returning(validateUser(user));
        }
    }

    public CredentialValidationResult validatePassword(final String nameOrEmail, final String password) {
        try(final NkTrace trace = NkTrace.info(log, "nameOrEmail: {}, password: ******", nameOrEmail)) {

            final Optional<User> userOpt = userService.findByNameOrEmail(nameOrEmail);
            if (!userOpt.isPresent()) {
                return trace.returning(CredentialValidationResult.INVALID_RESULT, "user not found");
            }
            final User user = userOpt.get();

            if (!user.getCredentials().isValid(password)) {
                return trace.returning(CredentialValidationResult.INVALID_RESULT, "invalid password");
            }

            return trace.returning(validateUser(user));
        }
    }

    private CredentialValidationResult validateUser(final User user) {
        try(final NkTrace trace = NkTrace.info(log, "user: {}", user)) {
            if (!user.isEmailVerified()) {
                throw trace.returning(new EmailNotVerifiedException(user.getName() + "'s email is not verified"));
            }

            final CaCallerPrincipal userPrincipal = new CaCallerPrincipal(user);
            final Set<String> userGroups = user.getGroups().stream()
                    .map(Enum::toString)
                    .collect(Collectors.toSet());
            trace.setExitMsg("Login OK for user: {} with groups: {}", user.getName(), userGroups);
            return new CredentialValidationResult(userPrincipal, userGroups);
        }
    }

    public String generateRememberMeToken(final CallerPrincipal callerPrincipal) {
        try(final NkTrace trace = NkTrace.info(log, "callerPrincipal: {}", callerPrincipal)) {

            final Optional<User> userOpt = userService.findByPrincipal(callerPrincipal);
            if(!userOpt.isPresent())
                throw new IllegalStateException("Couldn't find user for caller principal: " + callerPrincipal);
            final User user = userOpt.get();

            return loginTokenService.generateRememberMeToken(user);
        }
    }

    public void removeLoginToken(final String rawToken) {
        try(final NkTrace trace = NkTrace.info(log, "rawToken: {}", rawToken)) {
            loginTokenService.remove(rawToken);
        }
    }


}
