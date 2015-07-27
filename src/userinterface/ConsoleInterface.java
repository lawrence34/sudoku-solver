package userinterface;

import logic.Sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Author: Jason Healy
 * Date: 26 July 2015
 */
public class ConsoleInterface {
    private Scanner in;
    private final Sudoku s;

    public ConsoleInterface() throws FileNotFoundException {
        this.s = new Sudoku();
        this.in = new Scanner(new File("/home/jason/IdeaProjects/SudokuSolver/src/userinterface/testboard.txt"));
        // Read contents of file to sudoku board. First line is number of numbers initially given, followed by the cell
        // locations and contents
        int numbers = in.nextInt();
        for (int i = 0; i < numbers; i++) {
            int row = in.nextInt();
            int col = in.nextInt();
            int num = in.nextInt();
            s.changeNumber(row, col, num);
        }
        in = new Scanner(System.in);

    }

    public void start() {
        while(true) {
            printMenu();
            int selection = in.nextInt();
            if (selection == 3) {
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
                if (s.solve()) {
                    System.out.println("Success!");
                } else {
                    System.out.println("You'll probably get a stack overflow before you see this message.");
                }
                break;
        }
    }

    private void printMenu() {
        System.out.println("Choose an operation by number");
        System.out.println("1- Print board");
        System.out.println("2- Attempt solution");
        System.out.println("3- Quit");
    }
}
