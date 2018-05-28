package uk.co.hatless_studios.minesweeper;

class Cell {
    int x, y;
    /**
     * Negative is a mine, positive or zero is the number of neighbours that are mines.
     */
    int number;
    boolean flagged;
    boolean revealed;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
        flagged = false;
        revealed = false;
        number = 0;
    }

    void increment() {
        if (number >= 0) number++;
    }
}
