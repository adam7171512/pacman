package pl.edu.pja.s28687.model;

import pl.edu.pja.s28687.model.characters.GameCharacter;
import pl.edu.pja.s28687.model.characters.Ghost;
import pl.edu.pja.s28687.model.characters.Npc;
import pl.edu.pja.s28687.model.characters.Pac;
import pl.edu.pja.s28687.model.collectables.Food;
import pl.edu.pja.s28687.gui.PacGameView;
import pl.edu.pja.s28687.model.logistics.Cell;
import pl.edu.pja.s28687.model.logistics.Directions;
import pl.edu.pja.s28687.model.logistics.MazeAlgo;
import pl.edu.pja.s28687.model.logistics.RouteFindingAlgorithm;
import pl.edu.pja.s28687.model.upgrades.BlowUp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameModel {
    private final PacTableModel pacTableModel;
    private Cell[][] cellArray;

    private PacGameView pacGameView;
    private Thread timeThread;

    private Pac pac;

    private Directions npcDirections = Directions.RANDOM;

    private final List<Npc> npcList = new ArrayList<>();

    private final List<GameCharacter> gameCharacterList = new ArrayList<>();

    private int score;
    private int foodLeft;
    private int scoreMultiplier = 1;
    private int timeLeft;
    private boolean gameOver;
    private int[] directions = new int[2];

    public GameModel() {
        pacTableModel = new PacTableModel();
    }

    public GameModel(PacGameView pacGameView) {
        this.pacGameView = pacGameView;
        pacTableModel = new PacTableModel();
    }

    public void setPacGameView(PacGameView pacGameView) {
        this.pacGameView = pacGameView;
    }


    public void generateMaze(int width, int height) {
        MazeAlgo mazeAlgo = new MazeAlgo();
        generateMaze(width, height, mazeAlgo);
    }

    public void generateMaze(int width, int height, MazeAlgo mazeAlgo) {
        cellArray = mazeAlgo.generateMaze(width, height);
        pacTableModel.setCells(cellArray);
    }

    public Cell getCell(int row, int col) {
        return (Cell) pacTableModel.getValueAt(row, col);
    }

    public int getRowCount() {
        return pacTableModel.getRowCount();
    }

    public int getColumnCount() {
        return pacTableModel.getColumnCount();
    }

    public PacTableModel getPacTableModel() {
        return pacTableModel;
    }


    public void updateLives(int lives) {
        pacGameView.updateLives(lives);
        if (lives <= 0) {
            gameOver();
        }
    }

    public void updateScore(int points, int multiplier) {
        pacGameView.updateScore(points, multiplier);
    }

    public int addPoints(int points) {
        int pointsToAdd = points * scoreMultiplier;
        score += pointsToAdd;
        updateScore(score, scoreMultiplier);
        return pointsToAdd;
    }

    private void createPac() {
        pac = new Pac(this, getRowCount() - 1, getColumnCount() / 2);
        gameCharacterList.add(pac);
    }

    private void createGhosts(int number) {
        for (int i = 0; i < number; i++) {
            Ghost ghost = new Ghost(
                    this,
                    getRowCount() / 2 - 1,
                    getColumnCount() / 2
            );
            npcList.add(ghost);
            gameCharacterList.add(ghost);
        }
    }

    private void createFood() {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                if (!getCell(i, j).canEnter()) {
                    continue;
                }
                Food food = new Food();
                getCell(i, j).addCollectable(food);
                foodLeft++;
            }
        }
    }

    private void prepareGame() {
        createPac();
        int ghosts = (int) (Math.sqrt(getRowCount() * getColumnCount()) / 3);
        createGhosts(ghosts);
        createFood();
    }

    private void updateTime(int timeLeft) {
        pacGameView.updateTime(timeLeft);
        if (timeLeft <= 0) {
            gameOver();
        }
    }

    private void gameOver() {
        endGameProcedure();
        submitScore();
    }

    private void endGameProcedure() {
        Iterator<GameCharacter> iterator = gameCharacterList.iterator();
        while (iterator.hasNext()) {
            GameCharacter gameCharacter = iterator.next();
            gameCharacter.setGameOver(true);
            iterator.remove();
        }
        timeThread.interrupt();
        pac = null;
        npcList.clear();
    }

    private void nextRound() {
        for (GameCharacter gameCharacter : gameCharacterList) {
            gameCharacter.setPaused(true);
        }
        generateMaze(getRowCount(), getColumnCount());
        createFood();
        timeLeft = 120;
        for (GameCharacter gameCharacter : gameCharacterList) {
            gameCharacter.setNewMatch(true);
        }
        for (GameCharacter gameCharacter : gameCharacterList) {
            gameCharacter.setPaused(false);
        }
    }

    private void submitScore() {
        pacGameView.submitScore(score);
    }

    public void startGame() {
        prepareGame();
        for (GameCharacter gameCharacter : npcList) {
            new Thread(gameCharacter).start();
        }
        gameOver = false;
        Thread pacThread = new Thread(pac);
        pacThread.start();
        timeLeft = 120;

        timeThread = new Thread(() -> {
            while (!gameOver) {
                updateTime(timeLeft);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                timeLeft--;
                if (timeLeft == 0) {
                    gameOver();
                    break;
                }
            }
        });
        timeThread.start();
    }

    public void flipBoard() {
        pacGameView.flip();
    }

    public void renderNumbers() {
        pacGameView.numbersRenderer();
    }

    public Pac getPac() {
        return pac;
    }

    public void blowUpNpc() {
        for (Npc npc : npcList) {
            npc.addUpgrade(new BlowUp());
        }
    }

    public synchronized void setDirections(int[] directions) {
        this.directions = directions;
    }

    public int getScoreMultiplier() {
        return scoreMultiplier;
    }

    public void multiplyScoreMultiplier(double scoreMultiplier) {
        this.scoreMultiplier *= scoreMultiplier;
    }

    public synchronized int[] getDirections() {
        return directions;
    }

    public void forceQuit() {
        gameOver = true;
        for (GameCharacter gameCharacter : gameCharacterList) {
            gameCharacter.setGameOver(true);
        }
        pacGameView.forceQuit();
    }

    public void freezeNpc() {
        for (Npc npc : npcList) {
            npc.setPaused(true);
        }
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                for (Npc npc : npcList) {
                    npc.setPaused(false);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void foodConsumed() {
        foodLeft--;
        if (foodLeft == 0) {
            nextRound();
        }
    }

    public Cell getNextCell(Npc npc){
        if (this.pac == null
                || npc == null
                || this.pac.getCurrentCell() == null
                || npc.getCurrentCell() == null) {
            return null;
        }
        return RouteFindingAlgorithm.nextCell(
                npc.getCurrentCell(),
                npc.getLastCell(),
                pac.getCurrentCell(),
                cellArray,
                this.npcDirections);
    }

    public void frightenNpc() {
        this.npcDirections = Directions.FROM_PAC;
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                this.npcDirections = Directions.RANDOM;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void lureNpc(){
        this.npcDirections = Directions.TO_PAC;
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                this.npcDirections = Directions.RANDOM;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void addTime(int seconds) {
        timeLeft += seconds;
    }
}
