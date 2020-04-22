package game;

import file.Read;
import linkedlist.*;
import java.awt.event.KeyEvent;

public class Game {
    Board puzzle;
    Board solution;
    SLL solutionWordList;
    MLL wordList;
    DLL highScoreTable;

    int px, py;

    public Game(String puzzlePath, String solutionPath, String wordPath, String highscorePath) throws Exception {
        Console.getConsole("Word-Puzzle", 60, 30, 30, 1);
        Console.setup();
        puzzle = new Board(puzzlePath);
        solution = new Board(solutionPath);
        solutionWordList = Read.readSolutionWordList(solutionPath);
        wordList = Read.readWordList(wordPath);
        highScoreTable = Read.readHighScoreTable(highscorePath);

        px = py = 7;
    }

    private void takeKeyPress() throws InterruptedException {
        //------------------------------------------------------------------------------------------------//
        // Integer returned from the keypress event is always the uppercase equivalent of the key pressed.//
        //------------------------------------------------------------------------------------------------//
        int intKey = Console.takeKeyPress();
        char charKey = (char) intKey;
        if (intKey == KeyEvent.VK_UP && py > 0) {
            py--;
        } else if (intKey == KeyEvent.VK_DOWN && py < Board.ROWCOUNT - 1) {
            py++;
        } else if (intKey == KeyEvent.VK_LEFT && px > 0) {
            px--;
        } else if (intKey == KeyEvent.VK_RIGHT && px < Board.ROWLENGTH - 1) {
            px++;
        } else if(intKey == KeyEvent.VK_SPACE){
            System.exit(0);
        }
    }

    private void printCursor(int px, int py) {
        Console.setCursorPosition(px + 1, py + 1);
        Console.print(puzzle.getBoard()[py][px], Console.blackongreen);
    }

    public void run() throws InterruptedException {
        puzzle.displayBoard(0, 0, true);
        while (true) {
            puzzle.displayBoard(1, 1, false);
            printCursor(px, py);
            takeKeyPress();
            Thread.sleep(20);
        }
    }
}