package com.emergya.pageObjects;

import org.apache.log4j.Logger;

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
        this.accessToAdmin();
    }

    public void accessToAdmin() {
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

}
