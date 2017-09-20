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

import com.vaadin.shared.Registration;
import com.vaadin.ui.Component;
import com.vaadin.ui.event.ComponentEvent;
import com.vaadin.ui.event.ComponentEventListener;
import com.vaadin.ui.event.DomEvent;
import com.vaadin.ui.event.Tag;

/**
 * A component representing a cell.
 *
 * @author Vaadin Ltd
 * @since
 */
@Tag("td")
public class Cell extends Component {

    private static final String CLASS_MARKED = "marked";
    private static final String CLASS_EMPTY = "empty";
    private static final String CLASS_MINE = "mine";

    /**
     * Event triggered when a cell is clicked.
     *
     * @author Vaadin Ltd
     * @since
     */
    @DomEvent("click")
    public static class CellClickEvent extends ComponentEvent<Cell> {

        /**
         * Creates a new event using the given source and client information.
         *
         * @param source
         *            the cell which was clicked
         * @param fromClient
         *            <code>true</code> if the event originated from the client
         *            side, <code>false</code> otherwise
         */
        public CellClickEvent(Cell source, boolean fromClient) {
            super(source, fromClient);
        }

    }

    /**
     * Creates a new cell.
     */
    public Cell() {
        getElement().addEventListener("contextmenu", e -> setMarked(true),
                "event.preventDefault()");
    }

    /**
     * Sets the cell as marked or unmarked.
     * <p>
     * A marked cell cannot be clicked. A revealed cell cannot be marked.
     *
     * @param marked
     *            <code>true</code> to set the cell as marked,
     *            <code>false</code> to set it as unmarked
     */
    public void setMarked(boolean marked) {
        if (isRevealed()) {
            return;
        }

        getElement().getClassList().set(CLASS_MARKED, marked);
    }

    /**
     * Checks whether the cell is marked.
     * <p>
     * A marked cell cannot be clicked.
     *
     * @return <code>true</code> if the cell is marked, <code>false</code>
     *         otherwise
     */
    public boolean isMarked() {
        return hasClass(CLASS_MARKED);
    }

    /**
     * Adds a click listener for the cell.
     *
     * @param listener
     *            the listener to add
     * @return an handle which can be used to remove the listener
     */
    public Registration addCellClickListener(
            ComponentEventListener<CellClickEvent> listener) {
        return addListener(CellClickEvent.class, listener);
    }

    /**
     * Gets the column index for this cell.
     *
     * @return the column index for the cell
     */
    public int getCol() {
        return getElement().getParent().indexOfChild(getElement());
    }

    /**
     * Gets the row index for this cell.
     *
     * @return the row index for the cell
     */
    public int getRow() {
        return ((Row) getParent().get()).getRow();
    }

    private boolean hasClass(String className) {
        return getElement().getClassList().contains(className);
    }

    /**
     * Reveals the cell as empty or containing a mine.
     *
     * @param mine
     *            <code>true</code> if the cell contains a mine,
     *            <code>false</code> if the cell is empty
     */
    public void reveal(boolean mine) {
        if (mine) {
            getElement().getClassList().add(CLASS_MINE);
        } else {
            getElement().getClassList().add(CLASS_EMPTY);
        }
    }

    /**
     * Checks whether the cell has been revealed.
     *
     * @return <code>true</code> if the cell has been revealed,
     *         <code>false</code> otherwise
     */
    public boolean isRevealed() {
        return isMine() || isEmpty();
    }

    /**
     * Checks whether the cell has been revealed and contains a mine.
     *
     * @return <code>true</code> if the cell has been revealed and contains a
     *         mine, <code>false</code> otherwise
     */
    public boolean isMine() {
        return hasClass(CLASS_MINE);
    }

    /**
     * Checks whether the cell has been revealed and is empty.
     *
     * @return <code>true</code> if the cell has been revealed and is empty,
     *         <code>false</code> otherwise
     */
    public boolean isEmpty() {
        return hasClass(CLASS_EMPTY);
    }

    /**
     * Sets the text content of the cell.
     *
     * @param text
     *            the text content to set
     */
    public void setText(String text) {
        getElement().setText(text);
    }

}
