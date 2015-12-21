package com.vaadin.hummingbird;

import org.openqa.selenium.By;

public class DemoTestBenchTest extends AbstractTestBenchTest {

    protected void waitForApplicationToStart() {
        // Main div has a "pre-render" attribute for the pre-render version,
        // wait until it is removed by the client engine

        // Wait for ui div
        waitForElementPresent(By.xpath("//div[contains(@class,'ui')]"));

        // Wait for pre-render attribute to be removed
        waitForElementNotPresent(By.xpath("//div[@pre-render]"));

    }

}
