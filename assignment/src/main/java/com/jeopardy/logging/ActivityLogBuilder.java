package com.jeopardy.logging;

import com.jeopardy.game.Player;

import java.time.Instant;

/**
 * 
 */
public class ActivityLogBuilder {

    /**
     * Default constructor
     */
    public ActivityLogBuilder() {
    }

    /**
     * 
     */
    private String caseId;

    /**
     * 
     */
    private String playerId;

    /**
     * 
     */
    private String activityId;

    /**
     * 
     */
    private Instant timestamp;

    /**
     * 
     */
    private String category;

    /**
     * 
     */
    private int questionValue;

    /**
     * 
     */
    private String answerGiven;

    /**
     * 
     */
    private String result;

    /**
     * 
     */
    private int scoreAfterPlay;



    /**
     * @param id 
     * @return
     */
    public ActivityLogBuilder setCaseId(String id) {
        // TODO implement here
        return null;
    }

    /**
     * @param player 
     * @return
     */
    public ActivityLogBuilder setPlayerId(Player player) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public ActivityLogBuilder setActivityId(String id) {
        // TODO implement here
        return null;
    }

    /**
     * @param category 
     * @return
     */
    public ActivityLogBuilder setCategory(String category) {
        // TODO implement here
        return null;
    }

    /**
     * @param value 
     * @return
     */
    public ActivityLogBuilder setQuestionValue(int value) {
        // TODO implement here
        return null;
    }

    /**
     * @param answer 
     * @return
     */
    public ActivityLogBuilder setAnswerGiven(String answer) {
        // TODO implement here
        return null;
    }

    /**
     * @param result 
     * @return
     */
    public ActivityLogBuilder setResult(String result) {
        // TODO implement here
        return null;
    }

    /**
     * @param score 
     * @return
     */
    public ActivityLogBuilder setScoreAfterPlay(int score) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public ActivityLog createActivityLog() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void reset() {
        // TODO implement here
    }

}