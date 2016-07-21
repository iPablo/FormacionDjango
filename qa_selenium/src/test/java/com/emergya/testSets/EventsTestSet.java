package com.emergya.testSets;

import static org.testng.AssertJUnit.assertTrue;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.emergya.pageObjects.EventsPage;
import com.emergya.utils.DefaultTestSet;

public class EventsTestSet extends DefaultTestSet {
    Logger log = Logger.getLogger(AdministracionTestSet.class);
    EventsPage event;

    public EventsTestSet() {
        super();
    }

    @BeforeMethod(description = "startTest")
    public void before() {
        super.before();
    }

    @AfterMethod(description = "endTest")
    public void afterAllIsSaidAndDone() {
        super.afterAllIsSaidAndDone();
    }

    @Test(description = "newsPage")
    public void createEvent() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Start accessToPrivateFolderWithoutLogin test");
        event = new EventsPage(driver);

        try {
            driver.sleep(1);

            // Create NewsItem
            event.createEvent();
            this.isItemDisplayed();

            driver.sleep(5);

        } finally {
            if (this.isItem("Test QA")) {
                // Delete NewsItem
                event.deleteEvent();
                this.isItemDisplayed();
            }
        }
    }

    @Test(description = "newsPage")
    public void updateEvent() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Start accessToPrivateFolderWithoutLogin test");
        event = new EventsPage(driver);

        try {
            driver.sleep(1);

            // Create NewsItem
            event.createEvent();
            this.isItemDisplayed();

            // Update NewsItem
            event.updateEvent();
            this.isItemDisplayed();

            driver.sleep(5);

        } finally {
            // Delete NewsItem
            event.deleteEvent();
            this.isItemDisplayed();
        }
    }

    @Test(description = "newsPage")
    public void deleteEvent() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Start accessToPrivateFolderWithoutLogin test");
        event = new EventsPage(driver);

        try {
            driver.sleep(1);

            // Create NewsItem
            event.createEvent();
            this.isItemDisplayed();

            // Delete NewsItem
            event.deleteEvent();
            this.isItemDisplayed();

            driver.sleep(5);

        } finally {
        }
    }

    // ************************ Assertions *************************
    /**
     * This assertion checks if a item is displayed.
     */
    public void isItemDisplayed() {
        if (event == null) {
            event = new EventsPage(driver);
        }
        assertTrue("The item isn't displayed, it should say something",
                event.isElementVisibleByXPath("item"));

    }

    public boolean isItem(String x) {
        // return (event.getElementByXPath("itemTitle").getText().equals(x)) ?
        // true
        // : false;
        boolean result = false;

        for (WebElement aux : driver.findElements(By.name("events"))) {
            if (aux.getText().equals(x)) {
                result = true;
                break;
            } else {
                result = false;
            }
        }

        return result;
    }
}
