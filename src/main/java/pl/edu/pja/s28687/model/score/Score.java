package pl.edu.pja.s28687.model.score;

import java.io.Serializable;
import java.time.LocalDate;

public class Score implements Serializable, Comparable<Score> {

    private final String name;
    private final int points;
    private final LocalDate date;

    public Score(String name, int points, LocalDate date){
        this.name = name;
        this.points = points;
        this.date = date;
    }

    @Override
    public String toString(){
        return points + " points : " + name + " , " + date.toString();
    }

    @Override
    public int compareTo(Score score) {
        return score.points - this.points;
    }
}
