package com.jeopardy.logging;

import java.time.Instant;
import com.jeopardy.utils.ActivityType;

/**
 * 
 */
public class ActivityLog {

    /**
     * Default constructor
     */
    public ActivityLog() {
    }

    /**
     * 
     */
    public String caseId;

    /**
     * 
     */
    public String playerId;

    /**
     * 
     */
    public ActivityType activity;

    /**
     * 
     */
    public Instant timestamp;

    /**
     * 
     */
    public String category;

    /**
     * 
     */
    public int questionValue;

    /**
     * 
     */
    public String answerGiven;

    /**
     * 
     */
    public String result;

    /**
     * 
     */
    public int scoreAfterPlay;


    /**
     * @return
     */
    public String toString() {
        // TODO implement here
        return "";
    }

}