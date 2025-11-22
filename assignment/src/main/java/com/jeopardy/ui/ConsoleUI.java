package com.jeopardy.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import com.jeopardy.utils.GameConstants;

/**
 * ConsoleUI handles all console user interface operations.
 *
 * This class is responsible for:
 * - Clearing the console screen
 * - Displaying the game banner
 * - Pausing execution for user input
 * - Formatting console output
 *
 * SOLID principles:
 * - Single Responsibility Principle (SRP): Focuses solely on console UI operations
 * - Extracted from Client class to improve separation of concerns
 * - Makes the code more testable by isolating UI dependencies
 *
 * This separation allows the main Client class to focus on application flow
 * while ConsoleUI handles presentation concerns.
 */
public class ConsoleUI {

    /**
     * Clears the console by printing ANSI escape codes.
     * This is a cross-platform solution that works in most terminals.
     * After clearing, displays the banner.
     */
    public static void clear() {
        System.out.print("\033[H\033[2J\n");
        System.out.flush();
        showBanner();
    }

    /**
     * Prints a blank line to the console.
     */
    public static void newLine() {
        System.out.println();
    }

    /**
     * Pauses execution and waits for user to press any key.
     * Used between turns to give players time to read results.
     */
    public static void await() {
        try {
            System.out.println(GameConstants.PROMPT_CONTINUE);
            System.in.read();
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }

    /**
     * Displays the game banner from a resource file.
     * Reads the banner file line by line and prints to console.
     * Loads from classpath for better portability and packaging.
     */
    public static void showBanner() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                ConsoleUI.class.getResourceAsStream(GameConstants.BANNER_FILE)))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading banner file: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Error: Banner file not found in resources.");
        }
        System.out.println("\n");
    }
}
