package com.jeopardy.game;

import com.jeopardy.question.QuestionService;

import java.util.*;

/**
 * GameState maintains the current state of a Jeopardy game session.
 *
 * This class tracks all mutable game state including:
 * - Current turn number
 * - List of players participating in the game
 * - Question service for managing game questions
 * - Current score tracking
 *
 * The GameState provides methods for:
 * - Accessing and modifying player list
 * - Determining the current player based on turn rotation
 * - Managing score updates
 * - Tracking turn progression
 */
public class GameState {

    private int currentTurn;
    private ArrayList<Player> players;
    private QuestionService questionService;
    private int currentScore;

    /**
     * Constructs a new GameState with default initial values.
     * Initializes an empty player list, turn counter at 0, and score at 0.
     */
    public GameState() {
        this.players = new ArrayList<>();
        this.currentTurn = 0;
        this.currentScore = 0;
    }

    /**
     * Gets the current turn number.
     *
     * @return the current turn number
     */
    public int getCurrentTurn() {
        return this.currentTurn;
    }

    /**
     * Gets the list of players in the game.
     *
     * @return an ArrayList of Player objects
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Sets the list of players for the game.
     *
     * @param players an ArrayList of Player objects to set
     */
    public void setPlayer(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Gets the player whose turn it currently is.
     *
     * Uses modulo arithmetic to rotate through players based on turn number.
     * Returns null if no players are in the game.
     *
     * @return the Player object for the current turn, or null if no players exist
     */
    public Player getCurrentPlayer() {
        if (this.players == null || this.players.isEmpty()) {
            return null;
        }
        int playerIndex = this.currentTurn % this.players.size();
        return this.players.get(playerIndex);
    }

    /**
     * Adds points to the current score.
     *
     * @param s the score value to add (can be positive or negative)
     */
    public void addScore(int s) {
        this.currentScore = this.currentScore + s;
    }

    /**
     * Gets the current game score.
     *
     * @return the current score value
     */
    public int getScore() {
        return this.currentScore;
    }

    /**
     * Gets the question service associated with this game state.
     *
     * @return the QuestionService instance
     */
    public QuestionService getQuestionService() {
        return this.questionService;
    }

    /**
     * Sets the question service for this game state.
     *
     * @param questionService the QuestionService to use
     */
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Advances to the next turn.
     */
    public void nextTurn() {
        this.currentTurn++;
    }

}
