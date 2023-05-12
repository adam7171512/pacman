package pl.edu.pja.s28687.model.characters;

import pl.edu.pja.s28687.model.logistics.Cell;
import pl.edu.pja.s28687.model.logistics.Coordinates;
import pl.edu.pja.s28687.model.GameModel;
import pl.edu.pja.s28687.model.upgrades.Upgrade;

import java.awt.*;
import java.util.Arrays;

public class Pac extends GameCharacter implements Runnable {

    public Pac(GameModel gameModel, int startRow, int startCol) {
        super(gameModel, 3, 3, Color.YELLOW, startRow, startCol, false);
        gameModel.updateLives(lives);
    }

    public int addPoints(int i) {
        gameModel.addPoints(i);
        return i * gameModel.getScoreMultiplier();
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
        } while (cell == null || !cell.canEnter()) ;
        return cell;
    }

    public void draw(Coordinates coordinates, Graphics g, int cellSize) {
        Graphics2D g2d = (Graphics2D) g.create();
        double startAngle = Math.toDegrees(Math.atan2(-dy, dx));
        startAngle = (startAngle + 360) % 360;
        startAngle = startAngle + 30;
        g2d.setColor(getColor());
        int x = coordinates.getX() * cellSize / CELL_TRAVEL_DISTANCE;
        int y = coordinates.getY() * cellSize / CELL_TRAVEL_DISTANCE;
        if (!visualToggle()) {
            g2d.fillArc(x, y, cellSize, cellSize, (int) startAngle, 340);
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawArc(x, y, cellSize, cellSize, (int) startAngle, 340);
        } else {
            g2d.fillArc(x, y, cellSize, cellSize, (int) startAngle, 300);
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawArc(x, y, cellSize, cellSize, (int) startAngle, 300);
        }

        int eyeX = dx < 0 ? x + cellSize/2 - cellSize/16 - cellSize/3 : x + cellSize/2 + cellSize/16;
        int eyeY = dy <= 0 ? y + cellSize/2 - cellSize/12 - cellSize/3 : y + cellSize/2 + cellSize/12;

        if (isDead) {
            g2d.setColor(new Color(243, 27, 5, 255));
        } else {
            g2d.setColor(Color.BLACK);
        }
        g2d.fillOval(eyeX, eyeY, cellSize / 3, cellSize / 3);
        g2d.setColor(Color.WHITE);
        g2d.drawOval(eyeX, eyeY, cellSize / 3, cellSize / 3);

        for (Upgrade upgrade : getUpgrades()) {
            upgrade.animate(this, x, y, cellSize, g);
        }
    }

    public void flip() {
        gameModel.flip();
    }

    public void numbers() {
        gameModel.numbersRenderer();
    }

    public void addLives(int i) {
        lives += i;
        gameModel.updateLives(lives);
    }

    public void killAll() {
        gameModel.blowUpNpc();
    }

    @Override
    public void kill() {
        if (!isImmortal() && !isDead) {
            lives--;
            isDead = true;
            gameModel.updateLives(Math.max(lives, 0));
        }
    }

    public void multiplyPointsMultiplier(double i) {
        gameModel.setScoreMultiplier(gameModel.getScoreMultiplier()*i);
    }

    public void freezeEnemies() {
        gameModel.freezeNpc();
    }

    public void frightenEnemies(){
        gameModel.frightenNpc();
    }

    public void lureEnemies(){
        gameModel.lureNpc();
    }

    public void consumeFood() {
        gameModel.foodConsumed();
    }

}
