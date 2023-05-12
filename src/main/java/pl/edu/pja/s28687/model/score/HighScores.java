package pl.edu.pja.s28687.model.score;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class HighScores implements Serializable {
    private final List<Score> scores;

    public HighScores() {
        scores = new LinkedList<>();
    }

    public void addScore(Score score) {
        scores.add(score);
        Collections.sort(scores);
    }

    public List<Score> getScores() {
        return scores;
    }
}
