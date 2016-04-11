package com.emergya.testSets;

import static org.testng.AssertJUnit.assertTrue;

import org.apache.log4j.Logger;
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
    public void adminPage() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Start accessToPrivateFolderWithoutLogin test");
        event = new EventsPage(driver);

        try {
            Thread.sleep(1000);

            // Create NewsItem
            event.createEvent();
            this.isItemDisplayed();

            // Update NewsItem
            event.updateEvent();
            this.isItemDisplayed();

            // Delete NewsItem
            event.updateEvent();
            this.isItemDisplayed();

            Thread.sleep(5000);

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
}
