package uk.co.hatless_studios.minesweeper;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Solver {
    private static final Random RAND = new Random();
    private static int debug;

    /**
     * Checks cells adjacent to the given and cell and flags if a mine is guaranteed or checks if no mine is guaranteed.
     *
     * @param cell: the current cell
     */
    private static void checkAdjacentCells(Cell cell, Deque<Cell> stack) {
        if(cell.neighbours().filter(c -> !c.revealed).count() == cell.number) cell.neighbours().filter(c -> !c.revealed && !c.flagged).forEach(c -> c.minesweeper.flag(c.x, c.y, stack));
        if (cell.neighbours().filter(c -> c.flagged).count() == cell.number) cell.neighbours().filter(c -> !c.flagged && !c.revealed).forEach(c -> c.minesweeper.reveal(c.x, c.y, stack));
    }

    /**
     * Checks a random cell and selects it.
     *
     * @param puzzle: the Minesweeper grid to work on
     */
    private static void randomChoice(Minesweeper puzzle, Deque<Cell> stack) {
        System.out.println("Random " + debug++);
        int x = RAND.nextInt(puzzle.width);
        int y = RAND.nextInt(puzzle.height);
        if (!puzzle.cells[x][y].flagged) puzzle.reveal(x, y, stack);
    }

    /**
     * Solve the given minesweeper puzzle.
     *
     * @param puzzle: the Minesweeper grid to work on
     */
    public static void solve(Minesweeper puzzle) {
        Deque<Cell> stack = new ArrayDeque<>();
        while (!puzzle.isSolved()) {
            randomChoice(puzzle, stack);
            while (!stack.isEmpty()) checkAdjacentCells(stack.removeLast(), stack);
        }
    }

    static Predicate<Consumer<Deque<Cell>>> solveSteps(Minesweeper puzzle) {
        Deque<Cell> stack = new ArrayDeque<>();
        return debug -> {
            int currentSize = stack.size();
            while (currentSize == stack.size()) {
                if (stack.isEmpty()) {
                    randomChoice(puzzle, stack);
                } else {
                    checkAdjacentCells(stack.removeLast(), stack);
                    currentSize--;
                }
            }
            debug.accept(stack);
            return puzzle.isSolved();
        };
    }
}
