package com.vaadin.flow.demo.website;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.testbench.By;

public class BlogPostIT extends AbstractComplexStaticMenuTest {

    @Test
    public void blog_list_visible() {
        openRouteUrl("blog");

        Assert.assertTrue("Blog post 1 link not present",
                isElementPresent(By.xpath("//a[contains(@href,'blog/1')]")));
        Assert.assertTrue("Blog post 2 link not present",
                isElementPresent(By.xpath("//a[contains(@href,'blog/2')]")));
        Assert.assertTrue("Blog post 3 link not present",
                isElementPresent(By.xpath("//a[contains(@href,'blog/3')]")));
    }

    @Test
    public void valid_blog_post_url() {
        openRouteUrl("blog/1");
        Assert.assertTrue("Blog post content should be present",
                isElementPresent(By.className("blog-content")));
    }

    @Test
    public void invalid_blog_post_url() {
        openRouteUrl("blog/99");
        waitForElementPresent(By.id("error-content"));
        Assert.assertTrue("Error view content is not present",
                isElementPresent(By.id("error-content")));
        Assert.assertFalse(
                "Top navigation, i.e. the MainLayout is still present",
                isElementPresent(By.className("topnav")));
    }

    @Test
    public void blog_post_title_set_dynamically() {
        openRouteUrl("blog/1");
        Assert.assertEquals("Blog title is wrong for post #1 -",
                "Vaadin Charts for the Web Component world",
                getDriver().getTitle());
        openRouteUrl("blog/3");
        Assert.assertEquals("Blog title is wrong for post #3 -",
                "Community Spotlight - February 2016", getDriver().getTitle());
    }
}
