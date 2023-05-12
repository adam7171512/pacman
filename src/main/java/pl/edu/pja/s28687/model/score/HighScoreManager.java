package pl.edu.pja.s28687.model.score;

import pl.edu.pja.s28687.model.score.HighScores;
import pl.edu.pja.s28687.model.score.Score;

import java.io.*;
import java.util.List;

public class HighScoreManager {

    private static final String fileName = "scores.dat";
    private static HighScores highScores;

    private static HighScores loadScores(){
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            highScores = (HighScores) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            highScores = new HighScores();
        }
        return highScores;
    }

    private static void saveScores(){
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(highScores);
            oos.close();
        } catch (IOException e) {
            System.err.println("Error saving scores ! " + e.getMessage());
        }
    }

    public static List<Score> getHighScores(){
        return loadScores().getScores();
    }

    public static void addScore(Score score){
        loadScores().addScore(score);
        saveScores();
    }


}
