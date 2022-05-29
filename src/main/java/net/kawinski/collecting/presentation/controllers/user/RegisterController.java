package net.kawinski.collecting.presentation.controllers.user;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.service.user.UserRegistrationForm;
import net.kawinski.collecting.service.user.UserService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.java.JavaUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

@Model
@Slf4j
public class RegisterController {

    private final FacesContext facesContext;
    private final UserService userService;

    @Getter
    private final UserRegistrationForm newUser = new UserRegistrationForm();

    @Inject
    public RegisterController(final FacesContext facesContext, final UserService userService) {
        this.facesContext = facesContext;
        this.userService = userService;
    }

    public void register() {
        try(final NkTrace trace = NkTrace.info(log, "new user: {}", newUser)) {
            try {
                userService.register(newUser);
                Messages.addFlashGlobalInfo("Rejestracja udana, Możesz teraz się zalogować");
                JsfRedirectHelper.redirectIndex();
            } catch (final Exception e) {
                log.error("", e);
                final String errorMessage = JavaUtils.getRootErrorMessage(e);
                final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Rejestracja nieudana");
                facesContext.addMessage(null, m);
            }
        }
    }

    // Sadly, Class-level constraints are not triggered automatically by JSF during validation phase.
    // You can use only field-level constraints.
    // Moreover not all fields are valuated by JSF but only those that are in your facelet.
    // See: https://stackoverflow.com/a/11989937
    // To overcome this issue, manual validation was added below:
    public void validatePasswordMatch(FacesContext context, UIComponent component, Object value) {
        // Retrieve the temporary value from the password field
        UIInput passwordInput = (UIInput) component.findComponent("password");
        String password = (String) passwordInput.getLocalValue();
        String passwordRepeat = (String) value;

        if (password == null || !password.equals(passwordRepeat)) {
            String message = "Hasła nie są takie same";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
            throw new ValidatorException(msg);
        }
    }
}
