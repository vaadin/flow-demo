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

import com.vaadin.annotations.Tag;
import com.vaadin.ui.Component;

/**
 * A component which represents a row in the minesweeper grid.
 *
 * @author Vaadin Ltd
 * @since
 */
@Tag("tr")
public class Row extends Component {

    /**
     * Creates a new row.
     */
    public Row() {
        // No need to do anything, super constructor creates element
    }

    /**
     * Adds the given cell to the row.
     *
     * @param cell
     *            the cell to add
     */
    public void add(Cell cell) {
        getElement().appendChild(cell.getElement());
    }

    /**
     * Gets the row index for this row.
     *
     * @return the index of the row
     */
    public int getRow() {
        return getElement().getParent().indexOfChild(getElement());
    }
}
