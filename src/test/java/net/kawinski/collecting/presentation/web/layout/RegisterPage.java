package net.kawinski.collecting.presentation.web.layout;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

@SuppressWarnings({"InstanceVariableMayNotBeInitialized", "ClassHasNoToStringMethod"})
@Location(RegisterPage.location)
public class RegisterPage extends DefaultLayout implements ICollectorAssistantPage {
    public static final String location = "user/register.xhtml";
    public static final String name = "Rejestracja";

    @FindBy(id = "registerForm:username")
    private WebElement userNameField;

    @FindBy(id = "registerForm:email")
    private WebElement emailField;

    @FindBy(id = "registerForm:password")
    private WebElement passwordField;

    @FindBy(id = "registerForm:registerButton")
    private WebElement registerButton;

    public void register(final String userName, final String email, final String password) {
        userNameField.sendKeys(userName);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        guardHttp(registerButton).click();
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getName() {
        return name;
    }

}
