package com.vaadin.flow.demo.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.demo.testutil.AbstractChromeTest;

public class ReportsOverviewIT extends AbstractChromeTest {

    private static final String SELECTED_CSS_CLASS = "selected";

    @Test
    public void testRowSelection() {
        open();
        waitForElementPresent(By.tagName("table"));
        WebElement table = findElement(By.tagName("table"));
        WebElement firstRow = table
                .findElement(By.cssSelector("tbody tr:first-child"));

        String firstRowClasses = firstRow.getAttribute("class");
        // ensure that the first row is not selected at this stage
        Assert.assertFalse(firstRowClasses.contains(SELECTED_CSS_CLASS));
        firstRow.click();

        waitForElementPresent(By.className("selected-report"));
        firstRowClasses = firstRow.getAttribute("class");
        // ensure that the first row is selected after the click
        Assert.assertTrue(firstRowClasses.contains(SELECTED_CSS_CLASS));

        // verifying the contents of the dialog
        WebElement firstCell = firstRow
                .findElement(By.cssSelector("td:first-child"));
        String selectedId = firstCell.getText();
        WebElement dialog = findElement(By.className("selected-report"));
        Assert.assertTrue(dialog.getText().contains("#" + selectedId));

        // selecting the next row
        WebElement secondRow = table
                .findElement(By.cssSelector("tbody tr:nth-child(2)"));
        secondRow.click();
        firstRowClasses = firstRow.getAttribute("class");

        // ensure that the first row is deselected after the click
        Assert.assertFalse(firstRowClasses.contains(SELECTED_CSS_CLASS));
        String secondRowClasses = secondRow.getAttribute("class");

        // ensure that the second row is selected after the click
        Assert.assertTrue(secondRowClasses.contains(SELECTED_CSS_CLASS));

        // verifying the contents of the dialog
        WebElement secondCell = secondRow.findElement(By.tagName("td"));
        selectedId = secondCell.getText();
        dialog = findElement(By.className("selected-report"));
        Assert.assertTrue(dialog.getText().contains("#" + selectedId));

        // deselecting
        secondRow.click();
        waitForElementNotPresent(By.className("selected-report"));
        secondRowClasses = secondRow.getAttribute("class");
        Assert.assertFalse(secondRowClasses.contains(SELECTED_CSS_CLASS));
    }

    @Test
    public void testJQuerySorting() {
        open();
        waitForElementPresent(By.className("tablesorter"));

        WebElement table = findElement(By.className("tablesorter"));
        List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));
        List<Integer> idsBeforeJQuerySort = new ArrayList<>(rows.size());
        for (WebElement row : rows) {
            WebElement cell = row.findElement(By.tagName("td"));
            idsBeforeJQuerySort.add(Integer.parseInt(cell.getText()));
        }

        // clicking on the header to make the jquery sort the by the column
        WebElement firstHeader = table
                .findElement(By.cssSelector("thead th:first-child"));
        firstHeader.click();

        rows = table.findElements(By.cssSelector("tbody tr"));
        List<Integer> idsAfterJQuerySort = new ArrayList<>(rows.size());
        for (WebElement row : rows) {
            WebElement cell = row.findElement(By.tagName("td"));
            idsAfterJQuerySort.add(Integer.parseInt(cell.getText()));
        }
        // sort the first list and compare it with the list sorted by jquery
        Collections.sort(idsBeforeJQuerySort);
        Assert.assertTrue(idsBeforeJQuerySort.equals(idsAfterJQuerySort));
    }
}
