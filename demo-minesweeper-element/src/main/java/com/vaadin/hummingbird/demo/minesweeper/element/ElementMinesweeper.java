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
package com.vaadin.hummingbird.demo.minesweeper.element;

import java.util.Set;

import com.vaadin.hummingbird.dom.Element;

/**
 * The Minesweeper UI component with the root element returned by
 * {@link #getElement()}.
 */
public class ElementMinesweeper {

    private static final String CLASS_EMPTY = "empty";
    private static final String CLASS_MINE = "mine";
    private static final String CLASS_MARKED = "marked";
    private Element table = new Element("table");
    private Minefield minefield = new Minefield();

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
    public ElementMinesweeper(long seed, double mineDensity, int rows,
            int cols) {
        minefield.init(rows, cols, seed, mineDensity);
        initDOM();
    }

    /**
     * Initializes the DOM for the component.
     */
    private void initDOM() {
        for (int rowIndex = 0; rowIndex < minefield.getRows(); rowIndex++) {
            Element tr = new Element("tr");
            table.appendChild(tr);
            for (int colIndex = 0; colIndex < minefield.getCols(); colIndex++) {
                Element td = new Element("td");

                final int thisRow = rowIndex;
                final int thisCol = colIndex;

                // Left click reveals cells
                td.addEventListener("click", e -> cellClick(thisRow, thisCol));

                // Right-click/ctrl-click marks a mine
                // Here we abuse the event details feature which runs javascript
                // as part of the event handler, to prevent the default
                // behavior, i.e. showing the browser context menu
                td.addEventListener("contextmenu",
                        e -> markMine(thisRow, thisCol),
                        "event.preventDefault()");
                tr.appendChild(td);
            }
        }
    }

    /**
     * Handler called when the user clicks on a cell.
     *
     * @param row
     *            the row which was clicked
     * @param col
     *            the column which was clicked
     */
    private void cellClick(int row, int col) {
        if (isMarked(getTd(row, col))) {
            return;
        }
        if (minefield.isMine(row, col)) {
            boom();
            revealAll();
        } else {
            reveal(row, col);
            if (isAllRevealed()) {
                success();
            }
        }
    }

    /**
     * Mark the cell as a mine, preventing accidental clicks on it.
     *
     * @param row
     *            the row to mark
     * @param col
     *            the column to mark
     */
    private void markMine(int row, int col) {
        Element cell = getTd(row, col);
        if (isRevealed(cell)) {
            return;
        }

        Set<String> classList = cell.getClassList();
        if (isMarked(cell)) {
            classList.remove(CLASS_MARKED);
        } else {
            classList.add(CLASS_MARKED);
        }
    }

    /**
     * Shows a message indicating mine explosion and failure.
     */
    private void boom() {
        showMessage("BOOM! Reload to try again", "boom");
    }

    /**
     * Shows a message indicating the minefield was successfully cleared.
     */
    private void success() {
        showMessage("Congratulations!", "success");
    }

    private void showMessage(String message, String className) {
        Element div = new Element("div").setTextContent(message);
        div.getClassList().add(className);
        getElement().getParent().appendChild(div);
    }

    /**
     * Reveal the cell at the given coordinates
     *
     * @param row
     *            the row
     * @param col
     *            the column
     */
    public void reveal(int row, int col) {
        Element td = getTd(row, col);
        if (isRevealed(td)) {
            // Already revealed
            return;
        }
        if (minefield.isMine(row, col)) {
            td.getClassList().add(CLASS_MINE);
        } else {
            td.getClassList().add(CLASS_EMPTY);
            int count = minefield.getNearbyCount(row, col);
            if (count > 0) {
                td.setTextContent(Integer.toString(count));
            } else {
                // Autoreveal
                for (Point p : minefield.getNearbyPoints(row, col)) {
                    reveal(p.getRow(), p.getCol());
                }
            }
        }
    }

    /**
     * Checks if the cell represented by the given element has been marked as a
     * mine.
     *
     * @param td
     *            the cell element
     * @return true if the cell has been marked, false otherwise
     */
    private static boolean isMarked(Element td) {
        return td.getClassList().contains(CLASS_MARKED);
    }

    /**
     * Checks if the cell represented by the given element has been revealed.
     *
     * @param td
     *            the cell element
     * @return true if the cell has been revealed, false otherwise
     */
    private static boolean isRevealed(Element td) {
        Set<String> classList = td.getClassList();
        return classList.contains(CLASS_MINE)
                || classList.contains(CLASS_EMPTY);
    }

    /**
     * Checks if all empty cells have been revealed.
     *
     * @return true if all cells have been revealed, false otherwise.
     */
    private boolean isAllRevealed() {
        for (int row = 0; row < minefield.getRows(); row++) {
            for (int col = 0; col < minefield.getCols(); col++) {
                if (!isRevealed(getTd(row, col))
                        && !minefield.isMine(row, col)) {
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
        for (int r = 0; r < minefield.getRows(); r++) {
            for (int c = 0; c < minefield.getCols(); c++) {
                reveal(r, c);
            }
        }
    }

    /**
     * Gets the element representing the given cell.
     *
     * @param row
     *            the row coordinate
     * @param col
     *            the column coordinate
     * @return the TD element representing the cell
     */
    private Element getTd(int row, int col) {
        return table.getChild(row).getChild(col);
    }

    /**
     * Gets the root element for this component.
     *
     * @return the root element
     */
    public Element getElement() {
        return table;
    }

}
