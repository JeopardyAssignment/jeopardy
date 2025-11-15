package com.jeopardy.command;

/**
 * 
 */
public class SelectCategoryCommand implements Command {

    /**
     * Default constructor
     */
    public SelectCategoryCommand() {
    }

    /**
     * 
     */
    private String category;

    /**
     * 
     */
    public void execute() {
        if (category == null || category.trim().isEmpty()) {
           // System.out.println("Error: No category selected");
            return;
        }
        
        System.out.println("Category selected: " + category);
    }
}