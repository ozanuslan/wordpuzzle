package file;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import linkedlist.*;
import game.Word;
import game.User;
import game.Board;

public class Read {

    public static String[][] readBoard(String puzzlePath) {
        String[][] board = new String[Board.ROWCOUNT][Board.ROWLENGTH];
        try {
            File puzzle = new File(puzzlePath);
            Scanner sc = new Scanner(puzzle);
            for (int i = 0; i < Board.ROWCOUNT; i++) {
                String[] row = sc.nextLine().split("");
                if (row.length == Board.ROWLENGTH) {
                    for(int j = 0; j < row.length; j++){
                        if(row[j].equals("1")){
                            row[j] = " ";
                        } else if(row[j].equals("0")){
                            row[j] = "█";
                        }
                    }
                    board[i] = row;
                } else {
                    Scanner exit = new Scanner(System.in);
                    System.out.println("Incorrect length of row on line " + (i + 1) + ".");
                    System.out.println("The program cannot continue without a proper puzzle.");
                    exit.nextLine();
                    exit.close();
                    System.exit(1);
                }
            }
            sc.close();
            return board;
        } catch (FileNotFoundException e) {
            Scanner sc = new Scanner(System.in);
            System.out.println(e);
            sc.nextLine();
            sc.close();
            System.exit(1);
        } catch (Exception e) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Possibly incorrect amount of rows.");
            sc.nextLine();
            sc.close();
            System.exit(1);
        }

        return null;
    }

    public static String[][] readSolution(String solutionPath){
        String[][] solution = new String[Board.ROWCOUNT][Board.ROWLENGTH];
        return solution;
    }

    public static SLL readSolutionWordList(String solutionPath){
        SLL solutionWordList = new SLL();
        return solutionWordList;
    }

    public static MLL readWordList(String wordPath){
        MLL wordList = new MLL();
        return wordList;
    }

    public static DLL readHighScoreTable(String highscorePath){
        DLL highScoreTable = new DLL();
        return highScoreTable;
    }
}