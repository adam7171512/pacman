package pl.edu.pja.s28687.model.collectables;

import pl.edu.pja.s28687.model.characters.GameObject;
import pl.edu.pja.s28687.model.logistics.PacCollision;

import java.awt.*;

public abstract class Collectable extends GameObject implements PacCollision {

    public abstract void draw(Graphics g, int cellSize);
}
