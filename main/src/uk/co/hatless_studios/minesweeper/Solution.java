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

public class Solution {
    private JFrame frame;

    Solution(Minesweeper puzzle){
        frame = new JFrame("Minesweeper Solver");
        JPanel rootPanel = new JPanel(new GridBagLayout());
        JLabel solutionLabel = new JLabel("Solution");
        GridBagConstraints constraints = new GridBagConstraints(0, 0, puzzle.width, 1, 1D, 1D, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 10, 10);
        rootPanel.add(solutionLabel, constraints);
        constraints.gridwidth = 1;
        constraints.gridy++;
        for (int i = 0; i < puzzle.width; i++){
            for (int j = 0; j < puzzle.height; j++){
                JLabel label = new JLabel(puzzle.cells[i][j].toString());
                label.setOpaque(true);
                if (puzzle.cells[i][j].flagged) label.setBackground(Color.YELLOW);
                if (puzzle.cells[i][j].revealed)
                    label.setBackground(puzzle.cells[i][j].number >= 0 ? Color.GREEN : Color.RED);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                rootPanel.add(label, constraints);
                constraints.gridx++;
            }
            constraints.gridy++;
            constraints.gridx = 0;
        }
        frame.add(rootPanel);

        solutionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
