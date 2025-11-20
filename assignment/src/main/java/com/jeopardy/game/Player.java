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
    private final ActivityLogBuilder activityLogBuilder;

    /**
     * Constructs a new Player with the specified ID.
     *
     * Initializes the subscriber list and activity log builder.
     *
     * @param id the unique identifier for this player
     */
    public Player(String id) {
        this.id = id;
        this.subscribers = new ArrayList<>();
        this.activityLogBuilder = new ActivityLogBuilder();
    }

    /**
     * Gets the unique identifier for this player.
     *
     * @return the player's ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the command to be executed by this player.
     *
     * This method implements the Command pattern, allowing different
     * commands to be assigned and executed dynamically.
     *
     * @param command the Command object to execute
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Executes the currently set command.
     *
     * If no command is set, this method does nothing.
     * This is the primary way players perform actions in the game.
     */
    public void doCommand() {
        if (this.command != null) {
            this.command.execute();
        }
    }

    /**
     * Registers a subscriber to receive notifications about this player's activities.
     *
     * Duplicate subscriptions are prevented. This method implements the
     * Observer pattern's subscribe operation.
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
     * After unsubscription, the subscriber will no longer receive
     * activity updates from this player.
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
     * Notifies all registered subscribers of a game activity.
     *
     * Creates an ActivityLog with basic information and sends it to all subscribers.
     * This method is typically called after significant player actions.
     */
    @Override
    public void notifySubscribers() {
        if (this.subscribers != null) {
            for (Subscriber subscriber : this.subscribers) {
                if (subscriber != null) {
                    ActivityLog activity = new ActivityLogBuilder()
                        .setCaseId("GAME001")
                        .setPlayerId(this)
                        .setActivity(ActivityType.GAME_UPDATE)
                        .setTimestamp()
                        .createActivityLog();

                    subscriber.update(activity);
                }
            }
        }
    }

}
