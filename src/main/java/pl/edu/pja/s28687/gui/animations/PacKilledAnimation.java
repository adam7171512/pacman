package pl.edu.pja.s28687.gui.animations;

import java.awt.*;

public class PacKilledAnimation implements IAnimated {

    private int animationFrames;
    private final int startingFrames;

    public PacKilledAnimation(){
        animationFrames = 50;
        startingFrames = animationFrames;
    }

    @Override
    public void render(Graphics g, int cellSize) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke((float) (cellSize/7.0)));
        g2d.setFont(new Font("Arial", Font.PLAIN, cellSize/7));
        g2d.setColor(Color.RED);
        int hPos = (int) (((float) animationFrames / startingFrames) * cellSize);
        g2d.drawString("DED :( ", 0, hPos);
        animationFrames--;
    }

    @Override
    public boolean expired() {
        return false;
    }
}
