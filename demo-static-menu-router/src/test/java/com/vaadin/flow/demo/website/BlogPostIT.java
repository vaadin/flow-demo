package com.vaadin.flow.demo.website;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.testbench.By;

public class BlogPostIT extends AbstractComplexStaticMenuTest {

    @Test
    public void valid_blog_post_url() {
        openRouteUrl("blog/1");
        Assert.assertTrue("Blog post content should be present",
                this.isElementPresent(By.className("blog-content")));
    }

    @Test
    public void invalid_blog_post_url() {
        openRouteUrl("blog/asdf");
        Assert.assertFalse("No blog content should be present",
                this.isElementPresent(By.className("blog-content")));
        Assert.assertTrue(
                "Top navigation, i.e. the MainLayout is still present",
                isElementPresent(By.className("topnav")));
    }
}
