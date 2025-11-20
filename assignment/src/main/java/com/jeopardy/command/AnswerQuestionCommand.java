package com.jeopardy.command;

import com.jeopardy.game.GameEngine;
import com.jeopardy.question.Question;

/**
 * AnswerQuestionCommand encapsulates the action of answering a question in the Jeopardy game.
 *
 * This command implements the Command pattern and handles:
 * - Validation of the question and answer
 * - Evaluation of the player's answer
 * - Notification of game state changes via the GameEngine
 * - User feedback on answer correctness
 *
 * The command evaluates the player's choice against the correct answer and provides
 * appropriate feedback, including the correct answer when the player's response is incorrect.
 */
public class AnswerQuestionCommand implements Command {

    private final Question question;
    private final String choice;
    private final GameEngine engine;

    /**
     * Constructs an AnswerQuestionCommand with the specified question, answer choice, and game engine.
     *
     * @param question the Question object being answered
     * @param choice the player's answer choice
     * @param engine the GameEngine instance to interact with
     */
    public AnswerQuestionCommand(Question question, String choice, GameEngine engine) {
        this.question = question;
        this.choice = choice;
        this.engine = engine;
    }

    /**
     * Executes the answer question command.
     *
     * Validates that the question is not null, evaluates the player's answer,
     * notifies subscribers via the GameEngine, and provides feedback on whether
     * the answer was correct or incorrect.
     */
    @Override
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