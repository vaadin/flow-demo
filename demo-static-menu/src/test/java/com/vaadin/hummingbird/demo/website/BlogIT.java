package com.vaadin.hummingbird.demo.website;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BlogIT extends AbstractComplexStaticMenuTest {
    private static final String COMMUNITY = "Community";
    private static final String BLOG = "Blog";
    private static final String BLOG_ITEM_TITLE = "blog-item-title";

    @Test
    public void testInitialBlogRecord() {
        open();
        getMenuItem(COMMUNITY).click();
        getSubMenuItem(BLOG).click();

        String title = findBlogItem(0, false);

        Assert.assertEquals(title, findActiveBlog().getText());
    }

    @Test
    public void testBlogRecordClick() {
        open();
        getMenuItem(COMMUNITY).click();
        getSubMenuItem(BLOG).click();

        String title = findBlogItem(1, true);

        Assert.assertEquals(title, findActiveBlog().getText());
    }

    @Test
    public void testBlogErrorPage() {
        getDriver().get(getBlogUrl("4"));

        String headingText = findElement(By.cssSelector("h1")).getText();

        Assert.assertEquals("404 - Page not found", headingText);
    }

    @Test
    public void testBlogOkStatusCode() throws IOException {
        Assert.assertEquals(200, getHttpStatusCode(getBlogUrl("1")));
    }

    @Test
    public void testBlogErrorStatusCode() throws IOException {
        Assert.assertEquals(404, getHttpStatusCode(getBlogUrl("4")));
    }

    private static int getHttpStatusCode(String url) throws IOException {
        try (CloseableHttpClient client = HttpClientBuilder.create().build();
                CloseableHttpResponse response = client
                        .execute(new HttpGet(url))) {

            return response.getStatusLine().getStatusCode();
        }
    }

    private String getBlogUrl(String string) {
        return getTestURL() + "/blog/" + string;
    }

    private WebElement findActiveBlog() {
        return findElement(By.cssSelector("h1." + BLOG_ITEM_TITLE));
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
