/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.demo.minesweeper.component.component;

import com.vaadin.flow.demo.minesweeper.component.component.Cell.CellClickEvent;
import com.vaadin.flow.demo.minesweeper.component.data.MineFieldData;
import com.vaadin.flow.demo.minesweeper.component.data.Point;
import com.vaadin.ui.Component;
import com.vaadin.ui.common.HasComponents;
import com.vaadin.ui.Tag;

/**
 * The Minesweeper main UI component.
 */
@Tag("table")
public class MinesweeperComponent extends Component implements HasComponents {

    private MineFieldData mineFieldData = new MineFieldData();

    /**
     * Creates a component and sets up a minefield using the given parameters.
     *
     * @param seed
     *            the random seed to use when placing mines
     * @param mineDensity
     *            the ratio of mines to cells, between 0 and 1
     * @param rows
     *            the number of rows in the minefield
     * @param cols
     *            the number of columns in the minefield
     */
    public MinesweeperComponent(long seed, double mineDensity, int rows,
            int cols) {
        mineFieldData.init(rows, cols, seed, mineDensity);
        init();
    }

    /**
     * Initializes the component hierarchy.
     */
    private void init() {
        for (int rowIndex = 0; rowIndex < mineFieldData.getRows(); rowIndex++) {
            Row row = new Row();
            for (int colIndex = 0; colIndex < mineFieldData
                    .getCols(); colIndex++) {
                Cell cell = new Cell();
                row.add(cell);
                cell.addCellClickListener(this::cellClick);
            }
            add(row);
        }
    }

    /**
     * Called whenever a cell click event occurs.
     *
     * @param e
     *            the cell click event object
     */
    private void cellClick(CellClickEvent e) {
        Cell cell = e.getSource();
        if (cell.isMarked()) {
            return;
        }

        if (mineFieldData.isMine(cell.getRow(), cell.getCol())) {
            boom();
            revealAll();
        } else {
            reveal(cell);
            if (isAllRevealed()) {
                success();
            }
        }
    }

    /**
     * Shows a message indicating mine explosion and failure.
     */
    private void boom() {
        getUI().get()
                .add(new Notification("BOOM! Reload to try again", "boom"));
    }

    /**
     * Shows a message indicating the mine field was successfully cleared.
     */
    private void success() {
        getUI().get().add(new Notification("Congratulations!", "success"));
    }

    /**
     * Reveals the given cell.
     *
     * @param cell
     *            the cell to reveal
     */
    public void reveal(Cell cell) {
        if (cell.isRevealed()) {
            // Already revealed
            return;
        }

        int row = cell.getRow();
        int col = cell.getCol();

        boolean mine = mineFieldData.isMine(row, cell.getCol());
        cell.reveal(mine);
        if (!mine) {

            int count = mineFieldData.getNearbyCount(row, col);
            if (count > 0) {
                cell.setText(Integer.toString(count));
            } else {
                // Autoreveal
                for (Point p : mineFieldData.getNearbyPoints(row, col)) {
                    reveal(getCell(p.getRow(), p.getCol()));
                }
            }
        }
    }

    /**
     * Checks if all empty cells have been revealed.
     *
     * @return true if all cells have been revealed, false otherwise.
     */
    private boolean isAllRevealed() {
        for (int row = 0; row < mineFieldData.getRows(); row++) {
            for (int col = 0; col < mineFieldData.getCols(); col++) {
                Cell cell = getCell(row, col);
                if (!cell.isRevealed() && !mineFieldData.isMine(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Reveal all cells, regardless of whether they contain mines or not.
     */
    private void revealAll() {
        for (int r = 0; r < mineFieldData.getRows(); r++) {
            for (int c = 0; c < mineFieldData.getCols(); c++) {
                reveal(getCell(r, c));
            }
        }
    }

    /**
     * Gets the cell component for the given cell.
     *
     * @param row
     *            the row coordinate
     * @param col
     *            the column coordinate
     * @return the cell component for the given coordinates
     */
    private Cell getCell(int row, int col) {
        return (Cell) getElement().getChild(row).getChild(col).getComponent()
                .get();
    }

}
