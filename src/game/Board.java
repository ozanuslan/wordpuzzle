package game;

import file.Read;

public class Board {
    public static final int ROWCOUNT = 15;
    public static final int ROWLENGTH = 15;
    private String[][] board;

    public Board(String puzzlePath){
        board = Read.readBoard(puzzlePath);
    }

    public Board(String solutionPath){ //Ask for polimorphism
        board = Read.readSolution(solutionPath);
    }

    public String[][] getBoard(){
        return board;
    }

    public void displayBoard(int x, int y){
        int rowCount = board.length;
        int colCount;
        for(int i =0; i < rowCount; i++){
            colCount = board[i].length;
            for(int j = 0; j < colCount; j++){
                Console.setCursorPosition(x+j, y+i);
                Console.print(board[i][j]);
            }
        }
    }
}