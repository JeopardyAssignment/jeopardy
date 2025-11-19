package com.jeopardy.question;

import java.util.*;

/**
 * 
 */
public class Question {

    /**
     * Default constructor
     */
    public Question() {
        this.isAnswered = false;

    }

    /**
     * 
     */
    private String category;

    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    private String question;
    public void setQuestion(String question) {
        this.question = question;
    }


    /**
     * 
     */
    private int value;
    public int getValue() {
        return this.value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * 
     */
    private String correctAnswer;
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


    /**
     * 
     */
    private Map<String, String> options;
    public void setOptions(String[] options) {
        this.options = new HashMap<>();
        char optionLabel = 'A';
        for (String option : options) {
            this.options.put(String.valueOf(optionLabel), option);
            optionLabel++;
        }
    }
    

    public void setOptions(ArrayList<String> options) {
        this.options = new HashMap<>();
        char optionLabel = 'A';
        for (String option : options) {
            this.options.put(String.valueOf(optionLabel), option);
            optionLabel++;
        }
    }

    /**
     * 
     */
    private boolean isAnswered;
    public boolean getIsAnswered() {
        return this.isAnswered;
    }
    public void setIsAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }




    public Map<String, String> getOptions() {
        return this.options;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public String getQuestion() {
        return this.question;
    }

    /**
     * @param answer 
     * @return
     */
    public boolean evaluate(String answer) {
        if (answer.equalsIgnoreCase(this.correctAnswer)) {
            return true;
        }

        return false;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category: ").append(this.category).append("\n");
        sb.append("Question: ").append(this.question).append("\n");
        sb.append("Value: ").append(this.value).append("\n");
        sb.append("Options: \n");
        for (Map.Entry<String, String> entry : this.options.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        sb.append("Correct Answer: ").append(this.correctAnswer).append("\n");
        sb.append("Is Answered: ").append(this.isAnswered).append("\n");
        return sb.toString();
    }

}