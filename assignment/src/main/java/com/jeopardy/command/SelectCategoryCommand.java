package com.jeopardy.command;

import com.jeopardy.game.GameEngine;

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

    private final String category;
    private final GameEngine engine;

    /**
     * Constructs a SelectCategoryCommand with the specified category and game engine.
     *
     * @param category the name of the category to select
     * @param engine the GameEngine instance to interact with
     */
    public SelectCategoryCommand(String category, GameEngine engine) {
        this.category = category;
        this.engine = engine;
    }

    /**
     * Executes the category selection command.
     *
     * Validates that the category is not null or empty, then processes the selection.
     * Displays an error message if validation fails, otherwise confirms the selection.
     */
    @Override
    public void execute() {
        if (category == null || category.trim().isEmpty()) {
            System.out.println("Error: No category selected");
            return;
        }

        System.out.println("Category selected: " + category);
    }
}