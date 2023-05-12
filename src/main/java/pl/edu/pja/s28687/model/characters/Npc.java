package pl.edu.pja.s28687.model.characters;


import pl.edu.pja.s28687.gui.animations.GhostKilledAnimationInteraction;
import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.gui.animations.PacKilledAnimationInteraction;
import pl.edu.pja.s28687.model.GameModel;
import pl.edu.pja.s28687.model.logistics.Cell;
import pl.edu.pja.s28687.model.logistics.PacCollision;

import java.awt.*;

public abstract class Npc extends GameCharacter implements PacCollision {
    private Cell lastCell;

    public Npc(GameModel gameModel, int speed, int lives, int startRow, int startCol) {
        super(gameModel, speed, lives, new Color((int) (Math.random() * 0x1000000)), startRow, startCol, true);
    }

    @Override
    public void run() {
        int time = 0;
        while (! gameOver) {
            try {
                time += move();
            } catch (InterruptedException e) {
                endGame();
            }
            if (time / 5000 > 0) {
                time = 0;
                if (Math.random() > 0.75){
                    drop();
                }
            }
        }
    }

//    @Override
    protected int[] getDirections() {
        int x, y;
        do {
            x = Math.random() > 0.5 ? 0 : (Math.random() > 0.5 ? 1 : -1);
            y = x == 0 ? (Math.random() > 0.5 ? 1 : -1) : 0;
        }
        while (
                (dx != 0 && x == -dx)
                        || (dy != 0 && y == -dy)
        );
        return new int[]{x, y};
    }

    @Override
    protected Cell getNextCell(){
        Cell nextCell = gameModel.getNextCell(this);
        lastCell = currentCell;
        return nextCell;
    }

    @Override
    public IAnimated affectPac(Pac pac) {
        if (pac.isHarmful()){
            isDead = true;
            return new GhostKilledAnimationInteraction();
        }
        else if (!pac.isImmortal()){
            pac.kill();
            return new PacKilledAnimationInteraction();
        }
        else return null;
    }

    protected abstract void drop();

    public Cell getLastCell() {
        return lastCell;
    }
}
