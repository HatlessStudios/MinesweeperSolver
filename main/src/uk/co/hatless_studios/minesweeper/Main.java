package uk.co.hatless_studios.minesweeper;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main implements KeyListener {
    private JFrame frame;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField mineField;

    private Main() {
        frame = new JFrame("Minesweeper Solver");
        JPanel rootPanel = new JPanel(new GridBagLayout());
        JLabel widthLabel = new JLabel("Width:");
        JLabel heightLabel = new JLabel("Height:");
        JLabel mineLabel = new JLabel("Mines:");
        widthField = new JTextField();
        heightField = new JTextField();
        mineField = new JTextField();
        JButton cancelButton = new JButton("Cancel");
        JButton startButton = new JButton("Start");
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 1, 1, 1D, 1D, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        rootPanel.add(widthLabel, constraints);
        constraints.gridy++;
        rootPanel.add(heightLabel, constraints);
        constraints.gridy++;
        rootPanel.add(mineLabel, constraints);
        constraints.gridy++;
        rootPanel.add(cancelButton, constraints);
        constraints.gridx++;
        rootPanel.add(startButton, constraints);
        constraints.gridy--;
        rootPanel.add(mineField, constraints);
        constraints.gridy--;
        rootPanel.add(heightField, constraints);
        constraints.gridy--;
        rootPanel.add(widthField, constraints);
        frame.add(rootPanel);

        widthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        heightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        mineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        widthField.addKeyListener(this);
        heightField.addKeyListener(this);
        mineField.addKeyListener(this);
        cancelButton.addKeyListener(this);
        startButton.addKeyListener(this);
        cancelButton.addActionListener(e -> System.exit(0));
        startButton.addActionListener(e -> onStart());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getComponent() instanceof JTextField && e.getKeyChar() < '0' || e.getKeyChar() > '9' || ((JTextField) e.getComponent()).getText().length() >= 3) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (frame.getFocusOwner() instanceof JButton) {
                    ((JButton) frame.getFocusOwner()).doClick();
                } else {
                    onStart();
                    e.consume();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    private void onStart() {
        try {
            Minesweeper minesweeper = new Minesweeper(Integer.valueOf(widthField.getText()), Integer.valueOf(heightField.getText()), Integer.valueOf(mineField.getText()));
            frame.setVisible(false);
            Solver.solve(minesweeper);
            new Solution(minesweeper);
        } catch (NumberFormatException ignored) {}
    }
}
