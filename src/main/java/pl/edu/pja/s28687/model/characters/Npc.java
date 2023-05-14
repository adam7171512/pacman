package pl.edu.pja.s28687.model.characters;


import pl.edu.pja.s28687.gui.animations.GhostKilledAnimation;
import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.gui.animations.IRenderingInstructions;
import pl.edu.pja.s28687.gui.animations.PacKilledAnimation;
import pl.edu.pja.s28687.model.GameModel;
import pl.edu.pja.s28687.model.logistics.Cell;
import pl.edu.pja.s28687.model.logistics.PacCollision;

import java.awt.*;

public abstract class Npc extends GameCharacter implements PacCollision {

    // last cell used to prevent ghosts from going back and forth
    private Cell lastCell;

    public Npc(GameModel gameModel, int speed, int lives, int startRow, int startCol, IRenderingInstructions renderingInstructions) {
        super(gameModel, speed, lives, new Color((int) (Math.random() * 0x1000000)), startRow, startCol, true, renderingInstructions);
    }

    @Override
    public void run() {
        int time = 0;
        while (!gameOver) {
            try {
                time += move();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (time / 5000 > 0) {
                time = 0;
                if (Math.random() > 0.75) {
                    dropItem();
                }
            }
        }
        endGame();
    }

    @Override
    protected Cell getNextCell() {
        Cell nextCell = null;
        if (gameModel != null) {
            nextCell = gameModel.getNextCell(this);
            lastCell = currentCell;
        }
        return nextCell;
    }

    @Override
    public IAnimated affectPac(Pac pac) {
        if (pac.isHarmful()) {
            isDead = true;
            return new GhostKilledAnimation();
        } else if (!pac.isImmortal()) {
            pac.kill();
            return new PacKilledAnimation();
        } else return null;
    }

    protected abstract void dropItem();

    public Cell getLastCell() {
        return lastCell;
    }
}
