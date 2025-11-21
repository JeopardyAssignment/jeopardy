package com.jeopardy.logging;

import com.jeopardy.logging.observer.Publisher;
import com.jeopardy.logging.observer.Subscriber;
import java.util.ArrayList;

/**
 * ActivityPublisher handles the observer pattern logic for activity logging.
 *
 * This class is responsible for:
 * - Managing subscribers (observers)
 * - Notifying subscribers of activity log events
 * - Maintaining the current activity log state
 *
 * SOLID principles:
 * - Single Responsibility Principle (SRP): Focuses solely on observer pattern management
 * - Extracted from GameEngine to reduce its responsibilities
 * - Open/Closed Principle (OCP): Open for extension through the Publisher interface
 *
 * Design patterns:
 * - Observer: Implements Publisher to manage Subscriber notifications
 *
 * This separation improves maintainability by decoupling activity logging concerns
 * from game flow control.
 */
public class ActivityPublisher implements Publisher {

    private final ArrayList<Subscriber> subscribers;
    private ActivityLog currentActivityLog;

    /**
     * Constructs an ActivityPublisher with an empty subscriber list.
     */
    public ActivityPublisher() {
        this.subscribers = new ArrayList<>();
    }

    /**
     * Gets the current activity log.
     *
     * @return the current ActivityLog, or null if not set
     */
    public ActivityLog getCurrentActivityLog() {
        return this.currentActivityLog;
    }

    /**
     * Sets the current activity log for event tracking.
     *
     * @param log the ActivityLog to set as current
     */
    public void setCurrentActivityLog(ActivityLog log) {
        this.currentActivityLog = log;
    }

    /**
     * Registers a subscriber to receive activity log notifications.
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
     * Notifies all registered subscribers of an activity log event.
     * Sends the current activity log to all subscribers.
     */
    @Override
    public void notifySubscribers() {
        for (Subscriber subscriber : subscribers) {
            if (subscriber != null) {
                subscriber.update(this.currentActivityLog);
            }
        }
    }

    /**
     * Gets the list of registered subscribers.
     * Used internally for delegation and coordination.
     *
     * @return the ArrayList of subscribers
     */
    public ArrayList<Subscriber> getSubscribers() {
        return this.subscribers;
    }
}
