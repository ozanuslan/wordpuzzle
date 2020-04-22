package game;

import file.Read;

public class Board {
    public static final int ROWCOUNT = 15;
    public static final int ROWLENGTH = 15;
    private String[][] board;

    public Board(String boardPath) {
        board = Read.readBoard(boardPath);
    }

    public String[][] getBoard() {
        return board;
    }

    private void displayFrame(int x, int y) {
        int frameRowCount = Board.ROWCOUNT + 2;
        int frameRowLength = Board.ROWLENGTH + 2;
        for (int i = 0; i < frameRowCount; i++) {
            for (int j = 0; j < frameRowLength; j++) {
                Console.setCursorPosition(x + i, y + j);
                if (i == 0 && j == 0) {
                    Console.print("╔");
                } else if (i == 0 && j == frameRowLength - 1) {
                    Console.print("╚");
                } else if (i == frameRowCount - 1 && j == 0) {
                    Console.print("╗");
                } else if (i == frameRowCount - 1 && j == frameRowLength - 1) {
                    Console.print("╝");
                } else if ((i == 0 || i == frameRowCount - 1) && (j > 0 && j < frameRowLength - 1)) {
                    Console.print("║");
                } else if ((j == 0 || j == frameRowLength - 1) && (i > 0 && i < frameRowCount - 1)) {
                    Console.print("═");
                }
            }
        }
    }

    public void displayBoard(int x, int y, boolean hasDisplayFrame) {
        int rowCount = board.length;
        int colCount;
        int offset;
        if (hasDisplayFrame) {
            offset = 1;
            displayFrame(x, y);
        } else {
            offset = 0;
        }
        for (int i = 0; i < rowCount; i++) {
            colCount = board[i].length;
            for (int j = 0; j < colCount; j++) {
                Console.setCursorPosition(x + j + offset, y + i + offset);
                if (board[i][j].equals("1")) {
                    Console.print(" ");
                } else if (board[i][j].equals("0")) {
                    Console.print("█");
                } else {
                    Console.print(board[i][j]);
                }
            }
        }
    }
}