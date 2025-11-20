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
     * Constructs an AnswerQuestionCommand with the specified answer choice.
     * Gets the GameEngine singleton instance and retrieves the current question.
     *
     * @param choice the player's answer choice
     */
    public AnswerQuestionCommand(String choice) {
        this.engine = GameEngine.Instance();
        this.question = this.engine.getCurrentQuestion();
        this.choice = choice;
    }

    /**
     * Executes the answer question command.
     *
     * Evaluates the player's answer, updates the player's score if correct,
     * and provides feedback. Notifies subscribers via the GameEngine.
     */
    @Override
    public void execute() {
        if (question == null) {
            System.out.println("[Error]: No question to answer");
            return;
        }

        boolean isCorrect = question.evaluate(choice);

        engine.notifySubscribers();

        if (isCorrect) {
            engine.updateCurrentPlayerScore(question.getValue());
            System.out.println("That is correct!");
        } else {
            System.out.println("Incorrect. The correct answer is " + question.getCorrectAnswer());
        }
    }
}