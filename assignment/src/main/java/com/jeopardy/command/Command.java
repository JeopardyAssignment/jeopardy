package com.jeopardy.command;

/**
 * Command is an interface that defines the contract for the Command design pattern.
 *
 * The Command pattern encapsulates a request as an object, allowing for parameterization
 * of clients with different requests, queuing of requests, and logging of operations.
 *
 * In the Jeopardy game, Commands represent player actions such as:
 * - Selecting a category
 * - Selecting a question
 * - Answering a question
 *
 * Implementations of this interface should:
 * - Encapsulate all information needed to perform an action
 * - Execute the action when the execute() method is called
 * - Interact with the GameEngine to update game state
 */
public interface Command {

    /**
     * Executes the command's action.
     *
     * This method contains the logic for performing the command's specific operation.
     * Implementations should ensure proper error handling and state management.
     */
    void execute();

}