/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.demo.helloworld;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HelloWorldIT {
    private ChromeDriver driver;

    @Before
    public void setUpDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void checkBasicFunctionality() {
        driver.get("http://localhost:8080");

        // Template based view
        waitUntilPresent(By.id("template-link"));
        WebElement link = driver.findElement(By.id("template-link"));
        link.click();

        waitUntilPresent(By.id("template"));
        WebElement template = driver.findElement(By.id("template"));

        WebElement greeting = getElementFromShadowRoot(template,
                By.id("greeting"));
        assertInitialGreeting(greeting);

        getElementFromShadowRoot(template, By.id("inputId"))
                .sendKeys("John Doe");

        assertGreeting(greeting);

        // Components API based view
        WebElement components = driver.findElement(By.id("components-link"));
        assertHelloWorld(components, "componentsGreeting");

        // Elements API based view
        WebElement elements = driver.findElement(By.id("elements-link"));
        assertHelloWorld(elements, "elementsGreeting");
    }

    private void assertHelloWorld(WebElement link, String greetingId) {
        link.click();

        waitUntilPresent(By.id(greetingId));
        WebElement greeting = driver.findElement(By.id(greetingId));

        assertInitialGreeting(greeting);

        driver.findElement(By.id("inputId")).sendKeys("John Doe");

        assertGreeting(greeting);
    }

    private void assertGreeting(WebElement greeting) {
        assertEquals("Incorrect greeting after input", "Hello John Doe!",
                greeting.getText());
    }

    private void assertInitialGreeting(WebElement greeting) {
        assertEquals("Incorrect initial greeting state",
                "Please enter your name", greeting.getText());
    }

    private void waitUntilPresent(By by) {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement getElementFromShadowRoot(WebElement shadowRootOwner,
            By by) {
        WebElement shadowRoot = (WebElement) driver.executeScript(
                "return arguments[0].shadowRoot", shadowRootOwner);
        assertNotNull("Could not locate shadowRoot in the element", shadowRoot);
        return shadowRoot.findElements(by).stream().findFirst()
                .orElseThrow(() -> new AssertionError(
                        "Could not find required element in the shadowRoot"));
    }
}
