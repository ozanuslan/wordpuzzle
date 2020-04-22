package game;

import file.Read;
import linkedlist.*;

public class Game {
    Board puzzle;
    Board solution;
    SLL solutionWordList;
    MLL wordList;
    DLL highScoreTable;

    public Game(String puzzlePath, String solutionPath, String wordPath, String highscorePath) {
        Console.getConsole("Word-Puzzle", 60, 30, 15, 1);
        puzzle = new Board(puzzlePath);
        solution = new Board(solutionPath);
        solutionWordList = Read.readSolutionWordList(solutionPath);
        wordList = Read.readWordList(wordPath);
        highScoreTable = Read.readHighScoreTable(highscorePath);
    }

    public void run() {
        puzzle.displayBoard(0, 0, true);
    }
}