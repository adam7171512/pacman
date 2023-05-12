package pl.edu.pja.s28687.model.upgrades;

import pl.edu.pja.s28687.model.characters.GameCharacter;

import java.awt.*;

public class BlowUp extends Upgrade {

    private final int TOTAL_LENGTH = 100;

    public BlowUp() {
        super(100, 10);

    }

    @Override
    public boolean apply(GameCharacter gameCharacter) {
        gameCharacter.kill();
        return true;
    }

    public boolean animate(GameCharacter gameCharacter, int x, int y, int cellSize, Graphics g) {


        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(3));


        g2d.setColor(Math.random() > 0.5 ? Color.YELLOW : Color.RED);

        int a = (int) (Math.random() * cellSize);
        int b = 20 - a;
        int size = (int) (Math.random() * cellSize);
        g2d.fillOval(x + a, y + b, size, size);
        reduceLength();

        return true;
    }


}
