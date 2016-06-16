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
    public void validateTableData10Rows() {
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
            assertRowData(rows.get(i), contacts.get(i));
        }
    }

    @Test
    public void selectRow() {
        int index = 0;

        List<WebElement> rows = getRows();

        rows.get(index).click();

        List<WebElement> buttons = findElement(By.className("buttons"))
                .findElements(By.tagName("button"));
        Assert.assertEquals(2, buttons.size());

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

    @Test
    public void deselectRow() {
        int index = 0;

        List<WebElement> rows = getRows();

        // select a row
        rows.get(index).click();

        List<WebElement> buttons = findElement(By.className("buttons"))
                .findElements(By.tagName("button"));
        Assert.assertEquals(2, buttons.size());

        // deselect
        rows.get(index).click();
        Assert.assertFalse(isElementPresent(By.className("buttons")));
    }

    @Test
    public void updateEleventh() {
        int index = 10;

        List<WebElement> rows = getRows();

        // select a row
        rows.get(index).click();

        Contact before = getContact(rows.get(index));

        WebElement form = findElement(By.className("contactform"));
        List<WebElement> inputs = form.findElements(By.tagName("input"));
        inputs.get(0).sendKeys("123");
        inputs.get(3).sendKeys("456");
        findElement(By.id("save")).click();

        Contact after = getContact(getRows().get(index));
        Assert.assertEquals(before.getFirstName() + "123",
                after.getFirstName());
        Assert.assertEquals(before.getLastName(), after.getLastName());
        Assert.assertEquals(before.getEmail() + "456", after.getEmail());
    }

    private Contact getContact(WebElement tr) {
        List<WebElement> tds = tr.findElements(By.tagName("td"));
        Contact contact = new Contact();
        contact.setFirstName(tds.get(0).getText());
        contact.setLastName(tds.get(1).getText());
        contact.setEmail(tds.get(2).getText());
        return contact;
    }

    private List<WebElement> getRows() {
        WebElement table = findElement(By.className("contactstable"));

        List<WebElement> bodies = table.findElements(By.tagName("tbody"));
        List<WebElement> rows = bodies.get(bodies.size() - 1)
                .findElements(By.tagName("tr"));
        return rows;
    }

    private void assertRowData(WebElement tr, Contact expected) {
        Contact actual = getContact(tr);
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
    }
}
