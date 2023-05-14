package pl.edu.pja.s28687.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Objects;

public class StatsPanel {
    private static final Image barImage;

    static {
        try {
            barImage = ImageIO.read(Objects.requireNonNull(StatsPanel.class.getResource("/barv.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image", e);
        }
    }

    private JPanel panel;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel livesLabel;

    public StatsPanel() {
        createUIComponents();
    }

    public JPanel getPanel() {
        return panel;
    }


    private void createUIComponents() {

        panel = new JPanel(new GridLayout(1, 3)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(barImage, 0, 0, panel.getWidth(), panel.getHeight(), panel);
            }
        };

        panel.setVisible(true);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;

        scoreLabel = new JLabel();
        timeLabel = new JLabel();
        livesLabel = new JLabel();

        panel.add(scoreLabel, constraints);
        panel.add(timeLabel, constraints);
        panel.add(livesLabel, constraints);

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int minSize = Math.min(panel.getHeight(), 24);
                Font labelFont = new Font("Arial", Font.BOLD, minSize);
                scoreLabel.setFont(labelFont);
                timeLabel.setFont(labelFont);
                livesLabel.setFont(labelFont);

                Dimension labelSize = new Dimension(panel.getWidth(), panel.getHeight() / 3);
                scoreLabel.setMinimumSize(labelSize);
                timeLabel.setMinimumSize(labelSize);
                livesLabel.setMinimumSize(labelSize);
                scoreLabel.setMaximumSize(labelSize);
                timeLabel.setMaximumSize(labelSize);
                livesLabel.setMaximumSize(labelSize);
            }
        });
        updateUI();
    }

    private void updateUI() {
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        livesLabel.setHorizontalAlignment(JLabel.CENTER);

        scoreLabel.setLayout(new GridLayout(1, 1));
        timeLabel.setLayout(new GridLayout(1, 1));
        livesLabel.setLayout(new GridLayout(1, 1));

        scoreLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
        timeLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
        livesLabel.setBorder(new EmptyBorder(0, 10, 0, 10));

        scoreLabel.setForeground(Color.GREEN);
        timeLabel.setForeground(Color.WHITE);
        livesLabel.setForeground(Color.RED);

        scoreLabel.setOpaque(false);
        timeLabel.setOpaque(false);
        livesLabel.setOpaque(false);

        Font labelFont = new Font("Arial", Font.BOLD, panel.getHeight() / 5);
        scoreLabel.setFont(labelFont);
        timeLabel.setFont(labelFont);
        livesLabel.setFont(labelFont);
    }

    public void updateScore(int score, int multiplier) {
        scoreLabel.setText(String.valueOf(score) + " (" + multiplier + "x)");
    }

    public void updateTime(int time) {
        timeLabel.setText(String.valueOf(time));
    }

    public void updateLives(int lives) {
        livesLabel.setText("\u2665".repeat(lives));
    }

}