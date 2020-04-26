package file;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import linkedlist.*;
import game.Word;
import game.User;
import game.Board;
import game.Console;
import game.Game;

public class Read {
    private static final int DICTIONARYLIMIT = 100;
    private static final int HIGHSCOREPLAYERLIMIT = 10;

    private static boolean tryParseInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String[][] readBoard(String boardPath) {
        String[][] board = new String[Board.ROWCOUNT][Board.ROWLENGTH];
        try {
            Scanner sc = new Scanner(new File(boardPath));
            for (int i = 0; i < Board.ROWCOUNT; i++) {
                String[] row = sc.nextLine().split("");
                if (row.length == Board.ROWLENGTH) {
                    board[i] = row;
                } else {
                    Console.println(boardPath + " has incorrect row length on line " + (i + 1) + ".",
                            Console.redonblack);
                    Console.println("The program cannot continue without a proper "
                            + boardPath.substring(0, boardPath.indexOf(".")) + ".", Console.redonblack);
                    Console.readLine();
                    System.exit(1);
                }
            }
            sc.close();
            return board;
        } catch (FileNotFoundException e) {
            Console.println(e.toString(), Console.redonblack);
            Console.readLine();
            System.exit(1);
        } catch (Exception e) {
            Console.println("Possibly incorrect amount of rows.", Console.redonblack);
            Console.readLine();
            System.exit(1);
        }

        return null;
    }

    public static SLL readWordList(String wordPath) {
        SLL sllWordList = new SLL();
        try {
            Scanner sc = new Scanner(new File(wordPath));
            int lineCount = 0;
            String[] wordData;
            SLL errorList = new SLL();
            while (sc.hasNextLine()) {
                lineCount++;
                if (lineCount > DICTIONARYLIMIT) {
                    Console.setCursorPosition(0, 20);
                    Console.print("Dictionary word limit has been exceed, words coming after the " + DICTIONARYLIMIT
                            + "th word will not be included in the game.", Console.redonblack);
                    break;
                }
                wordData = sc.nextLine().split(",");
                if (wordData.length != 2) {
                    errorList.insert(lineCount);
                } else {
                    if (wordData[0].length() < 2 || wordData[1].length() < 2) {
                        errorList.insert(lineCount);
                    } else {
                        sllWordList.addWordAlphabetically(new Word(wordData[0].toLowerCase(), wordData[1]));
                    }
                }
            }
            if (errorList.size() > 0) {
                Console.setCursorPosition(0, 0);
                Console.print("Incorrect word(s) in " + wordPath.substring(0, wordPath.indexOf(".")) + " on line(s): ",
                        Console.redonblack);
                errorList.display();
            }
        } catch (FileNotFoundException e) {
            Console.println(e.toString(), Console.redonblack);
            Console.readLine();
            System.exit(1);
        }

        return sllWordList;
    }

    public static MLL readCheckList(SLL sllWordList){
        MLL checkList = new MLL();
        checkList.moveSLLToMLLAlphabetically(sllWordList);
        return checkList;
    }

    public static DLL readHighScoreTable(String highscorePath) {
        DLL highScoreTable = new DLL();

        try {
            Scanner sc = new Scanner(new File(highscorePath));
            String[] playerData;
            int playerCount = 0;
            SLL errorList = new SLL();
            while (sc.hasNextLine()) {
                playerCount++;
                if (playerCount > HIGHSCOREPLAYERLIMIT) {
                    Console.setCursorPosition(0, 30);
                    Console.print(highscorePath.substring(0, highscorePath.indexOf(".")) + " has more than "
                            + HIGHSCOREPLAYERLIMIT + " players. Players exceeding the limit will not be considered.",
                            Console.redonblack);
                } else {
                    playerData = sc.nextLine().split(";");
                    if (playerData.length != 2) {
                        errorList.insert(playerCount);
                    } else {
                        if (tryParseInt(playerData[1])) {
                            highScoreTable.add(new User(playerData[0], Integer.parseInt(playerData[1])));
                        } else {
                            errorList.insert(playerCount);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Console.println(e.toString(), Console.redonblack);
            Console.readLine();
            System.exit(1);
        }
        return highScoreTable;
    }

    //TODO: Create a trace algorithm to find words on the solution board
    public static SLL readSolutionWordList(String solutionPath, String puzzlePath) {
        SLL solutionWordList = new SLL();
        return solutionWordList;
    }

    private static void getSolutionWordCoords(){

    }
}