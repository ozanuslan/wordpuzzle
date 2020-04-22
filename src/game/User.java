package game;

public class User {
    String name;
    int score;

    public User(String name) {
        this.name = name;
        score = 0;
    }

    public User(String name, int score) {
        this.name = name;
        this.score = score;
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

    public void incrementScore(int amount) {
        score += score;
    }
}