package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.model.score.HighScoreManager;
import pl.edu.pja.s28687.model.score.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ScoreBoard {

    private JList<Score> scoreList;
    private JFrame frame;
    private DefaultListModel<Score> listModel;

    public ScoreBoard() {
        SwingUtilities.invokeLater(this::generateUI);
    }

    private void generateUI() {
        this.frame = new JFrame("High Scores");
        this.listModel = new DefaultListModel<>();
        scoreList = new JList<>(listModel);
        scoreList.setBackground(Color.BLACK);
        scoreList.setCellRenderer(new ScoreRenderer());
        fillScoreList();
        frame.setLayout(new BorderLayout());
        frame.add(new JLabel("High Scores") {
            {
                setFont(new Font("Arial", Font.BOLD, 20));
                setForeground(Color.WHITE);
                setOpaque(true);
                setBackground(Color.BLACK);
                setHorizontalAlignment(CENTER);
            }
        }, BorderLayout.NORTH);
        frame.add(new JScrollPane(scoreList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scoreList.setFont(new Font("Arial", Font.BOLD, frame.getWidth() / 20));
                super.componentResized(e);
            }
        });

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setVisible(true);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    }

    private void fillScoreList() {
        listModel.clear();
        for (Score score : HighScoreManager.getHighScores()) {
            listModel.addElement(score);
        }
    }
}
