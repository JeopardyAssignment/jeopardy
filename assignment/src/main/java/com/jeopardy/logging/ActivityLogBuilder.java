package com.jeopardy.logging;

import com.jeopardy.game.Player;
import com.jeopardy.question.Question;
import com.jeopardy.utils.ActivityType;

import java.time.Instant;

/**
 * ActivityLogBuilder is a Builder pattern implementation for constructing ActivityLog objects.
 * 
 * This class provides a fluent interface for incrementally setting the properties of an ActivityLog
 * before creating the final immutable object. All setter methods return 'this' to allow method chaining.
 * 
 * Usage Example:
 * <pre>
 * ActivityLog log = new ActivityLogBuilder()
 *     .setCaseId("case-001")
 *     .setPlayerId(player)
 *     .setActivity(ActivityType.ANSWER_QUESTION)
 *     .setCategory("Science")
 *     .setQuestionValue(200)
 *     .setAnswerGiven("DNA")
 *     .setResult("Correct")
 *     .setScoreAfterPlay(1200)
 *     .setQuestion(question)
 *     .setTurn(5)
 *     .setTimestamp()
 *     .createActivityLog();
 * </pre>
 * 
 * After building an ActivityLog, use {@link #reset()} to clear the builder for reuse.
 */
public class ActivityLogBuilder {

    /**
     * Unique identifier for the game case/session.
     */
    private String caseId;

    /**
     * Identifier of the player who performed the activity.
     */
    private String playerId;

    /**
     * Type of activity performed.
     */
    private ActivityType activity;

    /**
     * The timestamp when this activity was recorded.
     */
    private Instant timestamp;

    /**
     * The category of the question selected or answered.
     */
    private String category;

    /**
     * The point value of the question selected or answered.
     */
    private int questionValue;

    /**
     * The answer provided by the player.
     */
    private String answerGiven;

    /**
     * The result of the answer (e.g., "Correct", "Incorrect").
     */
    private String result;

    /**
     * The player's score after this activity was recorded.
     */
    private int scoreAfterPlay;

    /**
     * The Question object associated with this activity.
     */
    private Question question;

    /**
     * The turn number in which this activity occurred.
     */
    private int turn;


    /**
     * Sets the case ID for the activity log being built.
     * 
     * @param id the unique identifier for the game case/session
     * @return this ActivityLogBuilder instance for method chaining
     */
    public ActivityLogBuilder setCaseId(String id) {
        this.caseId = id;
        return this;
    }

    /**
     * Sets the player ID from a Player object.
     * 
     * @param player the Player object whose ID will be extracted and set
     * @return this ActivityLogBuilder instance for method chaining
     */
    public ActivityLogBuilder setPlayerId(Player player) {
        this.playerId = player.getId();
        return this;
    }

    /**
     * Sets the type of activity being logged.
     * 
     * @param t the ActivityType (e.g., SELECT_CATEGORY, SELECT_QUESTION, ANSWER_QUESTION)
     * @return this ActivityLogBuilder instance for method chaining
     */
    public ActivityLogBuilder setActivity(ActivityType t) {
        this.activity = t;
        return this;
    }

    /**
     * Sets the category of the question associated with this activity.
     * 
     * @param category the question category (e.g., "Science", "History", "Geography")
     * @return this ActivityLogBuilder instance for method chaining
     */
    public ActivityLogBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * Sets the point value of the question associated with this activity.
     * 
     * @param value the point value (e.g., 100, 200, 300, 400, 500)
     * @return this ActivityLogBuilder instance for method chaining
     */
    public ActivityLogBuilder setQuestionValue(int value) {
        this.questionValue = value;
        return this;
    }

    /**
     * Sets the answer given by the player.
     * 
     * @param answer the player's response to the question
     * @return this ActivityLogBuilder instance for method chaining
     */
    public ActivityLogBuilder setAnswerGiven(String answer) {
        this.answerGiven = answer;
        return this;
    }

    /**
     * Sets the result of the answer (e.g., whether it was correct or incorrect).
     * 
     * @param result the result string indicating the outcome of the answer
     * @return this ActivityLogBuilder instance for method chaining
     */
    public ActivityLogBuilder setResult(String result) {
        this.result = result;
        return this;
    }

    /**
     * Sets the player's score after this activity is completed.
     * 
     * @param score the player's updated score
     * @return this ActivityLogBuilder instance for method chaining
     */
    public ActivityLogBuilder setScoreAfterPlay(int score) {
        this.scoreAfterPlay = score;
        return this;
    }

    /**
     * Sets the Question object associated with this activity.
     * 
     * @param q the Question object
     * @return this ActivityLogBuilder instance for method chaining
     */
    public ActivityLogBuilder setQuestion(Question q) {
        this.question = q;
        return this;
    }
   
    /**
     * Sets the turn number for this activity.
     * 
     * @param t the turn number in the game
     * @return this ActivityLogBuilder instance for method chaining
     */
    public ActivityLogBuilder setTurn(int t) {
        this.turn = t;
        return this;
    }
    
    /**
     * Sets the timestamp to the current time when this method is called.
     * 
     * @return this ActivityLogBuilder instance for method chaining
     */
    public ActivityLogBuilder setTimestamp() {
        this.timestamp = Instant.now();
        return this;
    }

    /**
     * Constructs and returns a new ActivityLog object with all the values set through this builder.
     * 
     * This method creates a new ActivityLog instance and populates it with all the properties
     * that have been set on this builder. The builder itself is not modified by this operation.
     * 
     * @return a new ActivityLog object with all properties from this builder
     */
    public ActivityLog createActivityLog() {
        ActivityLog log = new ActivityLog();
        
        log.answerGiven = this.answerGiven;
        log.caseId = this.caseId;
        log.playerId = this.playerId;
        log.question = this.question;
        log.questionValue = this.questionValue;
        log.result = this.result;
        log.timestamp = this.timestamp;
        log.scoreAfterPlay = this.scoreAfterPlay;
        log.turn = this.turn;
        log.activity = this.activity;
        log.category = this.category;

        return log;
    }

    /**
     * Resets all fields in this builder to their default/null values.
     * 
     * This method should be called after creating an ActivityLog to prepare this builder
     * for constructing a new ActivityLog object. It clears all internal state:
     * - String fields are set to null
     * - int fields are reset to 0
     * - Object fields are set to null
     */
    public void reset() {
        this.answerGiven = null;
        this.caseId = null;
        this.playerId = null;
        this.question = null;
        this.questionValue = 0;
        this.result = null;
        this.timestamp = null;
        this.scoreAfterPlay = 0;
        this.turn = 0;
        this.activity = null;
    }

}