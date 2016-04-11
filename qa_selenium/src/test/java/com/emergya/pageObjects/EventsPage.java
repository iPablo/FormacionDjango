package com.emergya.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.emergya.drivers.EmergyaWebDriver;

public class EventsPage extends BasePageObject {
    /**
     * Logger class initialization.
     */
    Logger log = Logger.getLogger(EventsPage.class);

    /**
     * Constructor method
     * @param driver selenium webdriver
     */
    public EventsPage(EmergyaWebDriver driver) {
        super(driver);
        this.accessToEvent();
    }

    public void accessToEvent() {
        driver.get("localhost:8000/event/");
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
     * Create Event
     */
    public void createEvent() {
        log.info("[log-" + this.getClass().getSimpleName() + "]- Start Event -["
                + this.getClass().getSimpleName() + "- method]");

        this.getElementByXPath("buttonAddItem").click();
        this.getElementById("id_title").sendKeys("Test QA");
        this.getElementById("id_description").sendKeys("Test QA");
        this.getElementById("id_start_date").sendKeys("2016-04-11 10:00");
        this.getElementById("id_end_date").sendKeys("2016-04-12 10:00");
        driver.clickOutWithMouse(By.id("id_owner"));
        this.getElementByXPath("save").click();

        log.info("[log-" + this.getClass().getSimpleName() + "]- End Event -["
                + this.getClass().getSimpleName() + "- method]");
    }

    /**
     * Update Event
     */
    public void updateEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start NewsItem -[" + this.getClass().getSimpleName()
                + "- method]");

        this.getElementByXPath("buttonUpdate").click();
        this.getElementById("id_title").sendKeys("Test Updated QA");
        this.getElementById("id_description").sendKeys("Test Updated QA");
        this.getElementById("id_description").sendKeys("Test Updated QA");
        driver.clickOutWithMouse(By.id("id_owner"));
        this.getElementByXPath("save").click();

        log.info(
                "[log-" + this.getClass().getSimpleName() + "]- End NewsItem -["
                        + this.getClass().getSimpleName() + "- method]");
    }
}
