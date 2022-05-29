package net.kawinski.collecting.service.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.kawinski.collecting.data.model.User;
import net.kawinski.utils.jee.EqualFields;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualFields(baseField = "password", matchField = "passwordRepeat")
public class UserRegistrationForm {

    @NotEmpty
    @Size(min = User.MIN_NAME_LENGTH, max = User.MAX_NAME_LENGTH)
    @Pattern(regexp = User.NAME_PATTERN)
    private String name;

    @NotEmpty
    @Email
    @Size(max = User.MAX_EMAIL_LENGTH)
    private String email;

    @NotEmpty
    @Size(min = User.MIN_PASSWORD_LENGTH, max = User.MAX_PASSWORD_LENGTH)
    @ToString.Exclude // :)
    private String password;

    @NotEmpty
    @Size(min = User.MIN_PASSWORD_LENGTH, max = User.MAX_PASSWORD_LENGTH)
    @ToString.Exclude // :)
    private String passwordRepeat;

    @AssertTrue
    private boolean acceptRules;

}
