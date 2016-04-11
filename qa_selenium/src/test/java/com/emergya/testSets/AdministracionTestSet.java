package com.emergya.testSets;

import static org.testng.AssertJUnit.assertTrue;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.emergya.pageObjects.AdministracionPage;
import com.emergya.utils.DefaultTestSet;

/**
 * 
 * @author jchierro
 *
 */
public class AdministracionTestSet extends DefaultTestSet {

    Logger log = Logger.getLogger(AdministracionTestSet.class);
    AdministracionPage administracionPage; // = new AdministracionPage(driver);

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
            Thread.sleep(1000);
            administracionPage.loginAsAdmin();
            this.isTitleDisplayed();

            administracionPage.createEvent();
            this.alertCreateEventDisplayed();

            administracionPage.deleteEvent();
            this.alertDeleteEventDisplayed();

            administracionPage.createNewsItem();
            this.alertCreateEventDisplayed();

            administracionPage.deleteNewsItem();
            this.alertDeleteEventDisplayed();

            administracionPage.logout();

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
    public void formCreateEventDisplayed() {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        assertTrue("...", administracionPage.isElementVisibleById("form"));
    }

    /**
     * 
     */
    public void alertCreateEventDisplayed() {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        assertTrue("...",
                administracionPage.isElementVisibleByXPath("alertCreate"));
    }

    /**
     * 
     */
    public void alertDeleteEventDisplayed() {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        assertTrue("...",
                administracionPage.isElementVisibleByXPath("alertDelete"));
    }
}
