package com.jeopardy;

import com.jeopardy.game.GameEngine;
import com.jeopardy.question.Question;

/**
 * Client is the main entry point for the Jeopardy game application.
 */
public class Client {
    private static GameEngine gameEngine;
    
    public static void main(String[] args) {
        gameEngine = GameEngine.Instance();
        gameEngine.start();

    }

    /**
     * Clears the console by printing newlines.
     * This is a cross-platform solution that works in all environments.
     */
    public static void clear() {
        System.out.print("\033[H\033[2J\n");
        System.out.flush();
    }

    public static void newLine() {
        System.out.println();
    }

    /**
     * Prompts the user for an integer input within a specified range.
     * Displays options in a numbered list format.
     *
     * @param message the prompt message to display after the options
     * @param options the array of options to display
     * @param scanner the Scanner instance to use for input
     * @return the validated integer input (1-indexed)
     */
    public static int prompt(String message, String[] options, java.util.Scanner scanner) {
        int value;
        int min = 1;
        int max = options.length;

        do {
            // Display options in numbered list
            for (int i = 0; i < options.length; i++) {
                System.out.println(String.format("%d) %s", i + 1, options[i]));
            }
            System.out.print(message);

            while (!scanner.hasNextInt()) {
                System.out.println("[Error]: Please enter a valid number.");

                // Re-display options
                for (int i = 0; i < options.length; i++) {
                    System.out.println(String.format("%d) %s", i + 1, options[i]));
                }
                System.out.print(message);
                scanner.next(); // consume invalid input
            }

            value = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (value < min || value > max) {
                System.out.println(String.format("[Error]: Invalid input. Please enter a number between %d and %d.", min, max));
            }
        } while (value < min || value > max);

        return value;
    }

    /**
     * Prompts the user with a question and returns their answer selection.
     * Displays the question text and multiple choice options.
     *
     * @param question the Question object to present to the user
     * @param scanner the Scanner instance to use for input
     * @return the user's selected answer (A, B, C, D, etc.)
     */
    public static String prompt(Question question, java.util.Scanner scanner) {
        System.out.print(question.promptString());

        String answer;
        java.util.Map<String, String> options = question.getOptions();

        do {
            answer = scanner.nextLine().trim().toUpperCase();

            if (answer.isEmpty() || !options.containsKey(answer)) {
                System.out.println("[Error]: Invalid answer. Please enter a valid option.");
                System.out.print(question.promptString());
            }
        } while (answer.isEmpty() || !options.containsKey(answer));

        return answer;
    }
}
