package pl.edu.pja.s28687.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Objects;

public class MainMenu {

    private static final Image backgroundImage;

    static {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(MainMenu.class.getResource("/background.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image", e);
        }
    }

    private JFrame frame;
    private JButton startButton;
    private JButton scoreButton;
    private final MainMenu mainMenu;


    private static final double X_BUTTONS_RATIO = 0.3;
    private static final double Y_BUTTON1_RATIO = 0.36;
    private static final double Y_BUTTON2_RATIO = 0.54;
    private static final double BUTTON_WIDTH_RATIO = 0.375;
    private static final double BUTTON_HEIGHT_RATIO = 0.15;

    public MainMenu() {
        mainMenu = this;
        SwingUtilities.invokeLater(this::generateUI);
    }

    private void generateUI() {
        frame = new JFrame("PacMan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, frame.getWidth(), frame.getHeight(), null);
            }
        };
        panel.setVisible(true);
        panel.setOpaque(true);
        frame.add(panel);
        panel.setLayout(null);
        startButton = new JButton() {
            {
                setOpaque(false);
                addActionListener(e -> {
                    frame.setVisible(false);
                    int[] boardSize = askForBoardSize();
                    int rows = boardSize[0];
                    int cols = boardSize[1];
                    try {
                        new PacGame(mainMenu, rows, cols);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }

            @Override
            public void paint(Graphics g) {
            }
        };

        scoreButton = new JButton("High Scores") {
            {
                setOpaque(false);
                addActionListener(e -> {
                    new ScoreBoard();
                });
            }

            @Override
            public void paint(Graphics g) {
            }
        };

        panel.add(startButton);
        panel.add(scoreButton);

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int xBound = (int) (frame.getWidth() * X_BUTTONS_RATIO);
                int yBound1 = (int) (frame.getHeight() * Y_BUTTON1_RATIO);
                int yBound2 = (int) (frame.getHeight() * Y_BUTTON2_RATIO);
                int width = (int) (frame.getWidth() * BUTTON_WIDTH_RATIO);
                int height = (int) (frame.getHeight() * BUTTON_HEIGHT_RATIO);
                startButton.setBounds(xBound, yBound1, width, height);
                scoreButton.setBounds(xBound, yBound2, width, height);
                super.componentResized(e);

            }
        });
    }


    private int[] askForBoardSize() {
        JTextField width = new JTextField();
        JTextField height = new JTextField();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Rows: (min 10, max 100)"));
        panel.add(width);
        panel.add(new JLabel("Columns (min 10, max 100):"));
        panel.add(height);
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter board size", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Integer.parseInt(width.getText());
                Integer.parseInt(height.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Board size must be a integer", "Error", JOptionPane.ERROR_MESSAGE);
                return askForBoardSize();
            }
            int[] boardSize = new int[]{Integer.parseInt(width.getText()), Integer.parseInt(height.getText())};
            if (!validateBoardSizeInput(boardSize)) {
                JOptionPane.showMessageDialog(null, "Numbers must be between 10 and 100", "Error", JOptionPane.ERROR_MESSAGE);
                return askForBoardSize();
            }
            return boardSize;
        } else {
            return null;
        }
    }

    private boolean validateBoardSizeInput(int[] boardSize) {
        return boardSize[0] >= 10 && boardSize[0] <= 100 && boardSize[1] >= 10 && boardSize[1] <= 100;
    }

    public void setVisible(boolean b) {
        SwingUtilities.invokeLater(() -> frame.setVisible(b));
    }
}
