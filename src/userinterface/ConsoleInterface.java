package userinterface;

import logic.Sudoku;

import java.util.Scanner;

/**
 * Author: Jason Healy
 * Date: 26 July 2015
 */
public class ConsoleInterface {
    private Scanner in;
    private final Sudoku s;

    public ConsoleInterface() {
        this.s = new Sudoku();
        this.in = new Scanner(System.in);

    }

    public void start() {
        while(true) {
            printMenu();
            int selection = in.nextInt();
            if (selection == 6) {
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
                insertNumbers();
                break;
            case 3:
                System.out.print("Enter (row col num): ");
                int row = in.nextInt();
                int col = in.nextInt();
                int num = in.nextInt();
                s.changeNumber(row, col, num);
                break;
            case 4:
                if (s.solve()) {
                    System.out.println("Success!");
                } else {
                    System.out.println("Failure :(");
                }
                break;
            case 5:
                s.clear();
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
        System.out.println("3- Change number");
        System.out.println("4- Attempt solution");
        System.out.println("5- Clear board");
        System.out.println("6- Quit");
    }
}
