package com.emergya.drivers;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.emergya.utils.Initialization;

/**
 * Implements personalized methods to WebDriver
 * @author Jose Antonio Sanchez <jasanchez@emergya.com>
 * @author Alejandro Gomez <agommor@gmail.com>
 */
public class EmergyaWebDriverUtil {

    private static Logger log = Logger.getLogger(EmergyaWebDriverUtil.class);

    // **** Basic operation methods section ****//
    /**
     * Checks if an element exists in the DOM
     * @param driver WebDriver element
     * @param selector By element
     * @return True if the element exists in the DOM and false in the opposite case
     */
    public static boolean existsElement(EmergyaWebDriver driver, By selector) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start existsElement method");

        boolean exists = false;

        try {
            exists = (driver.findElement(selector) != null);
        } catch (Exception ex) {
            exists = false;
        }

        log.info("[log-Utils] EmergyaWebDriverUtil - End existsElement method");

        return exists;
    }

    /**
     * Checks if an element exists in the DOM and is displayed
     * @param driver WebDriver element
     * @param selector By element
     * @return True if the element exists in the DOM and is displayed and false in the opposite case
     */
    public static boolean isElementDisplayed(EmergyaWebDriver driver,
            By selector) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start isElementDisplayed method");

        boolean isDisplayed = false;

        try {
            WebElement element = driver.findElement(selector);
            isDisplayed = (element != null && element.isDisplayed());
        } catch (Exception ex) {
            isDisplayed = false;
        }

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End isElementDisplayed method");

        return isDisplayed;
    }

    /**
     * Clicks on an element after wait and if it is displayed
     * @param driver WebDriver element
     * @param selector By element
     */
    public static void clickIfExists(EmergyaWebDriver driver, By selector) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start clickIfExists method");

        wait(driver, selector, 20);

        if (isElementDisplayed(driver, selector)) {
            driver.findElement(selector).click();
        } else {
            log.error("The element " + selector.toString()
                    + " is not displayed.");
        }

        log.info("[log-Utils] EmergyaWebDriverUtil - End clickIfExists method");
    }

    // **** Javascript methods section ****//
    /**
     * Executes JavaScript in the context of the currently window
     * @param driver WebDriver element
     * @param script The JavaScript to execute
     * @return Boolean, Long, String, List, WebElement Or null
     */
    public static Object executeJavaScript(EmergyaWebDriver driver,
            String script) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start executeJavaScript method");
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End executeJavaScript method");

        return ((JavascriptExecutor) driver).executeScript(script);
    }

    /**
     * Puts the focus on an element through its id
     * @param driver WebDriver element
     * @param id string with the id of an element
     */
    public static void focus(EmergyaWebDriver driver, String id) {
        log.info("[log-Utils] EmergyaWebDriverUtil - Start focus method");

        driver.executeJavaScript(
                "document.getElementById('" + id + "').focus();");

        log.info("[log-Utils] EmergyaWebDriverUtil - End focus method");
    }

    // **** Screenshot methods section ****//
    /**
     * Saves a screenshot in a path with a timestamp
     * @param driver WebDriver element
     * @param folderPath to save the screenshot
     * @param baseFileName file name
     */
    public static void saveScreenshotPath(EmergyaWebDriver driver,
            String folderPath, String baseFileName) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start saveScreenshotPath method");

        String timeStamp = getTimeStamp();
        File scrFile = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(scrFile, new File(folderPath + File.separator
                    + baseFileName + "_" + timeStamp + ".png"));
        } catch (IOException e) {
            log.error("Error creating screenshot", e);
        }

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End saveScreenshotPath method");
    }

    /**
     * Saves a screenshot in the default path
     * @param driver WebDriver element
     */
    public static void saveScreenshotDefault(EmergyaWebDriver driver) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start saveScreenshotDefault method");

        String folderPath = Initialization.getInstance().getScreenshotPath();

        saveScreenshotPath(driver, folderPath, "");

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End saveScreenshotDefault method");
    }

    // **** Sleep method ****//
    /**
     * Stops the execution during some seconds
     * @param seconds time to stop the execution
     */
    public static void sleep(int seconds) {
        log.info("[log-Utils] EmergyaWebDriverUtil - Start sleep method");

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            log.error("A thread has interrupted the current thread", e);
        }

        log.info("[log-Utils] EmergyaWebDriverUtil - End sleep method");
    }

    // **** Keyboard events methods section ****//
    /**
     * Presses a keyboard key
     * @param key to press
     * @param sleepTime time to wait before and after to press the key
     */
    public static void pressKey(int key, int sleepTime) {
        log.info("[log-Utils] EmergyaWebDriverUtil - Start pressKey method");

        Robot r;

        try {
            r = new Robot();

            Thread.sleep(sleepTime * 1000);
            r.keyPress(key);
            Thread.sleep(sleepTime * 1000);
        } catch (AWTException e) {
            log.error(
                    "The platform configuration does not allow low-level input control",
                    e);
        } catch (InterruptedException e) {
            log.error("A thread has interrupted the current thread", e);
        }

        log.info("[log-Utils] EmergyaWebDriverUtil - End pressKey method");
    }

    /**
     * Releases a keyboard key
     * @param key to release
     * @param sleepTime time to wait before and after to release the key
     */
    public static void releaseKey(int key, int sleepTime) {
        log.info("[log-Utils] EmergyaWebDriverUtil - Start releaseKey method");

        Robot r;

        try {
            r = new Robot();

            Thread.sleep(sleepTime * 1000);
            r.keyRelease(key);
            Thread.sleep(sleepTime * 1000);
        } catch (AWTException e) {
            log.error(
                    "The platform configuration does not allow low-level input control",
                    e);
        } catch (InterruptedException e) {
            log.error("A thread has interrupted the current thread", e);
        }

        log.info("[log-Utils] EmergyaWebDriverUtil - End releaseKey method");
    }

    /**
     * Presses and releases a keyboard key
     * @param key to press and to release
     */
    public static void pressReleaseKey(int key) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start pressReleaseKey method");

        Robot r;

        try {
            r = new Robot();

            r.keyPress(key);
            Thread.sleep(500);
            r.keyRelease(key);
            Thread.sleep(500);
        } catch (AWTException e) {
            log.error(
                    "The platform configuration does not allow low-level input control",
                    e);
        } catch (InterruptedException e) {
            log.error("A thread has interrupted the current thread", e);
        }

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End pressReleaseKey method");
    }

    // **** Mouse events methods section ****//
    /**
     * Moves the mouse over an element
     * @param driver WebDriver element
     * @param selector By element
     */
    public static void moveMouseOverElement(EmergyaWebDriver driver,
            By selector) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start moveMouseOverElement method");

        Robot r;

        try {
            r = new Robot();
            Point point = getPositionToClick(driver, selector);
            java.awt.Point location = MouseInfo.getPointerInfo().getLocation();

            if (((int) location.getX()) != point.getX()
                    || ((int) location.getY()) != point.getY()) {
                r.mouseMove(point.getX(), point.getY());
                driver.sleep(2);
            }
        } catch (AWTException e) {
            log.error(
                    "The platform configuration does not allow low-level input control",
                    e);
        }

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End moveMouseOverElement method");
    }

    /**
     * Moves the mouse out of an element
     * @param driver WebDriver element
     * @param selector By element
     */
    public static void moveMouseOutElement(EmergyaWebDriver driver,
            By selector) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start moveMouseOutElement method");

        Robot r;
        int x = 0, y = 0;

        try {
            r = new Robot();

            if (existsElement(driver, selector)) {
                WebElement element = driver.findElement(selector);
                Point position = element.getLocation();

                x = position.getX() - 10;
                y = position.getY() - 10;
            }

            Point toMove = new Point(x, y);
            java.awt.Point location = MouseInfo.getPointerInfo().getLocation();

            if (((int) location.getX()) != toMove.getX()
                    && ((int) location.getY()) != toMove.getY()) {
                r.mouseMove(toMove.getX(), toMove.getY());
                driver.sleep(2);
            }
        } catch (AWTException e) {
            log.error(
                    "The platform configuration does not allow low-level input control",
                    e);
        }

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End moveMouseOutElement method");
    }

    /**
     * Clicks with the mouse on an element
     * @param driver WebDriver element
     * @param selector By element
     */
    public static void clickOnWithMouse(EmergyaWebDriver driver, By selector) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start clickOnWithMouse method");

        Robot r;

        try {
            r = new Robot();

            moveMouseOverElement(driver, selector);

            r.mousePress(InputEvent.BUTTON1_MASK);
            driver.sleep(1);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            driver.sleep(2);
        } catch (AWTException e) {
            log.error(
                    "The platform configuration does not allow low-level input control",
                    e);
        }

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End clickOnWithMouse method");
    }

    /**
     * Clicks with the mouse out of an element
     * @param driver WebDriver element
     * @param selector By element
     */
    public static void clickOutWithMouse(EmergyaWebDriver driver, By selector) {
        EmergyaWebDriverUtil.moveMouseOutElement(driver, selector);
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start clickOutWithMouse method");

        try {
            Robot r = new Robot();

            moveMouseOutElement(driver, selector);

            r.mousePress(InputEvent.BUTTON1_MASK);
            driver.sleep(1);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            driver.sleep(2);
        } catch (AWTException e) {
            log.error(
                    "The platform configuration does not allow low-level input control",
                    e);
        }

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End clickOutWithMouse method");
    }

    /**
     * Double clicks with the mouse on an element
     * @param driver WebDriver element
     * @param selector By element
     */
    public static void doubleClickOnWithMouse(EmergyaWebDriver driver,
            By selector) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start doubleClickOnWithMouse method");

        Robot r;

        try {
            r = new Robot();

            moveMouseOverElement(driver, selector);

            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            driver.sleep(2);
        } catch (AWTException e) {
            log.error(
                    "The platform configuration does not allow low-level input control",
                    e);
        }

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End doubleClickOnWithMouse method");
    }

    // **** Wait methods section ****//
    /**
     * It sleeps the driver for X seconds. If the element exist in the DOM, the execution continue without waiting X seconds
     * @param driver WebDriver element for sleep
     * @param selector By element for wait
     * @param seconds to be slept
     * @return true if the element exist in the DOM and false in the opposite case
     */
    public static boolean wait(EmergyaWebDriver driver, By selector,
            long seconds) {
        log.info("[log-Utils] EmergyaWebDriverUtil - Start wait method");

        WebDriverWait w = new WebDriverWait(driver, seconds);
        boolean retVal = true;

        try {
            w.until(ExpectedConditions.presenceOfElementLocated(selector));
        } catch (TimeoutException e) {
            retVal = false;

            log.error("The element: " + selector.toString()
                    + " is missing in the DOM. Waiting time: " + seconds
                    + " seconds");
        }

        log.info("[log-Utils] EmergyaWebDriverUtil - End wait method");

        return retVal;
    }

    /**
     * It sleeps the driver for X seconds. If the element is visible in the page, the execution continue without waiting X seconds
     * @param driver WebDriver element for sleep
     * @param selector By element for wait
     * @param seconds to be slept
     * @return true if the element is visible in the page and false in the opposite case
     */
    public static boolean waitUntilVisible(EmergyaWebDriver driver, By selector,
            long seconds) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start waitUntilVisible method");

        WebDriverWait w = new WebDriverWait(driver, seconds);
        boolean retVal = true;

        try {
            w.until(ExpectedConditions.visibilityOfElementLocated(selector));
        } catch (TimeoutException e) {
            retVal = false;

            log.error("The element: " + selector.toString()
                    + " is not visible in the page. Waiting time: " + seconds
                    + " seconds");
        }

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End waitUntilVisible method");

        return retVal;
    }

    /**
     * It sleeps the driver for X seconds. If the element is clickable in the page, the execution continue without waiting X seconds
     * @param driver WebDriver element for sleep
     * @param selector By element for wait
     * @param seconds to be slept
     * @return true if the element is clickable in the page and false in the opposite case
     */
    public static boolean waitUntilElementClickable(EmergyaWebDriver driver,
            By selector, long seconds) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start waitUntilElementClickable method");

        WebDriverWait w = new WebDriverWait(driver, seconds);
        boolean retVal = true;

        try {
            w.until(ExpectedConditions.elementToBeClickable(selector));
        } catch (TimeoutException e) {
            retVal = false;

            log.error("The element: " + selector.toString()
                    + " is not clickable. Waiting time: " + seconds
                    + " seconds");
        }

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End waitUntilElementClickable method");

        return retVal;
    }

    /**
     * It sleeps the driver for X seconds. If the text is present in element, the execution continue without waiting X seconds
     * @param driver WebDriver element for sleep
     * @param selector By element for wait
     * @param seconds to be slept
     * @param text to be find
     * @return true If the text is present in element, and false in the opposite case
     */
    public static boolean waitUntilTextPresent(EmergyaWebDriver driver,
            By selector, long seconds, String text) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start waitUntilTextPresent method");

        WebDriverWait w = new WebDriverWait(driver, seconds);
        boolean retVal = true;

        try {
            w.until(ExpectedConditions.textToBePresentInElementLocated(selector,
                    text));
        } catch (TimeoutException e) {
            retVal = false;

            log.error("The text: " + text + " in the element: "
                    + selector.toString()
                    + " is missing in the DOM. Waiting time: " + seconds
                    + " seconds");
        }

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End waitUntilTextPresent method");

        return retVal;
    }

    // **** Private methods section ****//
    /**
     * Generates a timestamp
     * @return string with a timestamp
     */
    private static String getTimeStamp() {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start getTimeStamp method");

        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss")
                .format(Calendar.getInstance().getTime());

        log.info("[log-Utils] EmergyaWebDriverUtil - End getTimeStamp method");

        return timeStamp;

    }

    /**
     * Retrieves the center of an element to click it.
     * @param driver WebDriver element
     * @param selector By element
     * @return Point the position of the center of the element
     */
    private static Point getPositionToClick(EmergyaWebDriver driver,
            By selector) {
        log.info(
                "[log-Utils] EmergyaWebDriverUtil - Start getPositionToClick method");

        Point toReturn = null;
        int x = 0, y = 0;

        if (existsElement(driver, selector)) {
            WebElement element = driver.findElement(selector);
            Point position = element.getLocation();

            x = position.getX() + (element.getSize().getWidth() / 2);
            y = position.getY() + (element.getSize().getHeight() / 2);

            toReturn = new Point(x, y);
        }

        log.info(
                "[log-Utils] EmergyaWebDriverUtil - End getPositionToClick method");

        return toReturn;
    }
}
