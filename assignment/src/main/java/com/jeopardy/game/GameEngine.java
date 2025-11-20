package com.jeopardy.game;

import com.jeopardy.logging.observer.Publisher;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.logging.observer.Subscriber;
import com.jeopardy.question.Question;
import com.jeopardy.logging.ActivityLog;
import com.jeopardy.utils.ActivityType;

import java.util.*;

/**
 * GameEngine is the central controller for the Jeopardy game.
 *
 * This class implements the Singleton pattern to ensure only one game instance exists,
 * and the Observer pattern to notify subscribers of game events.
 *
 * Key responsibilities:
 * - Managing game settings and state
 * - Coordinating game flow (turns, game over conditions)
 * - Handling question selection
 * - Publishing game events to subscribers
 * - Maintaining the single game instance
 *
 * Design patterns used:
 * - Singleton: Ensures single game instance via Instance() method
 * - Observer: Publishes game events to subscribers (e.g., ReportGenerator)
 *
 * The GameEngine orchestrates all game components and serves as the primary
 * interface for game control and state management.
 */
public class GameEngine implements Publisher {

    private static GameEngine instance;
    private Scanner scanner;
    private GameState state;
    private final ArrayList<Subscriber> subscribers;
    private final ActivityLogBuilder activityLogBuilder;
    private boolean isGameOver;
    private Question currentQuestion;

    /**
     * Private constructor used by the Singleton pattern.
     * Use Instance() to get the game engine instance.
     */
    private GameEngine() {
        this.state = new GameState();
        this.subscribers = new ArrayList<>();
        this.activityLogBuilder = new ActivityLogBuilder();
        this.isGameOver = false;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Gets the singleton instance of the GameEngine.
     *
     * Creates a new instance with default settings if one doesn't exist.
     * This method implements the Singleton pattern.
     *
     * @return the singleton GameEngine instance
     */
    public static GameEngine Instance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    /**
     * Gets the current game state.
     *
     * @return the GameState object
     */
    public GameState getState() {
        return this.state;
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game has ended, false otherwise
     */
    public boolean isGameOver() {
        return this.isGameOver;
    }

    /**
     * Gets the currently selected question.
     *
     * @return the current Question, or null if no question is selected
     */
    public Question getCurrentQuestion() {
        return this.currentQuestion;
    }

    /**
     * Handles the start of a turn.
     *
     * Retrieves the current turn information and notifies subscribers
     * if the game is not over.
     */
    public void onTurnStart() {
        if (!isGameOver && state != null) {
            state.getCurrentTurn();
            notifySubscribers();
        }
    }

    /**
     * Handles the end of a turn.
     *
     * Notifies subscribers if the game is not over.
     */
    public void onTurnEnd() {
        if (!isGameOver) {
            notifySubscribers();
        }
    }

    /**
     * Handles the game over condition.
     *
     * Sets the game over flag and notifies all subscribers.
     */
    public void onGameOver() {
        this.isGameOver = true;
        notifySubscribers();
    }

    /**
     * Handles category selection by a player.
     *
     * Updates the current category in the game state and displays it to the console.
     *
     * @param category the category name that was selected
     */
    public void selectCategory(String category) {
        if (state != null) {
            state.setCurrentCategory(category);
        }
        System.out.println("Category selected: " + category);
    }

    /**
     * Handles question selection by a player.
     *
     * Updates the current question in the game state and displays it to the console.
     *
     * @param question the Question that was selected
     */
    public void selectQuestion(Question question) {
        if (state != null) {
            state.setCurrentQuestion(question);
        }
        this.currentQuestion = question;
        System.out.println("Question selected: " + question.getQuestion());
    }

    /**
     * Registers a subscriber to receive game event notifications.
     *
     * Duplicate subscriptions are prevented.
     *
     * @param s the Subscriber to register
     */
    @Override
    public void subscribe(Subscriber s) {
        if (s != null && !subscribers.contains(s)) {
            subscribers.add(s);
        }
    }

    /**
     * Unregisters a subscriber from receiving notifications.
     *
     * @param s the Subscriber to unregister
     */
    @Override
    public void unsubscribe(Subscriber s) {
        if (s != null) {
            subscribers.remove(s);
        }
    }

    /**
     * Notifies all registered subscribers of a game event.
     *
     * Creates an ActivityLog with current game information and sends it
     * to all subscribers. Includes player information if available.
     */
    @Override
    public void notifySubscribers() {
        for (Subscriber subscriber : subscribers) {
            if (subscriber != null) {
                ActivityLogBuilder builder = new ActivityLogBuilder()
                    .setCaseId("GAME001")
                    .setTimestamp()
                    .setActivity(ActivityType.GAME_UPDATE);

                if (state != null && state.getCurrentPlayer() != null) {
                    builder.setPlayerId(state.getCurrentPlayer());
                }

                ActivityLog activity = builder.createActivityLog();
                subscriber.update(activity);
            }
        }
    }

    public void start() {
        // a. Set Players
        this.state.setPlayers(this.scanner);

        // b. Load questions
        this.state.setQuestionService(scanner);
    }
}
