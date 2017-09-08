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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.demo.testutil.AbstractChromeTest;
import com.vaadin.testbench.By;

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

        findFirstNameInput().sendKeys("foo");
        findLastNameInput().sendKeys("bar");
        findEmailInput().sendKeys("example@foo.bar");
        findBirthDayInput().sendKeys("01/02/2003");
        findBirthDayInput().sendKeys(Keys.ENTER);

        WebElement doNotCall = findElement(By.id("do-not-call"));
        WebElement checkBox = getInShadowRoot(doNotCall,
                By.id("nativeCheckbox"));
        click(checkBox);

        click(save);

        waitUntil(driver -> info.getText().startsWith("Saved bean values"));

        Assert.assertTrue(info.getText().contains("firstName=foo,"));
        Assert.assertTrue(info.getText().contains("lastName=bar,"));
        Assert.assertTrue(info.getText().contains("phone=,"));
        Assert.assertTrue(info.getText().contains("email=example@foo.bar,"));
        Assert.assertTrue(info.getText().contains("birthDate=2003-01-02"));
        Assert.assertTrue(info.getText().contains("doNotCall=true"));

        // Make email address incorrect
        findEmailInput().clear();
        findEmailInput().sendKeys("abc");
        click(save);

        waitUntil(driver -> info.getText().startsWith("There are errors"));
        Assert.assertEquals("There are errors :Incorrect email address",
                info.getText());

        // reset
        click(findElement(By.id("reset")));

        Assert.assertEquals("", findFirstNameInput().getAttribute("value"));
        Assert.assertEquals("", findLastNameInput().getAttribute("value"));
        Assert.assertEquals("",
                getInShadowRoot(findElement(By.id("phone")), By.id("input"))
                        .getAttribute("value"));
        Assert.assertEquals("", findEmailInput().getAttribute("value"));
        Assert.assertEquals("", findBirthDayInput().getAttribute("value"));
        Assert.assertFalse(checkBox.isSelected());
    }

    private WebElement findBirthDayInput() {
        return getInShadowRoot(findElement(By.id("birth-date")),
                By.id("input"));
    }

    private WebElement findEmailInput() {
        return getInShadowRoot(findElement(By.id("email")), By.id("input"));
    }

    private WebElement findLastNameInput() {
        return getInShadowRoot(findElement(By.id("last-name")), By.id("input"));
    }

    private WebElement findFirstNameInput() {
        return getInShadowRoot(findElement(By.id("first-name")),
                By.id("input"));
    }

    private void click(WebElement element) {
        scrollToElement(element);
        element.click();
    }
}
