package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.model.GameModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PacKeyListener implements KeyListener {
    private final GameModel gameModel;

    public PacKeyListener(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if
        (
                e.getKeyCode() == KeyEvent.VK_Q
                        && e.isControlDown()
                        && e.isShiftDown()
        ) {
            gameModel.forceQuit();
        }

        int[] directions = new int[]{0, 0};
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                directions = new int[]{0, -1};
                break;
            case KeyEvent.VK_DOWN:
                directions = new int[]{0, 1};
                break;
            case KeyEvent.VK_LEFT:
                directions = new int[]{-1, 0};
                break;
            case KeyEvent.VK_RIGHT:
                directions = new int[]{1, 0};
                break;
        }
        gameModel.setDirections(directions);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
