package userinterface;

import logic.Sudoku;

import java.io.FileNotFoundException;

/**
 * Author: Jason Healy
 * Date: 17 July 2015
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ConsoleInterface cli = new ConsoleInterface();
        cli.start();
    }
}
