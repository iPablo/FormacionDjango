package com.emergya.utils;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;
import static org.testng.AssertJUnit.assertTrue;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.monte.screenrecorder.ScreenRecorder.State;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.emergya.drivers.EmergyaWebDriver;
import com.emergya.pageObjects.AdministracionPage;
import com.emergya.pageObjects.BasePageObject;
import com.emergya.pageObjects.EmergyaMainPage;
import com.emergya.pageObjects.GoogleMainPage;

/**
 * TestNG after and before methods
 * @author Jose Antonio Sanchez <jasanchez@emergya.com>
 */
public abstract class DefaultTestSet {

    protected static EmergyaWebDriver driver;
    protected static Initialization config = Initialization.getInstance();
    protected GoogleMainPage googleMainPage;
    protected EmergyaMainPage emergyaMainPage;
    protected AdministracionPage administracionPage;
    protected ScreenRecorder screenRecorder;
    protected String tcName = "";
    private String failedSuitePath = "src/test/resources/suites/emergyaFailedTest.xml";

    protected Logger log = Logger.getLogger(DefaultTestSet.class);

    @BeforeMethod
    public void nameBefore(Method method) {
        this.tcName = method.getName();
    }

    @BeforeMethod
    public void before() {
        driver = config.initialize();

        if (driver != null && config.isRecordVideo() == true) {
            long startTime = System.currentTimeMillis();
            try {
                log.info("Recording video");

                GraphicsConfiguration gc = GraphicsEnvironment
                        .getLocalGraphicsEnvironment().getDefaultScreenDevice()
                        .getDefaultConfiguration();

                this.screenRecorder = new ScreenRecorder(gc, null,
                        new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey,
                                MIME_AVI),
                        new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
                                ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                                CompressorNameKey,
                                ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey,
                                24, FrameRateKey, Rational.valueOf(15),
                                QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                        new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
                                "black", FrameRateKey, Rational.valueOf(30)),
                        null, new File(config.getVideoRecordingPath()));

                screenRecorder.start();
            } catch (Exception e) {
                log.warn(
                        "Recorder could not be initilized. No video will be recording");
            }
            log.info("Start recording in "
                    + (System.currentTimeMillis() - startTime) + " ms");
        } else {
            log.info("No video recording");
        }
    }

    @AfterMethod
    public void afterAllIsSaidAndDone() {
        log.info("Function afterAllIsSaidAndDone");

        if (driver != null) {
            if (driver != null) {
                driver.manage().deleteAllCookies();
                driver.quit();
            }
        }
    }

    @AfterMethod
    public void recordVideo(ITestResult result) {
        if (driver != null && (config.isRecordVideo() == true)
                && (screenRecorder != null)
                && (screenRecorder.getState().equals(State.RECORDING))) {

            long startTime = System.currentTimeMillis();

            try {
                screenRecorder.stop();
                log.info("Recorder stoped");
                File tempRecordeFile = screenRecorder.getCreatedMovieFiles()
                        .get(0);

                if ((!config.isSaveVideoForPassed())
                        && (result.getStatus() == 1)) {
                    log.info("Test passed. Deleting video");

                    if (tempRecordeFile.delete()) {
                        log.info("File deleted");
                    } else {
                        log.warn("Video could not be deleted");
                    }
                } else {
                    String endPath = tempRecordeFile.getAbsolutePath()
                            .replaceAll("ScreenRecording", this.tcName);
                    if (tempRecordeFile.renameTo(new File(endPath))) {
                        log.info("File stored in " + endPath);
                    } else {
                        log.warn("Video could not be stored in " + endPath);
                    }
                }
            } catch (IOException e) {
                log.warn("Recorder could not be stoped");
            }

            log.info("Recording video took "
                    + (System.currentTimeMillis() - startTime) + " ms");
        }
    }

    @AfterMethod
    public void checkFailedTest(ITestResult result) {
        if (result.getMethod().getXmlTest().getName().equals("FailedTests")) {
            removePassedTestToXML(result.getMethod().getRealClass().getName(),
                    result.getMethod().getMethodName());
        } else {
            log.info("checkFailedTest.........result status of test is: "
                    + result.getStatus());

            if (result.getStatus() == 2) {
                log.info("name Of the xml TestSuite is "
                        + result.getMethod().getXmlTest().getName());

                if (!result.getMethod().getXmlTest().getName()
                        .equals("Default test")) {
                    addFailedTestToXML(
                            result.getMethod().getRealClass().getName(),
                            result.getMethod().getMethodName());
                }
            } else {
                if (!result.getMethod().getXmlTest().getName()
                        .equals("Default test")) {
                    removePassedTestToXML(
                            result.getMethod().getRealClass().getName(),
                            result.getMethod().getMethodName());
                }
            }
        }
    }

    // *** Public methods ***//
    /**
     * Obtains the initialization configuration
     * @return initialization instance
     */
    public static Initialization getConfig() {
        return config;
    }

    /**
     * Method to get a timestamp
     * @return string with the timestamp
     */
    public static String getTimeStamp() {
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss")
                .format(Calendar.getInstance().getTime());
        return timeStamp;
    }

    // *** Private methods ***//
    /**
     * Method to add failed test to a xml file
     * @param suite name
     * @param testName name of the test
     */
    private void addFailedTestToXML(String suite, String testName) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(failedSuitePath));

            Element foundElement = (Element) XPath.selectSingleNode(doc,
                    "/suite/test/classes/class[@name='" + suite
                            + "']/methods/include[@name='" + testName + "']");

            if (foundElement == null) {
                Element foundClass = (Element) XPath.selectSingleNode(doc,
                        "/suite/test/classes/class[@name='" + suite + "']");

                if (foundClass != null) {
                    // Adding the method element
                    Element methodsElement = foundClass.getChild("methods");

                    // Adding the include element with the test name
                    Element includeElement = new Element("include");
                    includeElement.setAttribute("name", testName);
                    methodsElement.addContent(includeElement);

                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.setFormat(
                            org.jdom.output.Format.getPrettyFormat());
                    xmlOutput.output(doc, new FileWriter(failedSuitePath));
                } else {
                    Element rootElement = doc.getRootElement();

                    Element testElement = rootElement.getChild("test");

                    Element classesElement = testElement.getChild("classes");

                    // Adding the class element with the testSet name
                    Element classElement = new Element("class");
                    classElement.setAttribute("name", suite);
                    classesElement.addContent(classElement);

                    // Adding the method element
                    Element methodsElement = new Element("methods");
                    classElement.addContent(methodsElement);

                    // Adding the include element with the test name
                    Element includeElement = new Element("include");
                    includeElement.setAttribute("name", testName);
                    methodsElement.addContent(includeElement);

                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.setFormat(
                            org.jdom.output.Format.getPrettyFormat());
                    xmlOutput.output(doc, new FileWriter(failedSuitePath));
                }
            } else {
                log.info(
                        "The test is already added in the xml of failed tests");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to remove passed test of the xml file
     * @param suite name
     * @param testName name of the test
     */
    private void removePassedTestToXML(String suite, String testName) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(failedSuitePath));

            Element rootElement = doc.getRootElement();
            Element testElement = rootElement.getChild("test");
            Element classesElement = testElement.getChild("classes");
            Element test = (Element) XPath.selectSingleNode(doc,
                    "/suite/test/classes/class[@name='" + suite
                            + "']/methods/include[@name='" + testName + "']");
            if (test != null) {
                if (XPath.selectNodes(doc, "/suite/test/classes/class[@name='"
                        + suite + "']/methods/include").size() > 1) {

                    Element testToRemove = (Element) XPath.selectSingleNode(doc,
                            "/suite/test/classes/class[@name='" + suite
                                    + "']/methods/include[@name='" + testName
                                    + "']");
                    Element methodElement = (Element) XPath.selectSingleNode(
                            doc, "/suite/test/classes/class[@name='" + suite
                                    + "']/methods");

                    methodElement.removeContent(testToRemove);
                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.setFormat(
                            org.jdom.output.Format.getPrettyFormat());
                    xmlOutput.output(doc, new FileWriter(failedSuitePath));
                } else {
                    classesElement.removeContent(
                            test.getParentElement().getParentElement());
                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.setFormat(
                            org.jdom.output.Format.getPrettyFormat());
                    xmlOutput.output(doc, new FileWriter(failedSuitePath));
                }

            } else {
                log.info("The test not exists in the xml of failed tests");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks that the PO is ready
     * @param pageObject page object to be used
     */
    protected void isReady(BasePageObject pageObject) {
        assertTrue(
                "The PO " + pageObject.getClass().getName() + " is not ready",
                pageObject.isReady());
    }
}
