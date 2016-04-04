package com.emergya.pageObjects;

import org.apache.log4j.Logger;

import com.emergya.drivers.EmergyaWebDriver;

/**
 * This class returns
 * 
 * @author Alfonso Rodríguez Martín
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
        accessAdminPage();
    }

    /**
     * This method goes directly to the Administration Page for us to do the tests.
     */
    public void accessAdminPage() {
        driver.get("localhost:8000/admin");
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
     * This method logs in using the admin account
     * @param stringSearch
     */
    public void loginAsAdmin() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start loginAsAdmin -[" + this.getClass().getSimpleName()
                + "- method]");

        String id = "usuarioTest";
        String password = "comomola";

        this.getElementById("login").sendKeys(id);
        this.getElementById("password").sendKeys(password);
        this.getElementByXPath("login").click();

        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End loginAsAdmin -[" + this.getClass().getSimpleName()
                + "- method]");
    }

    /**
     * This method accesses to the create a new event page.
     */

    public void goToCreateEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start goToCreateEvent -["
                + this.getClass().getSimpleName() + "- method]");
        if (this.getElementByXPath("botonEvento").isDisplayed()) {
            this.getElementByXPath("botonEvento").click();
        }

    }
}
