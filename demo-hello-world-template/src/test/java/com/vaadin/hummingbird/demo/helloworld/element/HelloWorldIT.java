/*
 * Copyright 2000-2016 Vaadin Ltd.
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
package com.vaadin.hummingbird.demo.helloworld.element;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.demo.testutil.AbstractDemoTest;
import com.vaadin.testbench.By;

public class HelloWorldIT extends AbstractDemoTest {

    @Test
    public void basicFunctionality() {
        open();
        WebElement input = findElement(By.id("inputId"));
        WebElement greeting = findElement(By.id("greeting"));
        WebElement button = findElement(By.tagName("button"));
        Assert.assertEquals("Please enter your name", greeting.getText());
        button.click();
        Assert.assertEquals("Please enter your name", greeting.getText());

        input.sendKeys("John Doe");
        button.click();
        Assert.assertEquals("Hello John Doe!", greeting.getText());
    }
}
