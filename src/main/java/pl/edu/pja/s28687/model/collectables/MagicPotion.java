package pl.edu.pja.s28687.model.collectables;

import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.gui.animations.MagicPotionAnimationInteraction;
import pl.edu.pja.s28687.model.characters.Pac;
import pl.edu.pja.s28687.model.upgrades.GodModeUpgrade;
import pl.edu.pja.s28687.model.upgrades.SpeedUpgrade;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class MagicPotion extends Collectable {

    private static final Image icon;

    static {
        try {
            icon = ImageIO.read(Objects.requireNonNull(MagicPotion.class.getResource("/drink.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image", e);
        }
    }

    public MagicPotion() {
        super();
    }

    @Override
    public IAnimated affectPac(Pac pac) {
        pac.addUpgrade(new GodModeUpgrade(40));
        pac.addUpgrade(new SpeedUpgrade(20));
        pac.frightenEnemies();
        return new MagicPotionAnimationInteraction();
    }

    @Override
    public void draw(Graphics g, int cellSize) {
        g.drawImage(icon, cellSize/2, 0, cellSize/3,2* cellSize/3, null);
    }

}
