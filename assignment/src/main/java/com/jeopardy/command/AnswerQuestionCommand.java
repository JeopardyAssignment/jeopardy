package com.jeopardy.command;

import com.jeopardy.game.GameEngine;
import com.jeopardy.question.Question;

/**
 * 
 */
public class AnswerQuestionCommand implements Command {

    public AnswerQuestionCommand(Question question, String choice, GameEngine engine) {
        this.question = question;
        this.choice = choice;
        this.engine = engine;
    }
    /**
     * 
     */
    private Question question;
     private String choice;
    private GameEngine engine;


    /**
     * 
     */
        /**
     * 
     */
   public void execute() {
        if (question == null) {
            System.out.println("Error: No question to answer");
            return;
        }

        boolean correct = question.evaluate(choice);

       
        engine.notifySubscribers();

        
        System.out.println("Question: " + question.getQuestion());
        if (correct) {
            System.out.println("Correct! Answer submitted: " + choice);
        } else {
            System.out.println("Incorrect. You submitted: " + choice);
            System.out.println("Correct answer: " + question.getCorrectAnswer());
        }
    }
}