package com.vaadin.hummingbird.minesweeper.secure;

import java.util.List;

import com.vaadin.annotations.JS;
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

	private boolean revealed = false;

    public SecureMinesweeper(long seed, double mineDensity) {
        this.mineDensity = mineDensity;
        this.seed = seed;
    }

    @Override
    protected void init() {
        super.init();

        minefield.init(10, 10, seed, mineDensity);
        getModel().setNumberOfMines(minefield.getNumberOfMines());
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

        public boolean isMarked();

        public void setMarked(boolean marked);

    }

    public interface Row {
        public List<Cell> getCells();

        public void setCells(List<Cell> cells);
    }

    public interface MinesweeperModel extends com.vaadin.ui.Template.Model {
        public List<Row> getRows();

        public void setRows(List<Row> rows);

        public int getNumberOfMines();

        public void setNumberOfMines(int numberOfMines);

        // FIXME WTF OMG LOL BBQ
        @JS("(function() {" + "var unmarked = numberOfMines; "
                + "for (var r=0; r < rows.length; r++) {"
                + "for (var c=0; c < rows[r].cells.length; c++) {"
                + "if (rows[r].cells[c].marked) unmarked--;" + "}" + "}"
                + "return unmarked;" + "})();")
        public int getNumberOfUnmarkedMines();
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
			boom(true);
        } else {
            reveal(row, column);
            if (allNonMineCellsRevealed()) {
				boom(false);
            }
        }

    }

	private void boom(boolean fail) {
		if (revealed) {
			return;
		}

		revealAll();
		if (fail) {
			Notification.show("BOOM");
		} else {
			Notification.show("Success!");
		}
	}

    private void revealAll() {
		revealed = true;
        for (int r = 0; r < minefield.getRows(); r++) {
            for (int c = 0; c < minefield.getCols(); c++) {
				reveal(r, c, false);
            }
        }
    }

    private void reveal(int row, int col) {
		reveal(row, col, true);
	}

	private void reveal(int row, int col, boolean traverse) {
        Cell cell = getCell(row, col);
		int nearby = minefield.getNearbyCount(row, col);

        if (cell.isRevealed()) {
			// Attempt to reveal all nearby cells:
			// Must be traverse (user click).
			// Cell must not be zero.
			// Number of markers surrounding the cell must be equal to the cell.
			if (traverse && nearby > 0
					&& nearby == getNearbyMarkedCount(row, col)) {

				for (Point p : minefield.getNearbyPoints(row, col)) {
					if (!getCell(p).isMarked()) {
						if (minefield.isMine(p.getRow(), p.getCol())) {
							boom(true);
							return;
						}
						reveal(p.getRow(), p.getCol(), false);
					}
				}
			}
            return;
        }

        cell.setRevealed(true);

        if (minefield.isMine(row, col)) {
            cell.setMine(true);
        }

        if (nearby > 0) {
            cell.setNearby(nearby);
        } else {
            // Autoreveal
            for (Point p : minefield.getNearbyPoints(row, col)) {
				reveal(p.getRow(), p.getCol(), false);
            }
        }
    }

	private Cell getCell(Point p) {
		return getCell(p.getRow(), p.getCol());
	}

    private Cell getCell(int row, int column) {
        return getModel().getRows().get(row).getCells().get(column);
    }

	public int getNearbyMarkedCount(int row, int col) {
		int count = 0;
		for (Point p : minefield.getNearbyPoints(row, col)) {
			if (getCell(p).isMarked()) {
				count++;
			}
		}
		return count;
	}

    private boolean allNonMineCellsRevealed() {
        for (int r = 0; r < minefield.getRows(); r++) {
            for (int c = 0; c < minefield.getCols(); c++) {
                Cell cell = getCell(r, c);
                if (!cell.isRevealed() && !minefield.isMine(r, c)) {
                    // System.out.println(r + "," + c + " still not revealed");
                    return false;
                }
            }
        }
        return true;
    }

}
