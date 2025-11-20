package com.jeopardy.command;

import com.jeopardy.game.GameEngine;
import com.jeopardy.question.Question;

/**
 * 
 */
public class SelectQuestionCommand implements Command {

    /**
     * Default constructor
     */
   public SelectQuestionCommand(Question question, GameEngine engine) {
        this.question = question;
        this.engine = engine;
    }

    /**
     * 
     */
    private Question question;
    private GameEngine engine;



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
            System.out.println("Error: No question to answer");
            return;
        }
        engine.selectQuestion(question);
        //System.out.println("Question selected: " + question.getQuestion());
}
}