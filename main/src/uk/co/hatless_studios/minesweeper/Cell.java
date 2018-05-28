package uk.co.hatless_studios.minesweeper;

import java.util.function.Consumer;

class Cell {
    Minesweeper minesweeper;
    int x, y;
    /**
     * Negative is a mine, positive or zero is the number of neighbours that are mines.
     */
    int number;
    boolean flagged;
    boolean revealed;

    Cell(Minesweeper minesweeper, int x, int y) {
        this.minesweeper = minesweeper;
        this.x = x;
        this.y = y;
        flagged = false;
        revealed = false;
        number = 0;
    }

    void increment() {
        if (number >= 0) number++;
    }

    void forEachNeighbour(Consumer<Cell> consumer) {
        int w = minesweeper.width - 1;
        int h = minesweeper.height - 1;
        if (x > 0) consumer.accept(minesweeper.cells[x - 1][y]);
        if (y > 0) consumer.accept(minesweeper.cells[x][y - 1]);
        if (x < w) consumer.accept(minesweeper.cells[x + 1][y]);
        if (y < h) consumer.accept(minesweeper.cells[x][y + 1]);
        if (x > 0 && y > 0) consumer.accept(minesweeper.cells[x - 1][y - 1]);
        if (x < w && y > 0) consumer.accept(minesweeper.cells[x + 1][y - 1]);
        if (x > 0 && y < h) consumer.accept(minesweeper.cells[x - 1][y + 1]);
        if (x < w && y < h) consumer.accept(minesweeper.cells[x + 1][y + 1]);
    }
}
