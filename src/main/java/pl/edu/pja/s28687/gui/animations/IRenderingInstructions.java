package pl.edu.pja.s28687.gui.animations;

import pl.edu.pja.s28687.model.characters.GameCharacter;
import pl.edu.pja.s28687.model.logistics.Coordinates;

import java.awt.*;

public interface IRenderingInstructions {
    public void render(GameCharacter gameCharacter, Coordinates coordinates, int cellSize, Graphics g);
}
