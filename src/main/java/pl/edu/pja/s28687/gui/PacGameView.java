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

    private static double defaultFrameWidth = 1920;
    private static double defaultBoardHeight = 900;
    private double aspectRatio;
    private JFrame frame;
    private StatsPanel statsPanel;

    private final GameModel gameModel;

    private PacTable pacTable;
    private final MainMenu mainMenu;

    public PacGameView(GameModel gameModel, MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.gameModel = gameModel;
        SwingUtilities.invokeLater(this::generateGUI);
    }

    public void generateGUI() {

        pacTable = new PacTable(gameModel.getPacTableModel());
        int rows = pacTable.getRowCount();
        int cols = pacTable.getColumnCount();

        if(rows > 50) {
            defaultBoardHeight = 1200;
        }
        if(cols > 50) {
            defaultFrameWidth = 2560;
        }
        double tableAspectRatio = (double) cols / rows;

        double startingBoardWidth;
        double startingBoardHeight;
        if ( (double) cols / rows > defaultFrameWidth / defaultBoardHeight) {
            startingBoardWidth = defaultFrameWidth;
            startingBoardHeight = defaultFrameWidth / tableAspectRatio;
        } else {
            startingBoardHeight = defaultBoardHeight;
            startingBoardWidth = defaultBoardHeight * tableAspectRatio;
        }


        double statsPanelHeight = startingBoardHeight / 10;
        aspectRatio = startingBoardWidth / (startingBoardHeight + statsPanelHeight);

        statsPanel = new StatsPanel();

        frame = new JFrame("PacMan");
        frame.setSize((int) startingBoardWidth, (int) (startingBoardHeight + statsPanelHeight));
        frame.setSize((int) startingBoardWidth, (int) (startingBoardHeight + statsPanelHeight));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().add(pacTable);
        frame.getContentPane().add(statsPanel.getPanel());


        pacTable.addKeyListener(new PacKeyListener(gameModel));

        //Todo: it works on mac but doesn't work well on windows, try to fix it.
//        frame.addComponentListener(new ComponentAdapter() {
//            private Rectangle rectangle = frame.getBounds();
//
//            @Override
//            public void componentResized(ComponentEvent e) {
//                Rectangle newRectangle = e.getComponent().getBounds();
//                if (rectangle.equals(newRectangle)) {
//                    return;
//                }
//                rectangle = newRectangle;
//                e.getComponent().setBounds(newRectangle.x, newRectangle.y, newRectangle.width, (int) (newRectangle.width / aspectRatio));
//            }
//        });

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.requestFocus();
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
                    public void keyPressed(KeyEvent e) {}
                    @Override
                    public void keyReleased(KeyEvent e) {}
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
