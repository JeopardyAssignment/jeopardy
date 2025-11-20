package com.jeopardy;

import com.jeopardy.game.GameEngine;

/**
 * Client is the main entry point for the Jeopardy game application.
 */
public class Client {
    private static GameEngine gameEngine;
    
    public static void main(String[] args) {
        gameEngine = GameEngine.Instance();
        gameEngine.start();
        
    }
}
