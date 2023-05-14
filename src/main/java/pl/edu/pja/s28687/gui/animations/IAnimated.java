package pl.edu.pja.s28687.gui.animations;

import java.awt.*;

public interface IAnimated {
    public void render(Graphics g, int cellSize);
    public boolean expired();
}
