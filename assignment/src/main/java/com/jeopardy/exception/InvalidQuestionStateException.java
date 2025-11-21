package com.jeopardy.exception;

/**
 * InvalidQuestionStateException is thrown when a question is in an invalid state for an operation.
 *
 * This is an unchecked (runtime) exception that indicates a question cannot be used because:
 * - It has already been answered
 * - It has no correct answer set
 * - It is missing required data
 *
 * Example usage:
 * <pre>
 * if (question.isAnswered()) {
 *     throw new InvalidQuestionStateException("Question has already been answered");
 * }
 * </pre>
 */
public class InvalidQuestionStateException extends RuntimeException {

    /**
     * Constructs an InvalidQuestionStateException with a descriptive message.
     *
     * @param message the detail message explaining why the question state is invalid
     */
    public InvalidQuestionStateException(String message) {
        super(message);
    }
}
