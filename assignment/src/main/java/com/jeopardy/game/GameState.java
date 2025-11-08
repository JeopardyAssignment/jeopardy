package com.jeopardy.game;

import com.jeopardy.question.QuestionService;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class GameState {

    /**
     * Default constructor
     */
    public GameState() {
    }

    /**
     * 
     */
    private int currentTurn;

    /**
     * 
     */
    private ArrayList<Player> players;

    /**
     * 
     */
    private QuestionService questionService;

    /**
     * 
     */
    private int currentScore;





    /**
     * @return
     */
    public int getCurrentTurn() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public ArrayList<Player> getPlayers() {
        // TODO implement here
        return null;
    }

    /**
     * @param players
     */
    public void setPlayer(ArrayList<Player> players) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Player getCurrentPlayer() {
        // TODO implement here
        return null;
    }

    /**
     * @param s
     */
    public void addScore(int s) {
        // TODO implement here
    }

    /**
     * @return
     */
    public int getScore() {
        // TODO implement here
        return 0;
    }

}