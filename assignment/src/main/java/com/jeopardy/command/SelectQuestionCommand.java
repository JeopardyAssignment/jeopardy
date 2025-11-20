package com.jeopardy.command;

import com.jeopardy.game.GameEngine;
import com.jeopardy.question.Question;

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

    private final Question question;
    private final GameEngine engine;

    /**
     * Constructs a SelectQuestionCommand with the specified question and game engine.
     *
     * @param question the Question object to select
     * @param engine the GameEngine instance to interact with
     */
    public SelectQuestionCommand(Question question, GameEngine engine) {
        this.question = question;
        this.engine = engine;
    }

    /**
     * Executes the question selection command.
     *
     * Validates that the question is not null, then notifies the GameEngine
     * of the selection. Displays an error message if validation fails.
     */
    @Override
    public void execute() {
        if (question == null) {
            System.out.println("Error: No question to answer");
            return;
        }
        engine.selectQuestion(question);
    }
}