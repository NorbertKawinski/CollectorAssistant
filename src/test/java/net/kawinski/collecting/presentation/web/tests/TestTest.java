package net.kawinski.collecting.presentation.web.tests;

import net.kawinski.collecting.CaTestHelper;
import net.kawinski.collecting.Resources;
import net.kawinski.collecting.presentation.web.WebTestUtils;
import net.kawinski.collecting.presentation.web.layout.HomePage;
import net.kawinski.collecting.presentation.web.layout.ICollectorAssistantPage;
import net.kawinski.collecting.presentation.web.layout.LoginPage;
import net.kawinski.collecting.presentation.web.layout.RegisterPage;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for tests!
 * We better make sure our test-helper methods work
 */
@SuppressWarnings({"InstanceVariableMayNotBeInitialized"})
// Sorry IntelliJ, Arquillian and Selenium do initialize them
@RunWith(Arquillian.class)
public class TestTest {

    @Deployment(testable = false)
    public static Archive<?> createTestArchive() {
        return CaTestHelper.createFullTestArchive();
    }

    @Drone
    private WebDriver driver;

    @ArquillianResource
    private URL deploymentUrl;

    @Page
    private HomePage homePage;

    @Page
    private LoginPage loginPage;

    @Before
    public void initialize() {
        WebTestUtils.setPcWindowSize(driver);
        driver.manage().deleteAllCookies(); // We want to be logged out between tests
    }

    @Test
    public void home_page_should_know_whether_its_being_displayed(@InitialPage final HomePage initialPage) {
        initialPage.assertNotLoggedIn();
        assertThat("isOnPage() should return true when on page", initialPage.isOnPage(driver), is(true));
        initialPage.navigateToLogin();
        assertThat("isOnPage() should return false after navigating away", initialPage.isOnPage(driver), is(false));
        WebTestUtils.navigate(driver, deploymentUrl, initialPage);
        assertThat("isOnPage() should return true after returning back", initialPage.isOnPage(driver), is(true));
    }

    @Test
    public void login_page_should_know_whether_its_being_displayed(@InitialPage final LoginPage initialPage) {
        test_page_location(initialPage);
    }

    @Test
    public void register_page_should_know_whether_its_being_displayed(@InitialPage final RegisterPage initialPage) {
        test_page_location(initialPage);
    }

    private void test_page_location(final ICollectorAssistantPage testedPage) {
        assertThat("isOnPage() should return true when on page", testedPage.isOnPage(driver), is(true));
        WebTestUtils.navigate(driver, deploymentUrl, homePage);
        assertThat("isOnPage() should return false after navigating away", testedPage.isOnPage(driver), is(false));
        WebTestUtils.navigate(driver, deploymentUrl, testedPage);
        assertThat("isOnPage() should return true after returning back", testedPage.isOnPage(driver), is(true));
    }

}
