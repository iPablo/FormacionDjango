package com.emergya.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.emergya.drivers.EmergyaWebDriver;

public class AdministrationMainPage extends BasePageObject {
    /**
     * Logger class initialization.
     */
    Logger log = Logger.getLogger(GoogleMainPage.class);

    /**
     * Constructor method
     * @param driver selenium webdriver
     */
    public AdministrationMainPage(EmergyaWebDriver driver) {
        super(driver);
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

        boolean status = this.isElementVisibleByXPath("imgLogo");

        log.info("[log-PageObjects] " + this.getClass().getSimpleName()
                + " - End isReady method");

        return status;

    }

    // Page object methods
    /**
     * This method do a search in google by a string search
     * @param stringSearch
     */
    public void doSearch() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start doSearch -[" + this.getClass().getSimpleName()
                + "- method]");

        String userField = "admin";
        String passField = "proyecto";
        boolean a = false;
        boolean b = false;

        if (this.isElementVisibleById("userField")) {
            this.getElementById("userField").sendKeys(userField);
            a = true;
        }
        ;
        if (this.isElementVisibleById("passField")) {
            this.getElementById("passField").sendKeys(passField);
            b = true;
        }
        ;
        if (a && b) {
            if (this.isElementVisibleByXPath("sessionButton")) {
                this.getElementByXPath("sessionButton").click();
            }
            ;
        }
        ;
        log.info(
                "[log-" + this.getClass().getSimpleName() + "]- End doSearch -["
                        + this.getClass().getSimpleName() + "- method]");
    };

    /**
     * This method insert a new news
     * @param stringSearch
     */
    public void addNew() {
        log.info(
                "[log-" + this.getClass().getSimpleName() + "]- Start addNew -["
                        + this.getClass().getSimpleName() + "- method]");

        String titleField = "hola";
        String descriptionField = "hola";
        boolean a = false;
        boolean b = false;
        boolean c = false;
        boolean d = false;

        if (this.isElementVisibleById("titlenewsfield")) {
            this.getElementById("titlenewsfield").sendKeys(titleField);
            a = true;
        }
        ;
        if (this.isElementVisibleById("descriptionnews")) {
            this.getElementById("descriptionnews").sendKeys(descriptionField);
            b = true;
        }
        ;
        if (this.isElementVisibleByXPath("datenews")) {
            this.getElementByXPath("datenews").click();
            c = true;
        }
        ;

        if (this.isElementVisibleByXPath("timenews")) {
            this.getElementByXPath("timenews").click();
            d = true;
        }
        ;

        if (a && b && c && d) {
            if (this.isElementVisibleByXPath("savenewsbutton")) {
                this.getElementByXPath("savenewsbutton").click();
            }
            ;
        }
        ;
        log.info("[log-" + this.getClass().getSimpleName() + "]- End addNew -["
                + this.getClass().getSimpleName() + "- method]");
    };

    /**
     * This method do a search in google by a string search
     * @param stringSearch
     */
    public void deleteNew() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start deleteNew -[" + this.getClass().getSimpleName()
                + "- method]");

        if (this.isElementVisibleByXPath("selectnews")) {
            this.getElementByXPath("selectnews").click();
        }
        if (this.isElementVisibleByXPath("selectdelete")) {
            this.getElementByXPath("selectdelete").click();
        }
        if (this.isElementVisibleByXPath("deletenews")) {
            this.getElementByXPath("deletenews").click();
        }
        ;
        if (this.isElementVisibleByXPath("confirmdelete")) {
            this.getElementByXPath("confirmdelete").click();
        }
        ;

        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End deleteNew -[" + this.getClass().getSimpleName()
                + "- method]");
    };

    public void goAdmin() {
        driver.get("localhost:8000/admin");
    };

    /**
     * This method insert a new news
     * @param stringSearch
     */
    public void addEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start addEvent -[" + this.getClass().getSimpleName()
                + "- method]");

        String titleField = "hola";
        String descriptionField = "hola";
        boolean a = false;
        boolean b = false;
        boolean c = false;
        boolean d = false;
        boolean e = false;
        boolean f = false;

        if (this.isElementVisibleById("titleeventfield")) {
            this.getElementById("titleeventfield").sendKeys(titleField);
            a = true;
        }
        ;
        if (this.isElementVisibleById("descriptionevent")) {
            this.getElementById("descriptionevent").sendKeys(descriptionField);
            b = true;
        }
        ;
        if (this.isElementVisibleByXPath("dateeventstart")) {
            this.getElementByXPath("dateeventstart").click();
            c = true;
        }
        ;

        if (this.isElementVisibleByXPath("timeeventstart")) {
            this.getElementByXPath("timeeventstart").click();
            d = true;
        }
        ;
        if (this.isElementVisibleByXPath("dateeventend")) {
            this.getElementByXPath("dateeventend").click();
            e = true;
        }
        ;
        if (this.isElementVisibleByXPath("timeeventend")) {
            this.getElementByXPath("timeeventend").click();
            f = true;
        }
        ;

        if (a && b && c && d && e && f) {
            if (this.isElementVisibleByXPath("saveeventbutton")) {
                this.getElementByXPath("saveeventbutton").click();
            }
            ;
        }
        ;
        log.info(
                "[log-" + this.getClass().getSimpleName() + "]- End addEvent -["
                        + this.getClass().getSimpleName() + "- method]");
    };

    /**
     * This method do a search in google by a string search
     * @param stringSearch
     */
    public void deleteEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start deleteEvent -[" + this.getClass().getSimpleName()
                + "- method]");

        if (this.isElementVisibleByXPath("selectevent")) {
            this.getElementByXPath("selectevent").click();
        }
        if (this.isElementVisibleByXPath("selectdeleteevent")) {
            this.getElementByXPath("selectdeleteevent").click();
        }
        if (this.isElementVisibleByXPath("deleteevent")) {
            this.getElementByXPath("deleteevent").click();
        }
        ;
        if (this.isElementVisibleByXPath("confirmdeleteevent")) {
            this.getElementByXPath("confirmdeleteevent").click();
        }
        ;

        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End deleteEvent -[" + this.getClass().getSimpleName()
                + "- method]");
    };

    /*
     * This method do click to access to the admin main page.
     */
    public AdministrationMainPage clickOnSessionAdministrationPage() {
        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- Start clickOnPage method");
        String xPathSelector = ".//*[@id='login-form']/div[3]/input";
        driver.clickIfExists(By.xpath(xPathSelector));

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End clickOnPage method");
        return new AdministrationMainPage(driver);
    };

    public AdministrationMainPage clickOnNewsAdministrationPage() {
        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- Start clickOnPage method");
        String xPathSelector = ".//*[@id='content-main']/div[2]/table/tbody/tr[2]/th/a";
        driver.clickIfExists(By.xpath(xPathSelector));

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End clickOnPage method");
        return new AdministrationMainPage(driver);
    };

    public AdministrationMainPage clickOnAddNewsAdministrationPage() {
        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- Start clickOnPage method");
        String xPathSelector = ".//*[@id='content-main']/ul/li/a";
        driver.clickIfExists(By.xpath(xPathSelector));

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End clickOnPage method");
        return new AdministrationMainPage(driver);
    };

    public AdministrationMainPage clickOnEventAdministrationPage() {
        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- Start clickOnPage method");
        String xPathSelector = ".//*[@id='content-main']/div[2]/table/tbody/tr[1]/th/a";
        driver.clickIfExists(By.xpath(xPathSelector));

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End clickOnPage method");
        return new AdministrationMainPage(driver);
    };

    public AdministrationMainPage clickOnAddEventAdministrationPage() {
        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- Start clickOnPage method");
        String xPathSelector = ".//*[@id='content-main']/ul/li/a";
        driver.clickIfExists(By.xpath(xPathSelector));

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End clickOnPage method");
        return new AdministrationMainPage(driver);
    };
}
