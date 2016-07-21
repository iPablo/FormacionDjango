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
     * Create Event
     */
    public void createEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start CreateEvent -[" + this.getClass().getSimpleName()
                + "- method]");
        this.clickButtonCreateEvent();
        this.setTitle("Test title");
        this.setDescription("Test Description");
        this.setOwner();
        this.setStartDateNewsItem();
        this.setStartTimeNewsItem();
        this.setEndDateNewsItem();
        this.setEndTimeNewsItem();
        this.save();
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End CreateEvent -[" + this.getClass().getSimpleName()
                + "- method]");
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

    public void setStartDateNewsItem() {
        if (this.getElementByXPath("start_date").isDisplayed()) {
            this.getElementByXPath("start_date").click();
        }
    }

    public void setStartTimeNewsItem() {
        if (this.getElementByXPath("start_time").isDisplayed()) {
            this.getElementByXPath("start_time").click();
        }
    }

    public void setEndDateNewsItem() {
        if (this.getElementByXPath("end_date").isDisplayed()) {
            this.getElementByXPath("end_date").click();
        }
    }

    public void setEndTimeNewsItem() {
        if (this.getElementByXPath("end_time").isDisplayed()) {
            this.getElementByXPath("end_time").click();
        }
    }

    public void save() {
        if (this.getElementByName("save_event").isDisplayed()) {
            this.getElementByName("save_event").click();
        }
    }

    public void clickButtonCreateEvent() {
        if (this.getElementByXPath("buttonCreateEvent").isDisplayed()) {
            this.getElementByXPath("buttonCreateEvent").click();
            this.getElementByXPath("buttonAddEvent").click();
        }

    }

    /**
     * Delete Event
     */
    public void deleteEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start DeleteEvent -[" + this.getClass().getSimpleName()
                + "- method]");
        this.goToEvent();
        this.buttonDeleteEvent();
        this.confirmDeleteEvent();
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End DeleteEvent -[" + this.getClass().getSimpleName()
                + "- method]");
    }

    public void goToEvent() {
        if (this.getElementByXPath("goToEvent").isDisplayed()) {
            this.getElementByXPath("goToEvent").click();
        }
    }

    public void buttonDeleteEvent() {
        if (this.getElementByXPath("delete_event").isDisplayed()) {
            this.getElementByXPath("delete_event").click();
        }
    }

    public void confirmDeleteEvent() {
        if (this.getElementByXPath("button_delete_event").isDisplayed()) {
            this.getElementByXPath("button_delete_event").click();
        }
    }

    /**
     * Create NewsItem
     */
    public void createNewsItem() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start CreateNewsItem -[" + this.getClass().getSimpleName()
                + "- method]");

        this.goToNewsItem();
        this.buttonAddNewsItem();
        this.setTitle("Test Title");
        this.setDescription("Test Description");
        this.setOwner();
        this.setPublishDate();
        this.setPublishTime();
        this.save();

        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End CreateNewsItem -[" + this.getClass().getSimpleName()
                + "- method]");

    }

    public void goToNewsItem() {
        driver.get("localhost:8000/admin/NoticiasEventos/newsitem/");
    }

    public void buttonAddNewsItem() {
        if (this.getElementByXPath("buttonAddNewsItem").isDisplayed()) {
            this.getElementByXPath("buttonAddNewsItem").click();
        }
    }

    public void setPublishDate() {
        if (this.getElementByXPath("publish_date").isDisplayed()) {
            this.getElementByXPath("publish_date").click();
        }
    }

    public void setPublishTime() {
        if (this.getElementByXPath("publish_time").isDisplayed()) {
            this.getElementByXPath("publish_time").click();
        }
    }

    public void deleteNewsItem() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start DeleteNewsItem -[" + this.getClass().getSimpleName()
                + "- method]");
        this.buttonGoToNewsItem();
        this.buttonNewsItem();
        this.confirmButtonNewsItem();
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End DeleteNewsItem -[" + this.getClass().getSimpleName()
                + "- method]");
    }

    public void buttonGoToNewsItem() {
        if (this.getElementByXPath("goToNewsItem").isDisplayed()) {
            this.getElementByXPath("goToNewsItem").click();
        }
    }

    public void buttonNewsItem() {
        if (this.getElementByXPath("delete_newsItem").isDisplayed()) {
            this.getElementByXPath("delete_newsItem").click();
        }
    }

    public void confirmButtonNewsItem() {
        if (this.getElementByXPath("button_delete_newsItem").isDisplayed()) {
            this.getElementByXPath("button_delete_newsItem").click();
        }
    }

    public void logout() {
        log.info(
                "[log-" + this.getClass().getSimpleName() + "]- Start logout -["
                        + this.getClass().getSimpleName() + "- method]");
        this.getElementByXPath("logout").click();
        log.info("[log-" + this.getClass().getSimpleName() + "]- End logout -["
                + this.getClass().getSimpleName() + "- method]");
    }
}
