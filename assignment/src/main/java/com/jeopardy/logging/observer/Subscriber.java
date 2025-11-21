package com.jeopardy.logging.observer;

import com.jeopardy.logging.ActivityLog;

/**
 * Subscriber is an interface that defines the contract for the Subscriber side of the Observer design pattern.
 * 
 * A Subscriber receives notifications from a Publisher when state changes occur.
 * This interface enables loose coupling between event producers (Publishers) and event consumers (Subscribers),
 * allowing multiple independent observers to react to the same events.
 * 
 * The Observer pattern is useful in the Jeopardy game for allowing multiple components
 * (e.g., logging systems, UI components, report generators) to react when game activities occur
 * without the event producer needing to know about or depend on these components.
 * 
 * Implementations of this interface should:
 * - React appropriately to notifications about ActivityLog events
 * - Perform their own business logic (e.g., logging, updating UI, generating reports)
 * - Complete their work efficiently to avoid blocking other Subscribers
 * 
 * @see Publisher
 */
public interface Subscriber {

    /**
     * Called by a Publisher to notify this Subscriber of an activity event.
     * 
     * This method is invoked whenever an ActivityLog event occurs that this Subscriber
     * has registered to receive. Implementations should use the provided ActivityLog
     * to perform appropriate actions such as:
     * - Writing to a log file
     * - Updating a database
     * - Updating user interface elements
     * - Triggering reports
     * 
     * @param activity the ActivityLog containing details about the event that occurred
     */
    public void update(ActivityLog activity);

}