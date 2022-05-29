package net.kawinski.collecting.presentation.web.layout;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

@SuppressWarnings({"InstanceVariableMayNotBeInitialized", "ClassHasNoToStringMethod"})
@Location(LoginPage.location)
public class LoginPage extends DefaultLayout implements ICollectorAssistantPage {
    public static final String location = "user/login.xhtml";
    public static final String name = "Logowanie";

    @FindBy(id = "loginForm:username")
    private WebElement userNameField;

    @FindBy(id = "loginForm:password")
    private WebElement passwordField;

    @FindBy(id = "loginForm:rememberMe")
    private WebElement rememberMeCheckbox;

    @FindBy(id = "loginForm:loginButton")
    private WebElement loginButton;

    public void login(final String userName, final String password) {
        login(userName, password, false);
    }

    public void login(final String userName, final String password, final boolean rememberMe) {
        userNameField.sendKeys(userName);
        passwordField.sendKeys(password);
        if(rememberMe)
            rememberMeCheckbox.click();

        guardHttp(loginButton).click();
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
