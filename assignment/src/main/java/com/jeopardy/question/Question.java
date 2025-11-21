package com.jeopardy.question;

import java.util.*;
import com.jeopardy.exception.InvalidQuestionStateException;

/**
 * Question represents a single Jeopardy game question with multiple choice options.
 *
 * This class encapsulates all information about a question including:
 * - Category (e.g., "Science", "History", "Geography")
 * - Question text
 * - Point value
 * - Multiple choice options (labeled A, B, C, D, etc.)
 * - Correct answer
 * - Whether the question has been answered
 *
 * The class provides methods for:
 * - Setting and getting question properties
 * - Evaluating player answers
 * - Converting options from arrays/lists to a labeled map
 * - Generating string representations for display
 */
public class Question {

    private String category;
    private String question;
    private int value;
    private String correctAnswer;
    private Map<String, String> options;
    private boolean isAnswered;

    /**
     * Constructs a new Question with default values.
     * The isAnswered flag is initialized to false.
     */
    public Question() {
        this.isAnswered = false;
    }

    // ==================== Category Accessors ====================

    /**
     * Gets the category of this question.
     *
     * @return the category name
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Sets the category of this question.
     *
     * @param category the category name to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    // ==================== Question Text Accessors ====================

    /**
     * Gets the question text.
     *
     * @return the question text
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * Sets the question text.
     *
     * @param question the question text to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    // ==================== Value Accessors ====================

    /**
     * Gets the point value of this question.
     *
     * @return the point value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Sets the point value of this question.
     *
     * @param value the point value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    // ==================== Answer Management ====================

    /**
     * Gets the correct answer for this question.
     *
     * @return the correct answer
     */
    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    /**
     * Sets the correct answer for this question.
     *
     * @param correctAnswer the correct answer to set
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Checks whether this question has been answered.
     *
     * @return true if the question has been answered, false otherwise
     */
    public boolean getIsAnswered() {
        return this.isAnswered;
    }

    /**
     * Sets whether this question has been answered.
     *
     * @param isAnswered true if the question has been answered, false otherwise
     */
    public void setIsAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    /**
     * Evaluates whether the given answer is correct.
     * Marks the question as answered and performs case-insensitive comparison.
     *
     * SOLID principles:
     * - Added validation to prevent invalid state (improved error handling)
     *
     * @param answer the player's answer to evaluate
     * @return true if the answer is correct, false otherwise
     * @throws InvalidQuestionStateException if the question has already been answered
     * @throws IllegalArgumentException if the answer is null or empty
     * @throws IllegalStateException if the correct answer has not been set
     */
    public boolean evaluate(String answer) {
        // Validation: Check if question already answered
        if (this.isAnswered) {
            throw new InvalidQuestionStateException("Question has already been answered");
        }

        // Validation: Check if correct answer is set
        if (this.correctAnswer == null || this.correctAnswer.trim().isEmpty()) {
            throw new IllegalStateException("Question has no correct answer set");
        }

        // Validation: Check if answer is valid
        if (answer == null || answer.trim().isEmpty()) {
            throw new IllegalArgumentException("Answer cannot be null or empty");
        }

        this.isAnswered = true;
        return answer.equalsIgnoreCase(this.correctAnswer);
    }

    // ==================== Options Management ====================

    /**
     * Gets the map of answer options with their labels.
     *
     * @return a map where keys are labels (A, B, C, D) and values are option text
     */
    public Map<String, String> getOptions() {
        return this.options;
    }

    /**
     * Sets the answer options from an array, automatically labeling them A, B, C, D, etc.
     *
     * @param options an array of option strings to set
     */
    public void setOptions(String[] options) {
        this.options = new HashMap<>();
        char optionLabel = 'A';
        for (String option : options) {
            this.options.put(String.valueOf(optionLabel), option);
            optionLabel++;
        }
    }

    /**
     * Sets the answer options from an ArrayList, automatically labeling them A, B, C, D, etc.
     *
     * @param options an ArrayList of option strings to set
     */
    public void setOptions(ArrayList<String> options) {
        this.options = new HashMap<>();
        char optionLabel = 'A';
        for (String option : options) {
            this.options.put(String.valueOf(optionLabel), option);
            optionLabel++;
        }
    }

    // ==================== String Representations ====================

    /**
     * Returns a string representation of this Question including all properties.
     *
     * @return a formatted string containing category, question text, value, options,
     *         correct answer, and answered status
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category: ").append(this.category).append("\n");
        sb.append("Question: ").append(this.question).append("\n");
        sb.append("Value: ").append(this.value).append("\n");
        sb.append("Options: \n");
        if (this.options != null) {
            for (Map.Entry<String, String> entry : this.options.entrySet()) {
                sb.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }
        sb.append("Correct Answer: ").append(this.correctAnswer).append("\n");
        sb.append("Is Answered: ").append(this.isAnswered).append("\n");
        return sb.toString();
    }

    /**
     * Returns a formatted string for prompting the user with this question.
     * Includes the question text and multiple choice options, but not the correct answer.
     *
     * @return a formatted string suitable for displaying to players
     */
    public String promptString() {
        StringBuilder sb = new StringBuilder();
        if (this.options != null) {
            for (Map.Entry<String, String> entry : this.options.entrySet()) {
                sb.append(entry.getKey()).append(") ").append(entry.getValue()).append("\n");
            }
        }
        sb.append(this.question).append(" ");
        return sb.toString();
    }
}
