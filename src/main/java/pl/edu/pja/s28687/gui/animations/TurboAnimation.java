package pl.edu.pja.s28687.gui.animations;

import java.awt.*;

public class TurboAnimation implements IAnimated {

    int animationFrames;
    int startingFrames;
    public TurboAnimation(){
        animationFrames = 200;
        startingFrames = animationFrames;
    }
    public void animEffect(Graphics g, int cellSize) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke((float) (cellSize/5.0)));
        g2d.setFont(new Font("Arial", Font.PLAIN, cellSize/5));
        g2d.setColor(Color.YELLOW);
        int hPos = (int) (((float) animationFrames / startingFrames) * cellSize);
        g2d.drawString("TURBO! ", 0, hPos);
        animationFrames--;
    }

    @Override
    public boolean expired() {
        return animationFrames <= 0;
    }
}
