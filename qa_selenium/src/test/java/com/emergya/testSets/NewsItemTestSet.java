package com.emergya.testSets;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.emergya.pageObjects.NewsItemPage;
import com.emergya.utils.DefaultTestSet;

/**
 * A test class contain the tests of a specific page in the application
 * @author Alfonso Rodríguez Martín
 */
public class NewsItemTestSet extends DefaultTestSet {

    Logger log = Logger.getLogger(NewsItemTestSet.class);

    public NewsItemTestSet() {
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
     * Description: This test is just to verify if we can create items.
     * 
     * Pre steps:
     * - None
     *   
     * Steps:
     * - Direction to a creation page
     * - Checking if everything is correct
     * - Create fresh news
     * - Checking if they were created
     * Post Steps:
     * - Delete the created news
     * - Verify
     * @throws InterruptedException 
     * 
     */
    @Test(description = "newsCreation")
    public void newsCreation() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Test 1 - Creation");
        newsItemPage = new NewsItemPage(driver);

        try {
            // We make a first check to see if we are in the News Page.
            areWeOnNewsPage();
            // Now, we proceed to the page that allows to create news.
            newsItemPage.goToCreationPage();
            // This assertion is for seeing if there are inputs.
            areInputsVisible();
            // Now we create news.
            newsItemPage.createNews();
            // Let's check if it was good
            isConfirmMessageVisible();
            // We go back
            newsItemPage.accessNewsPage();
            // And we confirm.
            areWeOnNewsPage();

        } finally {
            // Checking if the news was created and it it's there.
            isNewsCorrect(driver.getCurrentUrl());
            // We move forward to the Delete News page.
            newsItemPage.goToDeleteNews();
            // If everything is correct we gotta see a new confirmation button
            isConfirmDeleteButtonVisible();
            // And, we delete the News.
            newsItemPage.doDelete();
            // The last check is that if the news were deleted.
            isConfirmMessageVisible();
        }
    }

    /**
     * Description: This test checks if we can make an edit.
     * 
     * Pre steps:
     * - Create an news
     *   
     * Steps:
     * - Direction to edit page
     * - Checking if everything is correct
     * - Edit the previous news.
     * - Check if the edit was correct.
     * Post Steps:
     * - Delete the created news
     * - Verify
     * @throws InterruptedException 
     * 
     */
    @Test(description = "newsEdit")
    public void newsEdit() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Test 2: News edit ");
        newsItemPage = new NewsItemPage(driver);

        try {
            // We make a first check to see if we are in the News Page.
            areWeOnNewsPage();
            // Now, we proceed to the page that allows to create news.
            newsItemPage.goToCreationPage();
            // This assertion is for seeing if there are inputs.
            areInputsVisible();
            // Now we create news.
            newsItemPage.createNews();
            // Let's check if it was good
            isConfirmMessageVisible();
            // We try to move to the edit view.
            newsItemPage.accessNewsPage();
            // And we confirm.
            areWeOnNewsPage();
            // Now we move to edition.
            newsItemPage.goToEditNews();
            // Check if we are there
            areInputsVisible();
            // We do an edit
            newsItemPage.doEdit();
            // We check if it was correct
            isConfirmMessageVisible();
            // We go back
            newsItemPage.accessNewsPage();
            // And we confirm.
            areWeOnNewsPage();

        } finally {
            // Basic news check.
            isNewsCorrect(driver.getCurrentUrl());
            // We move forward to the Delete News page.
            newsItemPage.goToDeleteNews();
            // If everything is correct we gotta see a new confirmation button
            isConfirmDeleteButtonVisible();
            // And, we delete the News.
            newsItemPage.doDelete();
            // The last check is that if the news were deleted.
            isConfirmMessageVisible();
        }
    }

    /**
     * Description: This test is just to verify if we can create items.
     * 
     * Pre steps:
     * - Create an event
     *  -Check
     *   
     * Steps:
     * - Delete the event
     * Post Steps:
     * - Verify
     * @throws InterruptedException 
     * 
     */
    @Test(description = "newsDelete")
    public void newsDelete() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Test 3 - Delete");
        newsItemPage = new NewsItemPage(driver);

        try {
            // We make a first check to see if we are in the News Page.
            areWeOnNewsPage();
            // Now, we proceed to the page that allows to create news.
            newsItemPage.goToCreationPage();
            // This assertion is for seeing if there are inputs.
            areInputsVisible();
            // Now we create news.
            newsItemPage.createNews();
            // Let's check if it was good
            isConfirmMessageVisible();
            // We go back
            newsItemPage.accessNewsPage();
            // And we confirm.
            areWeOnNewsPage();

        } finally {
            // Checking if the news was created and it it's there.
            isNewsCorrect(driver.getCurrentUrl());
            // We move forward to the Delete News page.
            newsItemPage.goToDeleteNews();
            // If everything is correct we gotta see a new confirmation button
            isConfirmDeleteButtonVisible();
            // And, we delete the News.
            newsItemPage.doDelete();
            // The last check is that if the news were deleted.
            isConfirmMessageVisible();
        }
    }

    // ************************ Assertions *************************
    /**
     * This assertion is to prevent the deletion of a wrong news.
     */
    public void isNewsCorrect(String previousURL) {
        // First we need to move
        driver.get("localhost:8000/v2");
        String evaluate = newsItemPage.getElementByXPath("news").getText();
        String test = "No deberias estar viendo esto";
        assertEquals(test, evaluate);
        // once done, we move to where we were.
        driver.get(previousURL);
    }

    /**
     * Checking if we are on News Page.
     */
    public void areWeOnNewsPage() {
        if (newsItemPage == null) {
            newsItemPage = new NewsItemPage(driver);
        }
        String test = newsItemPage.getElementById("idnoticias").getText();
        assertEquals(test, "Noticias Django");
    }

    /**
     * This assertion is to see if the form inputs are visible
     */
    public void areInputsVisible() {
        if (newsItemPage == null) {
            newsItemPage = new NewsItemPage(driver);
        }
        assertTrue("The inputs are not visible. Something has gone wrong",
                newsItemPage.isElementVisibleById("campoT"));

    }

    /**
     * THis assertion checks if the confirmation message is visible 
     */
    public void isConfirmMessageVisible() {
        if (newsItemPage == null) {
            newsItemPage = new NewsItemPage(driver);
        }
        assertTrue("The message is not shown, something has gone wrong",
                newsItemPage.isElementVisibleByXPath("message"));
    }

    /**
     * This assertion is to check if the delete button is visible.
     */
    public void isConfirmDeleteButtonVisible() {
        if (newsItemPage == null) {
            newsItemPage = new NewsItemPage(driver);
        }
        assertTrue("The button is not displayed. Something has happened",
                newsItemPage.isElementVisibleByXPath("confirm"));
    }
}
