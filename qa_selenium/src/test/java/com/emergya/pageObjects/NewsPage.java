package com.emergya.pageObjects;

import org.apache.log4j.Logger;

import com.emergya.drivers.EmergyaWebDriver;

public class NewsPage extends BasePageObject {

    /**
     * Logger class initialization.
     */
    Logger log = Logger.getLogger(NewsPage.class);

    /**
     * Constructor method
     * @param driver selenium webdriver
     */
    public NewsPage(EmergyaWebDriver driver) {
        super(driver);
        this.accesoAdmin();
    }

    public void accesoAdmin() {
        driver.get("localhost:8000");
        this.isReady();
    }

    /**
     * Checks that the PO is ready
     * @param pageObject page object to be used
     */
    @Override
    public boolean isReady() {
        log.info("[log-PageObjects] " + this.getClass().getSimpleName()
                + " - Start isReady method");

        boolean status = this.isElementVisibleById("arranque");

        log.info("[log-PageObjects] " + this.getClass().getSimpleName()
                + " - End isReady method");

        return status;
    }

    // Page object methods
    /**
     * This method click on Emergya page
     * @return
     */

    public void newsCRUDFunciones(String title, String description) {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start CRUDNewsItemFunciones -["
                + this.getClass().getSimpleName() + "- method]");

        this.getElementByXPath("newsFunciones").click();

        this.getElementByXPath("nueva").click();

        this.getElementById("title").sendKeys(title);

        this.getElementById("description").sendKeys(description);

        this.getElementByXPath("guardar").click();

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End CRUDNewsItemFunciones method");
    }

    public void accesoNewsClases() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start accesoNewsItem -[" + this.getClass().getSimpleName()
                + "- method]");

        this.getElementByXPath("newsFunciones").click();

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End accesoNewsItem method");
    }

    public void createNewsItem(String introTitle, String introDescription)
            throws InterruptedException {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start CreateNewsItem -[" + this.getClass().getSimpleName()
                + "- method]");

        this.getElementByXPath("back").click();

        this.getElementByXPath("add2").click();

        if (this.isElementVisibleById("title")) {
            this.getElementById("title").sendKeys(introTitle);
        }

        if (this.isElementVisibleById("description")) {
            this.getElementById("description").sendKeys(introDescription);
        }

        this.getElementByXPath("today").click();

        this.getElementByName("save").click();

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End CreateNewsItem method");
    }

    public void updateNewsItem(String introDescription) {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start UpdateNewsItem -[" + this.getClass().getSimpleName()
                + "- method]");

        this.getElementByXPath("item").click();

        if (this.isElementVisibleById("description")) {
            this.getElementById("description").clear();
            this.getElementById("description").sendKeys(introDescription);
        }

        this.getElementByName("save").click();

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End UpdateNewsItem method");
    }

    public void deleteNewsItem() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start DeleteNewsItem -[" + this.getClass().getSimpleName()
                + "- method]");

        this.getElementByXPath("item").click();

        this.getElementByXPath("delete").click();

        this.getElementByXPath("confirm").click();

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End DeleteNewsItem method");
    }

}
