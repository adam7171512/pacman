package pl.edu.pja.s28687.gui.animations;

import pl.edu.pja.s28687.model.characters.GameCharacter;
import pl.edu.pja.s28687.model.logistics.Coordinates;
import pl.edu.pja.s28687.model.upgrades.Upgrade;

import java.awt.*;

public class PacmanRenderingInstructions implements IRenderingInstructions{
    @Override
    public void render(GameCharacter gameCharacter, Coordinates coordinates, int cellSize,  Graphics g) {
        int[] directions = gameCharacter.getDirections();

        int dx = directions[0];
        int dy = directions[1];

        Graphics2D g2d = (Graphics2D) g.create();
        double startAngle = Math.toDegrees(Math.atan2(-dy, dx));
        startAngle = (startAngle + 360) % 360;
        startAngle = startAngle + 30;
        g2d.setColor(gameCharacter.getColor());
        int x = coordinates.getX() * cellSize / 100;
        int y = coordinates.getY() * cellSize / 100;
        if (!gameCharacter.visualToggle()) {
            g2d.fillArc(x, y, cellSize, cellSize, (int) startAngle, 340);
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawArc(x, y, cellSize, cellSize, (int) startAngle, 340);
        } else {
            g2d.fillArc(x, y, cellSize, cellSize, (int) startAngle, 300);
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawArc(x, y, cellSize, cellSize, (int) startAngle, 300);
        }
        int eyeX = dx < 0 ? x + cellSize/2 - cellSize/16 - cellSize/3 : x + cellSize/2 + cellSize/16;
        int eyeY = dy <= 0 ? y + cellSize/2 - cellSize/12 - cellSize/3 : y + cellSize/2 + cellSize/12;
        if (gameCharacter.isDead()) {
            g2d.setColor(new Color(243, 27, 5, 255));
        } else {
            g2d.setColor(Color.BLACK);
        }
        g2d.fillOval(eyeX, eyeY, cellSize / 3, cellSize / 3);
        g2d.setColor(Color.WHITE);
        g2d.drawOval(eyeX, eyeY, cellSize / 3, cellSize / 3);
        for (Upgrade upgrade : gameCharacter.getUpgrades()) {
            upgrade.animate(gameCharacter, x, y, cellSize, g2d);
        }
    }
}
