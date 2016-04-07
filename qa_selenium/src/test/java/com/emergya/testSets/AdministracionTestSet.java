package com.emergya.testSets;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.emergya.pageObjects.AdministracionPage;
import com.emergya.utils.DefaultTestSet;

public class AdministracionTestSet extends DefaultTestSet {

    Logger log = Logger.getLogger(AdministracionTestSet.class);

    public AdministracionTestSet() {
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
    @Test(description = "administracionPageSearch")
    public void administracionPage() throws InterruptedException {
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start administracionPageSearch test");

        // Variable declaration and definition
        AdministracionPage administracionPage = new AdministracionPage(driver);

        try {
            // Go to localhost:8000/admin/
            // Enter usuario
            // Enter contraseña

            administracionPage.admin("admin", "password1234");

            administracionPage.createEvent("Title QA", "Description QA",
                    "2016-04-13", "2016-04-15");

            administracionPage.updateEvent("Descripción QA editada");

            administracionPage.deleteEvent();

            administracionPage.createNewsItem("Title QA", "Descripcion QA");

            administracionPage.updateNewsItem("Descripción QA editada");

            administracionPage.deleteNewsItem();

        } finally {
            // Steps to clear the stage (Post steps)
        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End googleDoSearchAndAccessToPage test");

    }

}
