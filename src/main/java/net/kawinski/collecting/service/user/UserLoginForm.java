package net.kawinski.collecting.service.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.kawinski.collecting.data.model.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginForm {

    @NotEmpty
    // At the moment of writing, the email column length is (and probably will be) longer than user's name column.
    // Sadly, we cannot use Math.max(MAX_NAME_LENGTH, MAX_EMAIL_LENGTH) because it's not constant
    // so we have to pick something manually. email it is.
    // @Size(min = Math.min(User.MIN_NAME_LENGTH, User.MAX_EMAIL_LENGTH), max = Math.max(User.MAX_NAME_LENGTH, User.MAX_EMAIL_LENGTH))
    @Size(min = 1, max = User.MAX_EMAIL_LENGTH)
    private String nameOrEmail;

    @NotEmpty
    @Size(min = User.MIN_PASSWORD_LENGTH, max = User.MAX_PASSWORD_LENGTH)
    @ToString.Exclude // :)
    private String password;

    @NotNull
    private boolean rememberMe;
}
