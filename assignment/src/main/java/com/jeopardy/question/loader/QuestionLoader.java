package com.jeopardy.question.loader;

import com.jeopardy.question.Question;
import java.util.ArrayList;

/**
 * QuestionLoader is an interface that defines the contract for loading questions from files.
 *
 * This interface implements the Strategy design pattern, allowing different file formats
 * to be supported without modifying the QuestionService that uses them.
 *
 * Implementations of this interface should:
 * - Parse a specific file format (CSV, JSON, XML, etc.)
 * - Extract question data from the file
 * - Create and populate Question objects
 * - Handle file I/O errors appropriately
 * - Return a list of Question objects
 *
 * Supported implementations:
 * - CSVQuestionLoader: loads questions from CSV files
 * - JSONQuestionLoader: loads questions from JSON files
 * - XMLQuestionLoader: loads questions from XML files
 */
public interface QuestionLoader {

    /**
     * Loads questions from the specified file.
     *
     * This method reads and parses the file at the given path, extracting question data
     * and returning a list of Question objects. The specific parsing logic depends on
     * the implementation.
     *
     * @param filename the path to the file containing questions
     * @return an ArrayList of Question objects parsed from the file
     */
    ArrayList<Question> load(String filename);

}
