package com.jeopardy.game;

import com.jeopardy.logging.observer.Publisher;
import com.jeopardy.command.Command;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.logging.observer.Subscriber;
import com.jeopardy.logging.ActivityLog;
import com.jeopardy.utils.ActivityType;

import java.util.*;

/**
 * Player represents a participant in the Jeopardy game.
 *
 * This class combines multiple design patterns:
 * - Command pattern: Players execute commands representing their actions
 * - Observer pattern: Players act as Publishers, notifying subscribers of their actions
 *
 * Key responsibilities:
 * - Maintaining player identity (ID)
 * - Executing game commands (select category, select question, answer question)
 * - Publishing activity notifications to subscribers (e.g., ReportGenerator)
 * - Managing subscriber list
 *
 * The Player class is central to the game architecture, bridging user actions
 * with game state changes and activity logging.
 */
public class Player implements Publisher {

    private final String id;
    private Command command;
    private final ArrayList<Subscriber> subscribers;
    private int currentScore;

    /**
     * Constructs a new Player with the specified ID.
     * Initializes the subscriber list and sets the initial score to 0.
     *
     * @param id the unique identifier for this player
     */
    public Player(String id, Subscriber initSub) {
        this.id = id;
        this.subscribers = new ArrayList<>();
        this.currentScore = 0;
        this.subscribe(initSub);

        this.notifySubscribers();
    }

    // ==================== Player Identity ====================

    /**
     * Gets the unique identifier for this player.
     *
     * @return the player's ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns a string representation of this player.
     *
     * @return the player's ID
     */
    @Override
    public String toString() {
        return this.id;
    }

    // ==================== Score Management ====================

    /**
     * Gets the current score for this player.
     *
     * @return the player's current score
     */
    public int getCurrentScore() {
        return this.currentScore;
    }

    /**
     * Updates the player's current score by adding the specified value.
     *
     * @param s the score value to add (can be positive or negative)
     */
    public void updateCurrentScore(int s) {
        this.currentScore += s;
    }

    // ==================== Command Pattern Implementation ====================

    /**
     * Sets the command to be executed by this player.
     * This implements the Command pattern, allowing different commands
     * to be assigned and executed dynamically.
     *
     * @param command the Command object to execute
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Executes the currently set command.
     * If no command is set, this method does nothing.
     */
    public void doCommand() {
        if (this.command != null) {
            this.command.execute();
        }
    }

    // ==================== Observer Pattern Implementation ====================

    /**
     * Registers a subscriber to receive notifications about this player's activities.
     * Duplicate subscriptions are prevented.
     *
     * @param s the Subscriber to register
     */
    @Override
    public void subscribe(Subscriber s) {
        if (s != null && !this.subscribers.contains(s)) {
            this.subscribers.add(s);
        }
    }

    /**
     * Unregisters a subscriber from receiving notifications.
     *
     * @param s the Subscriber to unregister
     */
    @Override
    public void unsubscribe(Subscriber s) {
        if (s != null && this.subscribers != null) {
            this.subscribers.remove(s);
        }
    }

    /**
     * Notifies all registered subscribers of player creation.
     * Creates an ActivityLog for the ENTER_PLAYER_NAME event and sends it to all subscribers.
     */
    @Override
    public void notifySubscribers() {
        if (this.subscribers != null) {
            for (Subscriber subscriber : this.subscribers) {
                if (subscriber != null) {
                    ActivityLog activity = new ActivityLogBuilder()
                        .setCaseId("GAME001")
                        .setAnswerGiven(this.id)
                        .setPlayerId(this)
                        .setResult("N/A")
                        .setActivity(ActivityType.ENTER_PLAYER_NAME)
                        .setTimestamp()
                        .createActivityLog();

                    subscriber.update(activity);
                }
            }
        }
    }
}
