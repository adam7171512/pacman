package pl.edu.pja.s28687.model.characters;

import pl.edu.pja.s28687.model.GameModel;
import pl.edu.pja.s28687.model.logistics.Cell;
import pl.edu.pja.s28687.model.logistics.CellPair;
import pl.edu.pja.s28687.model.logistics.Coordinates;
import pl.edu.pja.s28687.model.upgrades.ImmortalUpgrade;
import pl.edu.pja.s28687.model.upgrades.Upgrade;
import pl.edu.pja.s28687.model.upgrades.UpgradeManager;

import java.awt.*;
import java.util.Queue;

public abstract class GameCharacter extends GameObject implements Runnable {
    protected final static int CELL_TRAVEL_DISTANCE = 100;

    protected GameModel gameModel;
    private final int defaultSpeed;
    private final Color defaultColor;
    protected boolean isDead;
    protected int speed;
    protected int dx;
    protected int dy;
    int lives;
    int startRow;
    int startCol;
    protected Cell currentCell;
    private CellPair cellPair;
    private boolean immortal;
    private Color color;
    private boolean visualToggle;
    private UpgradeManager upgradeManager = null;
    private final boolean defaultHarmful;
    private boolean harmful;
    private boolean paused;
    protected boolean gameOver;
    boolean newMatch;

    public GameCharacter(GameModel gameModel, int speed, int lives, Color color, int startRow, int startCol, boolean defaultHarmful) {
        setUpgradeManager(new UpgradeManager(this));
        this.gameModel = gameModel;
        this.startRow = startRow;
        this.startCol = startCol;
        this.defaultColor = color;
        this.color = color;
        setStartingPosition();
        dx = 0;
        dy = 0;
        this.lives = lives;
        this.defaultSpeed = speed;
        this.speed = speed;
        this.defaultHarmful = defaultHarmful;
        this.harmful = defaultHarmful;
        immortal = false;
    }

    public void run() {
        while (!gameOver) {
            try {
                move();
            } catch (InterruptedException e) {
                endGame();
            }
        }
        endGame();
    }

    protected void endGame() {
        this.cellPair.leaveCells(this);
        this.cellPair.clearCells();
        this.cellPair = null;
        this.gameModel = null;
    }

    protected void setDefaultParameters() {
        setImmortal(false);
        setSpeed(defaultSpeed);
        setColor(defaultColor);
        setHarmful(defaultHarmful);
    }

    public void setStartingPosition() {
        if (cellPair != null) {
            cellPair.leaveCells(this);
            cellPair.clearCells();
        }
        this.cellPair = new CellPair();
        Cell cell = gameModel.getCell(startRow, startCol);
        cell.register(this);
        cell.updatePosition(this, 0, 0);
        this.currentCell = cell;
        cellPair.add(cell);
        setDefaultParameters();
        this.isDead = false;
        addUpgrade(new ImmortalUpgrade(10));
    }

    private void resetPosition() {
        cellPair.leaveCells(this);
        cellPair.clearCells();
        this.currentCell = null;
        setStartingPosition();
    }

    public int move() throws InterruptedException {
        if (isDead) {
            Thread.sleep(2000);
            if (lives > 0) {
                resetPosition();
            } else {
                gameOver = true;
            }
        }
        if (newMatch) {
            resetPosition();
            newMatch = false;
        }
        if (paused) {
            Thread.sleep(100);
            return 0;
        }

        int time = 0;
        Cell nextCell = getNextCell();

        dx = nextCell.getCol() - currentCell.getCol();
        dy = nextCell.getRow() - currentCell.getRow();

        time = switchCells(currentCell, nextCell, dx, dy, 20);
        visualToggle = !visualToggle;
        if (upgradeManager != null) {
            setDefaultParameters();
            upgradeManager.applyUpgrades();
        }
        return time;
    }

    abstract Cell getNextCell();

//    protected abstract int[] getDirections();

    public abstract void draw(Coordinates coordinates, Graphics g, int cellSize);

    public int switchCells(Cell currentCell, Cell newCell, int dx, int dy, int sleepTime) throws InterruptedException {
        int time = 0;
        cellPair.add(newCell);

        int distanceMultiplier = Math.max(speed * CELL_TRAVEL_DISTANCE / 20, 1);
        int end_posX = CELL_TRAVEL_DISTANCE * dx;
        int end_posY = CELL_TRAVEL_DISTANCE * dy;
        time += travelBetweenCells(
                currentCell,
                newCell,
                0,
                0,
                -end_posX,
                -end_posY,
                CELL_TRAVEL_DISTANCE / 2,
                distanceMultiplier,
                dx,
                dy,
                sleepTime
        );

        if (isDead) {
            return 0;
        }

        newCell.register(this);
        this.currentCell = newCell;

        time += travelBetweenCells(
                currentCell,
                newCell,
                end_posX / 2,
                end_posY / 2,
                -end_posX / 2,
                -end_posY / 2,
                CELL_TRAVEL_DISTANCE / 2,
                distanceMultiplier,
                dx,
                dy,
                25
        );

        if (isDead) {
            return 0;
        }

        currentCell.leave(this);
        cellPair.remove(currentCell);
        return time;
    }

    private int travelBetweenCells(
            Cell source,
            Cell destination,
            int sourceCellstartX,
            int sourceCellstartY,
            int destinationCellstartX,
            int destinationCellstartY,
            int distance,
            int distanceMultiplier,
            int dx,
            int dy,
            int sleepTime
    ) throws InterruptedException {
        int time = 0;

        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source and destination cannot be null");
        }
        if (!destination.canEnter()) {
            throw new IllegalArgumentException("Destination cannot be entered");
        }
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance must be positive");
        }
        if (distanceMultiplier <= 0) {
            throw new IllegalArgumentException("Distance multiplier must be positive");
        }
        if (dx < -1 || dx > 1 || dy < -1 || dy > 1) {
            throw new IllegalArgumentException("Invalid dx or dy value");
        }

        int sourceCellX = sourceCellstartX;
        int sourceCellY = sourceCellstartY;
        int destinationCellX = destinationCellstartX;
        int destinationCellY = destinationCellstartY;
        int distanceLeft = distance;

        while (distanceLeft > 0) {
            if (isDead) {
                break;
            }
            int distanceInterval = Math.min(
                    distanceMultiplier, distanceLeft
            );
            if (dx != 0) {
                sourceCellX += dx * distanceInterval;
                destinationCellX += dx * distanceInterval;
            } else if (dy != 0) {
                sourceCellY += dy * distanceInterval;
                destinationCellY += dy * distanceInterval;
            }
            source.updatePosition(this, sourceCellX, sourceCellY);
            destination.updatePosition(this, destinationCellX, destinationCellY);
            Thread.sleep(sleepTime);
            distanceLeft -= distanceInterval;
            time += sleepTime;

        }
        return time;
    }

    public void addUpgrade(Upgrade upgrade) {
        if (upgradeManager != null) {
            upgrade.apply(this);
            upgradeManager.addUpgrade(upgrade);
        }
    }

    public void clearUpgrades() {
        if (upgradeManager != null) {
            upgradeManager.clearUpgrades();
        }
    }

    public Queue<Upgrade> getUpgrades() {
        if (upgradeManager != null) {
            return upgradeManager.getUpgrades();
        }
        return null;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void kill() {
        if (!immortal && !isDead) {
            lives--;
            isDead = true;
        }
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public void setUpgradeManager(UpgradeManager upgradeManager) {
        this.upgradeManager = upgradeManager;
    }

    public boolean visualToggle() {
        return visualToggle;
    }


    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setHarmful(boolean b) {
        this.harmful = b;
    }

    public boolean isHarmful() {
        return harmful;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setNewMatch(boolean newMatch) {
        this.newMatch = newMatch;
    }


}
