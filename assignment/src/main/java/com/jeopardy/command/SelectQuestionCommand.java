package com.jeopardy.command;

import com.jeopardy.question.Question;

/**
 * 
 */
public class SelectQuestionCommand implements Command {

    /**
     * Default constructor
     */
    public SelectQuestionCommand() {
    }

    /**
     * 
     */
    private Question question;


    /**
     * 
     */
        /**
     * 
     */
        /**
     * 
     */
    public void execute() {
        // TODO implement Command.execute() here
        if (question == null) {
            //System.out.println("Error: No question to answer");
            return;
        }
        
        System.out.println("Answer question command executed");
    }
}