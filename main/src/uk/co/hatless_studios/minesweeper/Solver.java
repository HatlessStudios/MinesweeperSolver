package uk.co.hatless_studios.minesweeper;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Solver {
    private static final Random RAND = new Random();

    /**
     * Checks cells adjacent to the given and cell and flags if a mine is guaranteed or checks if no mine is guaranteed.
     *
     * @param cell: the current cell
     */
    private static void checkAdjacentCells(Cell cell, Deque<Cell> stack) {
        AtomicInteger unrevealedCounter = new AtomicInteger(0);
        AtomicInteger flaggedCounter = new AtomicInteger(0);

        cell.forEachNeighbour(c -> {
            if (!c.revealed) unrevealedCounter.incrementAndGet();
            else if (c.flagged) flaggedCounter.incrementAndGet();
        });

        if (flaggedCounter.get() == cell.number){
            cell.forEachNeighbour(c -> {
                if (!c.flagged && !c.revealed) c.minesweeper.reveal(c.x, c.y, stack);
            });
        } else if (unrevealedCounter.get() == cell.number){
            cell.forEachNeighbour(c -> {
                if (!c.revealed && !c.flagged) c.minesweeper.flag(c.x, c.y, stack);
            });
        }

    }

    /**
     * Checks a random cell and selects it.
     *
     * @param puzzle: the Minesweeper grid to work on
     */
    private static void randomChoice(Minesweeper puzzle, Deque<Cell> stack) {
        int x = RAND.nextInt(puzzle.width);
        int y = RAND.nextInt(puzzle.height);

        puzzle.reveal(x, y, stack);
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
            while (!stack.isEmpty()) {
                checkAdjacentCells(stack.removeLast(), stack);
            }
        }
    }
}
