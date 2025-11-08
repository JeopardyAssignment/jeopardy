package com.jeopardy.game;

import com.jeopardy.logging.observer.Publisher;
import com.jeopardy.command.Command;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.logging.observer.Subscriber;

import java.util.*;

/**
 * 
 */
public class Player implements Publisher {

    /**
     * Default constructor
     */
    public Player() {
    }

    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private Command command;

    /**
     * 
     */
    private ArrayList<Subscriber> subscribers;

    /**
     * 
     */
    private ActivityLogBuilder activityLogBuilder;





    /**
     * @return
     */
    public String getId() {
        // TODO implement here
        return "";
    }

    /**
     * @param command
     */
    public void setCommand(Command command) {
        // TODO implement here
    }

    /**
     * 
     */
    public void doCommand() {
        // TODO implement here
    }

    /**
     * @param s
     */
    public void subscribe(Subscriber s) {
        // TODO implement Publisher.subscribe() here
    }

    /**
     * @param s
     */
    public void unsubscribe(Subscriber s) {
        // TODO implement Publisher.unsubscribe() here
    }

    /**
     * 
     */
    public void notifySubscribers() {
        // TODO implement Publisher.notififySubscribers() here
    }

}