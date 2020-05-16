package file;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import linkedlist.*;
import game.*;

public class Input {
    public static SLL ERRORLIST = new SLL();

    // Checks if the read string can be converted to an integer
    private static boolean tryParseInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Reads the two dimensional array from the given path and returns a two dimensional array
    public static String[][] readBoard(String boardPath) {
        String[][] board = new String[Board.ROWCOUNT][Board.ROWLENGTH];
        try {
            Scanner sc = new Scanner(new File(boardPath));
            for (int i = 0; i < Board.ROWCOUNT; i++) {
                String[] row = sc.nextLine().split("");
                if (row.length == Board.ROWLENGTH) { // If the read row has the proper length
                    board[i] = row;
                } else { // If the read row doesn't have the proper length
                    Console.println(boardPath + " has incorrect row length on line " + (i + 1) + ".",
                            Console.redonblack);
                    Console.println("The program cannot continue without a proper "
                            + boardPath.substring(0, boardPath.indexOf(".")) + ".", Console.redonblack);
                    Console.readLine();
                    System.exit(1); // Terminate the program
                }
            }
            sc.close();
            return board;
        } catch (FileNotFoundException e) {
            Console.println(e.toString(), Console.redonblack);
            Console.readLine();
            System.exit(1);
        } catch (Exception e) { // Any other exception that may occour
            Console.println("Possibly incorrect amount of rows.", Console.redonblack);
            Console.readLine();
            System.exit(1);
        }

        return null;
    }

    // Reads the word list at given path and returns a SLL
    public static SLL readWordList(String wordPath) {
        SLL sllWordList = new SLL();

        try {
            Scanner sc = new Scanner(new File(wordPath));
            int lineCount = 0;
            String[] wordData;
            while (sc.hasNextLine()) {
                lineCount++;
                if (lineCount > Game.DICTIONARYLIMIT) { // If the dictionary limit is exceeded
                    Console.setCursorPosition(0, 20);
                    Console.print("Dictionary word limit has been exceed, words coming after the "
                            + Game.DICTIONARYLIMIT + "th word will not be included in the game.", Console.redonblack);
                    break;
                }
                wordData = sc.nextLine().split("\\,"); // Split the string using ','
                if (wordData.length != 2) { // If the split data doesn't have two elements, the data must contain a word and a meaning
                    ERRORLIST.addToEnd(lineCount); // Add the incorrect line to an error list
                } else {
                    if (wordData[0].length() < 2 || wordData[1].length() < 2) { // If the given words are shorter than 2 characters they are invalid
                        ERRORLIST.addToEnd(lineCount);
                    } else { // If the input is correct create a new word and add it to the SLL
                        sllWordList.addToEnd(new Word(wordData[0].toLowerCase(), wordData[1]));
                    }
                }
            }

            int size = sllWordList.size(); // get list size
            Object temp;

            // This loop sorts the sll using bubble sort
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (((Word) sllWordList.get(i)).getWord().compareTo(((Word) sllWordList.get(j)).getWord()) < 0) {
                        temp = sllWordList.get(i);
                        sllWordList.add(i, sllWordList.get(j));
                        sllWordList.add(j, temp);
                    }
                }
            }

            if (ERRORLIST.size() > 0) { // If there are errors
                Console.setCursorPosition(0, 20);
                Console.print("Incorrect word(s) in " + wordPath.substring(0, wordPath.indexOf(".")) + " on line(s): ",
                        Console.redonblack);
            }
        } catch (FileNotFoundException e) {
            Console.println(e.toString(), Console.redonblack);
            Console.readLine();
            System.exit(1);
        }

        return sllWordList;
    }

    // Transfers the words in the SLL to a MLL
    public static MLL readCheckList(SLL sllWordList) {
        MLL checkList = new MLL();

        int wordSize = sllWordList.size();
        Word word;
        for (int i = 0; i < wordSize; i++) {
            word = (Word) sllWordList.get(i);
            if (!checkList.hasOuter(word.getWord().charAt(0))) { // If the MLL doesn't have the letter as its outer node
                checkList.addOuterNode(word.getWord().charAt(0)); // Add the new letter as an outer note
                checkList.addInnerNode(word.getWord().charAt(0), word); // Add the word to the proper letter's list
            } else { // If the MLL has the letter
                if (!checkList.hasInner(word.getWord().charAt(0), word)) { // If the MLL doesn't have the word
                    checkList.addInnerNode(word.getWord().charAt(0), word);
                }
            }
        }
        return checkList;
    }

    // Reads the high-score table at given path and returns a DLL
    public static DLL readHighScoreTable(String highscorePath) {
        DLL highScoreTable = new DLL();

        try {
            Scanner sc = new Scanner(new File(highscorePath));
            String[] playerData;
            int playerCount = 0;
            SLL errorList = new SLL();
            while (sc.hasNextLine()) {
                playerCount++;
                if (playerCount > Game.HIGHSCOREPLAYERLIMIT) { // If the player count exceeds the high-score player limit
                    Console.setCursorPosition(0, 30);
                    Console.println(
                            highscorePath.substring(0, highscorePath.indexOf(".")) + " has more than "
                                    + Game.HIGHSCOREPLAYERLIMIT
                                    + " players. Players exceeding the limit will not be considered.",
                            Console.redonblack);
                    Console.println("Press enter to continue...");
                    Console.readLine();
                } else { // If the player limit hasn't been reached
                    playerData = sc.nextLine().split(";"); // Seperate the player name and score using ';'
                    if (playerData.length != 2) {
                        errorList.addToEnd(playerCount);
                    } else {
                        if (tryParseInt(playerData[1])) { // Checking if the score is parsable
                            highScoreTable.addToEnd(new User(playerData[0], Integer.parseInt(playerData[1])));
                        } else {
                            errorList.addToEnd(playerCount);
                        }
                    }
                }
            }

            if (errorList.size() > 0) { // If the high-score table has errors
                for (int i = 0; i < errorList.size(); i++) {
                    Console.println("Highscore table error on line " + errorList.get(i));
                }
                Console.println("Press enter to continue...");
                Console.readLine();
            }
        } catch (FileNotFoundException e) {
            Console.println(e.toString(), Console.redonblack);
            Console.readLine();
            System.exit(1);
        }

        return highScoreTable;
    }

    // Sets isSolution attribute of the words that are on the solution.txt
    public static void markSolutionWords(Board solution, SLL solutionWordCoords, SLL wordList) {
        int coordCount = solutionWordCoords.size();
        int listSize = wordList.size();
        String solutionWord;
        boolean wordExists;

        for (int i = 0; i < coordCount; i++) {
            solutionWord = findWordOnCoords((Coordinate[]) solutionWordCoords.get(i), solution); // Get the word on the given coordinates
            wordExists = false;
            for (int j = 0; j < listSize; j++) {
                if (solutionWord.equalsIgnoreCase(((Word) wordList.get(j)).getWord())) { // If the words match
                    ((Word) wordList.get(j)).setSolution(true);
                    ((Word) wordList.get(j)).setCoords((Coordinate[]) solutionWordCoords.get(i));
                    wordExists = true;
                    break;
                }
            }
            if (!wordExists) { // Removes edge cases, where coordinates for a word has been found but word doesn't exist in the solution 
                solutionWordCoords.delete(solutionWordCoords.get(i));
            }
        }
    }

    // Finds the coordinates at which there are words
    public static SLL getSolutionWordCoords(Board puzzle) {
        SLL coords = new SLL();
        String[][] b = puzzle.getBoard();
        Coordinate start;
        Coordinate end;
        Coordinate[] startEndCoordinates;

        // Traverse the board elements
        for (int i = 0; i < Board.ROWCOUNT; i++) {
            for (int j = 0; j < Board.ROWLENGTH; j++) {
                if (!(b[i][j].equals("1") || b[i][j].equals("0"))) { // If the current cell is a character
                    if (hasHorizontalWord(j, i, b) || hasVerticalWord(j, i, b)) { // If there's a word for the character found
                        if (hasHorizontalWord(j, i, b)) { // If the word is horizontal
                            start = new Coordinate(j, i); // Set starting coordinates
                            end = traceHorizontalWord(j, i, b); // Set ending coordinates
                            startEndCoordinates = new Coordinate[2];
                            startEndCoordinates[0] = start;
                            startEndCoordinates[1] = end;
                            coords.addToEnd(startEndCoordinates); // Add the coordinates found at the coordinates list
                        }
                        if (hasVerticalWord(j, i, b)) { // If the word is vertical
                            start = new Coordinate(j, i);
                            end = traceVerticalWord(j, i, b);
                            startEndCoordinates = new Coordinate[2];
                            startEndCoordinates[0] = start;
                            startEndCoordinates[1] = end;
                            coords.addToEnd(startEndCoordinates);
                        }
                    } else { // If there's not a word for the character found
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

    // Returns true if there's a word in a horizontal line for given starting coordinates
    private static boolean hasHorizontalWord(int x, int y, String[][] b) {
        if (x > 0 && x < Board.ROWLENGTH - 1) {
            return (b[y][x + 1].equals("1") && b[y][x - 1].equals("0"));
        } else if (x == 0) {
            return (b[y][x + 1].equals("1"));
        } else {
            return false;
        }
    }

    // Returns the end coordinates for the given starting coordinates of a word on horizontal
    private static Coordinate traceHorizontalWord(int x, int y, String[][] b) {
        Coordinate end = null;
        while (x < Board.ROWLENGTH - 1 && !b[y][x + 1].equals("0")) {
            x++;
        }
        end = new Coordinate(x, y);
        return end;
    }

    // Returns true if there's a word in a vertical line for given starting coordinates
    private static boolean hasVerticalWord(int x, int y, String[][] b) {
        if (y > 0 && y < Board.ROWCOUNT - 1) {
            return (b[y + 1][x].equals("1") && b[y - 1][x].equals("0"));
        } else if (y == 0) {
            return (b[y + 1][x].equals("1"));
        } else {
            return false;
        }
    }

    // Returns the end coordinates for the given starting coordinates of a word on vertical
    private static Coordinate traceVerticalWord(int x, int y, String[][] b) {
        Coordinate end = null;
        while (y < Board.ROWCOUNT - 1 && !b[y + 1][x].equals("0")) {
            y++;
        }
        end = new Coordinate(x, y);
        return end;
    }

    // Returns the word for the given start and end coordinates
    private static String findWordOnCoords(Coordinate[] c, Board solution) {
        String[][] b = solution.getBoard();
        String word = "";
        int startX = c[0].getX();
        int startY = c[0].getY();
        int endX = c[1].getX();
        int endY = c[1].getY();
        if (startX != endX && startY == endY) { // Horizontal word
            for (int i = startX; i <= endX; i++) { // Trace the word from start to finish
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