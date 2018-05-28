package uk.co.hatless_studios.minesweeper;

import java.util.stream.Stream;

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

    Stream<Cell> neighbours() {
        Stream.Builder<Cell> builder = Stream.builder();
        int w = minesweeper.width - 1;
        int h = minesweeper.height - 1;
        if (x > 0) builder.add(minesweeper.cells[x - 1][y]);
        if (y > 0) builder.add(minesweeper.cells[x][y - 1]);
        if (x < w) builder.add(minesweeper.cells[x + 1][y]);
        if (y < h) builder.add(minesweeper.cells[x][y + 1]);
        if (x > 0 && y > 0) builder.add(minesweeper.cells[x - 1][y - 1]);
        if (x < w && y > 0) builder.add(minesweeper.cells[x + 1][y - 1]);
        if (x > 0 && y < h) builder.add(minesweeper.cells[x - 1][y + 1]);
        if (x < w && y < h) builder.add(minesweeper.cells[x + 1][y + 1]);
        return builder.build();
    }

    @Override
    public String toString() {
        if (number < 0) return "M";
        return Integer.toString(number);
    }
}
