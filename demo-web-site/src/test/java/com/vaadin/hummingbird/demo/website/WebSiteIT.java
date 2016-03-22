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
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.demo.testutil.AbstractTestBenchTest;

public class WebSiteIT extends AbstractTestBenchTest {

    @Test
    public void testNavigation() {
        open();

        Assert.assertEquals("This is the home page", getContent().getText());

        getMenuItem("About").click();

        Assert.assertEquals("This is the about page", getContent().getText());

        getMenuItem("Parameter view").click();

        Assert.assertEquals("Id parameter: 1",
                getFirstContentChild().getText());

        getMenuItem("Resource view").click();

        Assert.assertEquals("Select the resource to display" + "",
                getFirstContentChild().getText());

        getMenuItem("Home").click();

        Assert.assertEquals("This is the home page", getContent().getText());
    }

    @Test
    public void testParametersView() {
        open();
        getMenuItem("Parameter view").click();
        assertLocation("param/1");
        Assert.assertEquals("Id parameter: 1",
                getFirstContentChild().getText());

        getContent().findElement(By.xpath("./a")).click();
        assertLocation("param/2");
        Assert.assertEquals("Id parameter: 2",
                getFirstContentChild().getText());

        getDriver().get("http://localhost:8080/param/3");
        Assert.assertEquals("Id parameter: 3",
                getFirstContentChild().getText());
    }

    @Test
    public void testWildcardView() {
        open();
        getMenuItem("Resource view").click();

        WebElement selectedResource = getContent()
                .findElement(By.xpath("./*[4]"));
        Assert.assertEquals("No resource selected", selectedResource.getText());

        getContent().findElement(By.xpath("//a[text()='css/site.css']"))
                .click();
        assertLocation("resource/css/site.css");

        WebElement iframe = getDriver().findElement(By.xpath("//iframe"));
        assertLocation("css/site.css", iframe.getAttribute("src"));
    }

    private void assertLocation(String expectedLocation) {
        assertLocation(expectedLocation, getDriver().getCurrentUrl());
    }

    private void assertLocation(String expectedLocation,
            String currentLocation) {
        Assert.assertEquals("http://localhost:8080/" + expectedLocation,
                currentLocation);
    }

    private WebElement getFirstContentChild() {
        return getContent().findElement(By.xpath("./*"));
    }

    public WebElement getContent() {
        return findElement(By.className("content"));
    }

    public WebElement getMenuItem(String text) {
        if (text.equals("Home")) {
            return findElement(By.cssSelector(".logo"));
        }
        List<WebElement> menuLinks = findElements(By.cssSelector(".menu a"));

        return menuLinks.stream().filter(link -> text.equals(link.getText()))
                .findFirst().get();
    }
}
