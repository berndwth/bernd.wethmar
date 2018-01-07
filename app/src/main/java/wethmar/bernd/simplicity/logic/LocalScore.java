package wethmar.bernd.simplicity.logic;

/**
 * Created by Bernd on 22/12/2017.
 */

public class LocalScore {
    long id;
    String name;
    int score;

    public LocalScore() {
    }

    public LocalScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
