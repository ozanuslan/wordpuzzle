package game;

import linkedlist.*;

public class Game {
    Board b;

    public Game(String puzzlePath) {
        Console.getConsole("H", 60, 30, 15, 1);
        b = new Board(puzzlePath);
    }

    public void run() {
        b.displayBoard(0, 0, true);
    }
}