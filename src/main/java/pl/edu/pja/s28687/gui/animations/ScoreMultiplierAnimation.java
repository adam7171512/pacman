package pl.edu.pja.s28687.gui.animations;

import java.awt.*;

public class ScoreMultiplierAnimation implements IAnimated {

    int animationFrames;
    int startingFrames;
    private final int multiplier;
    public ScoreMultiplierAnimation(int multiplier){
        this.multiplier = multiplier;
        animationFrames = 50;
        startingFrames = animationFrames;
    }
    public void animEffect(Graphics g, int cellSize) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke((float) (cellSize/5.0)));
        g2d.setFont(new Font("Arial", Font.PLAIN, cellSize/4));
        g2d.setColor(multiplier > 0 ? Color.GREEN : Color.RED);
        int hPos = (int) (((float) animationFrames / startingFrames) * cellSize);
        g2d.drawString(" X " + multiplier, cellSize/4, hPos);
        animationFrames--;
    }

    @Override
    public boolean expired() {
        return animationFrames <= 0;
    }
}
