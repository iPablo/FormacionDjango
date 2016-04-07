package com.emergya.pageObjects;

import org.apache.log4j.Logger;

import com.emergya.drivers.EmergyaWebDriver;

public class AdministracionPage extends BasePageObject {
    /**
     * Logger class initialization.
     */
    Logger log = Logger.getLogger(AdministracionPage.class);

    /**
     * Constructor method
     * @param driver selenium webdriver
     */
    public AdministracionPage(EmergyaWebDriver driver) {
        super(driver);
        this.accesoAdmin();
    }

    public void accesoAdmin() {
        driver.get("localhost:8000/admin/");
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

        boolean status = this.isElementVisibleByXPath("arranque");

        log.info("[log-PageObjects] " + this.getClass().getSimpleName()
                + " - End isReady method");

        return status;
    }

    // Page object methods
    /**
     * This method do a search in google by a string search
     * @param stringSearch
     */
    public void admin(String introUsu, String introPass) {
        log.info("[log-" + this.getClass().getSimpleName() + "]- Start admin -["
                + this.getClass().getSimpleName() + "- method]");

        String usuario = "usuario";
        String contrasenia = "contrasenia";

        if (this.isElementVisibleById(usuario)) {
            this.getElementById(usuario).sendKeys(introUsu);

            if (this.isElementVisibleById(contrasenia)) {
                this.getElementById(contrasenia).sendKeys(introPass);
            }
            this.getElementByXPath("login").click();
        }

        log.info("[log-" + this.getClass().getSimpleName() + "]- End admin -["
                + this.getClass().getSimpleName() + "- method]");
    }

    /**
     * This method click on Emergya page
     * @return
     */

    public void createEvent(String introTitle, String introDescription,
            String introStart, String introEnd) {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start CreateEvent -[" + this.getClass().getSimpleName()
                + "- method]");

        this.getElementByXPath("add").click();

        if (this.isElementVisibleById("title")) {
            this.getElementById("title").sendKeys(introTitle);
        }

        if (this.isElementVisibleById("description")) {
            this.getElementById("description").sendKeys(introDescription);
        }

        if (this.isElementVisibleById("start_date")) {
            this.getElementById("start_date").sendKeys(introStart);
        }

        if (this.isElementVisibleById("end_date")) {
            this.getElementById("end_date").sendKeys(introEnd);
        }

        this.getElementByName("save").click();

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End CreateEvent method");
    }

    public void updateEvent(String introDescription) {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start UpdateEvent -[" + this.getClass().getSimpleName()
                + "- method]");

        this.getElementByXPath("item").click();

        if (this.isElementVisibleById("description")) {
            this.getElementById("description").clear();
            this.getElementById("description").sendKeys(introDescription);
        }

        this.getElementByName("save").click();

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End UpdateEvent method");
    }

    public void deleteEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start DeleteEvent -[" + this.getClass().getSimpleName()
                + "- method]");

        this.getElementByXPath("item").click();

        this.getElementByXPath("delete").click();

        this.getElementByXPath("confirm").click();

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End DeleteEvent method");
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
