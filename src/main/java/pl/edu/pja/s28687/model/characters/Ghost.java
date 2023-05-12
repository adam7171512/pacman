package pl.edu.pja.s28687.model.characters;

import pl.edu.pja.s28687.model.logistics.Coordinates;
import pl.edu.pja.s28687.model.collectables.Collectable;
import pl.edu.pja.s28687.model.collectables.CollectableFactory;
import pl.edu.pja.s28687.model.GameModel;
import pl.edu.pja.s28687.model.upgrades.Upgrade;

import java.awt.*;

public class Ghost extends Npc {

    public Ghost(GameModel gameModel, int startingX, int startingY) {
        super(gameModel, 2, 99, startingX, startingY);
    }

    public void draw(Coordinates coordinates, Graphics g, int cellSize) {

        int x = coordinates.getX() * cellSize / 100;
        int y = coordinates.getY() * cellSize / 100;

        int ghostWidth = (int) (0.9 * cellSize);
        int arcGap = (int) (cellSize * 0.2);
        int arcOffset = (int) (cellSize * 0.8);
        arcOffset = visualToggle() ? arcOffset - 2 : arcOffset + 2;

        int arcHeight = (int) (cellSize * 0.35);

        int ghostWhiteEyeVerticalOffset = (int) (cellSize * 0.2);
        int ghostFirstWhiteEyeHorizontalOffset = (int) (cellSize * 0.2);
        int ghostEyeWidth = (int) (cellSize * 0.3);
        int ghostEyeGap = (int) (cellSize * 0.25);
        int ghostBlackEyeWidth = (int) (cellSize * 0.15);

        int ghostBlackEyeVerticalOffset = (int) (cellSize * 0.3);
        int ghostBlackEyeHorizontalOffset = (int) (cellSize * 0.3);
        int deltaX = (int) (dx * cellSize * .1);
        int deltaY = (int) (dy * cellSize * .1);


        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getColor());

        g2d.fillRoundRect(x, y, ghostWidth, ghostWidth, ghostWidth, ghostWidth);
        for (int i = 0; i < 4; i++) {
            g2d.fillArc(x  + i * arcGap, y + arcOffset, arcHeight, arcHeight, 0, 180);
        }

        if (isDead){
            g2d.setColor(new Color(112, 32, 23, 255));
        }
        else {
            g2d.setColor(Color.WHITE);
        }
        g2d.fillOval(x + ghostFirstWhiteEyeHorizontalOffset, y + ghostWhiteEyeVerticalOffset, ghostEyeWidth, ghostEyeWidth);
        g2d.fillOval(x + ghostFirstWhiteEyeHorizontalOffset + ghostEyeGap, y + ghostWhiteEyeVerticalOffset, ghostEyeWidth, ghostEyeWidth);

        g2d.setColor(Color.BLACK);
        g2d.fillOval(x + ghostBlackEyeHorizontalOffset + deltaX, y + ghostBlackEyeVerticalOffset + deltaY, ghostBlackEyeWidth, ghostBlackEyeWidth);
        g2d.fillOval(x + ghostBlackEyeHorizontalOffset + ghostEyeGap + deltaX, y + ghostBlackEyeVerticalOffset + deltaY, ghostBlackEyeWidth, ghostBlackEyeWidth);

        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(x, y, ghostWidth, ghostWidth, ghostWidth, ghostWidth);
        for (int i = 0; i < 4; i++) {
            g2d.drawArc(x  + i * arcGap, y + arcOffset, arcHeight, arcHeight, 0, 180);
        }

        for (Upgrade upgrade : getUpgrades()) {
            upgrade.animate(this, x, y, cellSize, g);
        }
    }

    @Override
    protected void drop() {
        Collectable collectable = CollectableFactory.createRandomCollectable();
        currentCell.addCollectable(collectable);
    }
}
