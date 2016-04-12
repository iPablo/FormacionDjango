package com.emergya.testSets;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.emergya.pageObjects.EventsPage;
import com.emergya.utils.DefaultTestSet;

/**
 * A test class contain the tests of a specific page in the application
 * @author Alfonso Rodríguez Martín
 */
public class EventsTestSet extends DefaultTestSet {

    Logger log = Logger.getLogger(EventsTestSet.class);

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

    /**
     * Description: We are going to access the Events page  and do a simple CRUD
     * to check the functionality.
     * 
     * Pre steps:
     * - None
     *   
     * Steps:
     * - Direction to a creation page
     * - Checking if everything is correct
     * - Create a random event
     * - Checking if they were created
     * Post Steps:
     * - Delete the created event
     *  - Verify the deletion
     * @throws InterruptedException 
     * 
     */
    @Test(description = "createEvent")
    public void createEvent() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Starting test 1: eventCreation");
        eventsPage = new EventsPage(driver);

        try {
            // We make a first check to see if we are in the News Page.
            areWeOnEventsPage();
            // Now, we proceed to the page that allows to create news.
            eventsPage.goToEventCreate();
            // This assertion is for seeing if there are inputs.
            areInputsVisible();
            eventsPage.createEvents("No deberias de poder leer esto.",
                    "Descripcion poco pensada");
            // Let's check if it was good
            isConfirmMessageVisible();
            // Now we got to delete the created Event. To do it we move back to
            // main
            eventsPage.accessEventsPage();
            // Normal check
            areWeOnEventsPage();
        } finally {
            isTheEventCorrect(driver.getCurrentUrl());
            // Moving forward to Deletion view
            eventsPage.goToDeleteEvents();
            // Checking if the button is displayed
            isConfirmDeleteButtonVisible();
            // Finally, delete the news
            eventsPage.doDelete();
        }
    }

    /**
     * Description: We are going to use the edition functionality of the webpage.
     * 
     * Pre steps:
     * - Create an Event.
     *   
     * Steps:
     * - Move to the edition page
     * - Edit the event
     * - Confirm it.
     * 
     * Post Steps:
     *  - Delete the event
     * @throws InterruptedException 
     * 
     */

    @Test(description = "editEvent")
    public void editEvent() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Starting test 2: eventEdition");
        eventsPage = new EventsPage(driver);
        try {
            // We make a first check to see if we are in the News Page.
            areWeOnEventsPage();
            // Now, we proceed to the page that allows to create news.
            eventsPage.goToEventCreate();
            Thread.sleep(2000);
            // This assertion is for seeing if there are inputs.
            areInputsVisible();
            eventsPage.createEvents("No deberias de poder leer esto.",
                    "Descripcion poco pensada");
            // Let's check if it was good
            isConfirmMessageVisible();
            // Now we got to edit the created Event. To do it we move back to
            // main
            eventsPage.accessEventsPage();
            // Normal check
            areWeOnEventsPage();
            // We move forward
            eventsPage.goToEditEvents();
            // Checking if we are here
            areInputsVisible();
            // Once checked, we do the edit
            eventsPage.doEdit();
            // Once we got the edition, we should check if a confirmation is
            // shown.
            isConfirmMessageVisible();

        } finally {
            isTheEventCorrect(driver.getCurrentUrl());
            // Now we got to delete the created Event. To do it we move back to
            // main
            eventsPage.accessEventsPage();
            // Normal check
            areWeOnEventsPage();
            // Moving forward to Deletion view
            eventsPage.goToDeleteEvents();
            // Checking if the button is displayed
            isConfirmDeleteButtonVisible();
            // Finally, delete the news
            eventsPage.doDelete();
        }
    }

    /**
     * Description: We are going to use the edition functionality of the webpage.
     * 
     * Pre steps:
     * - Create an Event.
     *   
     * Steps:
     * - Move to the deletion page
     * - Click on delete button.
     * - Delete the event
     * - Confirm it.
     * 
     * Post Steps:
     *  - Check if we were successful
     * @throws InterruptedException 
     * 
     */

    @Test(description = "deleteEvent")
    public void deleteEvent() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Starting test 3: eventDelete");
        eventsPage = new EventsPage(driver);
        try {
            // We make a first check to see if we are in the News Page.
            areWeOnEventsPage();
            // Now, we proceed to the page that allows to create news.
            eventsPage.goToEventCreate();
            driver.sleep(2);
            // This assertion is for seeing if there are inputs.
            areInputsVisible();
            eventsPage.createEvents("No deberias de poder leer esto.",
                    "Descripcion poco pensada");
            // Let's check if it was good
            isConfirmMessageVisible();
            // Now we got to delete the created Event. To do it we move back to
            // main
            eventsPage.accessEventsPage();
            // Normal check
            areWeOnEventsPage();
            // Moving forward to Deletion view
            eventsPage.goToDeleteEvents();
            // Confirmation
            isConfirmDeleteButtonVisible();
            // Finally, delete the news
            eventsPage.doDelete();
        } finally {
            // Lets see if the deletion was successful
            isConfirmMessageVisible();
        }
    }

    // ************************ Assertions *************************
    /**
     * To prevent wrong deletions we check if the event we are deleting is the title we wrote befpre.
     * 
     */
    public void isTheEventCorrect(String previousURL) {
        // First we need to move
        driver.get("localhost:8000/eventos");
        String evaluate = eventsPage.getElementByXPath("event").getText();
        String test = "No deberias de poder leer esto.";
        assertEquals(test, evaluate);
        // once done, we move to where we were.
        driver.get(previousURL);

    }

    /**
     * Checking if we are on News Page.
     */
    public void areWeOnEventsPage() {
        if (eventsPage == null) {
            eventsPage = new EventsPage(driver);
        }
        String test = eventsPage.getElementById("titulo").getText();
        assertEquals(test, "Eventos Django");
    }

    /**
     * This assertion is to see if the form inputs are visible
     */
    public void areInputsVisible() {
        if (eventsPage == null) {
            eventsPage = new EventsPage(driver);
        }
        assertTrue("The inputs are not visible. Something has gone wrong",
                eventsPage.isElementVisibleById("campoT"));

    }

    /**
     * THis assertion checks if the confirmation message is visible 
     */
    public void isConfirmMessageVisible() {
        if (eventsPage == null) {
            eventsPage = new EventsPage(driver);
        }
        assertTrue("The message is not shown, something has gone wrong",
                eventsPage.isElementVisibleByXPath("message"));
    }

    /**
     * This assertion is to check if the delete button is visible.
     */
    public void isConfirmDeleteButtonVisible() {
        if (eventsPage == null) {
            eventsPage = new EventsPage(driver);
        }
        assertTrue("The button is not displayed. Something has happened",
                eventsPage.isElementVisibleByXPath("confirm"));
    }
}
