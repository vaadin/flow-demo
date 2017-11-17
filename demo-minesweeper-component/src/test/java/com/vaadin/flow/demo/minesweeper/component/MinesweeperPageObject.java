package com.vaadin.flow.demo.minesweeper.component;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.demo.testutil.AbstractChromeTest;

public abstract class MinesweeperPageObject extends AbstractChromeTest {

    @Override
    protected void open() {
        super.open();
        waitForElementPresent(By.tagName("table"));
    }

    @Override
    protected void open(String... parameters) {
        super.open(parameters);
        waitForElementPresent(By.tagName("table"));
    }

    protected void waitForBoomNotification() {
        waitForNotification("BOOM! Reload to try again");
    }

    private void waitForNotification(String string) {
        waitForElementPresent(By.xpath("//div[text()='" + string + "']"));
    }

    protected void waitForSuccessNotification() {
        waitForNotification("Congratulations!");
    }

    protected void assertCellHidden(int row, int col) {
        Assert.assertTrue(isCellHidden(row, col));
    }

    private boolean isCellHidden(int row, int col) {
        WebElement cell = getCell(row, col);
        return !hasCssClass(cell, "empty") && !hasCssClass(cell, "revealed");
    }

    private boolean isCellEmpty(int row, int col) {
        WebElement cell = getCell(row, col);
        return hasCssClass(cell, "empty");
    }

    protected void assertCellEmpty(int row, int col) {
        Assert.assertTrue(isCellEmpty(row, col));
    }

    protected void assertCellHiddenOrEmpty(String message, int row, int col) {
        Assert.assertTrue(message,
                isCellHidden(row, col) || isCellEmpty(row, col));

    }

    protected WebElement getCell(int row, int col) {
        WebElement rowE = getMineField().findElements(By.xpath(".//tr"))
                .get(row);
        return rowE.findElements(By.xpath("td")).get(col);
    }

    protected abstract WebElement getMineField();

}
