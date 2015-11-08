package com.vaadin.hummingbird.minesweeper.mvc;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.annotations.TemplateHTML;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.hummingbird.minesweeper.mvc.MinesweeperModel.Cell;
import com.vaadin.hummingbird.minesweeper.mvc.MinesweeperModel.Row;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Template;

@TemplateHTML("MinesweeperView.html")
public class MinesweeperController extends Template {

    private Minefield minefield;

    public MinesweeperController() {
    }

    @Override
    protected void init() {
        super.init();
        minefield = new Minefield();
        populateModel(10, 10);
    }

    public void populateModel(int rows, int cols) {
        minefield.init(10, 10);

        getModel().setRows(new ArrayList<>());
        List<Row> data = getModel().getRows();

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            Row row = Model.create(Row.class);
            data.add(row);

            List<Cell> cells = new ArrayList<>();
            row.setCells(cells);
            cells = row.getCells();
            for (int colIndex = 0; colIndex < cols; colIndex++) {
                Cell cell = Model.create(Cell.class);
                cells.add(cell);
            }
        }
    }

    @TemplateEventHandler
    // FIXME, should get row+column+cell as parameters
    public void cellClick(Element td) {
        Element tr = td.getParent();
        Element table = tr.getParent();

        // First is a text node...
        int column = tr.getChildIndex(td) - 1;
        int row = table.getChildIndex(tr);

        cellClick(row, column);
    }

    // @TemplateEventHandler
    private void cellClick(int row, int column) {
        if (getMinefield().isMine(row, column)) {
            // Clicked on a mine
            Cell cell = getCell(row, column);
            cell.setMine(true);
            cell.setRevealed(true);
            revealAll();
            Notification.show("BOOM");
        } else {
            reveal(row, column);
        }
    }

    private Cell getCell(int row, int col) {
        return MinesweeperModel.hackGetCell(getModel(), row, col);
    }

    private Minefield getMinefield() {
        return minefield;
    }

    private int getRows() {
        return getModel().getNumberOfRows();
    }

    private int getCols() {
        return getModel().getNumberOfColumns();
    }

    private void revealAll() {
        for (int r = 0; r < getRows(); r++) {
            for (int c = 0; c < getCols(); c++) {
                reveal(r, c);
            }
        }
    }

    private void reveal(int row, int col) {
        Cell cell = getCell(row, col);
        if (cell.isRevealed()) {
            return;
        }

        cell.setRevealed(true);

        if (getMinefield().isMine(row, col)) {
            cell.setMine(true);
        }

        int nearby = getMinefield().getNearbyCount(row, col);
        if (nearby > 0) {
            cell.setNearby(nearby);
        } else {
            // Autoreveal
            for (Point p : getMinefield().getNearbyPoints(row, col)) {
                reveal(p.getRow(), p.getCol());
            }
        }
    }

    @Override
    protected MinesweeperModel getModel() {
        return (MinesweeperModel) super.getModel();
    }

}
