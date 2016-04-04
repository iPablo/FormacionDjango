package com.emergya.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.emergya.drivers.EmergyaChromeDriver;
import com.emergya.drivers.EmergyaFirefoxDriver;
import com.emergya.drivers.EmergyaIEDriver;
import com.emergya.drivers.EmergyaWebDriver;

/**
 * Initializes properties and driver
 * @author Jose Antonio Sanchez <jasanchez@emergya.com>
 */
public class Initialization {

    private String properties;
    private String browser;
    private String context;
    private String environment;
    private String loginURL;
    private String os;
    private String screenshotPath;
    private String videoRecordingPath;
    private boolean recordVideo;
    private boolean saveVideoForPassed;
    private boolean ideEnabled;
    private String downloadPath;
    private int widthBeforeMaximize;
    private int widthAfterMaximize;
    private int heightBeforeMaximize;
    private int heightAfterMaximize;
    private static Initialization instance = null;
    private Logger log = Logger.getLogger(Initialization.class);

    EmergyaWebDriver driver;

    /**
     * Singleton pattern
     * @return a single instance
     */
    public static Initialization getInstance() {
        if (instance == null) {
            instance = new Initialization();
        }
        return instance;
    }

    /**
     * Reads properties when the class is instanced
     */
    private Initialization() {
        this.readProperties();
    }

    // **** Read properties method ****//
    public void readProperties() {
        log.info("[log-Properties] " + this.getClass().getName()
                + "- Start readProperties test");

        properties = "test.properties";
        Properties prop = new Properties();
        log = Logger.getLogger(this.getClass());

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();

            // Load a properties file
            prop.load(loader.getResourceAsStream(properties));

            // Get the property value
            browser = prop.getProperty("browser");
            context = prop.getProperty("context");
            environment = prop.getProperty("environment");
            loginURL = environment + context;
            os = prop.getProperty("OS");
            screenshotPath = prop.getProperty("screenshotPath");
            videoRecordingPath = prop.getProperty("videoRecordingPath",
                    this.screenshotPath);
            recordVideo = "true".equals(
                    prop.getProperty("activateVideoRecording", "false"));
            saveVideoForPassed = "true"
                    .equals(prop.getProperty("saveVideoForPassed", "false"));
            ideEnabled = "true".equals(prop.getProperty("ideEnabled", "false"));

            // Generate download path
            downloadPath = "";

            String auxPath = this.getClass().getProtectionDomain()
                    .getCodeSource().getLocation().getFile();

            String[] arrayPath = auxPath.split("/");

            if (auxPath.startsWith(File.separator)) {
                downloadPath = File.separator;
            }

            for (int i = 1; i < arrayPath.length; i++) {
                downloadPath = downloadPath + arrayPath[i] + "/";
            }

            downloadPath = downloadPath.replace("target/classes/",
                    prop.getProperty("downloadPath"));

            downloadPath = downloadPath.replaceAll("/",
                    Matcher.quoteReplacement(File.separator));

            if (!downloadPath
                    .endsWith(Matcher.quoteReplacement(File.separator))) {
                downloadPath += Matcher.quoteReplacement(File.separator);
            }

            File file = new File(this.getDownloadPath());
            if (!file.exists()) {
                file.mkdir();
            }

            log.info("Auto detected operative System = " + os);
        } catch (IOException ex) {
            log.error(
                    "test.properties file is not found. If this is the first time you excuted your test you can copy the settings properties file in the test folder in svn and customized it to match your environment");
        }

        log.info("[log-Properties] " + this.getClass().getName()
                + "- End readProperties test");
    }

    // **** Driver initialization method ****//
    public EmergyaWebDriver initialize() {
        log.info("[log-Properties] " + this.getClass().getName()
                + "- Start initialize test");

        EmergyaWebDriver tmpDriver = null;

        // Driver initialization
        if (browser.equalsIgnoreCase("Firefox")) {
            FirefoxProfile firefoxProfile = new FirefoxProfile();

            firefoxProfile.setPreference(
                    "browser.download.manager.focusWhenStarting", true);
            firefoxProfile.setEnableNativeEvents(true);
            firefoxProfile.setPreference("browser.download.folderList", 2);
            firefoxProfile.setPreference(
                    "browser.download.manager.showWhenStarting", false);
            firefoxProfile.setPreference("browser.download.dir",
                    this.getDownloadPath());

            File dir = new File(this.getDownloadPath());
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();

                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }

            firefoxProfile.setPreference(
                    "browser.helperApps.neverAsk.saveToDisk",
                    "application/x-jar,application/application/vnd.android.package-archive,"
                            + "application/msword,application/x-rar-compressed,application/octet-stream,"
                            + "application/csv, application/excel, application/vnd.ms-excel,application/x-excel, application/x-msexcel,text/csv,"
                            + "image/jpeg, application/zip, video/x-msvideo, image/png, application/pdf, text/xml, text/html");
            firefoxProfile.setPreference("pdfjs.disabled", true);

            tmpDriver = new EmergyaFirefoxDriver(firefoxProfile);

        } else if (browser.equalsIgnoreCase("Chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");

            if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.chrome.driver",
                        "files/software/chromedriver.exe");
            } else {
                System.setProperty("webdriver.chrome.driver",
                        "files/software/chromedriver");
            }

            tmpDriver = new EmergyaChromeDriver(options);
        } else if (browser.equalsIgnoreCase("IE")
                && os.equalsIgnoreCase("windows")) {
            System.setProperty("webdriver.ie.driver",
                    "files/software/IEDriverServer.exe");
            tmpDriver = new EmergyaIEDriver();
        }

        // Common functions
        driver = tmpDriver;

        log.info("Browser initialized with dimensions: "
                + driver.manage().window().getSize().getWidth() + "px X "
                + driver.manage().window().getSize().getHeight() + "px");

        widthBeforeMaximize = driver.manage().window().getSize().getWidth();
        heightBeforeMaximize = driver.manage().window().getSize().getHeight();

        driver.get(loginURL);

        tmpDriver.findElement(By.tagName("body")).sendKeys(Keys.F11);

        driver.sleep(1);

        widthAfterMaximize = driver.manage().window().getSize().getWidth();
        heightAfterMaximize = driver.manage().window().getSize().getHeight();

        if ((widthBeforeMaximize == widthAfterMaximize)
                && (heightBeforeMaximize == heightAfterMaximize)) {
            log.info("Not maximized first time...try again");

            driver.sleep(1);

            tmpDriver.findElement(By.tagName("body")).sendKeys(Keys.F11);

            driver.sleep(2);
        }

        this.cleanDownloadDirectory();

        log.info("Browser resized with dimensions: "
                + driver.manage().window().getSize().getWidth() + "px X "
                + driver.manage().window().getSize().getHeight() + "px");

        log.info("[log-Properties] " + this.getClass().getName()
                + "- End initialize test");

        return driver;
    }

    // **** Getters methods section ****//
    public String getBrowser() {
        return browser;
    }

    public String getContext() {
        return context;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getLoginURL() {
        return loginURL;
    }

    public String getOS() {
        return os;
    }

    public String getScreenshotPath() {
        return this.screenshotPath;
    }

    public String getVideoRecordingPath() {
        return videoRecordingPath;
    }

    public boolean isRecordVideo() {
        return recordVideo;
    }

    public boolean isSaveVideoForPassed() {
        return saveVideoForPassed;
    }

    public boolean isIDEEnabled() {
        return ideEnabled;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    // **** Download methods section ****//
    /**
     * Returns the donwloaded filepath
     * @param filename the name of the file
     * @return the donwloaded filepath
     */
    public String getDownloadedFilePath(String filename) {
        String path = this.getDownloadPath() + filename;
        File f = new File(path);
        if (f.exists()) {
            return path;
        } else {
            log.info("Not exists the file");
            return null;
        }
    }

    /**
     * Cleans the download directory.
     */
    public void cleanDownloadDirectory() {
        File f = new File(this.getDownloadPath());
        if (f.exists() && f.isDirectory()) {
            try {
                FileUtils.cleanDirectory(f);
            } catch (IOException e) {
                log.error(e.getStackTrace());
            }
        }
    }

    /**
     * Checks if the directory is empty.
     * @return true is it's empty
     */
    public boolean isEmptyDownloadDirectory() {
        File f = new File(this.getDownloadPath());
        return f.exists() && f.isDirectory() && f.list().length == 0;
    }
}
