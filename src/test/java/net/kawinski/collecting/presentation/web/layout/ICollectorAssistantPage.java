package net.kawinski.collecting.presentation.web.layout;

import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;

public interface ICollectorAssistantPage {
    String getLocation();

    String getName();

    /**
     * Every page on Collector Assistant follows specific title pattern
     * to help user quickly find where they currently are
     */
    default String getExpectedTitle() {
        return getName() + " - Collector Assistant";
    }

    default boolean isOnPage(final WebDriver driver) {
        return driver.getTitle().trim().equals(getExpectedTitle());
    }

    default void assertOnPage(final WebDriver driver) {
        assertOnPage("Should be on page '" + getName() + "'", driver);
    }

    default void assertOnPage(final String reason, final WebDriver driver) {
        assertThat(reason, isOnPage(driver));
    }


}
