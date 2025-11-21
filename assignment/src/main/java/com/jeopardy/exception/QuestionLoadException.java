package com.jeopardy.exception;

/**
 * QuestionLoadException is thrown when questions cannot be loaded from a file.
 *
 * This exception indicates a failure in loading question data, which could be due to:
 * - File not found
 * - Invalid file format
 * - Parsing errors
 * - I/O errors
 *
 * Example usage:
 * <pre>
 * try {
 *     loadQuestions(filename);
 * } catch (IOException e) {
 *     throw new QuestionLoadException(filename, e);
 * }
 * </pre>
 */
public class QuestionLoadException extends GameException {

    /**
     * Constructs a QuestionLoadException with the filename and cause.
     *
     * @param filename the name of the file that failed to load
     * @param cause the underlying exception that caused the load failure
     */
    public QuestionLoadException(String filename, Throwable cause) {
        super("Failed to load questions from file: " + filename, cause);
    }

    /**
     * Constructs a QuestionLoadException with just a filename.
     *
     * @param filename the name of the file that failed to load
     */
    public QuestionLoadException(String filename) {
        super("Failed to load questions from file: " + filename);
    }
}
