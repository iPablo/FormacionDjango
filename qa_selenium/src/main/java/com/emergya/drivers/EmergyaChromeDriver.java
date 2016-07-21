package com.emergya.drivers;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Custom driver for chrome
 * @author Jose Antonio Sanchez <jasanchez@emergya.com>
 * @author Alejandro Gomez <agommor@gmail.com>
 */
public class EmergyaChromeDriver extends ChromeDriver
        implements EmergyaWebDriver {

    /**
     * Constructor
     */
    public EmergyaChromeDriver() {
        super();
    }

    public EmergyaChromeDriver(ChromeOptions chromeOptions) {
        super(chromeOptions);
    }

    // **** Basic operation methods section ****//
    /**
     * Checks if an element exists in the DOM
     * @param selector By element
     * @return True if the element exists in the DOM and false in the opposite case
     */
    public boolean existsElement(By selector) {
        return EmergyaWebDriverUtil.existsElement(this, selector);
    }

    /**
     * Checks if an element exists in the DOM and is displayed
     * @param selector By element
     * @return True if the element exists in the DOM and is displayed and false in the opposite case
     */
    public boolean isElementDisplayed(By selector) {
        return EmergyaWebDriverUtil.isElementDisplayed(this, selector);
    }

    /**
     * Clicks on an element after wait and if it is displayed
     * @param selector By element
     */
    public void clickIfExists(By selector) {
        EmergyaWebDriverUtil.clickIfExists(this, selector);
    }

    // **** Javascript methods section ****//
    /**
     * Executes JavaScript in the context of the currently window
     * @param script The JavaScript to execute
     * @return Boolean, Long, String, List, WebElement Or null
     */
    public Object executeJavaScript(String script) {
        return EmergyaWebDriverUtil.executeJavaScript(this, script);
    }

    /**
     * Puts the focus on an element through its id
     * @param driver WebDriver element
     * @param id string with the id of an element
     */
    public void focus(String id) {
        EmergyaWebDriverUtil.focus(this, id);
    }

    // **** Screenshot methods section ****//
    /**
     * Saves a screenshot in a path with a timestamp
     * @param folderPath to save the screenshot
     * @param baseFileName file name
     */
    public void saveScreenshotPath(String folderPath, String baseFileName) {
        EmergyaWebDriverUtil.saveScreenshotPath(this, folderPath, baseFileName);
    }

    /**
     * Saves a screenshot in the default path
     * @param driver WebDriver element
     */
    public void saveScreenshotDefault() {
        EmergyaWebDriverUtil.saveScreenshotDefault(this);
    }

    // **** Sleep method ****//
    /**
     * Stops the execution during some seconds
     * @param seconds time to stop the execution
     */
    public void sleep(int seconds) {
        EmergyaWebDriverUtil.sleep(seconds);
    }

    // **** Keyboard events methods section ****//
    /**
     * Presses a keyboard key
     * @param key to press
     * @param sleepTime time to wait before and after to press the key
     */
    public void pressKey(int key, int sleepTime) {
        EmergyaWebDriverUtil.pressKey(key, sleepTime);
    }

    /**
     * Releases a keyboard key
     * @param key to release
     * @param sleepTime time to wait before and after to release the key
     */
    public void releaseKey(int key, int sleepTime) {
        EmergyaWebDriverUtil.releaseKey(key, sleepTime);
    }

    /**
     * Presses and releases a keyboard key
     * @param key to press and release
     */
    public void pressReleaseKey(int key) {
        EmergyaWebDriverUtil.pressReleaseKey(key);
    }

    // **** Mouse events methods section ****//
    /**
     * Moves the mouse over an element
     * @param selector By element
     */
    public void moveMouseOverElement(By selector) {
        EmergyaWebDriverUtil.moveMouseOverElement(this, selector);
    }

    /**
     * Moves the mouse out of an element
     * @param selector By element
     */
    public void moveMouseOutElement(By selector) {
        EmergyaWebDriverUtil.moveMouseOutElement(this, selector);
    }

    /**
     * Clicks with the mouse on an element
     * @param selector By element
     */
    public void clickOnWithMouse(By selector) {
        EmergyaWebDriverUtil.clickOnWithMouse(this, selector);
    }

    /**
     * Clicks with the mouse out of an element
     * @param selector By element
     */
    public void clickOutWithMouse(By selector) {
        EmergyaWebDriverUtil.clickOutWithMouse(this, selector);
    }

    /**
     * Double clicks with the mouse on an element
     * @param selector By element
     */
    public void doubleClickOnWithMouse(By selector) {
        EmergyaWebDriverUtil.doubleClickOnWithMouse(this, selector);

    }

    // **** Wait methods section ****//
    /**
     * It sleeps the driver for X seconds. If the element is visible in the page, the execution continue without waiting X seconds
     * @param selector By element for wait
     * @param seconds to be slept
     * @return true if the element exist in the DOM and false in the opposite case
     */
    public boolean wait(By selector, long seconds) {
        return EmergyaWebDriverUtil.wait(this, selector, seconds);
    }

    /**
     * It sleeps the driver for X seconds. If the element is visible in the page, the execution continue without waiting X seconds
     * @param selector By element for wait
     * @param seconds to be slept
     * @return true if the element is visible in the page and false in the opposite case
     */
    public boolean waitUntilVisible(By selector, long seconds) {
        return EmergyaWebDriverUtil.waitUntilVisible(this, selector, seconds);
    }

    /**
     * It sleeps the driver for X seconds. If the element is clickable in the page, the execution continue without waiting X seconds
     * @param selector By element for wait
     * @param seconds to be slept
     * @return true if the element is clickable in the page and false in the opposite case
     */
    public boolean waitUntilElementClickable(By selector, long seconds) {
        return EmergyaWebDriverUtil.waitUntilElementClickable(this, selector,
                seconds);
    }

    /**
     * It sleeps the driver for X seconds. If the text is present in element, the execution continue without waiting X seconds
     * @param selector By element for wait
     * @param seconds to be slept
     * @param text to be find
     * @return true If the text is present in element, and false in the opposite case
     */
    public boolean waitUntilTextPresent(By selector, long seconds,
            String text) {
        return EmergyaWebDriverUtil.waitUntilTextPresent(this, selector,
                seconds, text);
    }
}
