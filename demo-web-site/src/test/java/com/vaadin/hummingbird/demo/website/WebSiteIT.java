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

    private static final String BLOGS = "Blogs";
    private static final String BLOG_ITEM_TITLE = "blog-item-title";

    @Test
    public void testNavigation() {
        open();

        Assert.assertEquals("This is the home page", getContent().getText());

        getMenuItem("About").click();

        Assert.assertEquals("This is the about page", getContent().getText());

        getMenuItem("Dynamic 1").click();

        Assert.assertEquals("Dynamic page one", getContent().getText());

        getMenuItem("Dynamic 2").click();

        Assert.assertEquals("Dynamic page two", getContent().getText());

        getMenuItem("Home").click();

        Assert.assertEquals("This is the home page", getContent().getText());
    }

    @Test
    public void testInitialBlogRecord() {
        open();
        getMenuItem(BLOGS).click();

        String title = findBlogItem(0, false);

        Assert.assertEquals(title, findActiveBlog().getText());
    }

    @Test
    public void testBlogRecordClick() {
        open();
        getMenuItem(BLOGS).click();

        String title = findBlogItem(1, true);

        Assert.assertEquals(title, findActiveBlog().getText());
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

    private WebElement findActiveBlog() {
        return findElement(By.cssSelector("div." + BLOG_ITEM_TITLE));
    }

    private String findBlogItem(int index, boolean click) {
        WebElement postLink = findElements(By.className("blog-item")).get(index)
                .findElement(By.className(BLOG_ITEM_TITLE));
        if (click) {
            postLink.click();
        }
        return postLink.getText();
    }
}
