package file;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import linkedlist.*;
import game.Word;
import game.User;
import game.Board;
import game.Console;

public class Read {
    private static final int DICTIONARYLIMIT = 100;

    public static String[][] readBoard(String boardPath) {
        String[][] board = new String[Board.ROWCOUNT][Board.ROWLENGTH];
        try {
            Scanner sc = new Scanner(new File(boardPath));
            for (int i = 0; i < Board.ROWCOUNT; i++) {
                String[] row = sc.nextLine().split("");
                if (row.length == Board.ROWLENGTH) {
                    board[i] = row;
                } else {
                    Scanner exit = new Scanner(System.in);
                    Console.println(boardPath + " has incorrect row length on line " + (i + 1) + ".",
                            Console.redonblack);
                    Console.println("The program cannot continue without a proper "
                            + boardPath.substring(0, boardPath.indexOf(".")) + ".", Console.redonblack);
                    exit.nextLine();
                    exit.close();
                    System.exit(1);
                }
            }
            sc.close();
            return board;
        } catch (FileNotFoundException e) {
            Scanner sc = new Scanner(System.in);
            Console.println(e.toString(), Console.redonblack);
            sc.nextLine();
            sc.close();
            System.exit(1);
        } catch (Exception e) {
            Scanner sc = new Scanner(System.in);
            Console.println("Possibly incorrect amount of rows.", Console.redonblack);
            sc.nextLine();
            sc.close();
            System.exit(1);
        }

        return null;
    }

    //TODO: Create a trace algorithm for words on the board
    public static SLL readSolutionWordList(String solutionPath) {
        Board solution = new Board(solutionPath);
        SLL solutionWordList = new SLL();
        return solutionWordList;
    }

    public static MLL readWordList(String wordPath) {
        MLL wordList = new MLL();
        try {
            Scanner sc = new Scanner(new File(wordPath));
            int lineCount = 0;
            String[] word;
            SLL errorLines = new SLL();
            while (sc.hasNextLine()) {
                lineCount++;
                if (lineCount > DICTIONARYLIMIT) {
                    Console.setCursorPosition(0, 20);
                    Console.print("Dictionary word limit has been exceed, words coming after the " + DICTIONARYLIMIT
                            + "th word will not be included in the game.", Console.redonblack);
                    break;
                }
                word = sc.nextLine().split(",");
                if (word.length != 2) {
                    errorLines.insert(lineCount);
                } else {
                    if (word[0].length() < 2 || word[1].length() < 2) {
                        errorLines.insert(lineCount);
                    } else {
                        //TODO: insert words with alphabetical order (function must be in MLL class)
                        wordList.addWordAlphabetically(new Word(word[0].toLowerCase(), word[1]));
                    }
                }
            }
            if(errorLines.size() > 0){
                Console.setCursorPosition(0, 0);
                Console.print("Incorrect word(s) in "+wordPath.substring(0, wordPath.indexOf("."))+" on line(s): ", Console.redonblack);
                errorLines.display();
            }
        } catch (FileNotFoundException e) {
            Scanner sc = new Scanner(System.in);
            Console.println(e.toString(), Console.redonblack);
            sc.nextLine();
            sc.close();
            System.exit(1);
        }
        return wordList;
    }

    public static DLL readHighScoreTable(String highscorePath) {
        DLL highScoreTable = new DLL();
        return highScoreTable;
    }
}