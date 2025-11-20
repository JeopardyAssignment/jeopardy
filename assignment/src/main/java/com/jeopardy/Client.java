package com.jeopardy;

import java.io.IOException;

import com.jeopardy.game.GameEngine;
import com.jeopardy.question.Question;
import com.jeopardy.report.ReportGenerator;
import com.jeopardy.report.format.CSVReportFormat;
import com.jeopardy.report.format.DOCXReportFormat;
import com.jeopardy.report.format.PDFReportFormat;
import com.jeopardy.report.format.TXTReportFormat;

/**
 * Client is the main entry point for the Jeopardy game application.
 */
public class Client {
    private static GameEngine gameEngine;
    
    public static void main(String[] args) {
        ReportGenerator reportGenerator = new ReportGenerator();

        gameEngine = GameEngine.Instance();
        gameEngine.subscribe(reportGenerator);
        gameEngine.subscribePlayersTo(reportGenerator);

        // Add shutdown hook to handle Ctrl+C gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n\nGame Quit. Generating final reports...");
            gameEngine.onGameOver();
            generateReports(reportGenerator);
        }));

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
    
    
    public static void await() {
        try {
            System.out.println("\nPress any key to continue...\n");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates reports in multiple formats (CSV and TXT) when the game ends.
     * Creates an output directory if it doesn't exist and saves the reports there.
     *
     * @param reportGenerator the ReportGenerator containing collected activity logs
     */
    private static void generateReports(ReportGenerator reportGenerator) {
        try {
            // Generate CSV report
            reportGenerator.setFormat(new CSVReportFormat());
            reportGenerator.createReport();
            System.out.println("CSV report generated successfully.");

            // Generate TXT report
            reportGenerator.setFormat(new TXTReportFormat());
            reportGenerator.createReport();
            System.out.println("TXT report generated successfully.");

            // Generate DOCX report
            reportGenerator.setFormat(new DOCXReportFormat());
            reportGenerator.createReport();
            System.out.println("DOCX report generated successfully.");
            
            reportGenerator.setFormat(new PDFReportFormat());
            reportGenerator.createReport();
            System.out.println("PDF report generated successfully.");

            System.out.println("\nReports saved to output/ directory.");
        } catch (Exception e) {
            System.err.println("Error generating reports: " + e.getMessage());
            e.printStackTrace();
        }
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
