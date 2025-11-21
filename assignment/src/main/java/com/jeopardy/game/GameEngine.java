package com.jeopardy.game;

import com.jeopardy.logging.observer.Publisher;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.logging.ActivityPublisher;
import com.jeopardy.logging.observer.Subscriber;
import com.jeopardy.Client;
import com.jeopardy.command.AnswerQuestionCommand;
import com.jeopardy.command.SelectCategoryCommand;
import com.jeopardy.command.SelectQuestionCommand;
import com.jeopardy.logging.ActivityLog;
import com.jeopardy.utils.ActivityType;
import com.jeopardy.utils.GameConstants;
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
 * SOLID principles:
 * - Implements GameController interface for Dependency Inversion Principle (DIP)
 *
 * The GameEngine orchestrates all game components and serves as the primary
 * interface for game control and state management.
 */
public class GameEngine implements GameController, Publisher {

    private static GameEngine instance;
    private Scanner scanner;
    private GameState state;
    private final ActivityPublisher activityPublisher;
    private final ActivityLogBuilder activityLogBuilder;
    private boolean isGameOver;

    // ==================== Singleton Pattern ====================

    /**
     * Private constructor used by the Singleton pattern.
     * Use Instance() to get the game engine instance.
     */
    private GameEngine() {
        this.state = new GameState();
        this.activityPublisher = new ActivityPublisher();
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

    // ==================== State Accessors ====================

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
     * Sets the current activity log for event tracking.
     * Delegates to the ActivityPublisher.
     *
     * @param log the ActivityLog to set
     */
    public void setCurrentActivityLog(ActivityLog log) {
        this.activityPublisher.setCurrentActivityLog(log);
    }

    // ==================== Score Management ====================

    /**
     * Updates the current player's score by adding the specified value.
     *
     * @param s the score value to add (can be positive or negative)
     */
    public void updateCurrentPlayerScore(int s) {
        this.state.getCurrentPlayer().updateCurrentScore(s);
    }

    // ==================== Game Flow Control ====================

    /**
     * Handles the start of a turn.
     * Checks if all questions have been answered, triggering game over if so.
     */
    public void onTurnStart() {
        if (!isGameOver && state != null) {
            state.getCurrentTurn();
        }

        if (this.state.getQuestionService().getUnansweredQuestions().size() == 0) {
            this.onGameOver();
        }
    }

    /**
     * Handles the end of a turn.
     * Advances to the next turn in the rotation.
     */
    public void onTurnEnd() {
        this.state.nextTurn();
    }

    /**
     * Handles the game over condition.
     * Sets game over flag and notifies all subscribers.
     */
    public void onGameOver() {
        this.isGameOver = true;
        this.activityPublisher.setCurrentActivityLog(
            this.activityLogBuilder
                    .setCaseId(GameConstants.DEFAULT_CASE_ID)
                    .setPlayerId(GameConstants.SYSTEM_PLAYER_ID)
                    .setActivity(ActivityType.GAME_OVER)
                    .setTimestamp()
                    .createActivityLog()
        );

        this.activityLogBuilder.reset();
        this.notifySubscribers();
    }

    /**
     * Handles the game start event.
     * Logs the start and notifies all subscribers.
     */
    public void onGameStart() {
        this.activityPublisher.setCurrentActivityLog(
            this.activityLogBuilder
                    .setCaseId(GameConstants.DEFAULT_CASE_ID)
                    .setPlayerId(GameConstants.SYSTEM_PLAYER_ID)
                    .setActivity(ActivityType.START_GAME)
                    .setTimestamp()
                    .createActivityLog()
        );

        this.activityLogBuilder.reset();
        this.notifySubscribers();
    }

    /**
     * Handles the file load event.
     * Logs the result (SUCCESS or FAILED) and notifies subscribers.
     *
     * @param state the result of the file load operation
     */
    public void onFileLoad(String state) {
        this.activityPublisher.setCurrentActivityLog(
            this.activityLogBuilder
                    .setCaseId(GameConstants.DEFAULT_CASE_ID)
                    .setPlayerId(GameConstants.SYSTEM_PLAYER_ID)
                    .setActivity(ActivityType.LOAD_FILE)
                    .setTimestamp()
                    .setResult(state)
                    .createActivityLog()
        );

        this.activityLogBuilder.reset();
        this.notifySubscribers();
    }

    // ==================== Player Actions ====================

    /**
     * Handles category selection by a player.
     * Delegates to game state to prompt for and set the current category.
     */
    public void selectCategory() {
        if (state != null) {
            state.setCurrentCategory(scanner);
        }
    }

    /**
     * Handles question selection by a player.
     * Delegates to game state to prompt for and set the current question.
     */
    public void selectQuestion() {
        if (state != null) {
            state.setCurrentQuestion(this.scanner);
        }
    }

    // ==================== Observer Pattern Implementation ====================

    /**
     * Registers a subscriber to receive game event notifications.
     * Delegates to the ActivityPublisher (SRP).
     *
     * @param s the Subscriber to register
     */
    @Override
    public void subscribe(Subscriber s) {
        this.activityPublisher.subscribe(s);
    }

    /**
     * Unregisters a subscriber from receiving notifications.
     * Delegates to the ActivityPublisher (SRP).
     *
     * @param s the Subscriber to unregister
     */
    @Override
    public void unsubscribe(Subscriber s) {
        this.activityPublisher.unsubscribe(s);
    }

    /**
     * Notifies all registered subscribers of a game event.
     * Delegates to the ActivityPublisher (SRP).
     */
    @Override
    public void notifySubscribers() {
        this.activityPublisher.notifySubscribers();
    }

    /**
     * Subscribes all players to the given subscriber.
     * Should be called after players are initialized.
     *
     * @param s the Subscriber to register to all players
     */
    public void subscribePlayersTo(Subscriber s) {
        if (state != null && state.getPlayers() != null) {
            for (Player player : state.getPlayers()) {
                player.subscribe(s);
            }
        }
    }

    // ==================== Game Initialization & Main Loop ====================

    /**
     * Starts the game by initializing players, loading questions, and beginning the game loop.
     */
    public void start() {
        this.onGameStart();

        this.state.setPlayers(this.scanner);

        // Subscribe all players to the same subscribers as the GameEngine
        for (Subscriber subscriber : activityPublisher.getSubscribers()) {
            subscribePlayersTo(subscriber);
        }

        Client.clear();

        this.activityPublisher.setCurrentActivityLog(
            this.activityLogBuilder
                    .setCaseId(GameConstants.DEFAULT_CASE_ID)
                    .setTimestamp()
                    .setPlayerId(GameConstants.SYSTEM_PLAYER_ID)
                    .setActivity(ActivityType.SELECT_PLAYER_COUNT)
                    .setResult(Integer.toString(this.state.getPlayers().size()))
                    .createActivityLog()
        );
        this.activityLogBuilder.reset();
        this.notifySubscribers();

        if (this.state.setQuestionService(scanner)) {
            this.onFileLoad(GameConstants.RESULT_SUCCESS);
            Client.clear();

            this.update();
        } else {
            this.onFileLoad(GameConstants.RESULT_FAILED);
        }
    }

    /**
     * Main game loop that handles a single turn.
     * Recursively calls itself until the game is over.
     * Each turn consists of: category selection, question selection, and answering.
     */
    public void update() {
        this.onTurnStart();

        Client.clear();

        Player currentPlayer = this.state.getCurrentPlayer();

        // Display current player and score
        System.out.println(String.format("=== %s's Turn (Score %s) ===", currentPlayer.getId(), currentPlayer.getCurrentScore()));

        // Step 1: Select category (using dependency injection for DIP)
        currentPlayer.setCommand(new SelectCategoryCommand(this));
        currentPlayer.doCommand();

        Client.clear();

        // Step 2: Select question (using dependency injection for DIP)
        currentPlayer.setCommand(new SelectQuestionCommand(this));
        currentPlayer.doCommand();

        Client.clear();

        // Step 3: Answer question (using dependency injection for DIP)
        System.out.println(String.format("=== %s's Turn (Score %s) ===", currentPlayer.getId(), currentPlayer.getCurrentScore()));
        String answer = Client.prompt(this.state.getCurrentQuestion(), this.scanner);
        currentPlayer.setCommand(new AnswerQuestionCommand(this, answer));
        currentPlayer.doCommand();

        this.onTurnEnd();

        Client.await();

        // Continue to next turn if game is not over
        if (!this.isGameOver) {
            this.update();
        }
    }
}
