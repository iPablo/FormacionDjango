package com.emergya.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

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
        String url = driver.getCurrentUrl() + "admin";
        driver.get(url);
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
        String password = "usuarioTest";

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
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End goToCreateEvent -[" + this.getClass().getSimpleName()
                + "- method]");
    }

    /**
     * This method creates an event
     */

    public void createEvent(String title, String description) {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start createEvent -[" + this.getClass().getSimpleName()
                + "- method]");
        // After checking we are in the Create Event
        if (insertTextIntoDescription(description) // If everything is correct
                && insertTextIntoTitle(title)) {

            clickOnTodaySD();

            clickOnNowSD();

            clickOnTodayED();

            clickOnNowED();

            setOwner();

            send();

            log.info("[log-" + this.getClass().getSimpleName()
                    + "]- End createEvent -[" + this.getClass().getSimpleName()
                    + "- method]");

        }
    }

    /**
     * This method clicks on submit.
     */
    public void send() {
        if (this.getElementByXPath("send").isDisplayed()) {
            this.getElementByXPath("send").click();
        }

    }

    /**
     * This method clicks on the owner to set the property.
     * @return True if done.
     */

    public void setOwner() {
        driver.clickIfExists(By.id("id_owner"));
        String xPath = ".//*[@id='id_owner']/option[2]";
        driver.clickOnWithMouse(By.xpath(xPath));
    }

    /**
     * This method clicks to auto-date the object. START DATE
     * @return True is everything is correct
     */

    public boolean clickOnTodaySD() {
        if (this.getElementByXPath("today1").isDisplayed()) {
            this.getElementByXPath("today1").click();
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method clicks to auto-time the object. START DATE
     * @return True is everything is correct
     */

    public boolean clickOnNowSD() {
        if (this.getElementByXPath("now1").isDisplayed()) {
            this.getElementByXPath("now1").click();
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method clicks to auto-date the object. END DATE
     * @return True is everything is correct
     */

    public boolean clickOnTodayED() {
        if (this.getElementByXPath("today2").isDisplayed()) {
            this.getElementByXPath("today2").click();
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method clicks to auto-time the object. END DATE
     * @return True is everything is correct
     */

    public boolean clickOnNowED() {
        if (this.getElementByXPath("now2").isDisplayed()) {
            this.getElementByXPath("now2").click();
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method introduces the text into the proper form.
     * @param text
     */
    public boolean insertTextIntoTitle(String text) {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Sending keys to ID-[" + this.getClass().getSimpleName()
                + "- method]");
        if (this.getElementById("formtitle").isDisplayed()) {
            this.getElementById("formtitle").sendKeys(text);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method inserts text into the description input.
     * @param text
     */
    public boolean insertTextIntoDescription(String text) {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Sending keys to Description-["
                + this.getClass().getSimpleName() + "- method]");
        if (this.getElementById("formdescription").isDisplayed()) {
            this.getElementById("formdescription").sendKeys(text);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method goes back to the events view page and deletes the one we created.
     */

    public void clickOnDeleteButton() {
        String xPath = ".//*[@id='event_form']/div/div/p/a";
        driver.clickIfExists(By.xpath(xPath));
    }

    /**
     * This method clicks on the delete button on the NEWS view. 
     */
    public void clickOnDeleteNewsButton() {
        String xPath = ".//*[@id='newsitem_form']/div/div/p/a";
        driver.clickIfExists(By.xpath(xPath));
    }

    /**
     * The name is self-explanatory
     */

    public void confirmDelete() {
        String xPath2 = ".//*[@id='content']/form/div/input[2]";
        driver.clickIfExists(By.xpath(xPath2));
    }

    /**
     * This method redirects us to the detail event page
     */
    public void goToLastEvent() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Starting to go to the Detail page -["
                + this.getClass().getSimpleName() + "- method]");
        this.getElementByXPath("lastevent").click();
    }

    /**
     * A simple method to redirect us to the NewsItem page.
     */
    public void goToNewsItemPage() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Going to News Item page -["
                + this.getClass().getSimpleName() + "- method]");
        driver.get("localhost:8000/admin/proyectoinicio/newsitem/");
    }

    /**
     * This one redirect us to the news item creation page.
     */
    public void goToNewsCreation() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Going to NewsCreation page -["
                + this.getClass().getSimpleName() + "- method]");
        driver.get("localhost:8000/admin/proyectoinicio/newsitem/add");
    }

    /**
     * This method is made for creating a new NewsItem.
     */
    public void createNews(String title, String description) {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start createNews -[" + this.getClass().getSimpleName()
                + "- method]");
        insertTextIntoTitle(title);

        insertTextIntoDescription(description);

        clickOnNowPD();

        clickOnTodayPD();

        setOwner();

        sendN();

        log.info("[log-" + this.getClass().getSimpleName()
                + "]- End createNews -[" + this.getClass().getSimpleName()
                + "- method]");
    }

    /**
     * This method clicks to auto-date the object. PUBLISH DATE
     * @return True is everything is correct
     */

    public boolean clickOnTodayPD() {
        if (this.getElementByXPath("today3").isDisplayed()) {
            this.getElementByXPath("today3").click();
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method clicks to auto-time the object. PUBLISH DATE
     * @return True is everything is correct
     */

    public boolean clickOnNowPD() {
        if (this.getElementByXPath("now3").isDisplayed()) {
            this.getElementByXPath("now3").click();
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method clicks on the News creation submit.
     */

    public void sendN() {
        if (this.getElementByXPath("send2").isDisplayed()) {
            this.getElementByXPath("send2").click();
        }

    }

    /**
     * THis method goes to the created News detail to do operations.
     */

    public void goToNewsDetail() {
        log.info("[log-" + this.getClass().getSimpleName()
                + "]- Start goToNewsPageAndDelete -["
                + this.getClass().getSimpleName() + "- method]");
        this.getElementByXPath("lastevent").click();
    }

    /**
     * And finally, this logs us out.
     */

    public void logOut() {
        log.info("[log-" + this.getClass().getSimpleName() + "]- Logging out -["
                + this.getClass().getSimpleName() + "- method]");
        this.getElementByXPath("logout").click();
    }
}
