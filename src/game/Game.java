package game;

import file.Read;
import linkedlist.*;
import java.awt.event.KeyEvent;

public class Game {
    private Board puzzle;
    private Board solution;
    private SLL solutionWordList;

    //------------------------------------------IMPORTANT!-----------------------------------------------------//
    /* 
    Word class has isSolution attribute which determines whether a word is in the solution or not
    after tracing the solution board to find words, words in the wordlist must be tagged as true for isSolution
                                    !!! THIS HASN'T BEEN IMPLEMENTED YET!!!
    */
    private SLL wordList; // SLL to hold all words and their completion, solution words will be tagged in here!
    //---------------------------------------------------------------------------------------------------------//

    private MLL checkList; // MLL for checking words throughout the game
    private DLL highScoreTable;

    public static final int WINX = 80;
    public static final int WINY = 30;
    private final int FONTSIZE = 22;
    private final int FONTNO = 2;
    private int px, py;

    private User user1;
    private User user2;

    public Game(String puzzlePath, String solutionPath, String wordPath, String highscorePath) throws Exception {
        Console.getConsole("Word-Puzzle", WINX, WINY, FONTSIZE, FONTNO);
        Console.setup();
        puzzle = new Board(puzzlePath);
        solution = new Board(solutionPath);
        solutionWordList = Read.readSolutionWordList(solutionPath, puzzlePath);
        wordList = Read.readWordList(wordPath);
        checkList = Read.readCheckList(wordPath);
        highScoreTable = Read.readHighScoreTable(highscorePath);

        px = py = 7;
    }

    private void menu(){
        
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
        } else if (intKey == KeyEvent.VK_SPACE) {
            System.exit(0);
        } else if (intKey > 64 && intKey < 91) { // Key press between A-Z
            puzzle.setElementAt(px, py, Character.toString(charKey).toLowerCase());
        }
    }

    private void printCursor(int px, int py) {
        Console.setCursorPosition(px + 1, py + 1);
        if (puzzle.getBoard()[py][px].equals("0") || puzzle.getBoard()[py][px].equals("1")) {
            Console.print("█", Console.greenonblack);
        } else {
            Console.print(puzzle.getBoard()[py][px].toUpperCase(), Console.blackongreen);
        }
    }

    public void run() throws InterruptedException {
        puzzle.displayBoard(0, 0, true);
        solution.displayBoard(17, 0, true);
        while (true) {
            puzzle.displayBoard(1, 1, false);
            printCursor(px, py);
            takeKeyPress();
            Thread.sleep(20);
        }
        // wordList.displayUnusedWords(0, 0);
        // checkList.display(Console.greenonblack);
        // highScoreTable.displayFromHead(0,0);
    }
}