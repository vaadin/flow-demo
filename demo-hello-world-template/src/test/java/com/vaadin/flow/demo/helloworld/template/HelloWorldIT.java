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
package com.vaadin.flow.demo.helloworld.template;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.demo.testutil.AbstractChromeTest;
import com.vaadin.testbench.By;

public class HelloWorldIT extends AbstractChromeTest {

    @Test
    public void basicFunctionality() {
        open();

        WebElement template = findElement(By.id("template"));

        WebElement input = getInShadowRoot(template, By.id("inputId")).get();
        WebElement greeting = getInShadowRoot(template, By.id("greeting")).get();
        Assert.assertEquals("Please enter your name", greeting.getText());

        input.sendKeys("John Doe");
        Assert.assertEquals("Hello John Doe!", greeting.getText());
    }
}
