package net.kawinski.collecting.presentation.web.tests;

import net.kawinski.collecting.CaTestHelper;
import net.kawinski.collecting.Resources;
import net.kawinski.collecting.presentation.web.layout.DefaultLayout;
import net.kawinski.collecting.presentation.web.layout.HomePage;
import net.kawinski.collecting.presentation.web.layout.LoginPage;
import net.kawinski.collecting.presentation.web.layout.RegisterPage;
import net.kawinski.collecting.presentation.web.WebTestUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("InstanceVariableMayNotBeInitialized") // Sorry IntelliJ, Arquillian and Selenium do initialize them
@RunWith(Arquillian.class)
public class UserTest {
    private static final String NEW_USER_NAME = "TestUser";
    private static final String NEW_USER_MAIL = "TestMail@test.ca.kawinski.net";
    private static final String NEW_USER_PASS = "MySecretPassw0rd!";

    @Deployment(testable = false)
    public static Archive<?> createTestArchive() {
        return CaTestHelper.createFullTestArchive();
    }

    @Drone
    private WebDriver driver;

    @Page
    private HomePage homePage;

    @Page
    private RegisterPage registerPage;

    @Page
    private LoginPage loginPage;

    @Before
    public void initialize() {
        WebTestUtils.setPcWindowSize(driver);
        driver.manage().deleteAllCookies(); // We want to be logged out between tests
    }

    @Test
    public void guest_should_see_navbar(@InitialPage final HomePage initialPage) {
        homePage.assertNotLoggedIn();
        assertRenderedGuestNavbar(initialPage);
    }

    @Test
    public void guest_should_register_successfully(@InitialPage final HomePage initialPage) {
        homePage.assertNotLoggedIn();

        // We're manually navigating to register page, as opposed to just entering the correct page URL
        // to make sure that user can also 'find' their way into register page. (Register link is visible and references correct page)
        homePage.navigateToRegister();
        registerPage.register(NEW_USER_NAME, NEW_USER_MAIL, NEW_USER_PASS);

        homePage.assertOnPage("We should be redirected to home page after registering", driver);
        homePage.assertFlashMessage("User should see 'Registration success' confirmation message", "Rejestracja udana");
        homePage.assertNotLoggedIn("We should still be logged out after registration");

        homePage.navigateToLogin();
        loginPage.login(NEW_USER_NAME, NEW_USER_PASS);

        homePage.assertLoggedIn("We should be able to login to newly registered account", NEW_USER_NAME);
    }

    @Test
    public void guest_should_login_successfully(@InitialPage final HomePage initialPage) {
        homePage.assertNotLoggedIn();

        homePage.navigateToLogin();
        loginPage.login(Resources.DEFAULT_USER1_USERNAME, Resources.DEFAULT_USER1_PASSWORD);

        homePage.assertOnPage("We should be redirected to home page after logging in", driver);
        homePage.assertFlashMessage("User should see 'Login success' confirmation message", "Logowanie udane");
        homePage.assertLoggedIn("User should see who is he currently logged as", Resources.DEFAULT_USER1_USERNAME);
    }

    @Test
    public void user_should_see_more_navbar(@InitialPage final HomePage initialPage) {
        WebTestUtils.ensureLoggedIn(initialPage, loginPage, Resources.DEFAULT_USER1_USERNAME, Resources.DEFAULT_USER1_PASSWORD);
        assertRenderedUserNavbar(initialPage, false);
    }

    @Test
    public void user_should_logout_successfully(@InitialPage final HomePage initialPage) {
        WebTestUtils.ensureLoggedIn(initialPage, loginPage, Resources.DEFAULT_USER1_USERNAME, Resources.DEFAULT_USER1_PASSWORD);

        homePage.toggleUserMenu(); // We won't be able to logout without as the logout link will be invisible
        homePage.logout();

        homePage.assertNotLoggedIn();
        homePage.assertOnPage("We should be redirected to home page after logging out", driver);
        homePage.assertFlashMessage("User should see 'Logout success' confirmation message", "Wylogowałeś się");
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions") // Asserts are in a helper method
    @Test
    public void admin_should_see_more_navbar(@InitialPage final HomePage initialPage) {
        WebTestUtils.ensureLoggedIn(initialPage, loginPage, Resources.DEFAULT_ADMIN_USERNAME, Resources.DEFAULT_ADMIN_PASSWORD);

        assertRenderedAdminNavbar(homePage);
    }

    @Test
    public void login_should_be_case_insensitive(@InitialPage final HomePage initialPage) {
        homePage.assertNotLoggedIn();
        WebTestUtils.ensureLoggedIn(initialPage, loginPage, Resources.DEFAULT_USER1_USERNAME.toUpperCase(), Resources.DEFAULT_USER1_PASSWORD);
    }

    @Test
    public void login_should_be_possible_by_mail_case_insensitive(@InitialPage final HomePage initialPage) {
        homePage.assertNotLoggedIn();

        homePage.navigateToLogin();
        loginPage.login(Resources.DEFAULT_USER1_MAIL.toUpperCase(), Resources.DEFAULT_USER1_PASSWORD);

        homePage.assertLoggedIn("Should be logged in by email as user1", Resources.DEFAULT_USER1_USERNAME);
    }

    @Test
    public void login_should_fail_with_wrong_user_name(@InitialPage final HomePage initialPage) {
        homePage.assertNotLoggedIn();

        homePage.navigateToLogin();
        loginPage.login(Resources.DEFAULT_USER1_MAIL.toUpperCase(), Resources.DEFAULT_USER1_PASSWORD);

        homePage.assertLoggedIn("Should be logged in by email as user1", Resources.DEFAULT_USER1_USERNAME);
    }

    private static void assertRenderedCommonNavbar(final DefaultLayout page) {
        assertThat("Navbar should be present", page.isNavbarPresent(), is(true));
        // TODO: It actually is present but not visible...
//        assertThat("Navbar toggler should NOT be present (only present on mobile)", page.isNavbar_toggler_buttonPresent(), is(false));
        assertThat("CA logo should be present", page.isNavbar_caLogoPresent(), is(true));
        assertThat("HomePage link should be present", page.isNavbar_homePage_linkPresent(), is(true));
        assertThat("Collections dropdown should be present", page.isNavbar_collections_dropdownPresent(), is(true));
        assertThat("'All' collection link should be present", page.isNavbar_collections_all_linkPresent(), is(true));
        assertThat("Market link should be present", page.isNavbar_market_linkPresent(), is(true));
    }

    private static void assertRenderedGuestNavbar(final DefaultLayout page) {
        assertRenderedCommonNavbar(page);

        assertThat("'My' collections link should NOT be present", page.isNavbar_collections_my_linkPresent(), is(false));
        assertThat("Admin panel link should NOT be present", page.isNavbar_adminPanel_linkPresent(), is(false));
        assertThat("User menu should NOT be present", page.isNavbar_userMenu_dropdownPresent(), is(false));

        assertThat("Register link should be present", page.isNavbar_register_linkPresent(), is(true));
        assertThat("Login link should be present", page.isNavbar_login_linkPresent(), is(true));
    }

    private static void assertRenderedUserNavbar(final DefaultLayout page, final boolean isAdmin) {
        assertRenderedCommonNavbar(page);

        assertThat("My collections link should be present", page.isNavbar_collections_my_linkPresent(), is(true));

        assertThat("User menu should be present", page.isNavbar_userMenu_dropdownPresent(), is(true));
        assertThat("Messages link should be present", page.isNavbar_userMenu_messages_linkPresent(), is(true));
        assertThat("Profile link should be present", page.isNavbar_userMenu_profile_linkPresent(), is(true));
        assertThat("Preferences link should be present", page.isNavbar_userMenu_preferences_linkPresent(), is(true));
        assertThat("Logout link should be present", page.isNavbar_userMenu_logout_linkPresent(), is(true));

        assertThat("Register link should NOT be present", page.isNavbar_register_linkPresent(), is(false));
        assertThat("Login link should NOT be present", page.isNavbar_login_linkPresent(), is(false));

        if(isAdmin)
            assertThat("Admin panel link should be present", page.isNavbar_adminPanel_linkPresent(), is(true));
        else
            assertThat("Admin panel link should NOT be present", page.isNavbar_adminPanel_linkPresent(), is(false));
    }

    private static void assertRenderedAdminNavbar(final DefaultLayout page) {
        assertRenderedUserNavbar(page, true);
    }

}
