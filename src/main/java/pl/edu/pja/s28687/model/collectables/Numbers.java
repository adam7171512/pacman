package pl.edu.pja.s28687.model.collectables;

import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.gui.animations.ScoreMultiplierAnimation;
import pl.edu.pja.s28687.model.characters.Pac;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Numbers extends Collectable {
    private static final Image icon;
    private static final int MULTIPLIER = 4;

    static {
        try {
            icon = ImageIO.read(Objects.requireNonNull(Numbers.class.getResource("/123.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image", e);
        }
    }

    public Numbers() {
        super();
    }

    @Override
    public IAnimated affectPac(Pac pac) {
        pac.addPoints(20);
        pac.multiplyScoreMultiplier(MULTIPLIER);
        pac.renderNumbers();
        new Thread(() -> {
            try {
                Thread.sleep(7000);
                pac.renderNumbers();
                pac.multiplyScoreMultiplier((double) 1 /MULTIPLIER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return new ScoreMultiplierAnimation(MULTIPLIER);
    }

    @Override
    public void draw(Graphics g, int cellSize) {
        g.drawImage(icon, 0, 0, cellSize,cellSize, null);
    }
}
