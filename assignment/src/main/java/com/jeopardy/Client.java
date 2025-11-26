package com.jeopardy;

import java.util.ArrayList;

import com.jeopardy.game.GameEngine;
import com.jeopardy.question.Question;
import com.jeopardy.report.ReportGenerator;
import com.jeopardy.report.format.CSVReportFormat;
import com.jeopardy.report.format.DOCXReportFormat;
import com.jeopardy.report.format.PDFReportFormat;
import com.jeopardy.report.format.ReportFormat;
import com.jeopardy.report.format.TXTReportFormat;
import com.jeopardy.ui.ConsoleUI;
import com.jeopardy.utils.GameConstants;

/**
 * Client is the main entry point for the Jeopardy game application.
 */
public class Client {
    private static GameEngine gameEngine;
    
    // ==================== Main Entry Point ====================

    public static void main(String[] args) {
        ReportGenerator reportGenerator = new ReportGenerator();

        gameEngine = GameEngine.Instance();
        gameEngine.subscribe(reportGenerator);
        gameEngine.subscribePlayersTo(reportGenerator);

        // Add shutdown hook to handle Ctrl+C gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println(GameConstants.MESSAGE_GAME_QUIT);
            generateReports(reportGenerator, gameEngine);
        }));

        ConsoleUI.showBanner();
        gameEngine.start();
    }

    // ==================== UI Helper Methods (Delegated to ConsoleUI) ====================

    /**
     * Clears the console.
     * Delegates to ConsoleUI (SRP).
     */
    public static void clear() {
        ConsoleUI.clear();
    }

    /**
     * Prints a blank line to the console.
     * Delegates to ConsoleUI (SRP).
     */
    public static void newLine() {
        ConsoleUI.newLine();
    }

    /**
     * Pauses execution and waits for user to press any key.
     * Delegates to ConsoleUI (SRP).
     */
    public static void await() {
        ConsoleUI.await();
    }

    /**
     * Displays the game banner from a file.
     * Delegates to ConsoleUI (SRP).
     */
    public static void showBanner() {
        ConsoleUI.showBanner();
    }

    // ==================== User Input Methods ====================

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
            value = -1;
            if (gameEngine.isGameOver()) {
                System.out.println("Game is over. No further input accepted.");
                return 0;
            }
            // Display options in numbered list
            for (int i = 0; i < options.length; i++) {
                System.out.println(String.format("%d) %s", i + 1, options[i]));
            }
            System.out.print(message);

            while (!scanner.hasNextInt()) {
                System.out.println(GameConstants.ERROR_INVALID_NUMBER);

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
                System.out.println(String.format(GameConstants.ERROR_OUT_OF_RANGE_FORMAT, min, max));
            }
        } while (value < min || value > max);
        clear();

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
            if (gameEngine.isGameOver()) {
                System.out.println("Game is over. No further input accepted.");
                break;
            }

            if (answer.isEmpty() || !options.containsKey(answer)) {
                System.out.println(GameConstants.ERROR_INVALID_ANSWER);
                System.out.print(question.promptString());
            }
        } while (answer.isEmpty() || !options.containsKey(answer));
        clear();

        return answer;
    }

    // ==================== Report Generation ====================

    /**
     * Generates reports in multiple formats (CSV, PDF, DOCX, TXT) when the game ends.
     * Creates an output directory if it doesn't exist and saves the reports there.
     *
     * @param reportGenerator the ReportGenerator containing collected activity logs
     */
    private static void generateReports(ReportGenerator reportGenerator, GameEngine gameEngine) {
        try {
            ArrayList<ReportFormat> nonCsvFormats = new ArrayList<>();
            nonCsvFormats.add(new PDFReportFormat());
            nonCsvFormats.add(new DOCXReportFormat());
            nonCsvFormats.add(new TXTReportFormat());

            for (ReportFormat f : nonCsvFormats) {
                reportGenerator.setFormat(f);
                reportGenerator.createReport();
            }

            gameEngine.onGameOver();
            reportGenerator.setFormat(new CSVReportFormat());
            reportGenerator.createReport();

            System.out.println(GameConstants.MESSAGE_REPORTS_SAVED);
        } catch (Exception e) {
            System.err.println("Error generating reports: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
