package com.jeopardy.game;

import com.jeopardy.question.QuestionService;

import java.util.*;

/**
 * 
 */
public class GameState {

    /**
     * Default constructor
     */
    public GameState() {
         this.players = new ArrayList<>();
        this.currentTurn = 0;
        this.currentScore = 0;
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
 return this.currentTurn;
        
    }

    /**
     * @return
     */
    public ArrayList<Player> getPlayers() {
         return this.players;
    }

    /**
     * @param players
     */
    public void setPlayer(ArrayList<Player> players) {
       this.players = players;
    }

    /**
     * @return
     */
    public Player getCurrentPlayer() {
         if (this.players == null || this.players.isEmpty()) {
            return null;
        }
        int playerIndex = this.currentTurn % this.players.size();
        return this.players.get(playerIndex);
    }

    /**
     * @param s
     */
    public void addScore(int s) {
         this.currentScore = this.currentScore + s;
    }

    /**
     * @return
     */
    public int getScore() {
        return this.currentScore;
    }

}