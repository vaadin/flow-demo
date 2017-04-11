package com.vaadin.flow.demo.minesweeper.component;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MinesweeperIT extends MinesweeperPageObject {

    @Test
    public void clickOnEmptyRevealsCell() {
        open("seed=1");
        assertTrue(isCellHidden(0, 0));
        getCell(0, 0).click();
        assertTrue(isCellEmpty(0, 0));
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
                    assertTrue(row + "," + col + " is revealed or a mine",
                            isCellHidden(row, col) || isCellEmpty(row, col));
                    getCell(row, col).click();
                }
            }
        }
        waitForSuccessNotification();
    }
}
