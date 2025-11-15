package com.jeopardy.game;

import com.jeopardy.logging.observer.Publisher;
import com.jeopardy.command.Command;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.logging.observer.Subscriber;
import com.jeopardy.logging.ActivityLog;
import com.jeopardy.utils.ActivityType;


import java.util.*;

/**
 * 
 */
public class Player implements Publisher {

    /**
     * Default constructor
     */
    public Player(String id) {
        this.id = id;
        this.subscribers = new ArrayList<>();
        this.activityLogBuilder = new ActivityLogBuilder();
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
        return this.id;
    }

    /**
     * @param command
     */
    public void setCommand(Command command) {
        this.command = command;    }

    /**
     * 
     */
    public void doCommand() {
         if (this.command != null) {
            this.command.execute();
        }
    }

    /**
     * @param s
     */
    public void subscribe(Subscriber s) {
         if (s != null && !this.subscribers.contains(s)) {
            this.subscribers.add(s);
        }
    }

    /**
     * @param s
     */
    public void unsubscribe(Subscriber s) {
        if (s != null && this.subscribers != null) {
        this.subscribers.remove(s);
    }
}

    /**
     * 
     */
public void notifySubscribers() {
    if (this.subscribers != null) {
        for (Subscriber subscriber : this.subscribers) {
            if (subscriber != null) {
                // Use an existing ActivityType value
                ActivityLog activity = new ActivityLogBuilder()
                    .setCaseId("GAME001")
                    .setPlayerId(this)
                    .setActivity(ActivityType.GAME_UPDATE) // Use existing enum value
                    .setTimestamp()
                    .createActivityLog();
                
                subscriber.update(activity);
            }
        }
    }
}
}