package com.jeopardy.utils;

/**
 * ActivityType enumerates all possible activity types in the Jeopardy game.
 *
 * This enum is used throughout the game to categorize player actions and game events
 * for logging and reporting purposes. Each activity type represents a distinct action
 * or event that can occur during gameplay.
 *
 * Activity types are used by:
 * - ActivityLog: to categorize logged events
 * - ActivityLogBuilder: to set the type of activity being recorded
 * - Report generators: to filter and organize activities in reports
 *
 * Categories:
 * - Game lifecycle: START_GAME, EXIT_GAME, GAME_OVER
 * - Setup: LOAD_FILE, SELECT_PLAYER_COUNT, ENTER_PLAYER_NAME
 * - Gameplay: SELECT_CATEGORY, SELECT_QUESTION, ANSWER_QUESTION
 * - Game state: SCORE_UPDATED, TURN_START, TURN_END, GAME_UPDATE
 * - Output: GENERATE_REPORT, GENERATE_EVENT_LOG
 */
public enum ActivityType {
    /**
     * Game initialization activity.
     */
    START_GAME,

    /**
     * Loading questions from a file.
     */
    LOAD_FILE,

    /**
     * Setting the number of players.
     */
    SELECT_PLAYER_COUNT,

    /**
     * Entering a player's name.
     */
    ENTER_PLAYER_NAME,

    /**
     * Selecting a question category.
     */
    SELECT_CATEGORY,

    /**
     * Selecting a specific question.
     */
    SELECT_QUESTION,

    /**
     * Answering a question.
     */
    ANSWER_QUESTION,

    /**
     * Player score has been updated.
     */
    SCORE_UPDATED,

    /**
     * Generating a game report.
     */
    GENERATE_REPORT,

    /**
     * Generating an event log.
     */
    GENERATE_EVENT_LOG,

    /**
     * Exiting the game.
     */
    EXIT_GAME,

    /**
     * General game state update.
     */
    GAME_UPDATE,

    /**
     * Start of a player's turn.
     */
    TURN_START,

    /**
     * End of a player's turn.
     */
    TURN_END,

    /**
     * Game has ended.
     */
    GAME_OVER
}
