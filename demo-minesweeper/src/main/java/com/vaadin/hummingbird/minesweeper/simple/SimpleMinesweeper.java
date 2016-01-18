package com.vaadin.hummingbird.minesweeper.simple;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.hummingbird.minesweeper.Point;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Template;

public class SimpleMinesweeper extends Template {

    int rows = 10;
    int cols = 10;
    private Random random;
    private double mineDensity;

    public SimpleMinesweeper(long seed, double mineDensity) {
        this.mineDensity = mineDensity;
        random = new Random(seed);
    }

    @Override
    protected void init() {
        super.init();
        List<Row> data = getModel().getRows();

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            Row row = Model.create(Row.class);
            data.add(row);
            List<Cell> cells = row.getCells();
            for (int colIndex = 0; colIndex < cols; colIndex++) {
                Cell cell = Model.create(Cell.class);
                cell.setMine(random.nextDouble() > (1 - mineDensity));
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

        Cell cell = getCell(row, column);

        if (cell.isMine()) {
            // Clicked on a mine
            revealAll();
            Notification.show("BOOM");
        } else {
            reveal(row, column);
            if (allRevealed()) {
                revealAll();
                Notification.show("Success!");
            }
        }

    }

    private boolean allRevealed() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = getCell(r, c);
                if (!cell.isRevealed() && !cell.isMine()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void revealAll() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
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

        int nearby = getNearbyCount(row, col);
        if (nearby > 0) {
            cell.setNearby(nearby);
        } else {
            // Autoreveal
            for (Point p : getNearbyPoints(row, col)) {
                reveal(p.getRow(), p.getCol());
            }
        }
    }

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

    public int getNearbyCount(int row, int col) {
        Cell cell = getCell(row, col);
        if (cell.isMine()) {
            return -1;
        }

        int count = 0;
        for (Point p : getNearbyPoints(row, col)) {
            if (getCell(p.getRow(), p.getCol()).isMine()) {
                count++;
            }
        }
        return count;
    }

    private Cell getCell(int row, int column) {
        return getModel().getRows().get(row).getCells().get(column);

    }

}
