package file;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import linkedlist.*;
import game.*;

public class Input {
    public static final int DICTIONARYLIMIT = 100;
    public static final int HIGHSCOREPLAYERLIMIT = 10;

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
                wordData = sc.nextLine().split("\\,");
                if (wordData.length != 2) {
                    errorList.addToEnd(lineCount);
                } else {
                    if (wordData[0].length() < 2 || wordData[1].length() < 2) {
                        errorList.addToEnd(lineCount);
                    } else {
                        sllWordList.addToEnd(new Word(wordData[0].toLowerCase(), wordData[1]));
                    }
                }
            }

            int size = sllWordList.size();
            Object temp;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (((Word) sllWordList.get(i)).getWord().compareTo(((Word) sllWordList.get(j)).getWord()) < 0) {
                        temp = sllWordList.get(i);
                        sllWordList.add(i, sllWordList.get(j));
                        sllWordList.add(j, temp);
                    }
                }
            }

            if (errorList.size() > 0) {
                Console.setCursorPosition(0, 20);
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

    public static MLL readCheckList(SLL sllWordList) {
        MLL checkList = new MLL();
        int wordSize = sllWordList.size();
        Word word;
        for (int i = 0; i < wordSize; i++) {
            word = (Word) sllWordList.get(i);
            if (!checkList.hasOuter(word.getWord().charAt(0))) {
                checkList.addOuterNode(word.getWord().charAt(0));
                checkList.addInnerNode(word.getWord().charAt(0), word);
            } else {
                if (!checkList.hasInner(word.getWord().charAt(0), word)) {
                    checkList.addInnerNode(word.getWord().charAt(0), word);
                }
            }
        }
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
                        errorList.addToEnd(playerCount);
                    } else {
                        if (tryParseInt(playerData[1])) {
                            highScoreTable.addToEnd(new User(playerData[0], Integer.parseInt(playerData[1])));
                        } else {
                            errorList.addToEnd(playerCount);
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

    public static void markSolutionWords(Board solution, SLL solutionWordCoords, SLL wordList) {
        int coordCount = solutionWordCoords.size();
        int wordSize = wordList.size();
        String solutionWord;
        for (int i = 0; i < coordCount; i++) {
            solutionWord = findWordOnCoords((Coordinate[]) solutionWordCoords.get(i), solution);
            for (int j = 0; j < wordSize; j++) {
                if (solutionWord.equalsIgnoreCase(((Word) wordList.get(j)).getWord())) {
                    ((Word) wordList.get(j)).setSolution(true);
                    ((Word) wordList.get(j)).setCoords((Coordinate[]) solutionWordCoords.get(i));
                    break;
                }
            }
        }
    }

    public static SLL getSolutionWordCoords(Board puzzle) {
        SLL coords = new SLL();
        String[][] b = puzzle.getBoard();
        Coordinate start;
        Coordinate end;
        Coordinate[] startEndCoordinates;
        for (int i = 0; i < Board.ROWCOUNT; i++) {
            for (int j = 0; j < Board.ROWLENGTH; j++) {
                if (!(b[i][j].equals("1") || b[i][j].equals("0"))) {
                    if (hasHorizontalWord(j, i, b) || hasVerticalWord(j, i, b)) {
                        if (hasHorizontalWord(j, i, b)) {
                            start = new Coordinate(j, i);
                            end = traceHorizontalWord(j, i, b);
                            startEndCoordinates = new Coordinate[2];
                            startEndCoordinates[0] = start;
                            startEndCoordinates[1] = end;
                            coords.addToEnd(startEndCoordinates);
                        }
                        if (hasVerticalWord(j, i, b)) {
                            start = new Coordinate(j, i);
                            end = traceVerticalWord(j, i, b);
                            startEndCoordinates = new Coordinate[2];
                            startEndCoordinates[0] = start;
                            startEndCoordinates[1] = end;
                            coords.addToEnd(startEndCoordinates);
                        }
                    } else {
                        Console.println("The puzzle has doesn't have a word for the character at " + j + " " + i
                                + " [x,y] coordinates.", Console.redonblack);
                        Console.println("The program cannot continue without a proper puzzle.", Console.redonblack);
                        Console.readLine();
                        System.exit(1);
                    }
                }
            }
        }
        return coords;
    }

    private static boolean hasHorizontalWord(int x, int y, String[][] b) {
        if (x > 0 && x < Board.ROWLENGTH - 1) {
            return (b[y][x + 1].equals("1") && b[y][x - 1].equals("0"));
        } else if (x == 0) {
            return (b[y][x + 1].equals("1"));
        } else {
            return false;
        }
    }

    private static Coordinate traceHorizontalWord(int x, int y, String[][] b) {
        Coordinate end = null;
        while (x < Board.ROWLENGTH - 1 && !b[y][x + 1].equals("0")) {
            x++;
        }
        end = new Coordinate(x, y);
        return end;
    }

    private static boolean hasVerticalWord(int x, int y, String[][] b) {
        if (y > 0 && y < Board.ROWCOUNT - 1) {
            return (b[y + 1][x].equals("1") && b[y - 1][x].equals("0"));
        } else if (y == 0) {
            return (b[y + 1][x].equals("1"));
        } else {
            return false;
        }
    }

    private static Coordinate traceVerticalWord(int x, int y, String[][] b) {
        Coordinate end = null;
        while (y < Board.ROWCOUNT - 1 && !b[y + 1][x].equals("0")) {
            y++;
        }
        end = new Coordinate(x, y);
        return end;
    }

    private static String findWordOnCoords(Coordinate[] c, Board solution) {
        String[][] b = solution.getBoard();
        String word = "";
        int startX = c[0].getX();
        int startY = c[0].getY();
        int endX = c[1].getX();
        int endY = c[1].getY();
        if (startX != endX && startY == endY) { // Horizontal word
            for (int i = startX; i <= endX; i++) {
                word += b[startY][i];
            }
        } else if (startY != endY && startX == endX) { // Vertical word
            for (int i = startY; i <= endY; i++) {
                word += b[i][startX];
            }
        }
        return word;
    }
}