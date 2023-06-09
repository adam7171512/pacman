package pl.edu.pja.s28687.gui.animations;

import java.awt.*;

public class FreezeAnimation implements IAnimated {

    int animationFrames;
    int startingFrames;
    public FreezeAnimation(){
        animationFrames = 200;
        startingFrames = animationFrames;
    }
    public void render(Graphics g, int cellSize) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke((float) (cellSize/5.0)));
        g2d.setFont(new Font("Arial", Font.PLAIN, cellSize/5));
        g2d.setColor(Color.CYAN);
        int hPos = (int) (((float) animationFrames / startingFrames) * cellSize);
        g2d.drawString(" FREEZE!! ", 0, hPos);
        animationFrames--;
    }

    @Override
    public boolean expired() {
        return animationFrames <= 0;
    }
}
