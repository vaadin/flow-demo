package com.vaadin.hummingbird.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PolymerSamplerIT extends PolymerSamplerPageObject {

    @Test
    public void testInitialPage_applicationStarts() {
        openUrl("");
        waitForApplicationToStart();
        assertNotNull("Category select paper-item should be visible",
                findElementWithClassName("paper-item",
                        "paper-elements-category"));
        assertNotNull("Vaadin link paper-fab should be visible",
                findElementWithClassName("paper-fab", "iconvaadin"));
    }

    @Test
    public void testPolymerStyles_customStyleModuleOnInitialPage_stylesUpdated() {
        openUrl("");
        waitForApplicationToStart();

        WebElement fab = findElementWithClassName("paper-fab", "iconpolymer");
        assertTrue(fab.getAttribute("class").contains("paper-fab-0"));

        fab = findElementWithClassName("paper-fab", "icongwt");
        assertTrue(fab.getAttribute("class").contains("paper-fab-1"));

        fab = findElementWithClassName("paper-fab", "iconvaadin");
        assertTrue(fab.getAttribute("class").contains("paper-fab-2"));
    }

    @Test
    public void testElement_attributesAndTextContent() {
        openUrl("");
        waitForApplicationToStart();
        openCategory("Paper Elements");
        openSample("Button");

        WebElement button = findElementWithClassName("paper-button",
                "testing-button");
        assertNotNull("Animated attribute should be set",
                button.getAttribute("animated"));
        assertNotNull("Toggles attribute should be set",
                button.getAttribute("toggles"));
        assertNotNull("Active attribute should be set",
                button.getAttribute("active"));
        assertTrue("Should contain colorful class",
                button.getAttribute("class").contains("colorful"));
        assertEquals("Elevation attribute should be set", "4",
                button.getAttribute("elevation"));
        assertEquals("Text content should be same", "colorful",
                button.getText().toLowerCase());
    }

    @Test
    public void testPolymerStyles_checkboxWithCustomStyleModule_correctPolymerScopedClasses() {
        openUrl("");
        openCategory("Paper Elements");
        openSample("CheckBox");

        // two blue checkboxes
        List<WebElement> blues = findElements(By.className("blue"));
        for (WebElement blue : blues) {
            assertTrue("Should have scoped class",
                    blue.getAttribute("class").contains("paper-checkbox-1"));
        }

        // one red, orange and green
        WebElement red = findElement(By.className("red"));
        assertTrue("Should have scoped class",
                red.getAttribute("class").contains("paper-checkbox-2"));

        WebElement orange = findElement(By.className("orange"));
        assertTrue("Should have scoped class",
                orange.getAttribute("class").contains("paper-checkbox-3"));

        WebElement green = findElement(By.className("green"));
        assertTrue("Should have scoped class",
                green.getAttribute("class").contains("paper-checkbox-4"));
    }

    @Test
    public void testDomEvents_iconButtonWithClickListener_eventTriggeredServerSide() {
        openUrl("");
        openCategory("Paper Elements");
        openSample("Icon Button");

        findElementWithClassName("paper-icon-button", "with-click-listener")
                .click();

        verifyEventDialogOpen();
    }

    @Test
    public void testPolymerEvents_checkboxChangeEvent_eventTriggeredServerSide() {
        openUrl("");
        openCategory("Paper Elements");
        openSample("CheckBox");

        findElementWithClassName("paper-checkbox", "paper-checkbox-3").click();

        verifyEventDialogOpen();
    }

}
