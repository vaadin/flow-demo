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
package com.vaadin.hummingbird.demo.addressbook;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.demo.addressbook.backend.Contact;
import com.vaadin.hummingbird.demo.addressbook.backend.ContactService;
import com.vaadin.hummingbird.demo.testutil.AbstractChromeTest;

public class AddressbookIT extends AbstractChromeTest {

    @Test
    public void displayAndEditTable() {
        open();

        WebElement webComponent = findElement(By.tagName("contacts-table"));
        List<WebElement> rows = findInShadowRoot(webComponent,
                By.className("contact-row"));

        List<Contact> contacts = ContactService.getDemoService().findAll("");
        Assert.assertEquals(contacts.size(), rows.size());

        // check values in the first row
        Contact contact = contacts.get(0);
        assertFirstRow(rows.get(0), contact.getFirstName(),
                contact.getLastName(), contact.getEmail(), true);

        // edit the first row
        rows.get(0).click();

        WebElement contactForm = findElement(By.tagName("contacts-form"));
        setText(getInShadowRoot(contactForm, By.id("firstName")).get(), "foo");
        setText(getInShadowRoot(contactForm, By.id("lastName")).get(), "bar");

        getInShadowRoot(contactForm, By.id("save")).get().click();

        waitUntil(driver -> assertFirstRow(rows.get(0), "foo", "bar",
                contact.getEmail(), false));
    }

    private void setText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    private boolean assertFirstRow(WebElement row, String firstName,
            String lastName, String email, boolean failImmidiately) {
        List<WebElement> columns = row.findElements(By.className("column"));
        if (firstName.equals(columns.get(0).getText())) {
            Assert.assertEquals(lastName, columns.get(1).getText());
            Assert.assertEquals(email, columns.get(2).getText());
            return true;
        } else if (failImmidiately) {
            Assert.fail("First name '" + columns.get(0).getText()
                    + "' is unexpected, expected is " + firstName);
        }
        return false;
    }
}
