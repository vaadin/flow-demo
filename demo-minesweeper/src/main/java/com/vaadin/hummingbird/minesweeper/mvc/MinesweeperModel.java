package com.vaadin.hummingbird.minesweeper.mvc;

import java.util.List;

import com.vaadin.ui.Template.Model;

public interface MinesweeperModel extends Model {

    public static Cell hackGetCell(MinesweeperModel model, int row,
            int column) {
        List<Row> rows = model.getRows();
        Row r = rows.get(row);
        List<Cell> cells = r.getCells();
        return cells.get(column);
    }
    // default public Cell getCell(int row, int column) {
    // return getRows().get(row).getCells().get(column);
    // }

    default public int getNumberOfRows() {
        return getRows().size();
    }

    default public int getNumberOfColumns() {
        return getRows().get(0).getCells().size();
    }

    public List<Row> getRows();

    public void setRows(List<Row> rows);

    public interface Row {
        public List<Cell> getCells();

        public void setCells(List<Cell> cells);
    }

    public interface Cell {
        public boolean isRevealed();

        public void setRevealed(boolean revealed);

        public Integer getNearby();

        public void setNearby(Integer nearby);

        public boolean isMine();

        public void setMine(boolean mine);

    }

}
