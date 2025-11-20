package com.jeopardy.command;

import com.jeopardy.game.GameEngine;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.question.Question;
import com.jeopardy.utils.ActivityType;

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
        this.question = this.engine.getState().getCurrentQuestion();
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
            System.out.println("[Error]: No question to answer");
            return;
        }

        boolean isCorrect = question.evaluate(choice);

        if (isCorrect) {
            // Award points for correct answer
            engine.updateCurrentPlayerScore(question.getValue());
            System.out.println("That is correct!");

            // Log correct answer activity
            engine.setCurrentActivityLog(
                new ActivityLogBuilder()
                        .setCaseId("GAME-001")
                        .setPlayerId(engine.getState().getCurrentPlayer())
                        .setActivity(ActivityType.ANSWER_QUESTION)
                        .setTimestamp()
                        .setResult("Correct")
                        .setCategory(engine.getState().getCurrentCategory())
                        .setQuestionValue(engine.getState().getCurrentQuestion().getValue())
                        .setQuestion(question)
                        .setTurn(engine.getState().getCurrentTurn() + 1)
                        .setScoreAfterPlay(engine.getState().getCurrentPlayer().getCurrentScore())
                        .setAnswerGiven(choice)
                        .createActivityLog()
            );
        } else {
            // No points for incorrect answer
            System.out.println("Incorrect. The correct answer is " + question.getCorrectAnswer());

            // Log incorrect answer activity
            engine.setCurrentActivityLog(
                new ActivityLogBuilder()
                        .setCaseId("GAME-001")
                        .setPlayerId(engine.getState().getCurrentPlayer())
                        .setActivity(ActivityType.ANSWER_QUESTION)
                        .setTimestamp()
                        .setResult("Incorrect")
                        .setCategory(engine.getState().getCurrentCategory())
                        .setQuestionValue(engine.getState().getCurrentQuestion().getValue())
                        .setTurn(engine.getState().getCurrentTurn() + 1)
                        .setQuestion(question)
                        .setScoreAfterPlay(engine.getState().getCurrentPlayer().getCurrentScore())
                        .setAnswerGiven(choice)
                        .createActivityLog()
            );
        }

        // Notify all subscribers of the activity
        engine.notifySubscribers();
    }
}