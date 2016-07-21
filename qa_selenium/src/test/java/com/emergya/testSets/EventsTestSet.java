package com.emergya.testSets;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.emergya.pageObjects.EventsPage;
import com.emergya.utils.DefaultTestSet;

public class EventsTestSet extends DefaultTestSet {

    Logger log = Logger.getLogger(AdministracionTestSet.class);

    EventsPage eventsPage;

    public EventsTestSet() {
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

    @Test(description = "createEvents")
    public void CreateEvents() {
        eventsPage = new EventsPage(driver);
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start CreateEvents test");

        try {

            eventsPage.doClickEvents();

            eventsPage.doClickOnCreate();

            eventsPage.introducirCampoTitle("Titulo QA");

            eventsPage.introducirCampoDescription("Description QA");

            eventsPage.clickStartDateMonth();

            eventsPage.clickStartDateDay();

            eventsPage.clickStartDateYear();

            eventsPage.clickFinishDateMonth();

            eventsPage.clickFinishDateDay();

            eventsPage.clickFinishDateYear();

            eventsPage.doClickOnGuardar();

            WebElement i = eventsPage.search("Titulo QA", "Titulo QA");

            isNuevoEventsDisplayed(i, "Titulo QA");

        } finally {

            WebElement i = eventsPage.search("Titulo QA", "Titulo Editado");

            i.click();

            eventsPage.doClickOnBorrar();

            eventsPage.doClickOnConfirmar();

        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End CreateEvents test");
    }

    @Test(description = "updateEvents")
    public void UpdateEvents() {
        eventsPage = new EventsPage(driver);
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start UpdateEvents test");

        try {

            eventsPage.doClickEvents();

            eventsPage.doClickOnCreate();

            eventsPage.introducirCampoTitle("Titulo QA");

            eventsPage.introducirCampoDescription("Description QA");

            eventsPage.clickStartDateMonth();

            eventsPage.clickStartDateDay();

            eventsPage.clickStartDateYear();

            eventsPage.clickFinishDateMonth();

            eventsPage.clickFinishDateDay();

            eventsPage.clickFinishDateYear();

            eventsPage.doClickOnGuardar();

            WebElement i = eventsPage.search("Titulo QA", "Titulo QA");

            isNuevoEventsDisplayed(i, "Titulo QA");

            i.click();

            eventsPage.doClickOnEditar();

            isTitleEqual("Titulo QA");

            eventsPage.introducirCampoTitle("Titulo Editado");

            eventsPage.doClickOnGuardar();

            WebElement z = eventsPage.search("Titulo Editado",
                    "Titulo Editado");

            isNuevoEventsDisplayed(z, "Titulo Editado");

        } finally {

            WebElement i = eventsPage.search("Titulo QA", "Titulo Editado");

            i.click();

            eventsPage.doClickOnBorrar();

            eventsPage.doClickOnConfirmar();

        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End UpdateEvents test");
    }

    @Test(description = "deleteEvents")
    public void DeleteEvents() {
        eventsPage = new EventsPage(driver);
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start DeleteEvents test");

        try {

            eventsPage.doClickEvents();

            eventsPage.doClickOnCreate();

            eventsPage.introducirCampoTitle("Titulo QA");

            eventsPage.introducirCampoDescription("Description QA");

            eventsPage.doClickOnGuardar();

            WebElement i = eventsPage.search("Titulo QA", "Titulo QA");

            isNuevoEventsDisplayed(i, "Titulo QA");

            i.click();

            eventsPage.doClickOnBorrar();

            eventsPage.doClickOnConfirmar();

            isBorrado();

        } finally {

            // Ya está borrado

        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End DeleteEvents test");
    }

    public void isLogoDisplayed() {
        assertTrue("The logo isn't displayed, it should be displayed",
                eventsPage.isElementVisibleByXPath("logo"));
    }

    public void isEventsDisplayed() {
        assertTrue("The logo isn't displayed, it should be displayed",
                eventsPage.isElementVisibleByXPath("logoEvents"));
    }

    public void isNuevoEventsDisplayed(WebElement i, String titulo) {
        String actual = i.getText();
        assertEquals(titulo, actual);
    }

    public void isBorrado() {
        assertTrue("El elemento no se ha borrado, debería haberse borrado",
                eventsPage.isElementVisibleByXPath("tituloEvents"));
    }

    public void isTitleEqual(String titulo) {
        String actual = eventsPage.getElementById("title")
                .getAttribute("value");
        assertEquals(titulo, actual);
    }
}
