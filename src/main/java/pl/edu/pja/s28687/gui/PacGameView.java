package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.model.GameModel;
import pl.edu.pja.s28687.model.score.HighScoreManager;
import pl.edu.pja.s28687.model.score.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;

public class PacGameView {

    private final GameModel gameModel;
    private final MainMenu mainMenu;
    private JFrame frame;
    private StatsPanel statsPanel;
    private PacTable pacTable;

    public PacGameView(GameModel gameModel, MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.gameModel = gameModel;
        SwingUtilities.invokeLater(this::generateGUI);
    }

    public void generateGUI() {

        frame = new JFrame("PacMan");
        pacTable = new PacTable(gameModel.getPacTableModel());
        statsPanel = new StatsPanel();

        int rows = pacTable.getRowCount();
        int cols = pacTable.getColumnCount();
        // desired stats panel height will be 10% of the board height
        double desiredAspectRatio = (double) cols / (rows * 1.1);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) dimension.getWidth();
        int screenHeight = (int) dimension.getHeight();
        double screenAspectRatio = (double) screenWidth / screenHeight;

        double defaultFrameWidth;
        double defaultFrameHeight;
        if (desiredAspectRatio > screenAspectRatio) {
            defaultFrameWidth = 0.8 * screenWidth;
            defaultFrameHeight = defaultFrameWidth / desiredAspectRatio;
        } else {
            defaultFrameHeight = 0.8 * screenHeight;
            defaultFrameWidth = defaultFrameHeight * desiredAspectRatio;
        }

        frame.setSize((int) defaultFrameWidth, (int) defaultFrameHeight);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().add(pacTable);
        frame.getContentPane().add(statsPanel.getPanel());

        //Todo: it works on mac but doesn't work well on windows, try to fix it.
//        frame.addComponentListener(new ComponentAdapter() {
//            private Rectangle rectangle;
//
//            @Override
//            public void componentResized(ComponentEvent e) {
//                Rectangle newRectangle = frame.getBounds();
//                if (rectangle != null && rectangle.equals(newRectangle)) {
//                    return;
//                }
//                rectangle = newRectangle;
//                frame.setBounds(newRectangle.x, newRectangle.y, newRectangle.width, (int) (newRectangle.width / desiredAspectRatio));
//            }
//        });

        frame.setLocation((int) (screenWidth - defaultFrameWidth) / 2, (int) ((screenHeight - defaultFrameHeight) / 2));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.requestFocus();

        pacTable.addKeyListener(new PacKeyListener(gameModel));
    }

    public void updateScore(int score, int multiplier) {
        SwingUtilities.invokeLater(() -> statsPanel.updateScore(score, multiplier));
    }

    public void updateTime(int time) {
        SwingUtilities.invokeLater(() -> statsPanel.updateTime(time));
    }

    public void updateLives(int lives) {
        SwingUtilities.invokeLater(() -> statsPanel.updateLives(lives));
    }

    public void startGame() {
        startRepainter(60);
        gameModel.startGame();
    }

    public void submitScore(int score) {
        new JDialog() {
            {
                this.setLocationRelativeTo(frame);
                setLayout(new BorderLayout());
                add(new JLabel("Game Over! Your score: " + score + " Please enter your name!"), BorderLayout.CENTER);
                JTextField textField = new JTextField();
                setAlwaysOnTop(true);
                textField.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (e.getKeyChar() == '\n') {
                            HighScoreManager.addScore(new Score(textField.getText(), score, LocalDate.now()));
                            dispose();
                            showHighScores();
                        }
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });
                add(textField, BorderLayout.SOUTH);
                pack();
                setVisible(true);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        };
        frame.dispose();
        mainMenu.setVisible(true);
    }

    public void showHighScores() {
        new ScoreBoard();
    }

    public void flip() {
        pacTable.flipBoard();
    }

    public void numbersRenderer() {
        pacTable.numberRenderingToggle();
    }

    public void startRepainter(int fps) {
        new Thread(() -> {
            while (true) {
                SwingUtilities.invokeLater(() -> pacTable.repaint());
                try {
                    Thread.sleep(1000 / fps);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void forceQuit() {
        SwingUtilities.invokeLater(() -> frame.dispose());
        SwingUtilities.invokeLater(() -> mainMenu.setVisible(true));
    }
}
