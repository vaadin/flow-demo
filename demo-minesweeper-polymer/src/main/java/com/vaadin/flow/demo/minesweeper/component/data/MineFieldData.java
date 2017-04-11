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
package com.vaadin.flow.demo.minesweeper.component.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * A representation of a mine field with {@code rows}x{@code cols} cells, where
 * each cell is either empty or contains a mine.
 */
public class MineFieldData implements Serializable {

    private final Set<Point> mines;
    private final int rows;
    private final int cols;
    private final int numberOfMines;
    private final Map<Point, Boolean> revealed = new HashMap<>();
    private final Map<Point, String> texts = new HashMap<>();
    private final Map<Point, Boolean> marked = new HashMap<>();

    /**
     * Initializes the mine field and randomly places mines.
     * <p>
     * The random seed is used for placing the mines, making it possible to
     * reproduce the same mine layout by passing the same seed and also
     * otherwise the same parameters.
     *
     * @param rows
     *            the number rows in the field
     * @param cols
     *            the number of columns in the field
     * @param seed
     *            a random seed used for placing mines
     * @param mineDensity
     *            a number between 0 and 1, describing the approximate fraction
     *            of the cells which should contain a mine
     */
    public MineFieldData(int rows, int cols, long seed, double mineDensity) {
        Random random = new Random(seed);
        this.rows = rows;
        this.cols = cols;
        int minesCount = 0;

        mines = new HashSet<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < cols; column++) {
                if (random.nextDouble() > (1 - mineDensity)) {
                    mines.add(new Point(row, column));
                    minesCount++;
                }
            }
        }
        numberOfMines = minesCount;
    }

    /**
     * Retrieves a set of coordinates of the cells surrounding the given cell.
     *
     * @param row
     *            the row of the cell to lookup
     * @param col
     *            the column of the cell to lookup
     * @return a set of points referring to the neighboring cells
     */
    public Set<Point> getNearbyPoints(int row, int col) {
        int rowStart = Math.max(0, row - 1);
        int rowEnd = Math.min(rows - 1, row + 1);
        int colStart = Math.max(0, col - 1);
        int colEnd = Math.min(cols - 1, col + 1);

        Set<Point> points = new HashSet<>();
        for (int r = rowStart; r <= rowEnd; r++) {
            for (int c = colStart; c <= colEnd; c++) {
                if (r == row && c == col) {
                    continue;
                }

                points.add(new Point(r, c));
            }
        }

        return points;

    }

    /**
     * Gets the total number of mines in the neighboring cells.
     *
     * @param row
     *            the row of the cell to lookup
     * @param col
     *            the column of the cell to lookup
     * @return the sum of mines in nearby cells
     */
    public int getNearbyCount(int row, int col) {
        if (isMine(row, col)) {
            return -1;
        }

        int count = 0;
        for (Point p : getNearbyPoints(row, col)) {
            if (isMine(p)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the number of rows in the mine field.
     *
     * @return the number of rows in the mine field
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the number of columns in the mine field.
     *
     * @return the number of columns in the mine field
     */
    public int getCols() {
        return cols;
    }

    /**
     * Checks whether the given cell contains a mine.
     *
     * @param row
     *            the row of the cell to lookup
     * @param col
     *            the column of the cell to lookup
     * @return true if the cell contains a mine, false otherwise
     */
    public boolean isMine(int row, int col) {
        return isMine(new Point(row, col));
    }

    /**
     * Checks whether the given cell contains a mine.
     *
     * @param point
     *            the row and column of the cell to lookup
     * @return true if the cell contains a mine, false otherwise
     */
    private boolean isMine(Point point) {
        return mines.contains(point);
    }

    /**
     * Gets the number of mines in total in the mine field.
     *
     * @return the number of mines in the mine field
     */
    public int getNumberOfMines() {
        return numberOfMines;
    }

    /**
     * Gets whether the cell is revealed.
     * 
     * @param row
     *            the row of the cell
     * @param col
     *            the column of the cell
     * @return whether the cell is revealed
     */
    public boolean isRevealed(int row, int col) {
        return revealed.getOrDefault(new Point(row, col), Boolean.FALSE);
    }

    /**
     * Sets the cell revealed.
     * 
     * @param row
     *            the row of the cell
     * @param col
     *            the column of the cell
     */
    public void setRevealed(int row, int col) {
        revealed.put(new Point(row, col), Boolean.TRUE);
    }

    /**
     * Gets whether the cell is marked.
     * 
     * @param row
     *            the row of the cell
     * @param col
     *            the column of the cell
     * @return whether the cell is revealed
     */
    public boolean isMarked(int row, int col) {
        return marked.getOrDefault(new Point(row, col), Boolean.FALSE);
    }

    /**
     * Sets the cell marked.
     * 
     * @param row
     *            the row of the cell
     * @param col
     *            the column of the cell
     */
    public void setMarked(int row, int col) {
        marked.put(new Point(row, col), Boolean.TRUE);
    }

    /**
     * Removes cell marking.
     *
     * @param row
     *            the row of the cell
     * @param col
     *            the column of the cell
     */
    public void removeMarked(int row, int col) {
        marked.put(new Point(row, col), Boolean.FALSE);
    }
    /**
     * Sets the text for the cell.
     * 
     * @param row
     *            the row of the cell
     * @param col
     *            the column of the cell
     * @param text
     *            the text to set for the cell
     */
    public void setText(int row, int col, String text) {
        texts.put(new Point(row, col), text);
    }

    /**
     * Gets the text for the cell.
     * 
     * @param row
     *            the row of the cell
     * @param col
     *            the column of the cell
     * @return the text for the cell
     */
    public String getText(int row, int col) {
        return texts.get(new Point(row, col));
    }
}
