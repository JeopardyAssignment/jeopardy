package com.jeopardy.logging.observer;

/**
 * Publisher is an interface that defines the contract for the Publisher side of the Observer design pattern.
 * 
 * A Publisher maintains a list of Subscriber objects and notifies them of state changes.
 * This interface enables a decoupled communication model where Publishers don't need to know
 * the concrete implementations of Subscribers.
 * 
 * The Publisher pattern is commonly used in event-driven systems where multiple listeners
 * need to be notified when an event occurs (in this case, when an ActivityLog is recorded).
 * 
 * Implementations of this interface are responsible for:
 * - Managing a collection of Subscribers
 * - Notifying all Subscribers when relevant events occur
 * - Supporting dynamic subscription and unsubscription
 * 
 * @see Subscriber
 */
public interface Publisher {

    /**
     * Registers a Subscriber to receive notifications when events occur.
     * 
     * Multiple Subscribers can be registered on the same Publisher. A Subscriber
     * that is already subscribed should typically not be added again (though this
     * depends on the implementation).
     * 
     * @param s the Subscriber to register for notifications
     */
    public void subscribe(Subscriber s);

    /**
     * Unregisters a Subscriber from receiving notifications.
     * 
     * After calling this method, the Subscriber will no longer receive update
     * notifications from this Publisher.
     * 
     * @param s the Subscriber to unregister
     */
    public void unsubscribe(Subscriber s);

    /**
     * Notifies all registered Subscribers of a state change or event.
     * 
     * This method iterates through all subscribed Subscribers and calls their
     * {@link Subscriber#update(ActivityLog)} method to inform them of the event.
     * 
     * This method should be called whenever a significant event occurs that
     * Subscribers need to be aware of (e.g., when a new ActivityLog is created).
     */
    public void notifySubscribers();

}