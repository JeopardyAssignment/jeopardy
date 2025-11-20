package com.jeopardy.command;

import com.jeopardy.game.GameEngine;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.utils.ActivityType;

/**
 * SelectCategoryCommand encapsulates the action of selecting a category in the Jeopardy game.
 *
 * This command implements the Command pattern and handles:
 * - Validation of category selection
 * - Communication with the GameEngine
 * - User feedback on category selection
 *
 * The command validates that a non-empty category has been provided before executing.
 */
public class SelectCategoryCommand implements Command {

    private final GameEngine engine;

    /**
     * Constructs a SelectCategoryCommand.
     * Gets the GameEngine singleton instance for interaction.
     */
    public SelectCategoryCommand() {
        this.engine = GameEngine.Instance();
    }

    /**
     * Executes the category selection command.
     * Prompts the user to select a category through the GameEngine.
     */
    @Override
    public void execute() {
        this.engine.selectCategory();

        engine.setCurrentActivityLog(
            new ActivityLogBuilder()
                    .setCaseId("GAME-001")
                    .setPlayerId(engine.getState().getCurrentPlayer())
                    .setActivity(ActivityType.SELECT_CATEGORY)
                    .setTimestamp()
                    .setCategory(engine.getState().getCurrentCategory())
                    .setTurn(engine.getState().getCurrentTurn() + 1)
                    .setScoreAfterPlay(engine.getState().getCurrentPlayer().getCurrentScore())
                    .createActivityLog()
        );

        engine.notifySubscribers();
    }
}