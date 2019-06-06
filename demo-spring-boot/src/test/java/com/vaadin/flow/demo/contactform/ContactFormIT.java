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

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.component.datepicker.testbench.DatePickerElement;
import com.vaadin.flow.component.textfield.testbench.EmailFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.flow.demo.testutil.AbstractChromeTest;

public class ContactFormIT extends AbstractChromeTest {
    @Test
    public void validateContactForm() {
        open();

        // Empty form validation: ther is an error
        WebElement save = findElement(By.id("save"));
        save.click();

        // Fill form: there shouldn't be an error
        WebElement info = findElement(By.id("info"));
        Assert.assertEquals(
                "There are errors :Both phone and email cannot be empty",
                info.getText());

        WebElement email = findEmail();
        findFirstName().sendKeys("foo");
        findLastName().sendKeys("bar");
        email.sendKeys("example@foo.bar");
        findBirthDay().sendKeys("01/02/2003");
        findBirthDay().sendKeys(Keys.ENTER);

        WebElement doNotCall = findElement(By.id("do-not-call"));
        click(doNotCall);

        click(save);

        waitUntil(driver -> info.getText().startsWith("Saved bean values"));

        Assert.assertTrue(info.getText().contains("firstName=foo,"));
        Assert.assertTrue(info.getText().contains("lastName=bar,"));
        Assert.assertTrue(info.getText().contains("phone=,"));
        Assert.assertTrue(info.getText().contains("email=example@foo.bar,"));
        Assert.assertTrue(info.getText().contains("birthDate=2003-01-02"));
        Assert.assertTrue(info.getText().contains("doNotCall=true"));

        // Make email address incorrect

        while (!email.getAttribute("value").isEmpty()) {
            email.sendKeys(Keys.BACK_SPACE);
        }
        email.sendKeys("abc");
        blur();
        click(save);

        waitUntil(driver -> info.getText().startsWith("There are errors"));
        Assert.assertEquals("There are errors :Incorrect email address",
                info.getText());

        email.sendKeys("a@foo.bar");

        // reset
        click(findElement(By.id("reset")));

        // Wait for everything to update.
        waitUntil(driver -> info.getText().isEmpty());

        Assert.assertEquals("", findFirstName().getAttribute("value"));
        Assert.assertEquals("", findLastName().getAttribute("value"));
        Assert.assertEquals("+1 ", findPhone().getAttribute("value"));
        Assert.assertEquals("", findEmail().getAttribute("value"));
        Assert.assertEquals("", findBirthDay().getAttribute("value"));
        Assert.assertFalse(doNotCall.isSelected());
    }

    @Test
    public void validatePhoneFormatting() {
        open();

        // Fill form: there shouldn't be an error
        WebElement info = findElement(By.id("info"));

        findFirstName().sendKeys("foo");
        findLastName().sendKeys("bar");
        findEmail().sendKeys("example@foo.bar");
        WebElement phone = findPhone();
        phone.sendKeys("2345678901");
        phone.sendKeys(Keys.ENTER);
        blur();

        findElement(By.id("save")).click();

        waitUntil(driver -> info.getText().startsWith("Saved bean values"));

        Assert.assertEquals("+1 234 567 89 01", phone.getAttribute("value"));

        Assert.assertThat(info.getText(),
                CoreMatchers.containsString("+1 234 567 89 01"));
    }

    private WebElement findBirthDay() {
        return $(DatePickerElement.class).id("birth-date");
    }

    private WebElement findEmail() {
        return $(EmailFieldElement.class).id("email");
    }

    private WebElement findLastName() {
        return $(TextFieldElement.class).id("last-name");
    }

    private WebElement findFirstName() {
        return $(TextFieldElement.class).id("first-name");
    }

    private WebElement findPhone() {
        return $(TextFieldElement.class).id("phone");
    }

    private void click(WebElement element) {
        scrollToElement(element);
        element.click();
    }
}
