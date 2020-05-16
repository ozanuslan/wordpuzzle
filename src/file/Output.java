package file;

import linkedlist.DLL;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import game.User;
import game.Game;

public class Output {

    // Writes the highscore to the given path
    public static void writeHighScoreTable(DLL highScoreTable, String path) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(new File(path))); // Create a new outputstream

        for (int i = 0; i < Game.HIGHSCOREPLAYERLIMIT; i++) {
            String name = ((User) highScoreTable.get(i)).getName();
            int score = ((User) highScoreTable.get(i)).getScore();
            
            // Print the name and the score in conjuction with ';'
            if (i == Game.HIGHSCOREPLAYERLIMIT - 1) { // If the last element
                pw.print(name + ";" + score); 
            } else { // If not the last element
                pw.print(name + ";" + score + "\n"); 
            }
        }
        pw.close();
    }
}