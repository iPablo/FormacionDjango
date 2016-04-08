package com.emergya.testSets;

import static org.testng.AssertJUnit.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.emergya.pageObjects.AdministrationMainPage;
import com.emergya.utils.DefaultTestSet;

/**
 * A test class contain the tests of a specific page in the application
 * @author Jose Antonio Sanchez <jasanchez@emergya.com>
 * @author Ivan Bermudez <ibermudez@emergya.com>
 */
public class AdministrationTestSet extends DefaultTestSet {

    Logger log = Logger.getLogger(AdministrationTestSet.class);

    public AdministrationTestSet() {
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

    // **************** TESTS ****************
    // ------ EMERGYA QA SAMPLE TEST ------ //
    // ------ US00001 - Check google main page and do a search ------ //
    /**
     * Description: Check the main page elements and do a search on google
     * 
     * Pre steps:
     * - Open the browser
     * 
     * Steps:
     * - Go to localhost:8000/admin
     * - Check that the Administracion Django is displayed
     * - Check that the 'user' field is displayed
     * - Write user
     * - Check that the 'pass' field is displayed
     * - Write pass
     * - Check that the 'iniciar sesion' button is displayed
     * - Click button
     * - Check that the 'evento' button is displayed
     * - Check that the 'añadir evento' button is displayed
     * - Check that the 'modificar noticia' button is displayed
     * - Click on news items
     * - Check 'añadir news item' button is displayed
     * - Click on this button
     * - Check text title field is displayed
     * - Check publish date is displayed
     * - Check 'save' button is displayed
     * - Write title
     * - write description
     * - click date
     * - click time
     * - borrar la noticia  creada
     * - volver al admin
     * hecho
     * darle a eventos
     * comprobar campos
     * crear evento
     * borrar evento creado
     * volver al admin
     * Post steps:
     * - Close the browser
     * @throws InterruptedException 
     * 
     */
    @Test(description = "administrationMainPageSearch")
    public void administrationMainPage() throws InterruptedException {
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start administrationMainPage test");

        // Variable declaration and definition
        administrationMainPage = new AdministrationMainPage(driver);

        // Steps to build the stage (Pre steps)

        try {
            // Go to localhost:8000/admin
            // Check that the administracion django logo is displayed
            isLogoDisplayed();

            // Check that the user field is displayed
            isFieldUserDisplayed();

            // Check that the pass field is displayed
            isFieldPassDisplayed();

            // Check that the 'iniciar sesion' button is displayed
            isSessionButtonDisplayed();

            // Check if we can go to the web
            administrationMainPage.doSearch();

            Thread.sleep(5000);

            // Check that the 'evento' button is displayed
            isEventButtonDisplayed();
            // Check that the 'añadir evento' button is displayed
            isAddEventButtonDisplayed();
            // Check that the 'añadir noticia' button is displayed
            isModifyNewsButtonDisplayed();
            // Click on news items
            administrationMainPage = administrationMainPage
                    .clickOnNewsAdministrationPage();
            // Check 'añadir news item' button is displayed
            isAddNewsButtonDisplayed();
            // Click on this button
            administrationMainPage = administrationMainPage
                    .clickOnAddNewsAdministrationPage();

            // Thread.sleep(5000);

            // Check text title field is displayed
            isTitleNewsFieldDisplayed();
            // Check publish date is displayed
            isDescriptionNewsDisplayed();
            // Check 'save' button is displayed
            isSaveNewsButtonDisplayed();

            // Thread.sleep(5000);
            // Publish a new
            administrationMainPage.addNew();

            // Thread.sleep(5000);

            // Delete a new
            administrationMainPage.deleteNew();
            // Thread.sleep(5000);
            // go admin page
            administrationMainPage.goAdmin();

            // Click on events
            administrationMainPage = administrationMainPage
                    .clickOnEventAdministrationPage();
            // Check 'añadir evento' button is displayed
            isAddEventsButtonDisplayed();
            // Click on this button
            administrationMainPage = administrationMainPage
                    .clickOnAddEventAdministrationPage();

            // Thread.sleep(5000);

            // Check text title field is displayed
            isTitleEventFieldDisplayed();
            // Check publish date is displayed
            isDescriptionEventDisplayed();
            // Check 'save' button is displayed
            isSaveEventButtonDisplayed();

            // Thread.sleep(5000);
            // Publish a new
            administrationMainPage.addEvent();

            // Thread.sleep(5000);

            // Delete a new
            administrationMainPage.deleteEvent();
            // Thread.sleep(5000);
            // go admin page
            administrationMainPage.goAdmin();
            Thread.sleep(5000);

        } finally {
            // Steps to clear the stage (Post steps)
        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End administrationMainPageSearch test");
    }

    // ************************ Methods *************************
    /**
     * Checks if the search return several results
     * @return true if there are several results and false in the opposite case
     */
    public boolean checkSeveralResults() {
        String resultClassName = "r";
        List<WebElement> elements = null;
        boolean isSeveral = false;

        driver.wait(By.className(resultClassName), 20);

        if (driver.isElementDisplayed(By.className(resultClassName))) {
            elements = driver.findElements(By.className(resultClassName));

            if (elements.size() > 1) {
                isSeveral = true;
            }
        }

        return isSeveral;
    }

    // ************************ Assertions *************************
    /**
     * This assertion check if the search return several results
     */
    public void areSeveralResults() {
        assertTrue(
                "There aren't several results, it should have several results",
                this.checkSeveralResults());
    }

    /**
     * This assertion check if logo is displayed
     */
    public void isLogoDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }
        assertTrue("The logo isn't displayed, it should be displayed",
                administrationMainPage.isElementVisibleByXPath("imgLogo"));
    }

    /**
     * This assertion check if user field is displayed
     */
    public void isFieldUserDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }
        assertTrue("The user field isn't displayed, it should be displayed",
                administrationMainPage.isElementVisibleByXPath("userField"));
    }

    /**
     * This assertion check if pass field is displayed
     */
    public void isFieldPassDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }
        assertTrue("The pass field isn't displayed, it should be displayed",
                administrationMainPage.isElementVisibleByXPath("passField"));
    }

    /**
     * This assertion check if session button is displayed
     */
    public void isSessionButtonDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }

        /* Check by Name */
        assertTrue(
                "The 'iniciar sesion' button isn't displayed, it should be displayed",
                administrationMainPage
                        .isElementVisibleByXPath("sessionButton"));
    }

    /**
     * This assertion check if event button is displayed
     */
    public void isEventButtonDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }

        /* Check by Name */
        assertTrue("The event button isn't displayed, it should be displayed",
                administrationMainPage.isElementVisibleByXPath("eventButton"));
    }

    /**
     * This assertion check if add event button is displayed
     */
    public void isAddEventButtonDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }

        /* Check by Name */
        assertTrue(
                "The add event button isn't displayed, it should be displayed",
                administrationMainPage
                        .isElementVisibleByXPath("addeventButton"));
    }

    /**
     * This assertion check if modify news button is displayed
     */
    public void isModifyNewsButtonDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }

        /* Check by Name */
        assertTrue(
                "The modify news button isn't displayed, it should be displayed",
                administrationMainPage
                        .isElementVisibleByXPath("modifynewsButton"));
    };

    /**
     * this assertion check if add news item is displayed on the news item page
     */
    public void isAddNewsButtonDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }

        /* Check by Name */
        assertTrue(
                "The add news button isn't displayed, it should be displayed",
                administrationMainPage
                        .isElementVisibleByXPath("addnewsitemButton"));
    };

    public void isTitleNewsFieldDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }

        /* Check by Name */
        assertTrue(
                "The add news button isn't displayed, it should be displayed",
                administrationMainPage.isElementVisibleById("titlenewsfield"));
    };

    public void isDescriptionNewsDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }

        /* Check by Name */
        assertTrue(
                "The add news button isn't displayed, it should be displayed",
                administrationMainPage.isElementVisibleById("descriptionnews"));
    };

    public void isSaveNewsButtonDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }

        /* Check by Name */
        assertTrue(
                "The add news button isn't displayed, it should be displayed",
                administrationMainPage
                        .isElementVisibleByXPath("savenewsbutton"));
    };

    /**
     * this assertion check if add news item is displayed on the news item page
     */
    public void isAddEventsButtonDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }

        /* Check by Name */
        assertTrue(
                "The add news button isn't displayed, it should be displayed",
                administrationMainPage
                        .isElementVisibleByXPath("addeventsButton"));
    };

    public void isTitleEventFieldDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }

        /* Check by Name */
        assertTrue(
                "The add news button isn't displayed, it should be displayed",
                administrationMainPage.isElementVisibleById("titleeventfield"));
    };

    public void isDescriptionEventDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }

        /* Check by Name */
        assertTrue(
                "The add news button isn't displayed, it should be displayed",
                administrationMainPage
                        .isElementVisibleById("descriptionevent"));
    };

    public void isSaveEventButtonDisplayed() {
        if (administrationMainPage == null) {
            administrationMainPage = new AdministrationMainPage(driver);
        }

        /* Check by Name */
        assertTrue(
                "The add news button isn't displayed, it should be displayed",
                administrationMainPage
                        .isElementVisibleByXPath("saveeventbutton"));
    };

}
