package net.kawinski.collecting.presentation.web;

import net.kawinski.collecting.presentation.web.layout.DefaultLayout;
import net.kawinski.collecting.presentation.web.layout.ICollectorAssistantPage;
import net.kawinski.collecting.presentation.web.layout.LoginPage;
import net.kawinski.utils.DebugUtils;
import org.openqa.selenium.*;

import java.net.URL;

public class WebTestUtils {

    /**
     * This represents different testing targets.
     *
     * "PC" and "mobile" rendering differs a bit. Examples include:
     * - Wide navbar on PC /vs/ Hamburger menu on mobile devices
     */
    public enum BrowserConfiguration {
        PC(1024, 768),
        MOBILE(768, 1024);

        public final int width, height;

        BrowserConfiguration(final int width, final int height) {
            this.width = width;
            this.height = height;
        }
    }

    public static void setPcWindowSize(final WebDriver browser) {
        setWindowSize(browser, BrowserConfiguration.PC.width, BrowserConfiguration.PC.height);
    }

    public static void setMobileWindowSize(final WebDriver browser) {
        setWindowSize(browser, BrowserConfiguration.MOBILE.width, BrowserConfiguration.MOBILE.height);
    }

    public static void setWindowSize(final WebDriver browser, final int width, final int height) {
        browser.manage().window().setSize(new Dimension(width, height));
    }

    @Deprecated // Transitive deprecation due to DebugUtils also being deprecated
    public static void saveScreenshot(final WebElement element) {
        DebugUtils.writeToTempFile("screenshot", ".png", element.getScreenshotAs(OutputType.BYTES));
    }

    /**
     * There is no "WebElement.isPresent()" method.
     * Selenium is designed to throw when an element doesn't exist.
     * In order to avoid try...catch'ing everywhere, here's the helper method.
     */
    public static boolean isPresent(final WebElement element) {
        try {
            element.getTagName();
            return true;
        } catch (final NoSuchElementException ignored) {
            return false;
        }
    }

    /**
     * Similar to {@link WebTestUtils#isPresent(WebElement)} but with different params
     */
    public static boolean isPresent(final WebDriver browser, final By by) {
        try {
            browser.findElement(by);
            return true;
        } catch (final NoSuchElementException ignored) {
            return false;
        }
    }

    /**
     * We're reusing the same browser session across tests,
     * so make sure we're setup at the beginning of the tests in case other tests crashed
     *
     * Note: If logged in, you'll be redirected to the home page from whatever you were in.
     *     In such case, you might find {@link WebTestUtils#navigate(WebDriver, URL, ICollectorAssistantPage)} handy.
     */
    public static void ensureLoggedOut(final DefaultLayout page) {
        if(page.isLoggedIn())
            page.logout();
        page.assertNotLoggedIn();
    }

    public static void ensureLoggedIn(final DefaultLayout page, final LoginPage loginPage, final String userName, final String password) {
        if(page.isLoggedIn()) {
            if(page.getLoggedInUser().equals(userName))
                return; // We're logged in, great!
            page.logout();
        }
        page.navigateToLogin();
        loginPage.login(userName, password);
        page.assertLoggedIn("Should be logged in as " + userName, userName);
    }

    public static void navigate(final WebDriver driver, final URL deploymentUrl, final ICollectorAssistantPage page) {
        driver.get(deploymentUrl.toExternalForm() + page.getLocation());
    }

}
