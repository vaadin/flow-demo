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
package com.vaadin.flow.demo.helloworld.template;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.demo.testutil.AbstractChromeTest;
import com.vaadin.testbench.By;

public class SignInFormIT extends AbstractChromeTest {

    @Test
    public void basicFunctionality() {
        open();

        setText("name", "John Doe");
        setText("day", "2");
        setText("month", "4");
        setText("year", "1975");

        WebElement template = findElement(By.id("template"));
        getInShadowRoot(template, By.id("register")).click();
        WebElement msg = getInShadowRoot(template, By.id("reg-msg"));

        int year = GregorianCalendar.getInstance().get(Calendar.YEAR);
        waitUntil(driver -> msg.getText().equals(
                "Welcome John Doe, your are " + (year - 1975) + " years old"));
    }

    private void setText(String inputId, String text) {
        WebElement template = findElement(By.id("template"));

        WebElement input = getInShadowRoot(template, By.id(inputId));
        WebElement nativeInput = getInShadowRoot(input, By.id("nativeInput"));
        nativeInput.clear();
        nativeInput.sendKeys(text);
    }
}
