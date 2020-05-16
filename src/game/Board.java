package game;

import file.Input;

public class Board {
    public static final int ROWCOUNT = 15;
    public static final int ROWLENGTH = 15;
    private String[][] board;

    public Board(String boardPath) {
        board = Input.readBoard(boardPath);
    }

    public String[][] getBoard() {
        return board;
    }

    // Print the frame of the board at given coordinates
    private void printBoardFrame(int x, int y) {
        int frameRowCount = Board.ROWCOUNT + 2;
        int frameRowLength = Board.ROWLENGTH + 2;
        for (int i = 0; i < frameRowCount; i++) {
            for (int j = 0; j < frameRowLength; j++) {
                Console.setCursorPosition(x + j, y + i);
                if (i == 0 && j == 0) {
                    Console.print("╔");
                } else if (i == 0 && j == frameRowLength - 1) {
                    Console.print("╗");
                } else if (i == frameRowCount - 1 && j == 0) {
                    Console.print("╚");
                } else if (i == frameRowCount - 1 && j == frameRowLength - 1) {
                    Console.print("╝");
                } else if ((i == 0 || i == frameRowCount - 1) && (j > 0 && j < frameRowLength - 1)) {
                    Console.print("═");
                } else if ((j == 0 || j == frameRowLength - 1) && (i > 0 && i < frameRowCount - 1)) {
                    Console.print("║");
                }
            }
        }
    }

    // Prints the board at given coordinates with or without a frame
    public void printBoard(int x, int y, boolean hasDisplayFrame) {
        int rowCount = Board.ROWCOUNT;
        int colCount = Board.ROWLENGTH;
        int offset;
        if (hasDisplayFrame) { // If has frame
            offset = 1; // Add 1 char offset to the board
            printBoardFrame(x, y); // Print the frame
        } else { // If has no frame
            offset = 0; 
        }
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                Console.setCursorPosition(x + j + offset, y + i + offset);
                if (board[i][j].equals("1")) { // If there's a space
                    Console.print(" "); // Print space
                } else if (board[i][j].equals("0")) { // If there's a wall
                    Console.print("█"); // Print wall
                } else { // If it is a character
                    Console.print(board[i][j].toUpperCase()); // Print upparcase letter
                }
            }
        }
    }

    //Changes the element at given indices if it is an empty space
    public void setElementAt(int x, int y, String element) {
        if (x >= 0 && x < Board.ROWLENGTH && y >= 0 && y < ROWCOUNT) {
            if(board[y][x].equals("1")){
                board[y][x] = element;
            }
        }
    }
}