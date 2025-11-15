package com.jeopardy.command;

import com.jeopardy.question.Question;

/**
 * 
 */
public class AnswerQuestionCommand implements Command {

    /**
     * Default constructor
     */
    public AnswerQuestionCommand() {
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
    public void execute() {
   
        if (question == null) {
          //  System.out.println("Error: No question to answer");
            return;
        }
        
        System.out.println("Answer question command executed successfully");
    }
}