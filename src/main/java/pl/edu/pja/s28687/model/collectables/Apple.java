package pl.edu.pja.s28687.model.collectables;

import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.gui.animations.PointsAddedAnimation;
import pl.edu.pja.s28687.model.characters.Pac;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Apple extends Collectable {
    private static final Image icon;

    static {
        try {
            icon = ImageIO.read(Objects.requireNonNull(Apple.class.getResource("/apple.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image", e);
        }
    }

    public Apple() {
        super();
    }

    @Override
    public IAnimated affectPac(Pac pac) {
        int pointsAdded = pac.addPoints(4);
        return new PointsAddedAnimation(pointsAdded);
    }

    @Override
    public void draw(Graphics g, int cellSize) {
        g.drawImage(icon, 0, 0, cellSize,cellSize, null);
    }
}
