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
    }

    /**
     * 
     */
    private String category;

    /**
     * 
     */
    private String question;

    /**
     * 
     */
    private int value;

    /**
     * 
     */
    private String correctAnswer;

    /**
     * 
     */
    private Map<String, String> options;

    /**
     * 
     */
    private boolean isAnswered;




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
        // TODO implement here
        return false;
    }

}