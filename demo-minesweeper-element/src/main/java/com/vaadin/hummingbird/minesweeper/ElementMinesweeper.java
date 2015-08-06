package com.vaadin.hummingbird.minesweeper;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.namespace.ElementPropertiesNamespace;

public class ElementMinesweeper {

    private int rows = 10;
    private int cols = 10;
    private Element table = new Element("table");
    private Minefield minefield = new Minefield();

    public ElementMinesweeper(long seed, double mineDensity) {
        minefield.init(rows, cols, seed, mineDensity);
    }

    void initDOM() {
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            Element tr = new Element("tr");
            table.appendChild(tr);
            for (int colIndex = 0; colIndex < cols; colIndex++) {
                Element td = new Element("td");

                final int thisRow = rowIndex;
                final int thisCol = colIndex;

                td.addEventListener("click", () -> {
                    if (minefield.isMine(thisRow, thisCol)) {
                        boom();
                        revealAll();
                    } else {
                        reveal(thisRow, thisCol);
                    }
                });
                tr.appendChild(td);
            }
        }

    }

    private void boom() {
        Element div = new Element("div").setAttribute("class", "boom");
        div.getNode().getNamespace(ElementPropertiesNamespace.class)
                .setProperty("textContent", "BOOM! Reload to try again");
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
        if (td.hasAttribute("class")) {
            // Already revealed
            return;
        }
        if (minefield.isMine(row, col)) {
            td.setAttribute("class", "mine");
        } else {
            td.setAttribute("class", "empty");
            int count = minefield.getNearbyCount(row, col);
            if (count > 0) {
                td.getNode().getNamespace(ElementPropertiesNamespace.class)
                        .setProperty("textContent", count + "");
            } else {
                // Autoreveal
                for (Point p : minefield.getNearbyPoints(row, col)) {
                    reveal(p.getRow(), p.getCol());
                }
            }
        }
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
