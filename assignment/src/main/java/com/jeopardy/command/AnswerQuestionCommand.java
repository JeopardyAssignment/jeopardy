package com.jeopardy.command;

import com.jeopardy.game.GameController;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.question.Question;
import com.jeopardy.utils.ActivityType;
import com.jeopardy.utils.GameConstants;

/**
 * AnswerQuestionCommand encapsulates the action of answering a question in the Jeopardy game.
 *
 * This command implements the Command pattern and handles:
 * - Validation of the question and answer
 * - Evaluation of the player's answer
 * - Notification of game state changes via the GameController
 * - User feedback on answer correctness
 *
 * SOLID principles:
 * - Follows Dependency Inversion Principle (DIP) by depending on GameController interface
 *   instead of concrete GameEngine implementation
 * - Enables dependency injection for better testability and loose coupling
 *
 * The command evaluates the player's choice against the correct answer and provides
 * appropriate feedback, including the correct answer when the player's response is incorrect.
 */
public class AnswerQuestionCommand implements Command {

    private final Question question;
    private final String choice;
    private final GameController controller;

    /**
     * Constructs an AnswerQuestionCommand with the specified answer choice and controller.
     * Uses dependency injection for loose coupling (DIP).
     *
     * @param controller the GameController instance to interact with
     * @param choice the player's answer choice
     */
    public AnswerQuestionCommand(GameController controller, String choice) {
        this.controller = controller;
        this.question = this.controller.getState().getCurrentQuestion();
        this.choice = choice;
    }

    /**
     * Executes the answer question command.
     * Evaluates the player's answer, updates score if correct, provides feedback,
     * and logs the activity.
     */
    @Override
    public void execute() {
        if (question == null) {
            System.out.println(GameConstants.ERROR_NO_QUESTION);
            return;
        }

        boolean isCorrect = question.evaluate(choice);

        if (isCorrect) {
            // Award points for correct answer
            controller.updateCurrentPlayerScore(question.getValue());
            System.out.println(GameConstants.MESSAGE_CORRECT);

            // Log correct answer activity
            controller.setCurrentActivityLog(
                new ActivityLogBuilder()
                        .setCaseId(GameConstants.DEFAULT_CASE_ID)
                        .setPlayerId(controller.getState().getCurrentPlayer())
                        .setActivity(ActivityType.ANSWER_QUESTION)
                        .setTimestamp()
                        .setResult(GameConstants.RESULT_CORRECT)
                        .setCategory(controller.getState().getCurrentCategory())
                        .setQuestionValue(controller.getState().getCurrentQuestion().getValue())
                        .setQuestion(question)
                        .setTurn(controller.getState().getCurrentTurn() + GameConstants.TURN_DISPLAY_OFFSET)
                        .setScoreAfterPlay(controller.getState().getCurrentPlayer().getCurrentScore())
                        .setAnswerGiven(choice)
                        .createActivityLog()
            );
        } else {
            // No points for incorrect answer
            System.out.println(String.format(GameConstants.MESSAGE_INCORRECT_FORMAT, question.getCorrectAnswer()));

            // Log incorrect answer activity
            controller.setCurrentActivityLog(
                new ActivityLogBuilder()
                        .setCaseId(GameConstants.DEFAULT_CASE_ID)
                        .setPlayerId(controller.getState().getCurrentPlayer())
                        .setActivity(ActivityType.ANSWER_QUESTION)
                        .setTimestamp()
                        .setResult(GameConstants.RESULT_INCORRECT)
                        .setCategory(controller.getState().getCurrentCategory())
                        .setQuestionValue(controller.getState().getCurrentQuestion().getValue())
                        .setTurn(controller.getState().getCurrentTurn() + GameConstants.TURN_DISPLAY_OFFSET)
                        .setQuestion(question)
                        .setScoreAfterPlay(controller.getState().getCurrentPlayer().getCurrentScore())
                        .setAnswerGiven(choice)
                        .createActivityLog()
            );
        }

        // Notify all subscribers of the activity
        controller.notifySubscribers();
    }
}