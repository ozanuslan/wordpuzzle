import game.Game;

public class Main {
    public static void main(String[] args) {
        Game g = new Game("puzzle.txt", "solution.txt", "word_list.txt", "high_score_table.txt");
        g.run();
    }
}