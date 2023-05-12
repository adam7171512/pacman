package pl.edu.pja.s28687.model.upgrades;

import pl.edu.pja.s28687.model.characters.GameCharacter;

import java.awt.*;

public class GodModeUpgrade extends Upgrade {

    private static final int POWER = 10;

    public GodModeUpgrade(int length) {
        super(length, POWER);
    }

    @Override
    public boolean apply(GameCharacter gameCharacter) {
        gameCharacter.setImmortal(true);
        gameCharacter.setHarmful(true);
        reduceLength();
        return true;
    }

    public boolean animate(GameCharacter gameCharacter, int x, int y, int cellSize,  Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.GREEN);
        g2d.setFont(new Font("Arial", Font.PLAIN, cellSize/6));
        g2d.drawString("GRR!!!", 0, y);
        if (getLength() > 1){
            gameCharacter.setColor(Color.CYAN);
        }
        else {
            gameCharacter.setColor(Color.YELLOW);
        }
        return true;
    }

}
