package pl.edu.pja.s28687.gui.animations;

import java.awt.*;

public class PointsAddedAnimation implements IAnimated {

    int animationFrames;
    int startingFrames;
    private final int points;
    public PointsAddedAnimation(int points){
        this.points = points;
        animationFrames = 50;
        startingFrames = animationFrames;
    }
    public void render(Graphics g, int cellSize) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke((float) (cellSize/5.0)));
        g2d.setFont(new Font("Arial", Font.PLAIN, cellSize/5));
        g2d.setColor(points > 0 ? Color.GREEN : Color.RED);
        int hPos = (int) (((float) animationFrames / startingFrames) * cellSize);
        g2d.drawString(" + " + points, cellSize/6, hPos);
        animationFrames--;
    }

    @Override
    public boolean expired() {
        return animationFrames <= 0;
    }
}
