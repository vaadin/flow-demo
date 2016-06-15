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
package com.vaadin.hummingbird.demo.addressbook;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.demo.addressbook.backend.Contact;
import com.vaadin.hummingbird.demo.addressbook.backend.ContactService;
import com.vaadin.hummingbird.demo.testutil.AbstractDemoTest;

/**
 * @author Vaadin Ltd
 *
 */
public class AddressbookIT extends AbstractDemoTest {

    @Before
    public void setUp() {
        open();
    }

    @Test
    public void tableWithData() {
        WebElement table = findElement(By.className("contactstable"));
        Assert.assertEquals("table", table.getTagName());

        List<WebElement> headers = table.findElements(By.tagName("th"));
        Assert.assertEquals(3, headers.size());

        Assert.assertEquals("First Name", headers.get(0).getText());
        Assert.assertEquals("Last Name", headers.get(1).getText());
        Assert.assertEquals("Email", headers.get(2).getText());

        List<WebElement> bodies = table.findElements(By.tagName("tbody"));
        List<WebElement> rows = bodies.get(bodies.size() - 1)
                .findElements(By.tagName("tr"));
        List<Contact> contacts = ContactService.getDemoService().findAll("");
        int size = contacts.size();
        Assert.assertEquals(size, rows.size());

        for (int i = 0; i < 10; i++) {
            assertRowData(i, rows, contacts);
        }
    }

    @Test
    public void selectRow() {
        int index = 0;

        WebElement table = findElement(By.className("contactstable"));

        List<WebElement> bodies = table.findElements(By.tagName("tbody"));
        List<WebElement> rows = bodies.get(bodies.size() - 1)
                .findElements(By.tagName("tr"));

        rows.get(index).click();

        List<WebElement> buttons = findElement(By.className("buttons"))
                .findElements(By.tagName("button"));
        Assert.assertEquals(2, buttons.size());

        Assert.assertEquals(Boolean.TRUE.toString(),
                buttons.get(0).getAttribute("disabled"));
        Assert.assertNull(buttons.get(1).getAttribute("disabled"));

        WebElement form = findElement(By.className("contactform"));
        List<WebElement> inputs = form.findElements(By.tagName("input"));
        Assert.assertEquals(4, inputs.size());

        Contact contact = ContactService.getDemoService().findAll("")
                .get(index);
        Assert.assertEquals(contact.getFirstName(),
                inputs.get(0).getAttribute("value"));
        Assert.assertEquals(contact.getLastName(),
                inputs.get(1).getAttribute("value"));
        Assert.assertEquals(contact.getPhoneNumber(),
                inputs.get(2).getAttribute("value"));
        Assert.assertEquals(contact.getEmail(),
                inputs.get(3).getAttribute("value"));
    }

    private void assertRowData(int row, List<WebElement> rows,
            List<Contact> contacts) {
        List<WebElement> columns = rows.get(row).findElements(By.tagName("td"));
        Assert.assertEquals(3, columns.size());
        Assert.assertEquals(contacts.get(row).getFirstName(),
                columns.get(0).getText());
        Assert.assertEquals(contacts.get(row).getLastName(),
                columns.get(1).getText());
        Assert.assertEquals(contacts.get(row).getEmail(),
                columns.get(2).getText());
    }
}
