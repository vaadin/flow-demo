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

public class ElementMinesweeper {

    private Element table = new Element("table");
    private Minefield minefield = new Minefield();

    public ElementMinesweeper(long seed, double mineDensity, int rows,
            int cols) {
        minefield.init(rows, cols, seed, mineDensity);
        initDOM();
    }

    private void initDOM() {
        for (int rowIndex = 0; rowIndex < minefield.getRows(); rowIndex++) {
            Element tr = new Element("tr");
            table.appendChild(tr);
            for (int colIndex = 0; colIndex < minefield.getCols(); colIndex++) {
                Element td = new Element("td");

                final int thisRow = rowIndex;
                final int thisCol = colIndex;

                td.addEventListener("click", e -> cellClick(thisRow, thisCol));
                tr.appendChild(td);
            }
        }
    }

    private void cellClick(int row, int col) {
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

    private void boom() {
        showMessage("BOOM! Reload to try again", "boom");
    }

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
            td.getClassList().add("mine");
        } else {
            td.getClassList().add("empty");
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

    private static boolean isRevealed(Element td) {
        Set<String> classList = td.getClassList();
        return classList.contains("mine") || classList.contains("empty");
    }

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

    private void revealAll() {
        for (int r = 0; r < minefield.getRows(); r++) {
            for (int c = 0; c < minefield.getCols(); c++) {
                reveal(r, c);
            }
        }
    }

    private Element getTd(int row, int col) {
        return table.getChild(row).getChild(col);
    }

    public Element getElement() {
        return table;
    }

}
