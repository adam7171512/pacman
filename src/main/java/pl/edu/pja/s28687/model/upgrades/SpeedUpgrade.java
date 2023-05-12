package pl.edu.pja.s28687.model.upgrades;

import pl.edu.pja.s28687.model.characters.GameCharacter;

import java.awt.*;

public class SpeedUpgrade extends Upgrade {

    private static final int POWER = 8;

    public SpeedUpgrade(int length) {
        super(length, POWER);
    }

    @Override
    public boolean apply(GameCharacter gameCharacter) {
        gameCharacter.setSpeed(gameCharacter.getSpeed() + 1);
        reduceLength();
        return true;
    }

    public boolean animate(GameCharacter gameCharacter, int x, int y, int cellSize, Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.PLAIN, cellSize/6));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawString("RUN!!!", 0, y);
        gameCharacter.setColor(Color.GREEN);
        return true;
    }

}
