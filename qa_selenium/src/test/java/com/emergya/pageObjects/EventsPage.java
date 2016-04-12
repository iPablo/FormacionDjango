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
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start Create Event -[" + this.getClass().getSimpleName()
                + "- method]");

        this.buttonAddItem();
        this.setTitle("Test QA");
        this.setDescription("Test QA");
        this.setStartDate("2016-04-12 10:00");
        this.setEndDate("2016-04-12 10:00");
        this.setOwner();
        this.save();

        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End Create Event -[" + this.getClass().getSimpleName()
                + "- method]");
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

    public void setStartDate(String x) {
        if (this.getElementById("id_start_date").isDisplayed()) {
            this.getElementById("id_start_date").sendKeys(x);
        }
    }

    public void setEndDate(String x) {
        if (this.getElementById("id_end_date").isDisplayed()) {
            this.getElementById("id_end_date").sendKeys(x);
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
     * Update Event
     */
    public void updateEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start Update Event -[" + this.getClass().getSimpleName()
                + "- method]");

        this.buttonUpdate();
        this.setTitle("Test QA Update");
        this.setDescription("Test QA Update");
        this.setOwner();
        this.save();

        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End Update Event -[" + this.getClass().getSimpleName()
                + "- method]");
    }

    public void buttonUpdate() {
        for (int i = 1; i <= driver.findElements(By.name("events"))
                .size(); i++) {
            if (driver.findElements(By.name("events")).get(i).getText()
                    .equals("Test QA")) {
                i++;
                String aux = "/html/body/div/div/div/table/tbody/tr[" + i
                        + "]/td[5]/a";
                driver.findElementByXPath(aux).click();
            }
        }

        /*
         * if (this.getElementByXPath("buttonUpdate").isDisplayed()) {
         * this.getElementByXPath("buttonUpdate").click(); }
         */
    }

    /**
     * Delete Event
     */
    public void deleteEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start Delete Event -[" + this.getClass().getSimpleName()
                + "- method]");

        this.buttonDelete();
        this.buttonConfirmDelete();

        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End Delete Event -[" + this.getClass().getSimpleName()
                + "- method]");
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
