package com.jeopardy.command;

import com.jeopardy.game.GameController;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.utils.ActivityType;
import com.jeopardy.utils.GameConstants;

/**
 * SelectCategoryCommand encapsulates the action of selecting a category in the Jeopardy game.
 *
 * This command implements the Command pattern and handles:
 * - Validation of category selection
 * - Communication with the GameController
 * - User feedback on category selection
 *
 * SOLID principles:
 * - Follows Dependency Inversion Principle (DIP) by depending on GameController interface
 *   instead of concrete GameEngine implementation
 * - Enables dependency injection for better testability and loose coupling
 *
 * The command validates that a non-empty category has been provided before executing.
 */
public class SelectCategoryCommand implements Command {

    private final GameController controller;

    /**
     * Constructs a SelectCategoryCommand with the given controller.
     * Uses dependency injection for loose coupling (DIP).
     *
     * @param controller the GameController instance to interact with
     */
    public SelectCategoryCommand(GameController controller) {
        this.controller = controller;
    }

    /**
     * Executes the category selection command.
     * Prompts the user to select a category and logs the activity.
     */
    @Override
    public void execute() {
        // Prompt player to select a category
        this.controller.selectCategory();

        // Log the category selection activity
        controller.setCurrentActivityLog(
            new ActivityLogBuilder()
                    .setCaseId(GameConstants.DEFAULT_CASE_ID)
                    .setPlayerId(controller.getState().getCurrentPlayer())
                    .setActivity(ActivityType.SELECT_CATEGORY)
                    .setTimestamp()
                    .setCategory(controller.getState().getCurrentCategory())
                    .setTurn(controller.getState().getCurrentTurn() + GameConstants.TURN_DISPLAY_OFFSET)
                    .setScoreAfterPlay(controller.getState().getCurrentPlayer().getCurrentScore())
                    .createActivityLog()
        );

        // Notify all subscribers of the activity
        controller.notifySubscribers();
    }
}