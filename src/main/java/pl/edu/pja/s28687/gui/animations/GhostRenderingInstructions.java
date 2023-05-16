package pl.edu.pja.s28687.gui.animations;

import pl.edu.pja.s28687.model.characters.GameCharacter;
import pl.edu.pja.s28687.model.logistics.Coordinates;
import pl.edu.pja.s28687.model.upgrades.Upgrade;

import java.awt.*;

public class GhostRenderingInstructions implements IRenderingInstructions {
    @Override
    public void render(GameCharacter gameCharacter, Coordinates coordinates, int cellSize, Graphics g) {

        int[] directions = gameCharacter.getDirections();

        int dx = directions[0];
        int dy = directions[1];

        int x = coordinates.getX() * cellSize / 100;
        int y = coordinates.getY() * cellSize / 100;

        int ghostWidth = (int) (0.9 * cellSize);
        int arcGap = (int) (cellSize * 0.2);
        int arcOffset = (int) (cellSize * 0.8);
        arcOffset = (int) (gameCharacter.visualToggle() ? arcOffset - 0.05 *cellSize : arcOffset + 0.05 *cellSize);
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
        g2d.setColor(gameCharacter.getColor());

        g2d.fillRoundRect(x, y, ghostWidth, ghostWidth, ghostWidth, ghostWidth);
        for (int i = 0; i < 4; i++) {
            g2d.fillArc(x + i * arcGap, y + arcOffset, arcHeight, arcHeight, 0, 180);
        }

        if (gameCharacter.isDead()) {
            g2d.setColor(new Color(112, 32, 23, 255));
        } else {
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
            g2d.drawArc(x + i * arcGap, y + arcOffset, arcHeight, arcHeight, 0, 180);
        }

        for (Upgrade upgrade : gameCharacter.getUpgrades()) {
            upgrade.animate(gameCharacter, x, y, cellSize, g);
        }

    }
}
