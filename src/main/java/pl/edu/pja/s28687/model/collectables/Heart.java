package pl.edu.pja.s28687.model.collectables;

import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.gui.animations.PointsAddedAnimation;
import pl.edu.pja.s28687.model.characters.Pac;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Heart extends Collectable {
    private static final Image icon;

    static {
        try {
            icon = ImageIO.read(Objects.requireNonNull(Heart.class.getResource("/heart.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image", e);
        }
    }

    public Heart() {
        super();
    }

    @Override
    public IAnimated affectPac(Pac pac) {
        pac.lureEnemies();
        pac.addLives(1);
        int pointsAdded = pac.addPoints(5);
        return new PointsAddedAnimation(pointsAdded);
    }

    @Override
    public void draw(Graphics g, int cellSize) {
        g.drawImage(icon, 0, 0, cellSize,cellSize, null);
    }
}
