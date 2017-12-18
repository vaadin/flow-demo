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

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.demo.minesweeper.component.component.MinesweeperComponent.MinesweeperModel;
import com.vaadin.flow.demo.minesweeper.component.data.MineFieldData;
import com.vaadin.flow.demo.minesweeper.component.data.Point;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * The Minesweeper main UI component.
 */
@Tag("mine-sweeper")
@HtmlImport("frontend://components/minesweeper.html")
public class MinesweeperComponent extends PolymerTemplate<MinesweeperModel> {

    private final MineFieldData mineFieldData;

    public static class Cell {

        private String text;
        private boolean marked;
        private boolean revealed;
        private boolean mine;

        public Cell() {
            // No-arg constructor for the model
        }

        public String getText() {
            return text;
        }

        public boolean isMarked() {
            return marked;
        }

        public void setMarked(boolean marked) {
            this.marked = marked;
        }

        public boolean isRevealed() {
            return revealed;
        }

        public void setRevealed(boolean revealed) {
            this.revealed = revealed;
        }

        public boolean isMine() {
            return mine;
        }

        public void setMine(boolean mine) {
            this.mine = mine;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public interface MinesweeperModel extends TemplateModel {
        void setCells(List<List<Cell>> cells);

        List<List<Cell>> getCells();
    }

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
        mineFieldData = new MineFieldData(rows, cols, seed, mineDensity);
        initModel();
        setId("template");
    }

    @EventHandler
    private void handleClick(
            @EventData("event.model.parentModel.index") int row,
            @EventData("event.model.index") int col) {
        Cell cell = getModelCell(row, col);
        if (cell.isMarked()) {
            // Unmark cell on click
            cell.setMarked(false);
            return;
        }
        if (mineFieldData.isMine(row, col)) {
            boom();
            revealAll();
        } else {
            reveal(row, col);
            if (isAllRevealed()) {
                success();
            }
        }
    }

    @EventHandler
    private void handleRightClick(
            @EventData("event.model.parentModel.index") int row,
            @EventData("event.model.index") int col) {
        Cell cell = getModelCell(row, col);
        cell.setMarked(!cell.isMarked());
    }

    private void initModel() {
        List<List<Cell>> cells = new ArrayList<>();
        for (int row = 0; row < mineFieldData.getRows(); row++) {
            List<Cell> rowCells = new ArrayList<>();
            for (int col = 0; col < mineFieldData.getCols(); col++) {
                rowCells.add(new Cell());
            }
            cells.add(rowCells);
        }

        getModel().setCells(cells);
    }

    private Cell getModelCell(int row, int col) {
        return getModel().getCells().get(row).get(col);
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
     * @param row
     *            the row of the cell to reveal
     * @param col
     *            the column of the cell to reveal
     */
    public void reveal(int row, int col) {
        Cell cell = getModelCell(row, col);
        if (cell.isRevealed()) {
            // Already revealed
            return;
        }

        boolean mine = mineFieldData.isMine(row, col);
        cell.setRevealed(true);
        if (!mine) {
            int count = mineFieldData.getNearbyCount(row, col);
            if (count > 0) {
                cell.setText(Integer.toString(count));
            } else {
                // Autoreveal
                for (Point p : mineFieldData.getNearbyPoints(row, col)) {
                    reveal(p.getRow(), p.getCol());
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
                if (!getModelCell(row, col).isRevealed()
                        && !mineFieldData.isMine(row, col)) {
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
        for (int row = 0; row < mineFieldData.getRows(); row++) {
            for (int column = 0; column < mineFieldData.getCols(); column++) {
                reveal(row, column);
            }
        }
    }
}
