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

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.demo.testutil.AbstractChromeTest;

public class HelloWorldIT extends AbstractChromeTest {

    @Test
    public void checkBasicFunctionality() {
        open();

        // Template based view
        waitForElementPresent(By.id("template-link"));
        WebElement link = findElement(By.id("template-link"));
        link.click();

        waitForElementPresent(By.id("template"));
        WebElement template = findElement(By.id("template"));

        WebElement greeting = getInShadowRoot(template,
                By.id("greeting"));
        assertInitialGreeting(greeting);

        getInShadowRoot(template, By.id("inputId"))
                .sendKeys("John Doe");

        assertGreeting(greeting);

        // Components API based view
        WebElement components = findElement(By.id("components-link"));
        assertHelloWorld(components, "componentsGreeting");

        // Elements API based view
        WebElement elements = findElement(By.id("elements-link"));
        assertHelloWorld(elements, "elementsGreeting");
    }

    private void assertHelloWorld(WebElement link, String greetingId) {
        link.click();

        waitForElementPresent(By.id(greetingId));
        WebElement greeting = findElement(By.id(greetingId));

        assertInitialGreeting(greeting);

        findElement(By.id("inputId")).sendKeys("John Doe");

        assertGreeting(greeting);
    }

    private void assertGreeting(WebElement greeting) {
        waitUntil(aDriver -> "Hello John Doe!".equals(greeting.getText()));
    }

    private void assertInitialGreeting(WebElement greeting) {
        Assert.assertEquals("Incorrect initial greeting state",
                "Please enter your name", greeting.getText());
    }

}
