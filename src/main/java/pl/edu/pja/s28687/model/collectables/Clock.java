package pl.edu.pja.s28687.model.collectables;

import pl.edu.pja.s28687.gui.animations.FreezeAnimation;
import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.model.characters.Pac;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Clock extends Collectable {
    private static final Image icon;

    static {
        try {
            icon = ImageIO.read(Objects.requireNonNull(Clock.class.getResource("/clock.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image", e);
        }
    }

    public Clock() {
        super();
    }

    @Override
    public IAnimated affectPac(Pac pac) {
        pac.freezeEnemies();
        return new FreezeAnimation();
    }

    @Override
    public void draw(Graphics g, int cellSize) {
        g.drawImage(icon, 0, 0, cellSize, cellSize, null);
    }
}
