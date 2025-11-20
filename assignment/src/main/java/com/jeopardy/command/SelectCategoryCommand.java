package com.jeopardy.command;

import com.jeopardy.game.GameEngine;

/**
 * 
 */
public class SelectCategoryCommand implements Command {

    private String category;
     private GameEngine engine;
    /**
     * Default constructor
     */
    public SelectCategoryCommand(String category, GameEngine engine) {
        this.category = category;
        this.engine = engine;
    }
    /**
     * 
     */


    /**
     * 
     */

    

    public void execute() {
        if (category == null || category.trim().isEmpty()) {
           System.out.println("Error: No category selected");
            return;
        }
        
        System.out.println("Category selected: " + category);
    }
}