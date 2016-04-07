package com.emergya.testSets;

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
     * - Create the main page and access to the admin
     *   
     * Steps:
     * - Go to folders
     * - Create a new Private Folder with Anonymous user enable
     * - Go to Asset Library
     * - Upload an asset
     * - Publish the uploaded asset
     * - Save the uploaded asset in the created folder
     * - Access to created asset
     * - Open asset details
     * - Copy the url of asset
     * - Access to the copy url with a new private browse
     * - Check that go to Landing page asset url
     * 
     * Post steps:
     * - Delete uploaded asset
     * - Delete create folder
     * - Logout
     * - Delete user
     * @throws InterruptedException 
     * 
     */
    @Test(description = "adminPage")
    public void adminPage() throws InterruptedException {
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
            Thread.sleep(5000);

        } finally {

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
     * This assertion is made for checking if the inputs of the create event admin page
     * are shown.
     */
    public void areInputsDisplayed() {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        assertTrue("The inputs are not shown. Something is wrong",
                administracionPage.isElementVisibleById("form"));
    }

}
