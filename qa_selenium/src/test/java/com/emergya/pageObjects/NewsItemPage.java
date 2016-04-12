package com.emergya.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.emergya.drivers.EmergyaWebDriver;

/**
 * This class returns
 * 
 * @author Alfonso Rodríguez Martín
 */
public class NewsItemPage extends BasePageObject {

    /**
     * Logger class initialization.
     */
    Logger log = Logger.getLogger(NewsItemPage.class);

    /**
     * Constructor method
     * @param driver selenium webdriver
     */
    public NewsItemPage(EmergyaWebDriver driver) {

        super(driver);
        accessNewsPage();
    }

    /**
     * This method goes directly to the Administration Page for us to do the tests.
     */
    public void accessNewsPage() {
        driver.get("localhost:8000/v2/");
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

        boolean status = this.isElementVisibleById("crear");

        log.info("[log-AdminPage] " + this.getClass().getSimpleName()
                + " - End isReady method");

        return status;
    }

    // Page object methods
    /**
     * Method to go to a News Creation page.
     */
    public void goToCreationPage() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start goToCreationPage method");
        driver.get("localhost:8000/v2/crear");
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - End goToCreationPage method");

    }

    /**
     * Method to create some fresh news.
     */

    public void createNews() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start createNews method");

        sendKeysToID("No deberias estar viendo esto");

        sendKeysToDescription("Descripcion poco trabajada, pero bueno");

        setOwner();

        setDateTime();

        clickOnSubmit();

        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - End createNews method");
    }

    /**
     * Clicking on submit form.
     */
    public void clickOnSubmit() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - CLICK: Submit");
        this.getElementByXPath("submit").click();
    }

    /**
     * This method sets an automatic date and time in our form.
     */

    public void setDateTime() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - SET: Date and Time");
        driver.findElementById("id_publish_date_year").click();
        String xPath2 = ".//*[@id='id_publish_date_year']/option[6]";
        driver.clickOnWithMouse(By.xpath(xPath2));
    }

    /**
     * This method sets an owner in our form.
     */
    public void setOwner() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - SET: Owner");
        driver.findElementById("id_owner").click();
        driver.sleep(2);
        String xPath = ".//*[@id='id_owner']/option[2]";
        driver.clickOnWithMouse(By.xpath(xPath));
    }

    /**
     * Method to send keys to the ID field of the News Form
     */
    public void sendKeysToID(String keys) {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - SET: Keys to Title field");

        if (this.getElementById("campoT").isDisplayed()) {
            this.getElementById("campoT").sendKeys(keys);
        }
    }

    /**
     * Method to send keys to the ID field of the News Form
     */
    public void sendKeysToDescription(String keys) {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - SET: Keys to Description field");

        if (this.getElementById("campoD").isDisplayed()) {
            this.getElementById("campoD").sendKeys(keys);
        }
    }

    /**
     * Method to redirect to the edit news page, using the last News we created
     */
    public void goToEditNews() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Starting gotoEditNews method");
        clickOnVerMas();

        clickOnEdit();

        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - End goToEditNews method");
    }

    /**
     * THis method clicks on the Ver Mas button
     */
    public void clickOnVerMas() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - CLICK: Ver mas");
        String xPath = "html/body/div[1]/div[3]/div[1]/div[2]/a[2]/button";
        driver.findElementByXPath(xPath).click();
    }

    public void clickOnEdit() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - CLICK: Edit");
        String xPath2 = "html/body/div[1]/div[3]/div/ul/li[2]/a";
        driver.findElementByXPath(xPath2).click();
    }

    /**
     * This method edits our created news with a String in the description to check it out later
     */
    public void doEdit() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start doEdit method");
        sendKeysToEditView("\b\b\b\billo");

        clickOnEditNews();
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - End doEdit method");
    }

    /**
     * This method clicks on submit in the edition.
     */
    public void clickOnEditNews() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - CLICK: Submit Edit");
        String xPath = "html/body/div[1]/form/input[2]";
        driver.clickIfExists(By.xpath(xPath));
    }

    /**
     * THis method sends an string to the edition view.
     */
    public void sendKeysToEditView(String keys) {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - SET: Keys to the description");
        if (this.isElementVisibleById("campoD")) {
            this.getElementById("campoD").sendKeys(keys);
        }
    }

    /**
     * This method deletes the created/edited news.
     */
    public void goToDeleteNews() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start goToDelete method");
        clickOnVerMas();

        clickOnDeleteButton();

        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - End goToDeleteNews method");

    }

    /**
     * This method clicks on delete News button
     */
    public void clickOnDeleteButton() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - CLICK : Delete BUTTON");
        String xPath2 = "html/body/div[1]/div[3]/div/ul/li[4]/a";
        driver.findElementByXPath(xPath2).click();
    }

    /**
     * This deletes the News.
     */

    public void doDelete() {
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - Start doDelete method");
        this.getElementByXPath("confirm").click();
        log.info("[log-NewsPage] " + this.getClass().getSimpleName()
                + " - End doDelete method");

    }

}
