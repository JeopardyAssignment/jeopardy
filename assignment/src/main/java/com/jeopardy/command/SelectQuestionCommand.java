package com.jeopardy.command;

import com.jeopardy.game.GameEngine;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.utils.ActivityType;

/**
 * SelectQuestionCommand encapsulates the action of selecting a question in the Jeopardy game.
 *
 * This command implements the Command pattern and handles:
 * - Validation of question selection
 * - Communication with the GameEngine to register the selected question
 * - User feedback on question selection
 *
 * The command validates that a valid question has been provided before executing.
 */
public class SelectQuestionCommand implements Command {

    private final GameEngine engine;

    /**
     * Constructs a SelectQuestionCommand.
     * Gets the GameEngine singleton instance for interaction.
     */
    public SelectQuestionCommand() {
        this.engine = GameEngine.Instance();
    }

    /**
     * Executes the question selection command.
     * Prompts the user to select a question and logs the activity.
     */
    @Override
    public void execute() {
        // Prompt player to select a question
        engine.selectQuestion();

        // Log the question selection activity
        engine.setCurrentActivityLog(
            new ActivityLogBuilder()
                    .setCaseId("GAME-001")
                    .setPlayerId(engine.getState().getCurrentPlayer())
                    .setActivity(ActivityType.SELECT_QUESTION)
                    .setTimestamp()
                    .setCategory(engine.getState().getCurrentCategory())
                    .setTurn(engine.getState().getCurrentTurn() + 1)
                    .setQuestionValue(engine.getState().getCurrentQuestion().getValue())
                    .setScoreAfterPlay(engine.getState().getCurrentPlayer().getCurrentScore())
                    .createActivityLog()
        );

        // Notify all subscribers of the activity
        engine.notifySubscribers();
    }
}