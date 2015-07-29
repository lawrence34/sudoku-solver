package userinterface;

import logic.Sudoku;

import java.util.Scanner;

/**
 * Author: Jason Healy
 * Date: 26 July 2015
 */
public class ConsoleInterface {
    private final Scanner in;
    private Sudoku s;

    public ConsoleInterface() {
        this.s = new Sudoku();
        this.in = new Scanner(System.in);

    }

    public void start() {
        while(true) {
            printMenu();
            int selection = in.nextInt();
            if (selection == 7) {
                break;
            }
            execute(selection);
        }
    }

    private void execute(int selection) {
        switch(selection) {
            case 1:
                System.out.println(s);
                break;
            case 2:
                getNumbers();
                break;
            case 3:
                if (s.solve()) {
                    System.out.println("Success!");
                } else {
                    System.out.println("Failure :(");
                }
                break;
            case 4:
                s.clear();
                break;
            case 5:
                s = new Sudoku();
                break;
            case 6:
                s = new Sudoku(4);
                break;
        }
    }

    private void getNumbers() {
        in.nextLine();  // Flush scanner buffer

        while(true) {
            System.out.print("Enter row and column (upper left is 1, 1), leave blank to end: ");
            String input= in.nextLine();
            if (input.isEmpty()) {
                break;
            }
            String[] rowAndCol = input.split("\\D+");
            if (rowAndCol.length != 2) {      // If length isn't two, input is invalid
                System.out.println("Invalid input");
                continue;
            }

            int row = Integer.parseInt(rowAndCol[0]) - 1;   // Subtract 1 to get 0-based index
            int col = Integer.parseInt(rowAndCol[1]) - 1;

            if (s.isHex()) {
                if (row < 0 || row > 15) {
                    System.out.println("Invalid row. Must be between 1 and 16.");
                    continue;
                }
                if (col < 0 || col > 15) {
                    System.out.println("Invalid column. Must be between 1 and 16.");
                    continue;
                }
                System.out.print("Enter number (1-F): ");
                int num = Integer.parseInt(in.nextLine(), 16);
                s.changeNumber(row, col, num);
            } else {
                if (row < 0 || row > 8) {
                    System.out.println("Invalid row. Must be between 1 and 9.");
                    continue;
                }
                if (col < 0 || col > 8) {
                    System.out.println("Invalid column. Must be between 1 and 9.");
                }
                System.out.print("Enter number (1-9): ");
                int num = Integer.parseInt(in.nextLine());
                s.changeNumber(row, col, num);
            }
        }
    }

    private void insertNumbers() {
        System.out.print("How many numbers are you entering? ");
        int amount = in.nextInt();
        for (int i = 0; i < amount; i++) {
            System.out.print("Enter (row col num): ");
            int row = in.nextInt();
            int col = in.nextInt();
            int num = in.nextInt();
            s.changeNumber(row, col, num);
        }
    }

    private void printMenu() {
        System.out.println("Choose an operation by number");
        System.out.println("1- Print board");
        System.out.println("2- Insert numbers");
        System.out.println("3- Attempt solution");
        System.out.println("4- Clear board");
        System.out.println("5- New regular game (1-9)");
        System.out.println("6- New hex sudoku game (1-F)");
        System.out.println("7- Quit");
    }
}
