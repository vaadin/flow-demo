package com.vaadin.hummingbird.minesweeper;

import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.DemoTestBenchTest;

public abstract class MinesweeperPageObject extends DemoTestBenchTest {

    protected void waitForBoomNotification() {
        waitForNotification("BOOM");
    }

    private void waitForNotification(String string) {
        waitForElementPresent(By.xpath(
                "//vaadin-notification/div/div[text()='" + string + "']"));
    }

    protected void waitForSuccessNotification() {
        waitForNotification("Success!");
    }

    protected void assertCellHidden(int row, int col) {
        WebElement cell = getCell(row, col);
        Assert.assertFalse(hasCssClass(cell, "revealed"));
    }

    protected void assertCellEmpty(int row, int col) {
        WebElement cell = getCell(row, col);
        Assert.assertTrue(hasCssClass(cell, "revealed"));
        Assert.assertFalse(hasCssClass(cell, "mine"));
    }

    protected void assertCellHiddenOrEmpty(String message, int row, int col) {
        WebElement cell = getCell(row, col);

        Assert.assertTrue(message,
                !hasCssClass(cell, "revealed") || !hasCssClass(cell, "mine"));

    }

    protected WebElement getCell(int row, int col) {
        WebElement rowE = getMineField()
                .findElements(By.xpath("//tr[@class='row']")).get(row);
        return rowE.findElements(By.xpath("td")).get(col);
    }

    protected abstract WebElement getMineField();

}
