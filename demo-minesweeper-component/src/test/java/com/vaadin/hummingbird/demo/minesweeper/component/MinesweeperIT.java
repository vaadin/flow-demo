package com.vaadin.hummingbird.demo.minesweeper.component;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.demo.minesweeper.component.data.MineFieldData;

public class MinesweeperIT extends MinesweeperPageObject {

    @Test
    public void clickOnEmptyRevealsCell() {
        open("seed=1");
        assertCellHidden(0, 0);
        getCell(0, 0).click();
        assertCellEmpty(0, 0);
    }

    @Test
    public void clickOnMineShowsNotification() {
        open("seed=1");
        getCell(9, 0).click();
        waitForBoomNotification();
    }

    @Test
    public void solveSeed1Minefield() {
        open("seed=1&rows=10&cols=10");
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (!isMineSeed1(row, col)) {
                    assertCellHiddenOrEmpty(
                            row + "," + col + " is revealed or a mine", row,
                            col);
                    getCell(row, col).click();
                }
            }
        }
        waitForSuccessNotification();
    }

    private MineFieldData minefieldSeed1 = new MineFieldData();

    {
        minefieldSeed1.init(10, 10, 1, 0.2);
    }

    private boolean isMineSeed1(int row, int col) {
        return minefieldSeed1.isMine(row, col);
    }

    @Override
    protected WebElement getMineField() {
        return findElement(By.cssSelector("table"));
    }

}
