package pl.edu.pja.s28687.model.upgrades;

import pl.edu.pja.s28687.model.characters.GameCharacter;

import java.awt.*;

public interface IUpgrade {
    public boolean apply(GameCharacter gameCharacter);
    public boolean animate(GameCharacter gameCharacter, int x, int y, int cellSize, Graphics g);
}
