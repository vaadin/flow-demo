package com.vaadin.hummingbird.minesweeper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Minefield {

    private Set<Point> mines;
    private int rows;
    private int cols;
    private int numberOfMines;

    public void init(int rows, int cols, long seed, double mineDensity) {
        Random random = new Random(seed);
        this.rows = rows;
        this.cols = cols;
        numberOfMines = 0;

        mines = new HashSet<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (random.nextDouble() > (1 - mineDensity)) {
                    mines.add(new Point(r, c));
                    numberOfMines++;
                }
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
        if (isMine(row, col)) {
            return -1;
        }

        int count = 0;
        for (Point p : getNearbyPoints(row, col)) {
            if (isMine(p)) {
                count++;
            }
        }
        return count;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean isMine(int row, int col) {
        return isMine(new Point(row, col));
    }

    public boolean isMine(Point p) {
        return mines.contains(p);
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

}
