package pl.edu.pja.s28687.model.characters;

import pl.edu.pja.s28687.gui.animations.IRenderingInstructions;
import pl.edu.pja.s28687.gui.animations.PacmanRenderingInstructions;
import pl.edu.pja.s28687.model.GameModel;
import pl.edu.pja.s28687.model.logistics.Cell;

import java.awt.*;

public class Pac extends GameCharacter implements Runnable {

    private static final IRenderingInstructions renderingInstructions = new PacmanRenderingInstructions();

    public Pac(GameModel gameModel, int startRow, int startCol) {
        super(gameModel, 3, 3, Color.YELLOW, startRow, startCol, false, renderingInstructions);
        gameModel.updateLives(lives);
    }

    public int addPoints(int i) {
        int pointsAdded = gameModel.addPoints(i);
        return pointsAdded;
    }

    @Override
    protected Cell getNextCell() {
        Cell cell = null;
        do {
            int[] directions = gameModel.getDirections();
            int _x = currentCell.getCol() + directions[0];
            int _y = currentCell.getRow() + directions[1];
            if (
                    (directions[0] != 0 || directions[1] != 0)
                            &&
                            _x >= 0
                            &&
                            _x < gameModel.getColumnCount()
                            && _y >= 0
                            && _y < gameModel.getRowCount()
            ) {
                cell = gameModel.getCell(_y, _x);
                if (cell.canEnter()) {
                    dx = directions[0];
                    dy = directions[1];
                }
            }
        } while (cell == null || !cell.canEnter());
        return cell;
    }

    public void flipBoard() {
        if (gameModel != null) {
            gameModel.flipBoard();
        }
    }

    public void renderNumbers() {
        if (gameModel != null) {
            gameModel.renderNumbers();
        }
    }

    public void addLives(int i) {
        lives += i;
        gameModel.updateLives(lives);
    }

    public void blowUpNpc() {
        if (gameModel != null) {
            gameModel.blowUpNpc();
        }
    }

    @Override
    public void kill() {
        if (!isImmortal() && !isDead) {
            lives--;
            isDead = true;
            gameModel.updateLives(Math.max(lives, 0));
        }
    }

    public void multiplyScoreMultiplier(double i) {
        gameModel.multiplyScoreMultiplier(i);
    }

    public void freezeEnemies() {
        if (gameModel != null) {
            gameModel.freezeNpc();
        }
    }

    public void frightenEnemies() {
        if (gameModel != null) {
            gameModel.frightenNpc();
        }
    }

    public void lureEnemies() {
        if (gameModel != null) {
            gameModel.lureNpc();
        }
    }

    public void consumeFood() {
        if (gameModel != null) {
            gameModel.foodConsumed();
        }
    }

}
