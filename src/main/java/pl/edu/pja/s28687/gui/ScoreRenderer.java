package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.model.score.Score;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ScoreRenderer extends JLabel implements ListCellRenderer<Score> {
    private static final Image bar;
    private static final Image icon;

    static {
        try {
            icon = ImageIO.read(Objects.requireNonNull(ScoreRenderer.class.getResource("/drink.png")));
            bar = ImageIO.read(Objects.requireNonNull(ScoreRenderer.class.getResource("/scorebar.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image", e);
        }
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Score> jList, Score score, int i, boolean b, boolean b1) {
        String text = i + 1 + ". " + score.toString();
        setText(text);
        setOpaque(false);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, jList.getFont().getSize()));
        setIcon(new ImageIcon(icon));
        return this;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bar, 0, 0, getWidth(), getHeight(), this);
        super.paintComponent(g);
    }
}
