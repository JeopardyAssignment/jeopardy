package com.jeopardy.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * GameConstants contains all constant values used throughout the Jeopardy game.
 *
 * This class centralizes magic numbers and hard-coded strings, making the codebase:
 * - More maintainable (change once, apply everywhere)
 * - More readable (self-documenting constant names)
 * - Easier to configure (all settings in one place)
 *
 * SOLID principles:
 * - Single Responsibility Principle (SRP): Focuses solely on defining constants
 * - Improves code quality by eliminating magic numbers
 *
 * Usage: Access constants via GameConstants.CONSTANT_NAME
 */
public final class GameConstants {

    // Prevent instantiation
    private GameConstants() {
        throw new AssertionError("Cannot instantiate GameConstants");
    }

    // ==================== Game Settings ====================

    /**
     * Minimum number of players allowed in a game.
     */
    public static final int MIN_PLAYERS = 1;

    /**
     * Maximum number of players allowed in a game.
     */
    public static final int MAX_PLAYERS = 4;

    /**
     * Default case ID for game sessions.
     */
    public static final String DEFAULT_CASE_ID = "GAME-001";

    // ==================== File Paths ====================

    /**
     * Directory containing question data files.
     * Uses Path API for cross-platform compatibility.
     */
    public static final Path DATA_DIRECTORY = Paths.get("data");

    /**
     * Directory for output reports.
     * Uses Path API for cross-platform compatibility.
     */
    public static final Path OUTPUT_DIRECTORY = Paths.get("output");

    /**
     * Resource path to the banner file.
     * Loaded from classpath for better portability and packaging.
     */
    public static final String BANNER_FILE = "/banner.txt";

    // ==================== Turn Offset ====================

    /**
     * Offset added to current turn for display (1-indexed vs 0-indexed).
     */
    public static final int TURN_DISPLAY_OFFSET = 1;

    // ==================== Player IDs ====================

    /**
     * System player ID for system-generated activities.
     */
    public static final String SYSTEM_PLAYER_ID = "System";

    // ==================== Result Strings ====================

    /**
     * Result string for successful operations.
     */
    public static final String RESULT_SUCCESS = "SUCCESS";

    /**
     * Result string for failed operations.
     */
    public static final String RESULT_FAILED = "FAILED";

    /**
     * Result string for correct answers.
     */
    public static final String RESULT_CORRECT = "Correct";

    /**
     * Result string for incorrect answers.
     */
    public static final String RESULT_INCORRECT = "Incorrect";

    // ==================== Messages ====================

    /**
     * Message displayed when answer is correct.
     */
    public static final String MESSAGE_CORRECT = "That is correct!";

    /**
     * Message format for displaying incorrect answer with correct answer.
     * Use with String.format(MESSAGE_INCORRECT_FORMAT, correctAnswer)
     */
    public static final String MESSAGE_INCORRECT_FORMAT = "Incorrect. The correct answer is %s";

    /**
     * Error message for missing question.
     */
    public static final String ERROR_NO_QUESTION = "[Error]: No question to answer";

    /**
     * Error message for invalid number input.
     */
    public static final String ERROR_INVALID_NUMBER = "[Error]: Please enter a valid number.";

    /**
     * Error message format for out of range input.
     * Use with String.format(ERROR_OUT_OF_RANGE_FORMAT, min, max)
     */
    public static final String ERROR_OUT_OF_RANGE_FORMAT = "[Error]: Invalid input. Please enter a number between %d and %d.";

    /**
     * Error message for invalid answer.
     */
    public static final String ERROR_INVALID_ANSWER = "[Error]: Invalid answer. Please enter a valid option.";

    /**
     * Prompt message for continuing to next action.
     */
    public static final String PROMPT_CONTINUE = "\nPress any key to continue...\n";

    /**
     * Message displayed when game is quit.
     */
    public static final String MESSAGE_GAME_QUIT = "\n\nGame Quit. Generating final reports...";

    /**
     * Message displayed when reports are saved.
     */
    public static final String MESSAGE_REPORTS_SAVED = "\nReports saved to output/ directory.";
}
