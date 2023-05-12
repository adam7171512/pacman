package pl.edu.pja.s28687.gui.animations;

import java.awt.*;

public class ShieldAnimationInteraction implements IAnimated {

    int animationFrames;
    int startingFrames;
    public ShieldAnimationInteraction(){
        animationFrames = 200;
        startingFrames = animationFrames;
    }
    public void animEffect(Graphics g, int cellSize) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke((float) (cellSize/5.0)));
        g2d.setFont(new Font("Arial", Font.PLAIN, cellSize/5));
        g2d.setColor(Color.BLUE);
        int hPos = (int) (((float) animationFrames / startingFrames) * cellSize);
        g2d.drawString("Shielded! ", 0, hPos);
        animationFrames--;
    }

    @Override
    public boolean expired() {
        return animationFrames <= 0;
    }
}
