package net.kawinski.collecting.presentation.web.layout;

import net.kawinski.collecting.presentation.web.WebTestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.jboss.arquillian.graphene.Graphene.guardNoRequest;

@SuppressWarnings("InstanceVariableMayNotBeInitialized") // Selenium/Graphene will initialize it
public abstract class DefaultLayout implements ICollectorAssistantPage {

    @FindBy(id = "globalFlashMessages")
    private WebElement flashMessages;

    @FindBy(id = "navbar")
    private WebElement navbar;

    @FindBy(id = "navbar-caLogo")
    private WebElement navbar_caLogo;

    @FindBy(id = "navbar-toggler-button")
    private WebElement navbar_toggler_button;

    @FindBy(id = "navbar-homePage-link")
    private WebElement navbar_homePage_link;

    @FindBy(id = "navbar-collections")
    private WebElement navbar_collections;

    @FindBy(id = "navbar-collections-dropdown")
    private WebElement navbar_collections_dropdown;

    @FindBy(id = "navbar-collections-all-link")
    private WebElement navbar_collections_all_link;

    @FindBy(id = "navbar-collections-my-link")
    private WebElement navbar_collections_my_link;

    // TODO: @FindBy(nkPath = "navbar-collections.collection-category-link")
    // TODO: public List<WebElement> navbar_collections_roots;

    @FindBy(id = "navbar-market-link")
    private WebElement navbar_market_link;

    @FindBy(id = "navbar-adminPanel-link")
    private WebElement navbar_adminPanel_link;

    @FindBy(id = "navbar-userMenu-dropdown")
    private WebElement navbar_userMenu_dropdown;

    @FindBy(id = "navbar-userMenu-messages-link")
    private WebElement navbar_userMenu_messages_link;

    @FindBy(id = "navbar-userMenu-profile-link")
    private WebElement navbar_userMenu_profile_link;

    @FindBy(id = "navbar-userMenu-preferences-link")
    private WebElement navbar_userMenu_preferences_link;

//    @FindBy(id = "navbar-userMenu-logout-link")
//    @FindBy(css = "#navbar-userMenu-logout.navbar-userMenu-logout-link")
    @FindBy(className = "navbar-userMenu-logout-link")
    private WebElement navbar_userMenu_logout_link;

    @FindBy(id = "navbar-register-link")
    private WebElement navbar_register_link;

    @FindBy(id = "navbar-login-link")
    private WebElement navbar_login_link;

    public String getFlashMessagesText() {
        return flashMessages.getText().trim();
    }

    public boolean isLoggedIn() {
        return WebTestUtils.isPresent(navbar_userMenu_dropdown);
    }

    public String getLoggedInUser() {
        return navbar_userMenu_dropdown.getText().trim();
    }

    public void navigateToRegister() {
        guardHttp(navbar_register_link).click();
    }

    public void navigateToLogin() {
        guardHttp(navbar_login_link).click();
    }

    public void toggleUserMenu() {
        navbar_userMenu_dropdown.click();
    }

    public void logout() {
        guardHttp(navbar_userMenu_logout_link).click();
    }

    public void assertLoggedIn(final String reason, final String username) {
        assertThat(reason, isLoggedIn(), equalTo(true));
        assertThat(reason, getLoggedInUser().toUpperCase(), equalTo(username.toUpperCase()));
    }

    public void assertNotLoggedIn() {
        assertNotLoggedIn("We should NOT be logged in");
    }

    public void assertNotLoggedIn(final String reason) {
        assertThat(reason, isLoggedIn(), equalTo(false));
    }

    public void assertFlashMessage(final String reason, final String message) {
        assertThat(reason, getFlashMessagesText(), containsString(message));
    }

    public boolean isNavbarPresent() {
        return WebTestUtils.isPresent(navbar);
    }

    public boolean isNavbar_caLogoPresent() {
        return WebTestUtils.isPresent(navbar_caLogo);
    }

    public boolean isNavbar_toggler_buttonPresent() {
        return WebTestUtils.isPresent(navbar_toggler_button);
    }

    public boolean isNavbar_homePage_linkPresent() {
        return WebTestUtils.isPresent(navbar_homePage_link);
    }

    public boolean isNavbar_collectionsPresent() {
        return WebTestUtils.isPresent(navbar_collections);
    }

    public boolean isNavbar_collections_dropdownPresent() {
        return WebTestUtils.isPresent(navbar_collections_dropdown);
    }

    public boolean isNavbar_collections_all_linkPresent() {
        return WebTestUtils.isPresent(navbar_collections_all_link);
    }

    public boolean isNavbar_collections_my_linkPresent() {
        return WebTestUtils.isPresent(navbar_collections_my_link);
    }

    public boolean isNavbar_market_linkPresent() {
        return WebTestUtils.isPresent(navbar_market_link);
    }

    public boolean isNavbar_adminPanel_linkPresent() {
        return WebTestUtils.isPresent(navbar_adminPanel_link);
    }

    public boolean isNavbar_userMenu_dropdownPresent() {
        return WebTestUtils.isPresent(navbar_userMenu_dropdown);
    }

    public boolean isNavbar_userMenu_messages_linkPresent() {
        return WebTestUtils.isPresent(navbar_userMenu_messages_link);
    }

    public boolean isNavbar_userMenu_profile_linkPresent() {
        return WebTestUtils.isPresent(navbar_userMenu_profile_link);
    }

    public boolean isNavbar_userMenu_preferences_linkPresent() {
        return WebTestUtils.isPresent(navbar_userMenu_preferences_link);
    }

    public boolean isNavbar_userMenu_logout_linkPresent() {
        return WebTestUtils.isPresent(navbar_userMenu_logout_link);
    }

    public boolean isNavbar_register_linkPresent() {
        return WebTestUtils.isPresent(navbar_register_link);
    }

    public boolean isNavbar_login_linkPresent() {
        return WebTestUtils.isPresent(navbar_login_link);
    }

    @Override
    public String toString() {
        return "DefaultLayout{" +
                "name='" + getName() + '\'' +
                "location='" + getLocation() + '\'' +
                '}';
    }
}
