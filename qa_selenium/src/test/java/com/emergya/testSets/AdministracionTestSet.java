package com.emergya.testSets;

import static org.testng.AssertJUnit.assertEquals;

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

    AdministracionPage administracionPage;

    /*
     * Descripción: Acceso al admin de la app de Proyecto de iniciación con las
     * siguientes pruebas:
     * 
     * - Login con User/Pass - Prueba CRUD Event - Prueba CRUD NewsItem
     * 
     */
    @Test(description = "administracionPageCreateNew")
    public void createNew() {
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start createNew test");

        administracionPage = new AdministracionPage(driver);

        try {

            administracionPage.introUsuario("admin");

            administracionPage.introContrasenia("password1234");

            administracionPage.clickOnLogin();

            administracionPage.clickOnAddNews();

            administracionPage.introTitle("Titulo QA");

            administracionPage.introDescription("Description QA");

            administracionPage.clickOnToday();

            administracionPage.clickOnSave();

            isCreacion(
                    "The news item \"Description QA\" was added successfully.");

        } finally {

            administracionPage.clickOnItem();

            administracionPage.clickOnDelete();

            administracionPage.clickOnConfirmar();

        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End createNew test");

    }

    @Test(description = "administracionPageCreateEvent")
    public void createEvent() {
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start createEvent test");

        administracionPage = new AdministracionPage(driver);

        try {

            administracionPage.introUsuario("admin");

            administracionPage.introContrasenia("password1234");

            administracionPage.clickOnLogin();

            administracionPage.clickOnAddEvents();

            administracionPage.introTitle("Titulo QA");

            administracionPage.introDescription("Description QA");

            administracionPage.clickOnStartDate();

            administracionPage.clickOnEndDate();

            administracionPage.clickOnSave();

            isCreacion("The event \"Description QA\" was added successfully.");

        } finally {

            administracionPage.clickOnItem();

            administracionPage.clickOnDelete();

            administracionPage.clickOnConfirmar();

        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End createEvent test");
    }

    @Test(description = "administracionPageUpdateNew")
    public void UpdateNew() {
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start updateNew test");

        administracionPage = new AdministracionPage(driver);

        try {

            administracionPage.introUsuario("admin");

            administracionPage.introContrasenia("password1234");

            administracionPage.clickOnLogin();

            administracionPage.clickOnAddNews();

            administracionPage.introTitle("Titulo QA");

            administracionPage.introDescription("Description QA");

            administracionPage.clickOnToday();

            administracionPage.clickOnSave();

            isCreacion(
                    "The news item \"Description QA\" was added successfully.");

            administracionPage.clickOnItem();

            isValueTitleOK("Titulo QA");

            administracionPage.introDescription("Description Editado");

            administracionPage.clickOnSave();

            isCreacion(
                    "The news item \"Description Editado\" was changed successfully.");

        } finally {

            administracionPage.clickOnItem();

            administracionPage.clickOnDelete();

            administracionPage.clickOnConfirmar();

        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End updateNew test");
    }

    @Test(description = "administracionPageUpdateEvent")
    public void UpdateEvent() {
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start createNew test");

        administracionPage = new AdministracionPage(driver);

        try {

            administracionPage.introUsuario("admin");

            administracionPage.introContrasenia("password1234");

            administracionPage.clickOnLogin();

            administracionPage.clickOnAddEvents();

            administracionPage.introTitle("Titulo QA");

            administracionPage.introDescription("Description QA");

            administracionPage.clickOnStartDate();

            administracionPage.clickOnEndDate();

            administracionPage.clickOnSave();

            isCreacion("The event \"Description QA\" was added successfully.");

            administracionPage.clickOnItem();

            isValueTitleOK("Titulo QA");

            administracionPage.introDescription("Description Editado");

            administracionPage.clickOnSave();

            isCreacion(
                    "The event \"Description Editado\" was changed successfully.");

        } finally {

            administracionPage.clickOnItem();

            administracionPage.clickOnDelete();

            administracionPage.clickOnConfirmar();

        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End createNew test");
    }

    @Test(description = "administracionPageDeleteNew")
    public void deleteNew() {
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start deleteNew test");

        administracionPage = new AdministracionPage(driver);

        try {

            administracionPage.introUsuario("admin");

            administracionPage.introContrasenia("password1234");

            administracionPage.clickOnLogin();

            administracionPage.clickOnAddNews();

            administracionPage.introTitle("Titulo QA");

            administracionPage.introDescription("Description QA");

            administracionPage.clickOnToday();

            administracionPage.clickOnSave();

            isCreacion(
                    "The news item \"Description QA\" was added successfully.");

            administracionPage.clickOnItem();

            administracionPage.clickOnDelete();

            administracionPage.clickOnConfirmar();

            isBorrado(
                    "The news item \"Description QA\" was deleted successfully.");

        } finally {
            // Ya está borrado
        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End deleteNew test");

    }

    @Test(description = "administracionPageDeleteEvent")
    public void deleteEvent() {
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start deleteEvent test");

        administracionPage = new AdministracionPage(driver);

        try {

            administracionPage.introUsuario("admin");

            administracionPage.introContrasenia("password1234");

            administracionPage.clickOnLogin();

            administracionPage.clickOnAddEvents();

            administracionPage.introTitle("Titulo QA");

            administracionPage.introDescription("Description QA");

            administracionPage.clickOnStartDate();

            administracionPage.clickOnEndDate();

            administracionPage.clickOnSave();

            isCreacion("The event \"Description QA\" was added successfully.");

            administracionPage.clickOnItem();

            administracionPage.clickOnDelete();

            administracionPage.clickOnConfirmar();

            isBorrado("The event \"Description QA\" was deleted successfully.");

        } finally {
            // Ya está borrado
        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End deleteEvent test");

    }

    public void isCreacion(String mensaje) {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        String actual = administracionPage.getElementByXPath("mensaje")
                .getText();
        assertEquals(mensaje, actual);
    }

    public void isValueTitleOK(String title) {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        String actual = administracionPage.getElementById("title").getText();
        assertEquals(title, actual);
    }

    public void isBorrado(String mensaje) {
        if (administracionPage == null) {
            administracionPage = new AdministracionPage(driver);
        }
        String actual = administracionPage.getElementByXPath("mensaje")
                .getText();
        assertEquals(mensaje, actual);
    }

}
