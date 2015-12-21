package com.vaadin.hummingbird.minesweeper.secure;

import java.util.List;

import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.hummingbird.kernel.StateNode;
import com.vaadin.hummingbird.minesweeper.Point;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Template;

public class SecureMinesweeper extends Template {

    private Minefield minefield = new Minefield();
    private double mineDensity;
    private long seed;

    public SecureMinesweeper(long seed, double mineDensity) {
        this.mineDensity = mineDensity;
        this.seed = seed;
    }

    @Override
    protected void init() {
        super.init();

        minefield.init(10, 10, seed, mineDensity);

        getNode().getMultiValued("rows");
        List<Row> data = getModel().getRows();

        for (int rowIndex = 0; rowIndex < minefield.getRows(); rowIndex++) {
            Row row = Model.create(Row.class);
            data.add(row);
            ((StateNode) getNode().getMultiValued("rows").get(rowIndex))
                    .getMultiValued("cells");
            List<Cell> cells = row.getCells();
            for (int colIndex = 0; colIndex < minefield.getCols(); colIndex++) {
                Cell cell = Model.create(Cell.class);
                cells.add(cell);
            }
        }
    }

    public interface Cell {
        public boolean isRevealed();

        public void setRevealed(boolean revealed);

        public Integer getNearby();

        public void setNearby(Integer nearby);

        public boolean isMine();

        public void setMine(boolean mine);

    }

    public interface Row {
        public List<Cell> getCells();

        public void setCells(List<Cell> cells);
    }

    public interface MinesweeperModel extends com.vaadin.ui.Template.Model {
        public List<Row> getRows();

        public void setRows(List<Row> rows);
    }

    @Override
    protected MinesweeperModel getModel() {
        return (MinesweeperModel) super.getModel();
    }

    @TemplateEventHandler
    public void cellClick(Element td) {
        Element tr = td.getParent();
        Element table = tr.getParent();

        // First is a text node...
        int column = tr.getChildIndex(td) - 1;
        int row = table.getChildIndex(tr);

        if (minefield.isMine(row, column)) {
            // Clicked on a mine
            getCell(row, column).setMine(true);
            getCell(row, column).setRevealed(true);
            revealAll();
            Notification.show("BOOM");
        } else {
            reveal(row, column);
        }

    }

    private void revealAll() {
        for (int r = 0; r < minefield.getRows(); r++) {
            for (int c = 0; c < minefield.getCols(); c++) {
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

        if (minefield.isMine(row, col)) {
            cell.setMine(true);
        }

        int nearby = minefield.getNearbyCount(row, col);
        if (nearby > 0) {
            cell.setNearby(nearby);
        } else {
            // Autoreveal
            for (Point p : minefield.getNearbyPoints(row, col)) {
                reveal(p.getRow(), p.getCol());
            }
        }
    }

    private Cell getCell(int row, int column) {
        return getModel().getRows().get(row).getCells().get(column);
    }

}
