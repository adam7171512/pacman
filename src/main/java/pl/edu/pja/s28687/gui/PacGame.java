package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.gui.MainMenu;
import pl.edu.pja.s28687.gui.PacGameView;
import pl.edu.pja.s28687.model.GameModel;

public class PacGame {

    public PacGame(MainMenu mainMenu, int rows, int cols) throws InterruptedException {
        GameModel gameModel = new GameModel();
        gameModel.generateMaze(rows, cols);
        PacGameView pacGameView = new PacGameView(gameModel, mainMenu);
        gameModel.setPacGameView(pacGameView);
        pacGameView.startGame();
    }
}
