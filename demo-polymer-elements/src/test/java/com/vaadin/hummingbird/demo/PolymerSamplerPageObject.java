package com.vaadin.hummingbird.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Samples being tested must have the root class name
 * <code>*componentname*-sample</code> e.g. <code>icon-button-sample</code> and
 * the last polymer element component in the sample must have the
 * {@link SampleBase#LAST_COMPONENT_CLASS_NAME} class name.
 */
public abstract class PolymerSamplerPageObject
        extends PolymerSamplerTestBenchTest {

    protected void openCategory(String categoryName) {
        getCategory(categoryName).click();
    }

    protected void openSample(String sampleName) {
        getSample(sampleName).click();
        waitForElementVisible(By.className(
                sampleName.toLowerCase().replace(" ", "-") + "-sample"));
        waitForElementVisible(
                By.className(SampleBase.LAST_COMPONENT_CLASS_NAME));
    }

    protected WebElement getCategory(String categoryName) {
        return findElementWithClassName("paper-item",
                categoryName.toLowerCase().replace(" ", "-") + "-category");
    }

    protected WebElement getSample(String sampleName) {
        return findElementWithClassName("paper-item",
                sampleName.toLowerCase() + "-sample");
    }

    protected WebElement findElementWithClassName(String tag,
            String className) {
        return findElement(By
                .xpath("//" + tag + "[contains(@class,'" + className + "')]"));
    }

    protected void verifyEventDialogOpen() {
        waitForElementVisible(
                By.xpath("//paper-dialog[contains(@class,'event-dialog')]"));
    }
}
