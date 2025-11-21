package com.jeopardy.exception;

/**
 * GameException is the base exception class for all game-related errors.
 *
 * This exception serves as the parent for all custom exceptions in the Jeopardy game,
 * providing a consistent exception hierarchy for error handling.
 *
 * SOLID principles:
 * - Single Responsibility Principle (SRP): Focused on representing game errors
 * - Open/Closed Principle (OCP): Open for extension through subclasses
 *
 * Benefits of custom exceptions:
 * - More descriptive error messages
 * - Better exception handling and recovery
 * - Clearer separation between business logic errors and system errors
 * - Improved code readability and maintainability
 */
public class GameException extends Exception {

    /**
     * Constructs a new GameException with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public GameException(String message) {
        super(message);
    }

    /**
     * Constructs a new GameException with the specified detail message and cause.
     *
     * @param message the detail message explaining the exception
     * @param cause the cause of this exception
     */
    public GameException(String message, Throwable cause) {
        super(message, cause);
    }
}
