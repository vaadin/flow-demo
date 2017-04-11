package com.vaadin.flow.demo.minesweeper.component;

import com.vaadin.flow.demo.minesweeper.component.data.MineFieldData;
import com.vaadin.flow.demo.testutil.AbstractChromeTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

abstract class MinesweeperPageObject extends AbstractChromeTest {
    private final MineFieldData seed1MineFieldData = new MineFieldData(10, 10, 1, 0.2);

    boolean isMineSeed1(int row, int col) {
        return seed1MineFieldData.isMine(row, col);
    }

    void waitForBoomNotification() {
        waitForNotification("BOOM! Reload to try again");
    }

    private void waitForNotification(String string) {
        waitForElementPresent(By.xpath("//div[text()='" + string + "']"));
    }

    void waitForSuccessNotification() {
        waitForNotification("Congratulations!");
    }

    boolean isCellHidden(int row, int col) {
        WebElement cell = getCell(row, col);
        return !hasCssClass(cell, "empty") && !hasCssClass(cell, "revealed");
    }

    boolean isCellEmpty(int row, int col) {
        WebElement cell = getCell(row, col);
        return hasCssClass(cell, "empty");
    }

    WebElement getCell(int row, int col) {
        WebElement template = findElement(By.id("template"));
        WebElement mineField = getInShadowRoot(template, By.cssSelector("table")).get();
        WebElement rowE = mineField.findElements(By.xpath(".//tr"))
                .get(row);
        return rowE.findElements(By.xpath("td")).get(col);
    }
}
