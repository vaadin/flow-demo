/*
 * Copyright 2000-2016 Vaadin Ltd.
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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * A representation of a mine field with {@code rows}x{@code cols} cells, where
 * each cell is either empty or contains a mine.
 */
public class MineFieldData implements Serializable {

    private Set<Point> mines;
    private int rows;
    private int cols;
    private int numberOfMines;

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
    public void init(int rows, int cols, long seed, double mineDensity) {
        Random random = new Random(seed);
        this.rows = rows;
        this.cols = cols;
        numberOfMines = 0;

        mines = new HashSet<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (random.nextDouble() > (1 - mineDensity)) {
                    mines.add(new Point(r, c));
                    numberOfMines++;
                }
            }
        }
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

}
