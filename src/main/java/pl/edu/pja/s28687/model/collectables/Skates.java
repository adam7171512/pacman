package pl.edu.pja.s28687.model.collectables;

import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.gui.animations.TurboAnimation;
import pl.edu.pja.s28687.model.characters.Pac;
import pl.edu.pja.s28687.model.upgrades.SpeedUpgrade;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Skates extends Collectable {
    private static final Image icon;

    static {
        try {
            icon = ImageIO.read(Objects.requireNonNull(Skates.class.getResource("/skates.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image", e);
        }
    }

    public Skates() {
        super();
    }

    @Override
    public IAnimated affectPac(Pac pac) {
        pac.addUpgrade(new SpeedUpgrade(30));
        return new TurboAnimation();
    }

    @Override
    public void draw(Graphics g, int cellSize) {
        g.drawImage(icon, 0, 0, cellSize,cellSize, null);
    }
}
