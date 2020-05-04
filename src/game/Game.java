package game;

import file.Input;
import file.Output;
import linkedlist.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;

public class Game {
    private Board puzzle;
    private Board solution;
    private SLL wordList;
    private SLL wordCoords;
    private SLL legalCoords;
    private SLL incorrectCharCoords;
    private MLL checkList; // MLL to check words before they are completed
    private DLL highScoreTable;
    private int solutionWordCount;

    private String highscorePath;

    public static final int WINX = 116;
    public static final int WINY = 30;
    private final int FONTSIZE = 20;
    private final int FONTNO = 2;
    private Coordinate playerPos;

    private User user1;
    private User user2;
    private boolean isCompleteGame;
    private boolean turn; // True for user1, false for user2

    public Game(String puzzlePath, String solutionPath, String wordPath, String highscorePath) throws Exception {
        Console.getConsole("Word-Puzzle", WINX, WINY, FONTSIZE, FONTNO);
        Console.setup();
        puzzle = new Board(puzzlePath);
        solution = new Board(solutionPath);
        wordList = Input.readWordList(wordPath);
        wordCoords = Input.getSolutionWordCoords(puzzle);
        Input.markSolutionWords(solution, wordCoords, wordList); //Sets words in the wordlist as solution if they exist in solution board
        checkList = Input.readCheckList(wordList);
        highScoreTable = Input.readHighScoreTable(highscorePath);
        legalCoords = new SLL();
        incorrectCharCoords = new SLL();
        solutionWordCount = countSolutionWords();

        this.highscorePath = highscorePath;

        playerPos = new Coordinate(7, 7);
        turn = true;
    }

    private int countSolutionWords() {
        int count = 0;
        int listSize = wordList.size();
        for (int i = 0; i < listSize; i++) {
            if (((Word) wordList.get(i)).isSolution()) {
                count++;
            }
        }
        return count;
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

    private void printWords(int x, int y, boolean hasDisplayFrame) {
        int generalOffset;
        if (hasDisplayFrame) {
            generalOffset = 1;
        } else {
            generalOffset = 0;
        }
        int listSize = wordList.size();
        int maxWordLen = -1;
        int rowOffset = 0;
        int columnOffset = 0;
        for (int i = 0; i < listSize; i++) {
            // if (((Word) wordList.get(i)).isSolution()) {
            Console.setCursorPosition(x + generalOffset + columnOffset, y + generalOffset + rowOffset);
            if (((Word) wordList.get(i)).isComplete()) {
                Console.print("[X]" + ((Word) wordList.get(i)).getWord().toUpperCase(), Console.greenonblack);
            } else {
                Console.print("[ ]" + ((Word) wordList.get(i)).getWord().toUpperCase());
            }
            rowOffset++;
            maxWordLen = Math.max(maxWordLen, ((Word) wordList.get(i)).getWord().length());
            if (rowOffset >= Board.ROWCOUNT && i != listSize - 1) {
                rowOffset = 0;
                columnOffset += maxWordLen + 3;
                maxWordLen = 0;
            }
            // }
        }
        columnOffset += maxWordLen;
        printWordFrame(x, y, columnOffset);
    }

    private void printWordFrame(int x, int y, int columnOffset) {
        int frameRowCount = Board.ROWCOUNT + 2;
        int frameRowLength = columnOffset + 5;
        for (int i = 0; i < frameRowCount; i++) {
            for (int j = 0; j < frameRowLength; j++) {
                Console.setCursorPosition(x + j, y + i);
                if (i == 0 && j == 0) {
                    Console.print("╔");
                } else if (i == 0 && j == 1) {
                    Console.print("WORD-LIST");
                    j += 8;
                } else if (i == 0 && j == frameRowLength - 1) {
                    Console.print("╗");
                } else if (i == frameRowCount - 1 && j == 0) {
                    Console.print("╚");
                } else if (i == frameRowCount - 1 && j == frameRowLength - 1) {
                    Console.print("╝");
                } else if ((i == 0 && j > 8 && j < frameRowLength - 1)
                        || (i == frameRowCount - 1 && j > 0 && j < frameRowLength - 1)) {
                    Console.print("═");
                } else if ((j == 0 || j == frameRowLength - 1) && (i > 0 && i < frameRowCount - 1)) {
                    Console.print("║");
                }
            }
        }
    }

    private void displayUnusedWords(int x, int y) {
        int wordSize = wordList.size();
        Console.setCursorPosition(x, y);
        Console.print("Unused words -> ");
        for (int i = 0; i < wordSize; i++) {
            if (!((Word) wordList.get(i)).isComplete()) {
                Console.print(((Word) wordList.get(i)).getWord() + " ");
            }
        }
    }

    private void displayHighScoreTable(int x, int y, int numberOfPlayersToDisplay) {
        highScoreTable.addToEnd(user1);
        highScoreTable.addToEnd(user2);
        int size = highScoreTable.size();
        Object temp;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (((User) highScoreTable.get(i)).getScore() > ((User) highScoreTable.get(j)).getScore()) {
                    temp = highScoreTable.get(i);
                    highScoreTable.add(i, highScoreTable.get(j));
                    highScoreTable.add(j, temp);
                }
            }
        }
        if (numberOfPlayersToDisplay > 0 && numberOfPlayersToDisplay < highScoreTable.size()) {
            for (int i = 0; i < numberOfPlayersToDisplay; i++) {
                Console.setCursorPosition(x, y + i);
                Console.print((i + 1) + " " + ((User) highScoreTable.get(i)).getName() + " "
                        + ((User) highScoreTable.get(i)).getScore());
            }
        }
    }

    private void printCursor(int px, int py) {
        Console.setCursorPosition(playerPos.getX() + 1, playerPos.getY() + 1);
        if (puzzle.getBoard()[playerPos.getY()][playerPos.getX()].equals("0")
                || puzzle.getBoard()[playerPos.getY()][playerPos.getX()].equals("1")) {
            Console.print("█", Console.greenonblack);
        } else {
            Console.print(puzzle.getBoard()[playerPos.getY()][playerPos.getX()].toUpperCase(), Console.blackongreen);
        }
    }

    private void askWordMeaning(Word w) {
        Random rnd = new Random();
        String[] options = new String[3];
        int ansIndex = rnd.nextInt(options.length);
        options[ansIndex] = w.getMeaning();

        String meaning = "";
        boolean hasDuplicate;
        for (int i = 0; i < options.length; i++) {
            if (i != ansIndex) {
                hasDuplicate = true;
                while (hasDuplicate) {
                    hasDuplicate = false;
                    meaning = ((Word) wordList.get(rnd.nextInt(wordList.size()))).getMeaning();
                    for (int j = 0; j < options.length; j++) {
                        if (meaning.equalsIgnoreCase(options[j])) {
                            hasDuplicate = true;
                        }
                    }
                }
                options[i] = meaning;
            }
        }

        Console.setCursorPosition(1, Board.ROWCOUNT + 3);
        Console.println("What is the meaning of " + w.getWord() + " in Turkish? Please enter your option.");
        Console.setCursorPosition(1, Board.ROWCOUNT + 4);
        for (int i = 0; i < options.length; i++) {
            Console.print(Character.toString(i + 65) + ") " + options[i] + " ");
        }
        Console.println("");

        boolean isValidAns = false;
        String ans = "";
        while (!isValidAns) {
            for (int i = 0; i < ans.length(); i++) {
                Console.setCursorPosition(i + 9, Board.ROWCOUNT + 5);
                Console.print(" ");
            }
            Console.setCursorPosition(1, Board.ROWCOUNT + 5);
            Console.print("Answer: ");
            ans = Console.readLine();
            if (ans.length() != 1) {
                continue;
            } else {
                if ((ans.charAt(0) - 65 >= 0 && ans.charAt(0) - 65 < options.length)
                        || (ans.charAt(0) - 97 >= 0 && ans.charAt(0) - 97 < options.length)) {
                    isValidAns = true;
                } else {
                    continue;
                }
            }
        }
        if ((ans.charAt(0) - 65 == ansIndex) || (ans.charAt(0) - 97 == ansIndex)) {
            //TODO: Add +10 points to the current user
        }
    }

    private int countCompletedWords() {
        int count = 0;
        int listSize = wordList.size();
        for (int i = 0; i < listSize; i++) {
            if (((Word) wordList.get(i)).isComplete()) {
                count++;
            }
        }
        return count;
    }

    private boolean isLegalChar(char c) {
        String charToCheck = Character.toString(c);
        if (legalCoords.size() == 0) {
            SLL tempCoords = new SLL();
            if (hasVertical()) {
                tempCoords.addToEnd(getVerticalCoords());
            }
            if (hasHorizontal()) {
                tempCoords.addToEnd(getHorizontalCoords());
            }
            int tempSize = tempCoords.size();
            boolean isLegal = false;
            for (int i = 0; i < tempSize; i++) {
                isLegal = checkPattern((Coordinate[]) tempCoords.get(i), charToCheck);
                if (!isLegal) {
                    break;
                }
            }
            if (isLegal) {
                for (int i = 0; i < tempSize; i++) {
                    legalCoords.addToEnd((Coordinate[]) tempCoords.get(i));
                }
                if (!checkRealPattern((Coordinate[]) legalCoords.get(0), charToCheck)) {
                    Coordinate coord = new Coordinate(playerPos.getX(), playerPos.getY());
                    incorrectCharCoords.addToEnd(coord);
                }
                return true;
            } else {
                return false;
            }
        } else if (legalCoords.size() > 1) {
            int legalSize = legalCoords.size();
            boolean isWithinLegalCoords = false;
            Coordinate startCoord;
            Coordinate endCoord;
            Coordinate[] tempCoord;
            for (int i = 0; i < legalSize; i++) {
                tempCoord = (Coordinate[]) legalCoords.get(i);
                startCoord = tempCoord[0];
                endCoord = tempCoord[1];
                if (playerPos.getX() == startCoord.getX() && playerPos.getX() == endCoord.getX()) {
                    if (playerPos.getY() >= startCoord.getY() && playerPos.getY() <= endCoord.getY()) {
                        isWithinLegalCoords = true;
                    }
                } else if (playerPos.getY() == startCoord.getY() && playerPos.getY() == endCoord.getY()) {
                    if (playerPos.getX() >= startCoord.getX() && playerPos.getX() <= endCoord.getX()) {
                        isWithinLegalCoords = true;
                    }
                }
                if (isWithinLegalCoords) {
                    legalCoords.empty();
                    legalCoords.addToEnd(tempCoord);
                    break;
                }
            }
            if (isWithinLegalCoords) {
                SLL tempCoords = new SLL();
                if (hasVertical()) {
                    tempCoords.addToEnd(getVerticalCoords());
                }
                if (hasHorizontal()) {
                    tempCoords.addToEnd(getHorizontalCoords());
                }
                boolean isLegal;
                int tempSize = tempCoords.size();
                for (int i = 0; i < tempSize; i++) {
                    isLegal = checkPattern((Coordinate[]) tempCoords.get(i), charToCheck);
                    if (!isLegal) {
                        return false;
                    }
                }
                if (!checkRealPattern((Coordinate[]) legalCoords.get(0), charToCheck)) {
                    Coordinate coord = new Coordinate(playerPos.getX(), playerPos.getY());
                    incorrectCharCoords.addToEnd(coord);
                }
                return true;
            } else {
                return false;
            }
        } else {
            boolean isWithinLegalCoords = false;
            Coordinate startCoord = ((Coordinate[]) legalCoords.get(0))[0];
            Coordinate endCoord = ((Coordinate[]) legalCoords.get(0))[1];

            if (playerPos.getX() == startCoord.getX() && playerPos.getX() == endCoord.getX()) {
                if (playerPos.getY() >= startCoord.getY() && playerPos.getY() <= endCoord.getY()) {
                    isWithinLegalCoords = true;
                }
            } else if (playerPos.getY() == startCoord.getY() && playerPos.getY() == endCoord.getY()) {
                if (playerPos.getX() >= startCoord.getX() && playerPos.getX() <= endCoord.getX()) {
                    isWithinLegalCoords = true;
                }
            }
            if (isWithinLegalCoords) {
                SLL tempCoords = new SLL();
                if (hasVertical()) {
                    tempCoords.addToEnd(getVerticalCoords());
                }
                if (hasHorizontal()) {
                    tempCoords.addToEnd(getHorizontalCoords());
                }
                boolean isLegal;
                int tempSize = tempCoords.size();
                for (int i = 0; i < tempSize; i++) {
                    isLegal = checkPattern((Coordinate[]) tempCoords.get(i), charToCheck);
                    if (!isLegal) {
                        return false;
                    }
                }
                if (!checkRealPattern((Coordinate[]) legalCoords.get(0), charToCheck)) {
                    Coordinate coord = new Coordinate(playerPos.getX(), playerPos.getY());
                    incorrectCharCoords.addToEnd(coord);
                }
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean checkRealPattern(Coordinate[] coords, String ch) {
        String patternToCheck = "";
        if (coords[0].getX() == coords[1].getX()) {
            for (int i = coords[0].getY(); i <= coords[1].getY(); i++) {
                if (i != playerPos.getY()) {
                    patternToCheck += puzzle.getBoard()[i][coords[0].getX()];
                } else {
                    patternToCheck += ch;
                }
            }
            return hasMatchingRealPattern(coords, patternToCheck);
        } else if (coords[0].getY() == coords[1].getY()) {
            for (int i = coords[0].getX(); i <= coords[1].getX(); i++) {
                if (i != playerPos.getX()) {
                    patternToCheck += puzzle.getBoard()[coords[0].getY()][i];
                } else {
                    patternToCheck += ch;
                }
            }
            return hasMatchingRealPattern(coords, patternToCheck);
        }
        return false;
    }

    private boolean hasMatchingRealPattern(Coordinate[] coords, String pattern) {
        int listSize = wordList.size();
        for (int i = 0; i < listSize; i++) {
            Word word = (Word) wordList.get(i);
            if (word.isSolution() && word.getCoords() == coords) {
                String wordStr = word.getWord();
                int wordLen = wordStr.length();
                for (int j = 0; j < wordLen; j++) {
                    char ch = pattern.charAt(j);
                    if (ch == '1') {
                        continue;
                    } else {
                        char wordCh = wordStr.charAt(j);
                        if (ch != wordCh) {
                            return false;
                        }
                    }
                }
                break;
            }
        }

        return true;
    }

    private boolean checkPattern(Coordinate[] coords, String ch) {
        String patternToCheck = "";
        if (coords[0].getX() == coords[1].getX()) {
            for (int i = coords[0].getY(); i <= coords[1].getY(); i++) {
                if (i != playerPos.getY()) {
                    patternToCheck += puzzle.getBoard()[i][coords[0].getX()];
                } else {
                    patternToCheck += ch;
                }
            }
            return hasMatchingPattern(patternToCheck);
        } else if (coords[0].getY() == coords[1].getY()) {
            for (int i = coords[0].getX(); i <= coords[1].getX(); i++) {
                if (i != playerPos.getX()) {
                    patternToCheck += puzzle.getBoard()[coords[0].getY()][i];
                } else {
                    patternToCheck += ch;
                }
            }
            return hasMatchingPattern(patternToCheck);
        }
        return false;
    }

    private boolean hasMatchingPattern(String pattern) {
        int innerSize;
        int outerSize = checkList.outerSize();
        boolean flag;
        for (int i = 0; i < outerSize; i++) {
            if ((char) checkList.getOuterDataAt(i) == pattern.charAt(0)) {
                innerSize = checkList.innerSize(pattern.charAt(0));
                for (int j = 0; j < innerSize; j++) {
                    flag = true;
                    if (((Word) checkList.getInnerDataAt(i, j)).getWord().length() == pattern.length()) {
                        Word word = ((Word) checkList.getInnerDataAt(i, j));
                        String wordStr = word.getWord();
                        for (int k = 0; k < pattern.length(); k++) {
                            char ch = pattern.charAt(k);
                            char wordCh = wordStr.charAt(k);
                            if (ch == '1') {
                                continue;
                            } else {
                                if (ch != wordCh) {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    } else {
                        flag = false;
                    }
                    if (flag) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean hasVertical() {
        int coordsSize = wordCoords.size();
        for (int i = 0; i < coordsSize; i++) {
            if (playerPos.getX() == ((Coordinate[]) wordCoords.get(i))[0].getX()
                    && playerPos.getX() == ((Coordinate[]) wordCoords.get(i))[1].getX()
                    && playerPos.getY() >= ((Coordinate[]) wordCoords.get(i))[0].getY()
                    && playerPos.getY() <= ((Coordinate[]) wordCoords.get(i))[1].getY()) {
                return true;
            }
        }
        return false;
    }

    private Coordinate[] getVerticalCoords() {
        int coordsSize = wordCoords.size();
        for (int i = 0; i < coordsSize; i++) {
            if (playerPos.getX() == ((Coordinate[]) wordCoords.get(i))[0].getX()
                    && playerPos.getX() == ((Coordinate[]) wordCoords.get(i))[1].getX()
                    && playerPos.getY() >= ((Coordinate[]) wordCoords.get(i))[0].getY()
                    && playerPos.getY() <= ((Coordinate[]) wordCoords.get(i))[1].getY()) {
                return ((Coordinate[]) wordCoords.get(i));
            }
        }
        return null;
    }

    private boolean hasHorizontal() {
        int coordsSize = wordCoords.size();
        for (int i = 0; i < coordsSize; i++) {
            if (playerPos.getY() == ((Coordinate[]) wordCoords.get(i))[0].getY()
                    && playerPos.getY() == ((Coordinate[]) wordCoords.get(i))[1].getY()
                    && playerPos.getX() >= ((Coordinate[]) wordCoords.get(i))[0].getX()
                    && playerPos.getX() <= ((Coordinate[]) wordCoords.get(i))[1].getX()) {
                return true;
            }
        }
        return false;
    }

    private Coordinate[] getHorizontalCoords() {
        int coordsSize = wordCoords.size();
        for (int i = 0; i < coordsSize; i++) {
            if (playerPos.getY() == ((Coordinate[]) wordCoords.get(i))[0].getY()
                    && playerPos.getY() == ((Coordinate[]) wordCoords.get(i))[1].getY()
                    && playerPos.getX() >= ((Coordinate[]) wordCoords.get(i))[0].getX()
                    && playerPos.getX() <= ((Coordinate[]) wordCoords.get(i))[1].getX()) {
                return ((Coordinate[]) wordCoords.get(i));
            }
        }
        return null;
    }

    private boolean isFinalChar(char ch) {
        if (legalCoords.size() == 1) {
            String patternToCheck = "";
            Coordinate[] coords = (Coordinate[]) legalCoords.get(0);
            if (coords[0].getX() == coords[1].getX()) {
                for (int i = coords[0].getY(); i <= coords[1].getY(); i++) {
                    if (i != playerPos.getY()) {
                        patternToCheck += puzzle.getBoard()[i][coords[0].getX()];
                    } else {
                        patternToCheck += ch;
                    }
                }
            } else if (coords[0].getY() == coords[1].getY()) {
                for (int i = coords[0].getX(); i <= coords[1].getX(); i++) {
                    if (i != playerPos.getX()) {
                        patternToCheck += puzzle.getBoard()[coords[0].getY()][i];
                    } else {
                        patternToCheck += ch;
                    }
                }
            }
            return !patternToCheck.contains("1");
        }
        return false;
    }

    private boolean isFinalChar() {
        if (legalCoords.size() == 1) {
            String patternToCheck = "";
            Coordinate[] coords = (Coordinate[]) legalCoords.get(0);
            if (coords[0].getX() == coords[1].getX()) {
                for (int i = coords[0].getY(); i <= coords[1].getY(); i++) {
                    patternToCheck += puzzle.getBoard()[i][coords[0].getX()];
                }
            } else if (coords[0].getY() == coords[1].getY()) {
                for (int i = coords[0].getX(); i <= coords[1].getX(); i++) {
                    patternToCheck += puzzle.getBoard()[coords[0].getY()][i];
                }
            }
            return !patternToCheck.contains("1");
        }
        return false;
    }

    private boolean isCorrectWord() {
        String wordToCheck = "";
        Coordinate[] coords = (Coordinate[]) legalCoords.get(0);
        if (coords[0].getX() == coords[1].getX()) {
            for (int i = coords[0].getY(); i <= coords[1].getY(); i++) {
                wordToCheck += puzzle.getBoard()[i][coords[0].getX()];
            }
        } else if (coords[0].getY() == coords[1].getY()) {
            for (int i = coords[0].getX(); i <= coords[1].getX(); i++) {
                wordToCheck += puzzle.getBoard()[coords[0].getY()][i];
            }
        }

        int listSize = wordList.size();
        for (int i = 0; i < listSize; i++) {
            Word currWord = (Word) wordList.get(i);
            if ((Coordinate[]) legalCoords.get(0) == currWord.getCoords()) {
                return wordToCheck.equalsIgnoreCase(currWord.getWord());
            }
        }
        return false;
    }

    private Word getFinishedWord() {
        int listSize = wordList.size();
        for (int i = 0; i < listSize; i++) {
            if (((Word) wordList.get(i)).getCoords() == legalCoords.get(0)) {
                return (Word) wordList.get(i);
            }
        }
        return null;
    }

    private void removeWordFromMLL(Word w) {
        int outerSize = checkList.outerSize();
        boolean flag = false;
        for (int i = 0; i < outerSize; i++) {
            if (((char) checkList.getOuterDataAt(i) == w.getWord().charAt(0))) {
                int innerSize = checkList.innerSize(checkList.getOuterDataAt(i));
                for (int j = 0; j < innerSize; j++) {
                    if (((Word) checkList.getInnerDataAt(i, j)) == w) {
                        checkList.removeInnerAt(i, j);
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
    }

    private void removeIncorrectLetters() {
        int incWordCount = incorrectCharCoords.size();
        for (int i = 0; i < incWordCount; i++) {
            Coordinate coords = (Coordinate) incorrectCharCoords.get(i);
            puzzle.getBoard()[coords.getY()][coords.getX()] = "1";
        }
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
        } else if (intKey > 64 && intKey < 91) { // Key press between 'A'-'Z'
            if (puzzle.getBoard()[playerPos.getY()][playerPos.getX()].equals("1")) {
                String temp = Character.toString(charKey).toLowerCase();
                charKey = temp.charAt(0);
                if (isLegalChar(charKey)) {
                    //TODO: add +1 points to the current user
                    puzzle.getBoard()[playerPos.getY()][playerPos.getX()] = Character.toString(charKey);
                    //TODO: check if the placed char is the final char of the word
                    if (isFinalChar()) {
                        if (isCorrectWord()) {
                            //TODO: Add +10 points to the current user
                            puzzle.printBoard(1, 1, false);
                            Word word = getFinishedWord();
                            word.setComplete(true);
                            askWordMeaning(word);
                            if (countCompletedWords() == solutionWordCount) {
                                isCompleteGame = true;
                            }
                            removeWordFromMLL(word);
                        } else {
                            // and -2 points per char removed
                            removeIncorrectLetters();
                        }
                        incorrectCharCoords.empty();
                        legalCoords.empty();
                        Console.clear();
                        puzzle.printBoard(0, 0, true);
                        printWords(17, 0, true);
                    }
                } else {
                    /**
                     * IMPORTANT!!!
                     * This if condition covers the possibility for the final character of an incorrect word has been placed
                     * which intersects with another word, THIS IS AN EXTREME CASE AND MUST BE ASKED TO THE TEACHER TO KNOW HOW TO
                     * HANDLE IT!!!
                     */
                    if (isFinalChar(charKey)) {
                        String charToCheck = Character.toString(charKey);
                        if (checkPattern((Coordinate[]) legalCoords.get(0), charToCheck)) {
                            // -2 points per char removed
                            removeIncorrectLetters();
                            legalCoords.empty();
                            incorrectCharCoords.empty();
                        }
                    }
                    turn = !turn; // change turns
                }
            }
        }
    }

    public void run() throws InterruptedException, IOException {
        // menu();
        // puzzle.printBoard(0, 0, true);
        // printWords(17, 0, true);
        // while (!isCompleteGame) {
        //     puzzle.printBoard(1, 1, false);
        //     printCursor(playerPos.getX(), playerPos.getY());
        //     takeKeyPress();
        //     Thread.sleep(20);
        // }
        // Console.clear();
        // displayHighScoreTable(0, 0, 10);
        // Output.writeHighScoreTable(highScoreTable, highscorePath);
        // displayUnusedWords(0, 11);
    }
}