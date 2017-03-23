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
package com.vaadin.hummingbird.demo.website;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.demo.testutil.AbstractChromeTest;

public abstract class AbstractComplexStaticMenuTest extends AbstractChromeTest {

    protected void testSubMenuItem(String caption) {
        testSubMenuItem(caption, caption);

    }

    protected void testSubMenuItem(String caption, String contents) {
        getSubMenuItem(caption).click();
        assertContents(contents);
        assertSubMenuSelected(caption);
    }

    protected void testSubSubMenuItem(String caption) {
        testSubSubMenuItem(caption, caption);

    }

    protected void testSubSubMenuItem(String caption, String contents) {
        getSubSubMenuItem(caption).click();
        assertContents(contents);
        assertSubSubMenuSelected(caption);
    }

    protected void assertContents(String viewName) {
        Assert.assertEquals("This is the " + viewName + " view",
                getContent().getText());
    }

    protected WebElement getContent() {
        return findElement(By.className("content"));
    }

    protected WebElement getMenuItem(String text) {
        if (text.equals("Home")) {
            return findElement(By.cssSelector(".logo"));
        }
        List<WebElement> menuLinks = getMenuItems();

        return menuLinks.stream().filter(link -> text.equals(link.getText()))
                .findFirst().get();
    }

    protected List<WebElement> getMenuItems() {
        return findElements(By.cssSelector(".menu a"));
    }

    protected void assertMenuSelected(String text) {
        getMenuItems().forEach(element -> {
            boolean selected = text.equals(element.getText());
            Assert.assertEquals(selected, hasCssClass(element, "selected"));
        });
    }

    protected void assertSubMenuSelected(String text) {
        getSubMenuItems().forEach(element -> {
            boolean selected = text.equals(element.getText());
            Assert.assertEquals(selected, hasCssClass(element, "selected"));
        });
    }

    protected void assertSubSubMenuSelected(String text) {
        getSubSubMenuItems().forEach(element -> {
            boolean selected = text.equals(element.getText());
            Assert.assertEquals(selected, hasCssClass(element, "selected"));
        });
    }

    protected WebElement getSubMenuItem(String text) {
        List<WebElement> menuLinks = getSubMenuItems();
        return menuLinks.stream().filter(link -> text.equals(link.getText()))
                .findFirst().get();
    }

    protected List<WebElement> getSubMenuItems() {
        return findElements(By.cssSelector(".submenu a"));
    }

    protected WebElement getSubSubMenuItem(String text) {
        List<WebElement> menuLinks = getSubSubMenuItems();
        return menuLinks.stream().filter(link -> text.equals(link.getText()))
                .findFirst().get();
    }

    protected List<WebElement> getSubSubMenuItems() {
        return findElements(By.cssSelector(".subsubmenu a"));
    }

}
