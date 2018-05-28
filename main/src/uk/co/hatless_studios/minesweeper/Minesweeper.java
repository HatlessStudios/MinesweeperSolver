package uk.co.hatless_studios.minesweeper;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class Minesweeper {
    private Cell[][] cells;

    public Minesweeper(int width, int height, int mines) {
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
        Random random = new Random();
        for (int index = 0; index < mines; index++) {
            int x, y;
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (cells[x][y].number != -1);
            cells[x][y].number = -9;
            incrementNeighbours(x, y, width - 1, height - 1);
        }
    }

    boolean reveal(int x, int y, Deque<Cell> updateQueue) {
        if (x < 0 || y < 0 || x >= cells.length || y >= cells[x].length || cells[x][y].number < 0 || cells[x][y].revealed) return false;
        if (cells[x][y].number > 0) {
            cells[x][y].revealed = true;
            updateQueue.add(cells[x][y]);
            return true;
        }
        int w = cells.length - 1, h = cells[x].length - 1;
        Deque<Cell> stack = new ArrayDeque<>();
        stack.add(cells[x][y]);
        while (!stack.isEmpty()) {
            Cell cell = stack.removeLast();
            x = cell.x;
            y = cell.y;
            cell.revealed = true;
            updateQueue.add(cell);
            if (cell.number == 0) {
                if (x > 0) stack.add(cells[x - 1][y]);
                if (y > 0) stack.add(cells[x][y - 1]);
                if (x < w) stack.add(cells[x + 1][y]);
                if (y < h) stack.add(cells[x][y + 1]);
                if (x > 0 && y > 0) stack.add(cells[x - 1][y - 1]);
                if (x < w && y > 0) stack.add(cells[x + 1][y - 1]);
                if (x > 0 && y < h) stack.add(cells[x - 1][y + 1]);
                if (x < w && y < h) stack.add(cells[x + 1][y + 1]);
            }
        }
        return true;
    }

    boolean flag(int x, int y) {
        if (x < 0 || y < 0 || x >= cells.length || y >= cells[x].length || cells[x][y].flagged) return false;
        cells[x][y].flagged = true;
        return true;
    }

    private void incrementNeighbours(int x, int y, int w, int h) {
        if (x > 0) cells[x - 1][y].increment();
        if (y > 0) cells[x][y - 1].increment();
        if (x < w) cells[x + 1][y].increment();
        if (y < h) cells[x][y + 1].increment();
        if (x > 0 && y > 0) cells[x - 1][y - 1].increment();
        if (x < w && y > 0) cells[x + 1][y - 1].increment();
        if (x > 0 && y < h) cells[x - 1][y + 1].increment();
        if (x < w && y < h) cells[x + 1][y + 1].increment();
    }
}
