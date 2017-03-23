package com.vaadin.hummingbird.demo.page;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.demo.testutil.AbstractDemoTest;

public class ReportsOverviewIT extends AbstractDemoTest {

    private static final String SELECTED_CSS_CLASS = "selected";

    @Test
    public void testRowSelection() {
        open();
        waitForElementPresent(By.tagName("table"));
        WebElement table = findElement(By.tagName("table"));
        WebElement firstRow = table
                .findElement(By.cssSelector("tbody tr:first-child"));

        String classes = firstRow.getAttribute("class");
        // ensure that the row is not selected at this stage
        Assert.assertFalse(classes.contains(SELECTED_CSS_CLASS));
        firstRow.click();

        waitForElementPresent(By.className("selected-report"));
        classes = firstRow.getAttribute("class");
        // ensure that the row is selected after the click
        Assert.assertTrue(classes.contains(SELECTED_CSS_CLASS));
    }

}
