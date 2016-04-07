package com.emergya.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.emergya.drivers.EmergyaWebDriver;

/**
 * 
 * @author jchierro
 *
 */
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
        this.accessToAdmin();
    }

    public void accessToAdmin() {
        driver.get("localhost:8000/admin/");
        this.isReady();
    }

    /**
     * Checks that the PO is ready
     * @param pageObject page object to be used
     */
    @Override
    public boolean isReady() {
        log.info("[log-AdminPage] " + this.getClass().getSimpleName()
                + " - Start isReady method");

        boolean status = this.isElementVisibleById("title");

        log.info("[log-AdminPage] " + this.getClass().getSimpleName()
                + " - End isReady method");

        return status;
    }

    // Page object methods

    /**
     * 
     */
    public void loginAsAdmin() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start loginAsAdmin -[" + this.getClass().getSimpleName()
                + "- method]");

        this.getElementById("login").sendKeys("admin");
        this.getElementById("password").sendKeys("passpass");
        this.getElementByXPath("login").click();

        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End loginAsAdmin -[" + this.getClass().getSimpleName()
                + "- method]");
    }

    /**
     * 
     */
    public void clickButtonCreateEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start createEvent -[" + this.getClass().getSimpleName()
                + "- method]");
        if (this.getElementByXPath("buttonCreateEvent").isDisplayed()) {
            this.getElementByXPath("buttonCreateEvent").click();
            this.getElementByXPath("buttonAddEvent").click();
        }

    }

    /**
     * 
     */
    public void formCreateEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start formCreateEvent -["
                + this.getClass().getSimpleName() + "- method]");

        this.getElementById("id_title").sendKeys("Test title");
        this.getElementById("id_description").sendKeys("Test description");
        driver.clickOutWithMouse(By.id("id_owner"));
        // driver.clickOutWithMouse(By.xpath(".//*[@id='id_owner']/option[1]"));
        this.getElementByXPath("start_date").click();
        this.getElementByXPath("start_time").click();
        this.getElementByXPath("end_date").click();
        this.getElementByXPath("end_time").click();
        this.getElementByName("save_event").click();
    }

    /**
     * 
     */
    public void formDeleteEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start formDeleteEvent -["
                + this.getClass().getSimpleName() + "- method]");

        this.getElementByXPath("goToEvent").click();
        this.getElementByXPath("delete_event").click();
        this.getElementByXPath("button_delete_event").click();
    }

    /**
     * 
     */
    public void formCreateNewsItem() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start formCreateNewsItem -["
                + this.getClass().getSimpleName() + "- method]");

        driver.get("localhost:8000/admin/NoticiasEventos/newsitem/");
        this.getElementByXPath("buttonAddNewsItem").click();
        this.getElementById("id_title").sendKeys("Test title");
        this.getElementById("id_description").sendKeys("Test description");
        driver.clickOutWithMouse(By.id("id_owner"));
        this.getElementByXPath("publish_date").click();
        this.getElementByXPath("publish_time").click();
        this.getElementByName("save_newsItem").click();
    }

    public void formDeleteNewsItem() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start formDeleteNewsItem -["
                + this.getClass().getSimpleName() + "- method]");
        this.getElementByXPath("goToNewsItem").click();
        this.getElementByXPath("delete_newsItem").click();
        this.getElementByXPath("button_delete_newsItem").click();
    }

    public void logout() {
        log.info(
                "[log-" + this.getClass().getSimpleName() + "]- Start logout -["
                        + this.getClass().getSimpleName() + "- method]");
        this.getElementByXPath("logout").click();
    }
}
