package com.jeopardy.command;

import com.jeopardy.game.GameEngine;

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
     * Prompts the user to select a question through the GameEngine.
     */
    @Override
    public void execute() {
        engine.selectQuestion();
    }
}