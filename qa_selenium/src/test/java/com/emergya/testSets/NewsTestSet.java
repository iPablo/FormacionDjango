package com.emergya.testSets;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.emergya.pageObjects.NewsPage;
import com.emergya.utils.DefaultTestSet;

public class NewsTestSet extends DefaultTestSet {
    Logger log = Logger.getLogger(AdministracionTestSet.class);

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

    /*
     * Descripción: Acceso al admin de la app de Proyecto de iniciación con las
     * siguientes pruebas:
     * 
     * - Login con User/Pass - Prueba CRUD Event - Prueba CRUD NewsItem
     * 
     */
    @Test(description = "newsPageSearch")
    public void NewsPage() throws InterruptedException {
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start newsPageSearch test");

        // Variable declaration and definition
        NewsPage newsPage = new NewsPage(driver);

        try {
            newsPage.newsCRUDFunciones("Title QA", "Description QA");
            /*
             * administracionPage.createEvent("Title QA", "Description QA",
             * "2016-04-13", "2016-04-15"); administracionPage.updateEvent(
             * "Descripción QA editada"); administracionPage.deleteEvent();
             * administracionPage.createNewsItem("Title QA", "Descripcion QA");
             * administracionPage.updateNewsItem("Descripción QA editada");
             * administracionPage.deleteNewsItem();
             */

        } finally {
            // Steps to clear the stage (Post steps)
        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End newsPageSearch test");

    }
}
