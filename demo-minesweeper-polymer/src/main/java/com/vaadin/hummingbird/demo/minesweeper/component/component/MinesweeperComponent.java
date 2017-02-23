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
package com.vaadin.hummingbird.demo.minesweeper.component.component;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.demo.minesweeper.component.data.MineFieldData;
import com.vaadin.hummingbird.demo.minesweeper.component.data.Point;
import com.vaadin.hummingbird.html.HtmlComponent;
import com.vaadin.hummingbird.util.JsonUtils;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

/**
 * The Minesweeper main UI component.
 */
@Tag("mine-sweeper")
@HtmlImport("context://minesweeper.html")
public class MinesweeperComponent extends HtmlComponent {

    private static final String EVENT_DETAIL = "event.detail";
    private final MineFieldData mineFieldData;

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
        init();
    }

    /**
     * Initializes the component hierarchy.
     */
    private void init() {
        setModel();

        getElement().addEventListener("clicked",
                e -> cellClick(e.getEventData()), EVENT_DETAIL);

        getElement().addEventListener("marked", e -> marked(e.getEventData()),
                EVENT_DETAIL);
    }

    private void setModel() {
        JsonArray[] rows = new JsonArray[mineFieldData.getRows()];
        for (int rowIndex = 0; rowIndex < mineFieldData.getRows(); rowIndex++) {
            JsonValue[] values = new JsonValue[mineFieldData.getCols()];
            for (int colIndex = 0; colIndex < mineFieldData
                    .getCols(); colIndex++) {
                JsonObject object = Json.createObject();
                object.put("style", computeStyle(rowIndex, colIndex));
                String text = mineFieldData.getText(rowIndex, colIndex);
                if (text != null) {
                    object.put("text", text);
                }
                object.put("row", rowIndex);
                object.put("col", colIndex);
                values[colIndex] = object;
            }
            rows[rowIndex] = JsonUtils.createArray(values);
        }

        getElement().setPropertyJson("model", JsonUtils.createArray(rows));
    }

    private String computeStyle(int row, int col) {
        StringBuilder builder = new StringBuilder();
        if (mineFieldData.isRevealed(row, col)) {
            if (mineFieldData.isMine(row, col)) {
                builder.append("mine");
            } else {
                builder.append("empty");
            }
            builder.append(' ');
        }
        if (mineFieldData.isMarked(row, col)) {
            builder.append("marked");
        }

        return builder.toString();
    }

    private void cellClick(JsonObject object) {
        JsonObject json = object.get(EVENT_DETAIL);
        int row = (int) json.getNumber("row");
        int col = (int) json.getNumber("col");
        if (mineFieldData.isMarked(row, col)) {
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
        setModel();
    }

    private void marked(JsonObject object) {
        JsonObject json = object.get(EVENT_DETAIL);
        int row = (int) json.getNumber("row");
        int col = (int) json.getNumber("col");
        mineFieldData.setMarked(row, col);
        setModel();
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
        if (mineFieldData.isRevealed(row, col)) {
            // Already revealed
            return;
        }

        boolean mine = mineFieldData.isMine(row, col);
        mineFieldData.setRevealed(row, col);
        if (!mine) {
            int count = mineFieldData.getNearbyCount(row, col);
            if (count > 0) {
                mineFieldData.setText(row, col, Integer.toString(count));
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
                if (!mineFieldData.isRevealed(row, col)
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
