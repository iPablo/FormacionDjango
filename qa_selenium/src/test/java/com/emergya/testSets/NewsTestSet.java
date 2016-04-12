package com.emergya.testSets;

import static org.testng.AssertJUnit.assertTrue;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.emergya.pageObjects.NewsPage;
import com.emergya.utils.DefaultTestSet;

public class NewsTestSet extends DefaultTestSet {
    Logger log = Logger.getLogger(AdministracionTestSet.class);
    NewsPage newspage;

    public NewsTestSet() {
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
    public void createNewsItem() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Start Create News Item - ");
        newspage = new NewsPage(driver);

        try {
            Thread.sleep(1000);

            // Create NewsItem
            newspage.createNewsItem();
            this.isItemDisplayed();

            // Update NewsItem
            // newspage.updateNewsItem();
            // this.isItemDisplayed();

            // Delete NewsItem
            newspage.deleteNewsItem();
            this.isItemDisplayed();

            Thread.sleep(5000);

        } finally {

        }
    }

    @Test(description = "newsPage")
    public void updateNewsItem() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Start Create News Item - ");
        newspage = new NewsPage(driver);

        try {
            Thread.sleep(1000);

            // Create NewsItem
            newspage.createNewsItem();
            this.isItemDisplayed();

            // Update NewsItem
            newspage.updateNewsItem();
            this.isItemDisplayed();

            // Delete NewsItem
            newspage.deleteNewsItem();
            this.isItemDisplayed();

            Thread.sleep(5000);

        } finally {

        }
    }

    @Test(description = "newsPage")
    public void deleteNewsItem() throws InterruptedException {
        log.info("[log-TestSet] " + this.getClass().getName()
                + "- Start Create News Item - ");
        newspage = new NewsPage(driver);

        try {
            Thread.sleep(1000);

            // Create NewsItem
            newspage.createNewsItem();
            this.isItemDisplayed();

            // Update NewsItem
            // newspage.updateNewsItem();
            // this.isItemDisplayed();

            // Delete NewsItem
            newspage.deleteNewsItem();
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
        if (newspage == null) {
            newspage = new NewsPage(driver);
        }
        assertTrue("The item isn't displayed, it should say something",
                newspage.isElementVisibleByXPath("item"));
    }
}
