package com.emergya.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.emergya.drivers.EmergyaWebDriver;

/**
 * This class returns
 * 
 * @author Alfonso Rodríguez Martín
 */
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
        accessEventsPage();
    }

    /**
     * This method goes directly to the Administration Page for us to do the tests.
     */
    public void accessEventsPage() {
        driver.get("localhost:8000/eventos");
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

        boolean status = this.isElementVisibleById("titulo");

        log.info("[log-AdminPage] " + this.getClass().getSimpleName()
                + " - End isReady method");

        return status;
    }

    // Page object methods
    /**
     * This method is for redirecting us to the event creation page.
     */
    public void goToEventCreate() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start goToEventCreate method");

        // driver.get("localhost:8000/eventos/crear");

        String url = driver.getCurrentUrl() + "crear";
        driver.get(url);

        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - End goToEventCreate method");

    }

    /**
     * Method to create some fresh events.
     */

    public void createEvents(String id, String description) {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start createEvents method");

        sendKeysToID(id);

        sendKeysToDescription(description);

        setOwner();

        setSEDateTime();

        clickOnSubmit();

        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - End createEvents method");
    }

    /**
     * This method send keys to the inputs if they are visible (input)
     */
    public void sendKeysToID(String keys) {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start sending keys to id method");
        if (this.getElementById("campoT").isDisplayed()) {
            this.getElementById("campoT").sendKeys(keys);
        }
    }

    /**
     * This method send keys to the inputs if they are visible, (description)
     */
    public void sendKeysToDescription(String keys) {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start sending keys to description method");
        if (this.getElementById("campoD").isDisplayed()) {
            this.getElementById("campoD").sendKeys(keys);
        }
    }

    /**
     *This method sets an Start date and a End date datetime on the created event
     */
    public void setSEDateTime() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - START setting Start and End date method");

        setStartDate();

        setEndDate();

        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - END - SetSEDateTime");

    }

    /**
     * Self explanatory
     */

    public void clickOnSubmit() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Clicking on submit");
        this.getElementByXPath("submit").click();

    }

    /**
     * Method in setSEDateTime to set the Start Date
     */
    public void setStartDate() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - SETTING start date");
        String xPath2 = ".//*[@id='id_start_date_year']/option[6]";
        driver.findElementById("id_start_date_year").click();
        driver.clickOnWithMouse(By.xpath(xPath2));
    }

    /**
     * Method in set SEDateTime to set the End Date
     */

    public void setEndDate() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - START End Date");
        String xPath3 = ".//*[@id='id_end_date_year']/option[7]";
        driver.findElementById("id_end_date_year").click();
        driver.clickOnWithMouse(By.xpath(xPath3));
    }

    /**
     *This method sets a random owner to the created event. (We can't leave it null) 
     */
    public void setOwner() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - START setting Owner");
        driver.findElementById("id_owner").click();
        String xPath = ".//*[@id='id_owner']/option[2]";
        driver.clickOnWithMouse(By.xpath(xPath));
    }

    /**
     * Method to redirect to the edit events page, using the event of before.
     */
    public void goToEditEvents() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start goToEditEvents method");

        clickOnVerMas();

        clickOnEditar();

        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - End goToEditEvents method");
    }

    /**
     * This method clicks on the Editar button located on the options bar below the Event detailed watch
     */
    public void clickOnEditar() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - START Click on Editar");
        String xPath2 = "html/body/div[1]/div[3]/div/ul/li[1]/a";
        driver.findElementByXPath(xPath2).click();
    }

    /**
     * This method clicks on the "Ver más" button to see an extended view of the event
     */

    public void clickOnVerMas() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - START clicking Ver Mas");
        String xPath = "html/body/div[1]/div[3]/div[1]/div[2]/a/button";
        driver.findElementByXPath(xPath).click();
    }

    /**
     * This method edits our created event with a String in the description to check it out later
     */
    public void doEdit() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start doEdit method");

        sendKeysToDescription("\b\b\b\b\b\b\b\b\b Hola, editado");
        clickOnSubmitEdit();

        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - End doEdit method");
    }

    /**
     * This method clicks on the submit in the edit form·
     */

    public void clickOnSubmitEdit() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - CLICK: Submit (Edit)");
        String xPath = "html/body/div[1]/form/input[2]";
        driver.clickIfExists(By.xpath(xPath));
    }

    /**
     * This method sends some keys to the description back to edit.
     * 
     */

    public void sendKeysToEditView(String description) {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - START Sendking keys to description");
        if (this.isElementVisibleById("campoD")) {
            this.getElementById("campoD").sendKeys(description);
        }
    }

    /**
     * This method send us directly to the deletion page of the event we created and edited.
     */
    public void goToDeleteEvents() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start goToDeleteEvents method");

        clickOnVerMas();

        clickOnDeleteButton();

        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - End goToDeleteEvents method");

    }

    /**
     * This method clicks on the delete button located on the detailed event view.
     */
    public void clickOnDeleteButton() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - CLICK : Delete Button");
        driver.sleep(2);
        String xPath2 = "html/body/div[1]/div[3]/div/ul/li[2]/a";
        driver.findElementByXPath(xPath2).click();
    }

    /**
     * This confirms the deletion of the event.
     */

    public void doDelete() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start doDelete method");
        this.getElementByXPath("confirm").click();
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - End doDelete method");

    }

}
