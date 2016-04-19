package com.emergya.testSets;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.emergya.pageObjects.NewsPage;
import com.emergya.utils.DefaultTestSet;

public class NewsTestSet extends DefaultTestSet {
    Logger log = Logger.getLogger(AdministracionTestSet.class);
    NewsPage newsPage;

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

    @Test(description = "createNews")
    public void CreateNews() {
        newsPage = new NewsPage(driver);
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start CreateNews test");

        try {

            newsPage.doClickNews();

            newsPage.doClickOnCreate();

            newsPage.introducirCampoTitle("Titulo QA");

            newsPage.introducirCampoDescription("Description QA");

            newsPage.doClickOnGuardar();

            WebElement i = newsPage.search("Titulo QA", "Titulo QA");

            isNuevaNewsDisplayed(i, "Titulo QA");

        } finally {

            WebElement i = newsPage.search("Titulo QA", "Titulo Editado");

            i.click();

            newsPage.doClickOnBorrar();

            newsPage.doClickOnConfirmar();

        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End CreateNews test");
    }

    @Test(description = "updateNews")
    public void UpdateNews() {
        newsPage = new NewsPage(driver);
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start UpdateNews test");

        try {

            newsPage.doClickNews();

            newsPage.doClickOnCreate();

            newsPage.introducirCampoTitle("Titulo QA");

            newsPage.introducirCampoDescription("Description QA");

            newsPage.doClickOnGuardar();

            WebElement i = newsPage.search("Titulo QA", "Titulo QA");

            isNuevaNewsDisplayed(i, "Titulo QA");

            i.click();

            newsPage.doClickOnEditar();

            isTitleEqual("Titulo QA");

            newsPage.introducirCampoTitle("Titulo Editado");

            newsPage.doClickOnGuardar();

            WebElement z = newsPage.search("Titulo Editado", "Titulo Editado");

            isNuevaNewsDisplayed(z, "Titulo Editado");

        } finally {

            WebElement i = newsPage.search("Titulo QA", "Titulo Editado");

            i.click();

            newsPage.doClickOnBorrar();

            newsPage.doClickOnConfirmar();

        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End UpdateNews test");
    }

    @Test(description = "deleteNews")
    public void DeleteNews() {
        newsPage = new NewsPage(driver);
        log.info("[log-TestSet]es que  " + this.getClass().getName()
                + "- Start DeleteNews test");

        try {

            newsPage.doClickNews();

            newsPage.doClickOnCreate();

            newsPage.introducirCampoTitle("Titulo QA");

            newsPage.introducirCampoDescription("Description QA");

            newsPage.doClickOnGuardar();

            WebElement i = newsPage.search("Titulo QA", "Titulo QA");

            isNuevaNewsDisplayed(i, "Titulo QA");

            i.click();

            newsPage.doClickOnBorrar();

            newsPage.doClickOnConfirmar();

            isBorrado();

        } finally {

            // Ya está borrado

        }

        log.info("[log-TestSet] " + this.getClass().getName()
                + "- End DeleteNews test");
    }

    public void isLogoDisplayed() {
        assertTrue("The logo isn't displayed, it should be displayed",
                newsPage.isElementVisibleByXPath("logo"));
    }

    public void isNewsDisplayed() {
        assertTrue("The logo isn't displayed, it should be displayed",
                newsPage.isElementVisibleByXPath("logoNews"));
    }

    public void isNuevaNewsDisplayed(WebElement i, String titulo) {
        String actual = i.getText();
        assertEquals(titulo, actual);
    }

    public void isBorrado() {
        assertTrue("El elemento no se ha borrado, debería haberse borrado",
                newsPage.isElementVisibleByXPath("tituloNews"));
    }

    public void isTitleEqual(String titulo) {
        String actual = newsPage.getElementById("title").getAttribute("value");
        assertEquals(titulo, actual);
    }

}
