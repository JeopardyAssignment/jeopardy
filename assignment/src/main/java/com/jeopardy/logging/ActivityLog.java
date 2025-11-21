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
 * ActivityLog instances are used for game auditing, reporting, and creating activity summaries
 * that can be exported in various formats (CSV, DOCX, PDF, TXT).
 */
public class ActivityLog {

    /**
     * Unique identifier for the game case/session.
     */
    public String caseId;

    /**
     * Identifier of the player who performed the activity.
     */
    public String playerId;

    /**
     * Type of activity performed (e.g., SELECT_CATEGORY, SELECT_QUESTION, ANSWER_QUESTION).
     */
    public ActivityType activity;

    /**
     * The timestamp when this activity was recorded.
     */
    public Instant timestamp;

    /**
     * The category of the question selected or answered.
     */
    public String category;

    /**
     * The point value of the question selected or answered.
     */
    public int questionValue;

    /**
     * The answer provided by the player.
     */
    public String answerGiven;

    /**
     * The result of the answer (e.g., "Correct", "Incorrect").
     */
    public String result;

    /**
     * The player's score after this activity was recorded.
     */
    public int scoreAfterPlay;

    /**
     * The turn number in which this activity occurred.
     */
    public int turn;
  
    /**
     * The Question object associated with this activity.
     */
    public Question question;


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
        if (question == null) return null;
        String line1 = String.format("Turn %d: %s selected %s for %d pts\n", this.turn, this.playerId, this.category, this.questionValue);
        
        String line2 = String.format("Question: %s\n", this.question.getQuestion());
        
        String line3;
        if (question.evaluate(answerGiven)) {
            String answerText = question.getOptions() != null && question.getOptions().containsKey(this.answerGiven) 
                ? question.getOptions().get(this.answerGiven) 
                : this.answerGiven;
            line3 = String.format("Answer: %s — Correct (+%d pts)\n", answerText, this.questionValue);
        } else {
            String answerText = question.getOptions() != null && question.getOptions().containsKey(this.answerGiven) 
                ? question.getOptions().get(this.answerGiven) 
                : this.answerGiven;
            line3 = String.format("Answer: %s — Incorrect (0 pts)\n", answerText);
        }

        String line4 = String.format("Score after turn: %s = %d", this.playerId, this.scoreAfterPlay);

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
            this.caseId,
            this.playerId,
            this.activity.toString(),
            this.timestamp.toString(),
            this.category,
            this.questionValue,
            this.answerGiven,
            this.result,
            this.scoreAfterPlay
        );
    }

}