package com.vaadin.hummingbird.minesweeper;

import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SimpleMinesweeperIT extends MinesweeperPageObject {

    @Test
    public void applicationStarts() {
        openUrl("");
        waitForApplicationToStart();
    }

    @Test
    public void clickOnEmptyRevealsCell() {
        openUrl("seed=1");
        assertCellHidden(0, 0);
        getCell(0, 0).click();
        assertCellEmpty(0, 0);
    }

    @Test
    public void clickOnMineShowsNotification() {
        openUrl("seed=1");
        getCell(9, 0).click();
        waitForBoomNotification();
    }

    @Test
    public void solveSeed1Minefield() {
        openUrl("seed=1");
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (!isMine(row, col)) {
                    assertCellHiddenOrEmpty(
                            row + "," + col + " is revealed or a mine", row,
                            col);
                    getCell(row, col).click();
                }
            }
        }
        waitForSuccessNotification();
    }

    @Override
    protected WebElement getMineField() {
        return findElement(By.className("simple"));
    }

    protected boolean isMine(int row, int col) {
        WebElement cell = getCell(row, col);
        return hasCssClass(cell, "mine");
    }

}
