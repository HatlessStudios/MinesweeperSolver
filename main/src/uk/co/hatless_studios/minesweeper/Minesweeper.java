package uk.co.hatless_studios.minesweeper;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.stream.IntStream;

class Minesweeper {
    private static final Random RANDOM = new Random();
    Cell[][] cells;
    int width, height;

    Minesweeper(int width, int height, int mines) {
        cells = new Cell[width][height];
        this.width = width;
        this.height = height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y);
            }
        }
        IntStream.range(0, width * height).boxed().sorted(this::randomize).limit(mines).forEach(k -> {
            int x = k % width;
            int y = k / width;
            cells[x][y].number = -9;
            cells[x][y].forEachNeighbour(Cell::increment);
        });
    }

    void reveal(int x, int y, Deque<Cell> updateQueue) {
        if (x < 0 || y < 0 || x >= width || y >= height || cells[x][y].number < 0 || cells[x][y].revealed) return;
        if (cells[x][y].number > 0) {
            cells[x][y].revealed = true;
            updateQueue.add(cells[x][y]);
            return;
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
    }

    void flag(int x, int y, Deque<Cell> updateQueue) {
        if (x < 0 || y < 0 || x >= width || y >= height || cells[x][y].flagged) return;
        cells[x][y].flagged = true;
        updateQueue.add(cells[x][y]);
    }

    boolean isSolved() {
        for (Cell[] row : cells) for (Cell cell : row) if (!cell.revealed && cell.number >= 0) return false;
        return true;
    }

    private int randomize(int a, int b) {
        return RANDOM.nextBoolean() ? 1 : -1;
    }
}
