package uk.co.hatless_studios.minesweeper;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class Minesweeper {
    Cell[][] cells;
    int width, height;

    public Minesweeper(int width, int height, int mines) {
        cells = new Cell[width][height];
        this.width = width;
        this.height = height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y);
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
            cells[x][y].forEachNeighbour(Cell::increment);
        }
    }

    boolean reveal(int x, int y, Deque<Cell> updateQueue) {
        if (x < 0 || y < 0 || x >= width || y >= height || cells[x][y].number < 0 || cells[x][y].revealed) return false;
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
            if (cell.number == 0) cell.forEachNeighbour(stack::add);
        }
        return true;
    }

    boolean flag(int x, int y, Deque<Cell> updateQueue) {
        if (x < 0 || y < 0 || x >= width || y >= height || cells[x][y].flagged) return false;
        cells[x][y].flagged = true;
        updateQueue.add(cells[x][y]);
        return true;
    }

    boolean isSolved() {
        for (Cell[] row : cells) for (Cell cell : row) if (!cell.revealed && cell.number >= 0) return false;
        return true;
    }
}
