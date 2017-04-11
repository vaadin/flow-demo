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
import java.util.Objects;

/**
 * A representation of a position (row and column) in a grid.
 */
public class Point implements Serializable {
    int row, col;

    /**
     * Creates a point with the given position.
     *
     * @param row
     *            the row coordinate
     * @param col
     *            the column coordinate
     */
    public Point(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column.
     *
     * @return the column
     */
    public int getCol() {
        return col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Point other = (Point) obj;
        return col == other.col && row == other.row;
    }

    @Override
    public String toString() {
        return "Point [row=" + row + ", col=" + col + "]";
    }
}
