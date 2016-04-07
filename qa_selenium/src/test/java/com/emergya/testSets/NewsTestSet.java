package com.emergya.testSets;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.emergya.pageObjects.NewsPage;
import com.emergya.utils.DefaultTestSet;

public class NewsTestSet extends DefaultTestSet {
    Logger log = Logger.getLogger(AdministracionTestSet.class);
    NewsPage newspage;

    public NewsTestSet() {
        super();
    }

    @BeforeMethod(description = "startTest")
    public void before() {
        super.before();
    }

    @AfterMethod(description = "endTest")
    public void afterAllIsSaidAndDone() {
        super.afterAllIsSaidAndDone();
    }
}
