package com.jeopardy.game;

/**
 * GameSettings holds configuration settings for a Jeopardy game session.
 *
 * This class stores game configuration parameters that can be customized
 * before or during a game. Currently supports:
 * - Player count configuration
 *
 * Additional settings can be added as needed for game customization.
 */
public class GameSettings {

    private int playerCount;

    /**
     * Constructs a new GameSettings with default values.
     */
    public GameSettings() {
    }

    /**
     * Gets the number of players configured for the game.
     *
     * @return the player count
     */
    public int getPlayerCount() {
        return this.playerCount;
    }

    /**
     * Sets the number of players for the game.
     *
     * @param playerCount the number of players
     */
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

}
