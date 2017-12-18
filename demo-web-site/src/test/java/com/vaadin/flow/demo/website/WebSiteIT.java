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
package com.vaadin.flow.demo.website;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.demo.testutil.AbstractChromeTest;

public class WebSiteIT extends AbstractChromeTest {

    private static final String TITLE = "Flow Web Site Demo";

    @Test
    public void testNavigation() {
        open();
        assertMenuItemSelected(null);
        assertPageTitle(TITLE);
        assertContent("This is the home page");

        getMenuItem("About").click();
        assertMenuItemSelected("About");
        assertPageTitle(TITLE);
        assertContent("This is the about page");

        getMenuItem("Parameter view").click();
        assertMenuItemSelected("Parameter view");
        assertPageTitle(TITLE);

        Assert.assertEquals("Id parameter: 1",
                getFirstContentChild().getText());

        getMenuItem("Resource view").click();
        assertMenuItemSelected("Resource view");
        assertPageTitle(TITLE);

        Assert.assertEquals("Select the resource to display" + "",
                getFirstContentChild().getText());

        getMenuItem("Home").click();
        assertMenuItemSelected(null);
        assertPageTitle(TITLE);

        assertContent("This is the home page");
    }

    private void assertMeta(WebElement element, Map<String, String> metas) {
        String property = element.getAttribute("property");
        String content = element.getAttribute("content");
        String value = metas.remove(property);
        if (value != null) {
            Assert.assertEquals(value, content);
        }
    }

    private void assertMenuItemSelected(String menuItem) {
        List<WebElement> activeItems = findElements(
                By.cssSelector(".menu-item.active"));
        if (menuItem == null) {
            Assert.assertEquals(0, activeItems.size());
        } else {
            Assert.assertEquals(1, activeItems.size());
            WebElement activeItem = activeItems.get(0);
            Assert.assertEquals(menuItem, activeItem.getText());
        }
    }

    private void assertContent(String expected) {
        Assert.assertEquals(expected, getContent().getText());
    }

    private void assertPageTitle(String expected) {
        Assert.assertEquals(expected, getDriver().getTitle());
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

        getDriver().get(getRootURL() + "/param/3");
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

    @Test
    public void checkMetatagsAndBootstrapListener() {
        open();

        Map<String, String> map = new HashMap<>();
        map.put("og:title", "The Rock");
        map.put("og:type", "video.movie");
        map.put("og:url", "http://www.imdb.com/title/tt0117500/");
        map.put("og:image", "http://ia.media-imdb.com/images/rock.jpg");
        List<WebElement> metas = findElements(By.tagName("meta"));
        metas.stream().forEach(meta -> assertMeta(meta, map));

        Assert.assertTrue("Unexpected meta tags left: "
                + map.keySet().stream().collect(Collectors.joining(",")),
                map.isEmpty());
    }

    private void assertLocation(String expectedLocation) {
        assertLocation(expectedLocation, getDriver().getCurrentUrl());
    }

    private void assertLocation(String expectedLocation,
            String currentLocation) {
        Assert.assertEquals(getRootURL() + "/" + expectedLocation,
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
