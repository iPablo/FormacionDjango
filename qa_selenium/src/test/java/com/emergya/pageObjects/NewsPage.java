package com.emergya.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.emergya.drivers.EmergyaWebDriver;

public class NewsPage extends BasePageObject {
    /**
     * Logger class initialization.
     */
    Logger log = Logger.getLogger(AdministracionPage.class);

    /**
     * Constructor method
     * @param driver selenium webdriver
     */
    public NewsPage(EmergyaWebDriver driver) {
        super(driver);
        this.accessToNewsItem();
    }

    public void accessToNewsItem() {
        driver.get("localhost:8000/v2/");
        this.isReady();
    }

    /**
     * Checks that the PO is ready
     * @param pageObject page object to be used
     */
    @Override
    public boolean isReady() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start isReady method");

        boolean status = this.isElementVisibleById("title");

        log.info("[log-NewsnPage] " + this.getClass().getSimpleName()
                + " - End isReady method");

        return status;
    }

    // Page object methods
    /**
     * Create NewsItem
     */
    public void createNewsItem() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start NewsItem -[" + this.getClass().getSimpleName()
                + "- method]");
        this.buttonAddItem();
        this.setTitle("Test QA");
        this.setDescription("Test QA");
        this.setOwner();
        this.save();

        log.info(
                "[log-" + this.getClass().getSimpleName() + "]- End NewsItem -["
                        + this.getClass().getSimpleName() + "- method]");
    }

    public void buttonAddItem() {
        if (this.getElementByXPath("buttonAddItem").isDisplayed()) {
            this.getElementByXPath("buttonAddItem").click();
        }
    }

    public void setTitle(String x) {
        if (this.getElementById("id_title").isDisplayed()) {
            this.getElementById("id_title").sendKeys(x);
        }
    }

    public void setDescription(String x) {
        if (this.getElementById("id_description").isDisplayed()) {
            this.getElementById("id_description").sendKeys(x);
        }
    }

    public void setOwner() {
        driver.clickOutWithMouse(By.id("id_owner"));
    }

    public void save() {
        if (this.getElementByXPath("save").isDisplayed()) {
            this.getElementByXPath("save").click();
        }
    }

    /**
     * Update NewsItem
     */
    public void updateNewsItem() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start NewsItem -[" + this.getClass().getSimpleName()
                + "- method]");
        this.buttonUpdate();
        this.setTitle("Update");
        this.setDescription("Update");
        this.setOwner();
        this.save();

        log.info(
                "[log-" + this.getClass().getSimpleName() + "]- End NewsItem -["
                        + this.getClass().getSimpleName() + "- method]");
    }

    public void buttonUpdate() {
        if (this.getElementByXPath("buttonUpdate").isDisplayed()) {
            this.getElementByXPath("buttonUpdate").click();
        }
    }

    /**
     * Delete NewsItem
     */
    public void deleteNewsItem() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start NewsItem -[" + this.getClass().getSimpleName()
                + "- method]");
        this.buttonDelete();
        this.buttonConfirmDelete();

        log.info(
                "[log-" + this.getClass().getSimpleName() + "]- End NewsItem -["
                        + this.getClass().getSimpleName() + "- method]");
    }

    public void buttonDelete() {
        if (this.getElementByXPath("buttonDelete").isDisplayed()) {
            this.getElementByXPath("buttonDelete").click();
        }
    }

    public void buttonConfirmDelete() {
        if (this.getElementByXPath("buttonConfirmDelete").isDisplayed()) {
            this.getElementByXPath("buttonConfirmDelete").click();
        }
    }

}
