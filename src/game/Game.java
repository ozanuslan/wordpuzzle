package game;

import file.Input;
import linkedlist.*;
import java.awt.event.KeyEvent;

public class Game {
    private Board puzzle;
    private Board solution;
    //------------------------------------------IMPORTANT!-----------------------------------------------------//
    /* 
    Word class has isSolution attribute which determines whether a word is in the solution or not
    after tracing the solution board to find words, words in the wordlist must be tagged as true for isSolution
                                    !!! THIS HASN'T BEEN IMPLEMENTED YET!!!
    */
    private SLL wordList; // SLL to hold all words and their completion, solution words will be tagged in here!
    //---------------------------------------------------------------------------------------------------------//
    private SLL wordCoords;
    private MLL checkList; // MLL to check words before they are completed
    private DLL highScoreTable;

    public static final int WINX = 80;
    public static final int WINY = 30;
    private final int FONTSIZE = 22;
    private final int FONTNO = 2;
    private Coordinate playerPos;

    private User user1;
    private User user2;

    public Game(String puzzlePath, String solutionPath, String wordPath, String highscorePath) throws Exception {
        Console.getConsole("Word-Puzzle", WINX, WINY, FONTSIZE, FONTNO);
        Console.setup();
        puzzle = new Board(puzzlePath);
        solution = new Board(solutionPath);
        wordList = Input.readWordList(wordPath);
        Input.readSolutionWordList(solution, puzzle, wordList); //Sets words in the wordlist as solution if they exist in solution board
        checkList = Input.readCheckList(wordList);
        wordCoords = Input.getSolutionWordCoords(puzzle);
        highScoreTable = Input.readHighScoreTable(highscorePath);
        playerPos = new Coordinate(7, 7);
    }

    private void menu() {
        Console.println("Welcome to Word-Puzzle!");
        Console.print("What is player1's name? ");
        user1 = new User(takeUsername());
        Console.print("What is player2's name? ");
        user2 = new User(takeUsername());
        Console.clear();
    }

    private String takeUsername() {
        String name = "";
        boolean isLegitName = false;
        while (!isLegitName) {
            name = Console.readLine();
            if (name.length() < 2) {
                Console.println("Username cannot be shorter than 2", Console.redonblack);
                Console.print("Please enter a legal username: ");
            } else if (name.length() > 10) {
                Console.println("Username cannot be longer than 10", Console.redonblack);
                Console.print("Please enter a legal username: ");
            } else {
                isLegitName = true;
            }
        }
        return name;
    }

    private void takeKeyPress() throws InterruptedException {
        //-------------------------------------IMPORTANT!-------------------------------------------------//
        // Integer returned from the keypress event is always the uppercase equivalent of the key pressed.//
        //------------------------------------------------------------------------------------------------//
        int intKey = Console.takeKeyPress();
        char charKey = (char) intKey;
        if (intKey == KeyEvent.VK_UP && playerPos.getY() > 0) {
            playerPos.setY(playerPos.getY() - 1);
        } else if (intKey == KeyEvent.VK_DOWN && playerPos.getY() < Board.ROWCOUNT - 1) {
            playerPos.setY(playerPos.getY() + 1);
        } else if (intKey == KeyEvent.VK_LEFT && playerPos.getX() > 0) {
            playerPos.setX(playerPos.getX() - 1);
        } else if (intKey == KeyEvent.VK_RIGHT && playerPos.getX() < Board.ROWLENGTH - 1) {
            playerPos.setX(playerPos.getX() + 1);
        } else if (intKey == KeyEvent.VK_SPACE) {
            System.exit(0);
        } else if (intKey > 64 && intKey < 91) { // Key press between A-Z
            puzzle.setElementAt(playerPos.getX(), playerPos.getY(), Character.toString(charKey).toLowerCase());
        }
    }

    private void printCursor(int px, int py) {
        Console.setCursorPosition(playerPos.getX() + 1, playerPos.getY() + 1);
        if (puzzle.getBoard()[playerPos.getY()][playerPos.getX()].equals("0") || puzzle.getBoard()[playerPos.getY()][playerPos.getX()].equals("1")) {
            Console.print("█", Console.greenonblack);
        } else {
            Console.print(puzzle.getBoard()[playerPos.getY()][playerPos.getX()].toUpperCase(), Console.blackongreen);
        }
    }

    public void run() throws InterruptedException {
        // menu();
        puzzle.displayBoard(0, 0, true);
        // solution.displayBoard(17, 0, true);
        wordList.displaySolutionWords(17, 0, true);
        while (true) {
            puzzle.displayBoard(1, 1, false);
            printCursor(playerPos.getX(), playerPos.getY());
            takeKeyPress();
            Thread.sleep(20);
        }
        // wordList.displayUnusedWords(0, 0);
        // checkList.display(Console.greenonblack);
        // highScoreTable.displayFromHead(0,0);
    }
}