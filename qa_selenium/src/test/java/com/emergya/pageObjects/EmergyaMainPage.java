package com.emergya.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.emergya.drivers.EmergyaWebDriver;

/**
 * A Page Object (PO) contain the behavior of a specific page in the application
 * EmergyaMainPage: This PO contain the methods to interact with the emergya main page
 * @author Ivan Bermudez <ibermudez@emergya.com>
 * @author Jose Antonio Sanchez <jasanchez@emergya.com>
 */
public class EmergyaMainPage extends BasePageObject {

    /**
     * Logger class initialization.
     */
    Logger log = Logger.getLogger(GoogleMainPage.class);

    /**
     * Constructor method
     * @param driver selenium webdriver
     */
    public EmergyaMainPage(EmergyaWebDriver driver) {
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

        boolean status = this.isElementVisibleByXPath("imgLogoEmergya");

        log.info("[log-PageObjects] " + this.getClass().getSimpleName()
                + " - End isReady method");

        return status;
    }

    // Page object methods
    /*
     * This method search contact tab on Emergya main page.
     */
    public EmergyaMainPage clickOnContactEmergyaPage() {
        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- Start clickOnPage method");
        String xPathSelector = ".//*[@id='block-menu-menu-cabecera']/ul/li[3]/a/span";
        driver.clickIfExists(By.xpath(xPathSelector));

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End clickOnPage method");
        return new EmergyaMainPage(driver);
    }

    /*
     * This method search Work with us in contact Emergya main page.
     */
    public EmergyaMainPage clickOnWorkEmergyaPage() {
        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- Start clickOnPage method");
        String xPathSelector = ".//*[@id='block-menu-menu-cabecera']/ul/li[1]/a/span";
        driver.clickIfExists(By.xpath(xPathSelector));

        log.info("[log-pageObjects]" + this.getClass().getSimpleName()
                + "]- End clickOnPage method");
        return new EmergyaMainPage(driver);
    }
}
