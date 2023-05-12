package pl.edu.pja.s28687.model.collectables;

import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.gui.animations.PointsAddedAnimation;
import pl.edu.pja.s28687.model.characters.Pac;

import java.awt.*;

public class Food extends Collectable {
    @Override
    public void draw(Graphics g, int cellSize) {
        g.setColor(Color.YELLOW);
        g.fillOval(cellSize / 2, cellSize / 2, cellSize / 7, cellSize / 7);
    }

    @Override
    public IAnimated affectPac(Pac pac) {
        int pointsAdded = pac.addPoints(1);
        pac.consumeFood();
        return new PointsAddedAnimation(pointsAdded);
    }
}