package com.jeopardy.question.loader;

import java.util.ArrayList;
import com.jeopardy.question.Question;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * CSVQuestionLoader loads questions from CSV (Comma-Separated Values) files.
 *
 * This class implements the QuestionLoader interface and provides functionality
 * to parse CSV files containing Jeopardy questions.
 *
 * Expected CSV format (8 columns):
 * Category, Value, Question, Option1, Option2, Option3, Option4, CorrectAnswer
 *
 * Example:
 * Science,100,What is H2O?,Water,Hydrogen,Oxygen,Salt,A
 *
 * Features:
 * - Automatically detects and skips header rows (lines containing "category")
 * - Parses each row into a Question object with labeled options (A, B, C, D)
 * - Handles malformed rows gracefully by skipping them
 * - Provides error messages for file I/O issues
 */
public class CSVQuestionLoader implements QuestionLoader {

    /**
     * Constructs a new CSVQuestionLoader.
     */
    public CSVQuestionLoader() {
    }

    /**
     * Loads questions from a CSV file.
     *
     * Parses the CSV file line by line, creating Question objects from each valid row.
     * The first line is checked to see if it's a header row (contains "category").
     * If not a header, it's parsed as a question.
     *
     * Each CSV row must have at least 8 columns:
     * 1. Category name
     * 2. Point value
     * 3. Question text
     * 4-7. Four answer options
     * 8. Correct answer (A, B, C, or D)
     *
     * @param filename the path to the CSV file containing questions
     * @return an ArrayList of Question objects parsed from the file
     */
    @Override
    public ArrayList<Question> load(String filename) {
        ArrayList<Question> questions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String firstLine = reader.readLine();

            // Check if first line is data (not a header)
            if (firstLine != null && !firstLine.toLowerCase().contains("category")) {
                parseLineToQuestion(firstLine, questions);
            }

            // Read remaining lines
            String line;
            while ((line = reader.readLine()) != null) {
                parseLineToQuestion(line, questions);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
            e.printStackTrace();
        }

        return questions;
    }

    /**
     * Parses a single CSV line into a Question object and adds it to the list.
     * Handles quoted fields that may contain commas.
     *
     * @param line the CSV line to parse
     * @param questions the list to add the parsed question to
     */
    private void parseLineToQuestion(String line, ArrayList<Question> questions) {
        String[] parts = parseCSVLine(line);
        if (parts.length < 8) {
            return;
        }

        Question question = new Question();
        question.setCategory(parts[0].trim());

        try {
            question.setValue(Integer.parseInt(parts[1].trim()));
        } catch (NumberFormatException e) {
            // If value parsing fails, default to 0
        }

        question.setQuestion(parts[2].trim());

        String[] answers = {
            parts[3].trim(),
            parts[4].trim(),
            parts[5].trim(),
            parts[6].trim()
        };
        question.setOptions(answers);
        question.setCorrectAnswer(parts[7].trim());

        questions.add(question);
    }

    /**
     * Parses a CSV line handling quoted fields that may contain commas.
     * Follows RFC 4180 CSV standard for quoted fields.
     *
     * @param line the CSV line to parse
     * @return array of field values with quotes removed
     */
    private String[] parseCSVLine(String line) {
        ArrayList<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                // Check if this is an escaped quote (two consecutive quotes)
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    currentField.append('"');
                    i++; // Skip the next quote
                } else {
                    // Toggle quote state
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                // End of field
                fields.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }

        // Add the last field
        fields.add(currentField.toString());

        return fields.toArray(new String[0]);
    }

}
