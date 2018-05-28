package uk.co.hatless_studios.minesweeper;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Deque;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Solution implements KeyListener {
    private JFrame frame;
    private Predicate<Consumer<Deque<Cell>>> step;
    private JLabel[][] labels;
    private Minesweeper puzzle;
    private boolean done;

    Solution(Minesweeper puzzle){
        this.puzzle = puzzle;
        frame = new JFrame("Minesweeper Solver");
        JPanel rootPanel = new JPanel(new GridBagLayout());
        JLabel solutionLabel = new JLabel("Solution");
        GridBagConstraints constraints = new GridBagConstraints(0, 0, puzzle.width, 1, 1D, 1D, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 10, 10);
        rootPanel.add(solutionLabel, constraints);
        constraints.gridwidth = 1;
        constraints.gridy++;
        labels = new JLabel[puzzle.width][puzzle.height];
        for (int i = 0; i < puzzle.width; i++){
            for (int j = 0; j < puzzle.height; j++){
                JLabel label = new JLabel(puzzle.cells[i][j].toString());
                label.setOpaque(true);
                if (puzzle.cells[i][j].flagged) label.setBackground(Color.YELLOW);
                if (puzzle.cells[i][j].revealed)
                    label.setBackground(puzzle.cells[i][j].number >= 0 ? Color.GREEN : Color.RED);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                labels[i][j] = label;
                rootPanel.add(label, constraints);
                constraints.gridy++;
            }
            constraints.gridx++;
            constraints.gridy = 1;
        }
        frame.add(rootPanel);

        solutionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    Solution(Minesweeper puzzle, Predicate<Consumer<Deque<Cell>>> step) {
        this(puzzle);
        this.step = step;
        frame.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !done) {
            for (int i = 0; i < puzzle.width; i++) for (int j = 0; j < puzzle.height; j++) labels[i][j].setForeground(Color.BLACK);
            try {
                done = step.test(s -> s.forEach(c -> labels[c.x][c.y].setForeground(Color.BLUE)));
            } catch (UnsupportedOperationException ex) {
                done = true;
            }
            for (int i = 0; i < puzzle.width; i++) {
                for (int j = 0; j < puzzle.height; j++) {
                    if (puzzle.cells[i][j].flagged) labels[i][j].setBackground(Color.YELLOW);
                    if (puzzle.cells[i][j].revealed)
                        labels[i][j].setBackground(puzzle.cells[i][j].number >= 0 ? Color.GREEN : Color.RED);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
