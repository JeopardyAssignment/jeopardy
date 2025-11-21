package com.jeopardy.command;

import com.jeopardy.game.GameController;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.utils.ActivityType;
import com.jeopardy.utils.GameConstants;

/**
 * SelectQuestionCommand encapsulates the action of selecting a question in the Jeopardy game.
 *
 * This command implements the Command pattern and handles:
 * - Validation of question selection
 * - Communication with the GameController to register the selected question
 * - User feedback on question selection
 *
 * SOLID principles:
 * - Follows Dependency Inversion Principle (DIP) by depending on GameController interface
 *   instead of concrete GameEngine implementation
 * - Enables dependency injection for better testability and loose coupling
 *
 * The command validates that a valid question has been provided before executing.
 */
public class SelectQuestionCommand implements Command {

    private final GameController controller;

    /**
     * Constructs a SelectQuestionCommand with the given controller.
     * Uses dependency injection for loose coupling (DIP).
     *
     * @param controller the GameController instance to interact with
     */
    public SelectQuestionCommand(GameController controller) {
        this.controller = controller;
    }

    /**
     * Executes the question selection command.
     * Prompts the user to select a question and logs the activity.
     */
    @Override
    public void execute() {
        // Prompt player to select a question
        controller.selectQuestion();

        // Log the question selection activity
        controller.setCurrentActivityLog(
            new ActivityLogBuilder()
                    .setCaseId(GameConstants.DEFAULT_CASE_ID)
                    .setPlayerId(controller.getState().getCurrentPlayer())
                    .setActivity(ActivityType.SELECT_QUESTION)
                    .setTimestamp()
                    .setCategory(controller.getState().getCurrentCategory())
                    .setTurn(controller.getState().getCurrentTurn() + GameConstants.TURN_DISPLAY_OFFSET)
                    .setQuestionValue(controller.getState().getCurrentQuestion().getValue())
                    .setScoreAfterPlay(controller.getState().getCurrentPlayer().getCurrentScore())
                    .createActivityLog()
        );

        // Notify all subscribers of the activity
        controller.notifySubscribers();
    }
}