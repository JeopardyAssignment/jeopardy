package com.jeopardy.question;

import java.util.*;

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
     *
     * This method performs a case-insensitive comparison between the provided
     * answer and the correct answer.
     *
     * @param answer the player's answer to evaluate
     * @return true if the answer is correct, false otherwise
     */
    public boolean evaluate(String answer) {
        return answer != null && answer.equalsIgnoreCase(this.correctAnswer);
    }

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

}
