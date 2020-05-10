package file;

import linkedlist.DLL;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import game.User;
import game.Game;

public class Output {
    public static void writeHighScoreTable(DLL highScoreTable, String path) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(new File(path)));
        for (int i = 0; i < Game.HIGHSCOREPLAYERLIMIT; i++) {
            String name = ((User) highScoreTable.get(i)).getName();
            int score = ((User) highScoreTable.get(i)).getScore();
            if (i == Game.HIGHSCOREPLAYERLIMIT - 1) {
                pw.print(name + ";" + score);
            } else {
                pw.print(name + ";" + score + "\n");
            }
        }
        pw.close();
    }
}