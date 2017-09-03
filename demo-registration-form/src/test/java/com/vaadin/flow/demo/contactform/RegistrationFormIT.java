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
package com.vaadin.flow.demo.contactform;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.vaadin.flow.demo.testutil.AbstractChromeTest;

public class RegistrationFormIT extends AbstractChromeTest {

    @Test
    public void validateContactForm() {
        open();

        // empty fields -> validation error
        WebElement signUp = findElement(By.id("sign-up"));
        signUp.click();

        Assert.assertTrue(isElementPresent(By.id("notification")));
        Assert.assertNotNull(
                findElement(By.id("notification"))
                .findElement(By.className("error")));

        // discard the dialog
        WebElement fullName = findElement(By.id("full-name"));
        new Actions(getDriver()).moveToElement(fullName).click().build()
        .perform();
        WebElement fullNameInput = getInShadowRoot(fullName, By.id("input"));
        fullNameInput.sendKeys("foo");

        // fill full name -> status should be OK
        WebElement fullNameStatus = findElement(By.id("full-name-status"));
        Assert.assertEquals("ok", fullNameStatus.getAttribute("class"));

        // clear full name -> status should be ERROR
        fullNameInput.clear();
        fullNameInput.sendKeys("a");
        fullNameInput.sendKeys(Keys.BACK_SPACE);

        waitUntil(driver -> !fullNameStatus.getAttribute("class").equals("ok"));
        Assert.assertEquals("error", fullNameStatus.getAttribute("class"));

        fullNameInput.sendKeys("foo");

        // fill phoneOrEmal with possible but incorrect phone number
        WebElement phoneOrEmail = findElement(By.id("phone-or-email"));
        WebElement phoneOrEmailInput = getInShadowRoot(phoneOrEmail,
                By.id("input"));
        phoneOrEmailInput.sendKeys("+1");

        WebElement phoneOrEmailStatus = findElement(
                By.id("phone-or-email-status"));
        Assert.assertEquals("error", phoneOrEmailStatus.getAttribute("class"));
        waitUntil(driver -> !phoneOrEmailStatus.getText().isEmpty());
        Assert.assertEquals(
                "The string '+1' is not a valid phone number. "
                        + "Phone should start with a plus sign and contain at least 10 digits",
                        phoneOrEmailStatus.getText());

        // fill phoneOrEmal with possible but incorrect email address
        phoneOrEmailInput.clear();
        phoneOrEmailInput.sendKeys("foo");
        Assert.assertEquals("error", phoneOrEmailStatus.getAttribute("class"));
        Assert.assertEquals("The string 'foo' is not a valid email address",
                phoneOrEmailStatus.getText());

        // fill phoneOrEmal with correct phone number
        phoneOrEmailInput.clear();
        phoneOrEmailInput.sendKeys("+1234567890");
        Assert.assertEquals("ok", phoneOrEmailStatus.getAttribute("class"));

        // fill phoneOrEmal with correct email address
        phoneOrEmailInput.clear();
        phoneOrEmailInput.sendKeys("foo@example.com");
        Assert.assertEquals("ok", phoneOrEmailStatus.getAttribute("class"));
    }

}
