package logic;


import java.util.ArrayList;
import java.util.List;

/**
 * Author: Jason Healy
 * Date: 16 July 2015
 */
public class Sudoku {
    private final int n;        // Square root of the board dimensions. For normal 9x9 Sudoku, this would be 3
    private int[][] board;      // Game board

    public Sudoku(int n) {
        this.n = n;
        board = new int[n * n][n * n];

        // Initialize all elements as empty (0)
        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < n * n; j++) {
                board[i][j] = 0;
            }
        }
    }

    public Sudoku() {
        this(3);
    }

    /**
     * copyBoard method currently unused.
     * @return Deep copy of the board array
     */
//    private int[][] copyBoard(int[][] b) {
//        int boardCopy[][] = new int[n * n][n * n];
//        for (int i = 0; i < n * n; i++) {
//            for (int j = 0; j < n * n; j++) {
//                boardCopy[i][j] = b[i][j];
//            }
//        }
//        return boardCopy;
//    }

    /**
     * Checks to see if current board is a legal sudoku solution
     * @return True if solution is legal
     */
    public boolean isSolution() {
        return isSolution(board);
    }

    /**
     * Checks to see if specified board is a legal sudoku solution
     * @param check Sudoku board to be checked
     * @return True if solution is legal, false otherwise
     */
    public boolean isSolution(int[][] check) {
        List<Integer> numbers = new ArrayList<>();

        // Check rows
        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < n * n; j++) {
                int currentVal = check[i][j];
                if (currentVal == 0 || numbers.contains(currentVal)) {  // First time through also checks for unfilled
                    return false;
                }
                numbers.add(currentVal);
            }
            numbers.clear();
        }

        // Check cols
        for (int j = 0; j < n * n; j++) {
            for (int i = 0; i < n * n; i++) {
                int currentVal = check[i][j];
                if (numbers.contains(currentVal)) {
                    return false;
                }
                numbers.add(currentVal);
            }
            numbers.clear();
        }
        // Check blocks
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n * n; i++) {
                for (int j = 0; j < n; j++) {
                    int currentVal = check[i][j + k * n];
                    if (numbers.contains(currentVal)) {
                        return false;
                    }
                }
                if (i % n == n - 1) {
                    numbers.clear();
                }
            }
        }

        return true;
    }

    /**
     * Checks to see if specified number is a legal entry in the cell
     * @param test Sudoku board to be tested
     * @param row Row that cell is located in
     * @param col Column that cell is located in
     * @param num Value to be checked for legality
     * @return True if value is legal on current board, false otherwise
     */
    public boolean isValidNumber(int[][] test, int row, int col, int num) {
        // Check row
        for (int j = 0; j < n * n; j++) {
            if (j != col) {
                if (test[row][j] == num) {
                    return false;
                }
            }
        }

        // Check column
        for (int i = 0; i < n * n; i++) {
            if (i != row) {
                if (test[i][col] == num ) {
                    return false;
                }
            }
        }

        // Check block
        int rowStart = row / n * n;
        int colStart = col / n * n;
        for (int i = rowStart; i < rowStart + n; i++) {
            for (int j = colStart; j < colStart + n; j++) {
                if (!(i == row && j == col) && test[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks to see if specified number is a legal entry in the cell on the current board
     * @param row Row that cell is located in
     * @param col Column that cell is located in
     * @param num Value to be checked for legality
     * @return True if value is legal on current board, false otherwise
     */
    public boolean isValidNumber(int row, int col, int num) {
        return this.isValidNumber(board, row, col, num);
    }

    /**
     * General solve method for the board. Starts at upper-left corner and checks every square moving right and down
     * @return True if a solution can be found for the board
     */
    public boolean solve() {
        return this.solve(0, 0);
    }

    /**
     * Recursive method for solving sudoku board. Called by solve() function.
     * @param row Row to begin solution
     * @param col Column to begin solution
     * @return True if solution is found
     */
    public boolean solve(int row, int col) {
        if (row == n * n) {
           return true;
        }
        // Set values for the next cell to be tested. If Java had pointers this could be refactored
        int nextRow = row;
        int nextCol = col + 1;
        if (nextCol == n * n) {
            nextRow = row + 1;
            nextCol = 0;
        }

        // If cell is not 0, then it is preset, go to next cell
        if (board[row][col] != 0) {
            if (solve(nextRow, nextCol)) {
                return true;
            }
        } else {
            // Start checking values in ascending order
            for (int i = 1; i <= n * n; i++) {
                if (isValidNumber(row, col, i)) {
                    board[row][col] = i;
                    if (solve(nextRow, nextCol)) {
                        return true;
                    } else {
                        // Reset cell when backtracking
                        board[row][col] = 0;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Method to generate a valid test board. Used for testing and debugging.
     */
    public void generateTestBoard() {
        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < n * n; j++) {
                board[i][j] = (i * n + i / n + j) % (n * n) + 1;
            }
        }
    }

    /**
     * Formats the sudoku board for printing, with spacers between sub-boxes to assist reading
     * @return Properly formatted board
     */
    @Override
    public String toString() {
        StringBuilder prettyBoard = new StringBuilder();
        String blockDivider = "------+-------+------\n";
        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < n * n; j++) {
                prettyBoard.append(board[i][j]);
                prettyBoard.append(' ');
                if (j % n == n - 1 && j != n * n - 1) {
                    prettyBoard.append("| ");
                }
            }
            prettyBoard.append("\n");
            if (i % n == n - 1 && i != n * n - 1) {
                prettyBoard.append(blockDivider);
            }
        }
        return prettyBoard.toString();
    }

    public void removeNumber(int row, int col) {
        this.changeNumber(row, col, 0);
    }

    /**
     * Changes the number on the board at location [row][col]
     * @param row Row to change
     * @param col Column to change
     * @param num New value for the cell
     */
    public void changeNumber(int row, int col, int num) {
        board[row][col] = num;
    }
}