package com.jeopardy.logging;

import java.time.Instant;

import com.jeopardy.question.Question;
import com.jeopardy.utils.ActivityType;

/**
 * ActivityLog represents a single recorded activity or event during a Jeopardy game session.
 *
 * This class captures detailed information about player actions, including:
 * - Question selection and category
 * - Player responses and correctness
 * - Score changes and game progression
 * - Timestamp of the activity
 *
 * SOLID principles:
 * - Proper encapsulation: Fields are now private with public getters
 * - Use ActivityLogBuilder to construct instances
 *
 * ActivityLog instances are used for game auditing, reporting, and creating activity summaries
 * that can be exported in various formats (CSV, DOCX, PDF, TXT).
 */
public class ActivityLog {

    /**
     * Unique identifier for the game case/session.
     */
    private String caseId;

    /**
     * Identifier of the player who performed the activity.
     */
    private String playerId;

    /**
     * Type of activity performed (e.g., SELECT_CATEGORY, SELECT_QUESTION, ANSWER_QUESTION).
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
     * The turn number in which this activity occurred.
     */
    private int turn;

    /**
     * The Question object associated with this activity.
     */
    private Question question;

    // ==================== Getters ====================

    /**
     * Gets the case ID.
     * @return the case ID
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * Gets the player ID.
     * @return the player ID
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Gets the activity type.
     * @return the activity type
     */
    public ActivityType getActivity() {
        return activity;
    }

    /**
     * Gets the timestamp.
     * @return the timestamp
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the category.
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets the question value.
     * @return the question value
     */
    public int getQuestionValue() {
        return questionValue;
    }

    /**
     * Gets the answer given by the player.
     * @return the answer given
     */
    public String getAnswerGiven() {
        return answerGiven;
    }

    /**
     * Gets the result.
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * Gets the score after play.
     * @return the score after play
     */
    public int getScoreAfterPlay() {
        return scoreAfterPlay;
    }

    /**
     * Gets the turn number.
     * @return the turn number
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Gets the question.
     * @return the question
     */
    public Question getQuestion() {
        return question;
    }

    // ==================== Package-private Setters (for Builder) ====================

    /**
     * Sets the case ID. Package-private for use by ActivityLogBuilder.
     * @param caseId the case ID to set
     */
    void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    /**
     * Sets the player ID. Package-private for use by ActivityLogBuilder.
     * @param playerId the player ID to set
     */
    void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * Sets the activity type. Package-private for use by ActivityLogBuilder.
     * @param activity the activity type to set
     */
    void setActivity(ActivityType activity) {
        this.activity = activity;
    }

    /**
     * Sets the timestamp. Package-private for use by ActivityLogBuilder.
     * @param timestamp the timestamp to set
     */
    void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets the category. Package-private for use by ActivityLogBuilder.
     * @param category the category to set
     */
    void setCategory(String category) {
        this.category = category;
    }

    /**
     * Sets the question value. Package-private for use by ActivityLogBuilder.
     * @param questionValue the question value to set
     */
    void setQuestionValue(int questionValue) {
        this.questionValue = questionValue;
    }

    /**
     * Sets the answer given. Package-private for use by ActivityLogBuilder.
     * @param answerGiven the answer given to set
     */
    void setAnswerGiven(String answerGiven) {
        this.answerGiven = answerGiven;
    }

    /**
     * Sets the result. Package-private for use by ActivityLogBuilder.
     * @param result the result to set
     */
    void setResult(String result) {
        this.result = result;
    }

    /**
     * Sets the score after play. Package-private for use by ActivityLogBuilder.
     * @param scoreAfterPlay the score after play to set
     */
    void setScoreAfterPlay(int scoreAfterPlay) {
        this.scoreAfterPlay = scoreAfterPlay;
    }

    /**
     * Sets the turn. Package-private for use by ActivityLogBuilder.
     * @param turn the turn to set
     */
    void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * Sets the question. Package-private for use by ActivityLogBuilder.
     * @param question the question to set
     */
    void setQuestion(Question question) {
        this.question = question;
    }


    // ==================== String Representation Methods ====================

    /**
     * Generates a summary string representation of this activity log.
     *
     * The summary includes:
     * - The turn number and player who selected the question
     * - The category and point value of the question
     * - The question text
     * - The player's answer and whether it was correct or incorrect with points awarded
     * - The player's score after this turn
     *
     * @return a formatted string containing a human-readable summary of this activity
     */
    public String toSummaryString() {
        if (getQuestion() == null) return null;
        String line1 = String.format("Turn %d: %s selected %s for %d pts\n",
            getTurn(), getPlayerId(), getCategory(), getQuestionValue());

        String line2 = String.format("Question: %s\n", getQuestion().getQuestion());

        String line3;
        // Use the stored result instead of re-evaluating (which would trigger validation errors)
        boolean isCorrect = "Correct".equalsIgnoreCase(getResult());
        if (isCorrect) {
            String answerText = getQuestion().getOptions() != null && getQuestion().getOptions().containsKey(getAnswerGiven())
                ? getQuestion().getOptions().get(getAnswerGiven())
                : getAnswerGiven();
            line3 = String.format("Answer: %s — Correct (+%d pts)\n", answerText, getQuestionValue());
        } else {
            String answerText = getQuestion().getOptions() != null && getQuestion().getOptions().containsKey(getAnswerGiven())
                ? getQuestion().getOptions().get(getAnswerGiven())
                : getAnswerGiven();
            line3 = String.format("Answer: %s — Incorrect (0 pts)\n", answerText);
        }

        String line4 = String.format("Score after turn: %s = %d", getPlayerId(), getScoreAfterPlay());

        return String.format("%s%s%s%s", line1, line2, line3, line4);
    }


    /**
     * Converts this activity log to a CSV (Comma-Separated Values) formatted string.
     *
     * The CSV format includes the following fields in order:
     * Case_ID, Player_ID, Activity, Timestamp, Category, Question_Value, Answer_Given, Result, Score_After_Play
     *
     * This method is used for exporting activity logs to CSV files for reporting and analysis.
     *
     * @return a CSV formatted string representation of this activity log
     */
    public String toCSVString() {
        // Case_ID, Player_ID, Activity, Timestamp, Category, Question_Value, Answer_Given, Result, Score_After_Play
        return String.format("%s,%s,%s,%s,%s,%d,%s,%s,%d\n",
            getCaseId(),
            getPlayerId(),
            getActivity().toString(),
            getTimestamp().toString(),
            getCategory(),
            getQuestionValue(),
            getAnswerGiven(),
            getResult(),
            getScoreAfterPlay()
        );
    }

}