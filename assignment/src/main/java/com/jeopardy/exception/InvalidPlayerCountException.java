package com.jeopardy.exception;

/**
 * InvalidPlayerCountException is thrown when an invalid number of players is specified.
 *
 * This exception indicates that the player count is outside the valid range
 * (typically 1-4 players for Jeopardy).
 *
 * Example usage:
 * <pre>
 * if (playerCount &lt; MIN_PLAYERS || playerCount &gt; MAX_PLAYERS) {
 *     throw new InvalidPlayerCountException(playerCount, MIN_PLAYERS, MAX_PLAYERS);
 * }
 * </pre>
 */
public class InvalidPlayerCountException extends GameException {

    /**
     * Constructs an InvalidPlayerCountException with a descriptive message.
     *
     * @param count the invalid player count that was provided
     * @param min the minimum allowed player count
     * @param max the maximum allowed player count
     */
    public InvalidPlayerCountException(int count, int min, int max) {
        super(String.format("Invalid player count: %d. Must be between %d and %d.", count, min, max));
    }
}
