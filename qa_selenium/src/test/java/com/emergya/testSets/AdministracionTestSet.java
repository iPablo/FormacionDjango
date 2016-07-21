package com.emergya.testSets;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.emergya.pageObjects.AdministracionPage;
import com.emergya.utils.DefaultTestSet;

/**
 * A test class contain the tests of a specific page in the application
 * @author Jose Antonio Sanchez <jasanchez@emergya.com>
 * @author Ivan Bermudez <ibermudez@emergya.com>
 */
public class AdministracionTestSet extends DefaultTestSet {

    Logger log = Logger.getLogger(GoogleTestSet.class);

    public AdministracionTestSet() {
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
     * Description: We are going to get to our projects main page, and check if the admin label
     * goes directly to admin.
     * 
     * Pre steps:
     * - Log in as the administrator
     *   
     * Steps:
     * - Go the admin page
     * - Check is the title is visible
     * - Go to create event page
     * - Check if the inputs are visible
     * - Create an event
     * - Check if the event was created
     *  -Delete and check again.
     * - Go forward to the news page
     * - Create some news
     * - Check if they were created
     * -
     * Post Steps:
     * - Logout
     * @throws InterruptedException 
     * 
     */
    @Test(description = "adminPage")
    public void login() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Start accessToPrivateFolderWithoutLogin test");
        administracionPage = new AdministracionPage(driver);

        try {
            /**
             * This logs us as the administrator
             */
            Thread.sleep(1000);
            administracionPage.loginAsAdmin();
            /**
             * This assertion checks if the main title of the page is displayed.
             */
            isTitleDisplayed();
            /**
             * Now, we get to the create event page and check if everything
             * is correct.
             */
            administracionPage.goToCreateEvent();

            /**
            * One we are in the Create Event Page, we check if the input texts
            * are available
            */
            areInputsDisplayed();
            Thread.sleep(2000);

            /**
             * Proceeding to create a new event
             */
            administracionPage.createEvent("Hola", "Descripcion tonta");

            /**
             * This assertion checks if the event was created successfully
             */
            isMessageDisplayed();
            Thread.sleep(2000);
            /**
             * This method goes back to the event page detail.
             */
            administracionPage.goToLastEvent();
            /**
             * We should check if we are in the Last Event.
             */
            isBrowserOnEventDetail();
            /**
             * Once there we click on Delete.
             */
            administracionPage.clickOnDeleteButton();
            /**
             * And confirm.
             */
            administracionPage.confirmDelete();
            /**
             * We check again if the message was displayed and is succesfull
             */
            isMessageDisplayed();
            Thread.sleep(2000);
            /**
             * We go forward to the News Item page to check
             */
            administracionPage.goToNewsItemPage();
            Thread.sleep(3000);
            /**
             * Checking if we are on the NewsItem page
             */
            isBrowserOnNewsItem();
            /**
             * Redirecting to news creation page
             */
            administracionPage.goToNewsCreation();
            /**
             * Check if everything is correct about the inputs
             */
            areInputsDisplayed();
            /**
             * We create a new News Item now.
             */
            administracionPage.createNews("Hola", "Noticia caca");
            /**
             * This is for confirming if we are succesful
             */
            isMessageDisplayed();
            /**
             * We delete the created news so we go forward to the detail
             */
            administracionPage.goToNewsDetail();
            /**
             * We check if we are on the correct page
             */
            isBrowserOnNewsItemDetail();
            /**
             * Once there we need to click on the delete button
             */
            administracionPage.clickOnDeleteNewsButton();
            /**
             * And then confirm the delete.
             */
            administracionPage.confirmDelete();
            /**
             * And finally we check if the delete was succesfull
             */
            isMessageDisplayed();
            Thread.sleep(5000);
            /**
             * After 5 seconds, we proceed to log out.
             */

        } finally {
            administracionPage.logOut();
            Thread.sleep(3000);
        }
    }

    // ************************ Assertions *************************
    /**
     * This assertion checks if a title is displayed.
     */
    public void isTitleDisplayed() {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        assertTrue("The title isn't displayed, it should say something",
                administracionPage.isElementVisibleById("title"));
    }

    /**
     * This assertion is made for checking if the inputs of the create event administrator page
     * are shown.
     */
    public void areInputsDisplayed() {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        assertTrue("The inputs are not shown. Something is wrong",
                administracionPage.isElementVisibleById("form"));
    }

    /**
     * Assertion to check if we are in the Event Detail page
     */

    public void isBrowserOnEventDetail() {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        assertTrue("The inputs are not shown. Something is wrong",
                administracionPage.isElementVisibleById("form2"));
    }

    /**
     * Assertion to check if we are in the News Detail page
     */

    public void isBrowserOnNewsItemDetail() {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        assertTrue("The inputs are not shown. Something is wrong",
                administracionPage.isElementVisibleById("form3"));
    }

    /**
     * This assertion is made for checking if the confirmation message is visible
     */
    public void isMessageDisplayed() {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        assertTrue("The message can't be found, please check it out",
                administracionPage
                        .isElementVisibleByXPath("mensajeconfirmacion"));
    }

    /**
     * This assertion checks if we are in the NewsItem Page.
     */
    public void isBrowserOnNewsItem() {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        assertEquals("NEWS ITEM", administracionPage
                .getElementByXPath("newsitemtitle").getText());
    }
}
