package pl.edu.pja.s28687.model.logistics;

import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.model.characters.Pac;

public interface PacCollision {
    public abstract IAnimated affectPac(Pac pac);
}
