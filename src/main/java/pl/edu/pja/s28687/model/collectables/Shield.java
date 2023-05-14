package pl.edu.pja.s28687.model.collectables;

import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.gui.animations.ShieldAnimation;
import pl.edu.pja.s28687.model.characters.Pac;
import pl.edu.pja.s28687.model.upgrades.ImmortalUpgrade;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Shield extends Collectable {

    private static final Image icon;

    static {
        try {
            icon = ImageIO.read(Objects.requireNonNull(MagicPotion.class.getResource("/shield.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image", e);
        }
    }

    private int animationFrames;
    private int effectSize;

    private final IAnimated pacInteractionAnimation;

    public Shield() {
        super();
        animationFrames = 200;
        pacInteractionAnimation = new ShieldAnimation();
    }

    @Override
    public IAnimated affectPac(Pac pac) {
        pac.addUpgrade(new ImmortalUpgrade(20));
        return pacInteractionAnimation;
    }

    public boolean animAffect(Graphics g, int cellSize) {
        g.setColor(Color.BLUE);
        g.fillOval(0, 0, effectSize, effectSize++);
        animationFrames--;
        return true;
    }

    @Override
    public void draw(Graphics g, int cellSize) {
        g.drawImage(icon, cellSize / 4, 0, cellSize ,  cellSize , null);
    }

    public boolean expired() {
        return animationFrames <= 0;
    }
}
