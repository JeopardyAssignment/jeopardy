package com.jeopardy.game;

import com.jeopardy.logging.ActivityLog;

/**
 * GameController defines the contract for game control operations.
 *
 * This interface provides an abstraction over the GameEngine, allowing
 * components like Commands to depend on this interface rather than the
 * concrete GameEngine implementation (Dependency Inversion Principle).
 *
 * Key benefits:
 * - Enables dependency injection and loose coupling
 * - Facilitates testing by allowing mock implementations
 * - Follows SOLID principles (DIP)
 * - Maintains single point of control for game operations
 *
 * @see GameEngine
 */
public interface GameController {

    /**
     * Gets the current game state.
     *
     * @return the GameState object containing current game information
     */
    GameState getState();

    /**
     * Handles category selection during gameplay.
     * Prompts the player to select a category from available options.
     */
    void selectCategory();

    /**
     * Handles question selection during gameplay.
     * Prompts the player to select a question from the chosen category.
     */
    void selectQuestion();

    /**
     * Updates the current player's score.
     *
     * @param points the score value to add (can be positive or negative)
     */
    void updateCurrentPlayerScore(int points);

    /**
     * Sets the current activity log for event tracking.
     *
     * @param log the ActivityLog to set as current
     */
    void setCurrentActivityLog(ActivityLog log);

    /**
     * Notifies all registered subscribers of a game event.
     * Used by the Observer pattern to publish game state changes.
     */
    void notifySubscribers();
}
