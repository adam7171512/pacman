package pl.edu.pja.s28687.model.collectables;

import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.gui.animations.ScoreMultiplierAnimation;
import pl.edu.pja.s28687.model.characters.Pac;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Flower extends Collectable {
    private static final Image icon;
    private static final int MULTIPLIER = 2;

    static {
        try {
            icon = ImageIO.read(Objects.requireNonNull(Flower.class.getResource("/flower.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image", e);
        }
    }

    public Flower() {
        super();
    }

    @Override
    public IAnimated affectPac(Pac pac) {
        pac.lureEnemies();
        pac.multiplyScoreMultiplier(MULTIPLIER);
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                pac.multiplyScoreMultiplier(0.5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return new ScoreMultiplierAnimation(MULTIPLIER);
    }

    @Override
    public void draw(Graphics g, int cellSize) {
        g.drawImage(icon, 0, 0, cellSize, cellSize, null);
    }
}
