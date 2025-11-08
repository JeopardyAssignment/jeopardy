package com.jeopardy.game;

import com.jeopardy.logging.observer.Publisher;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.logging.observer.Subscriber;

import java.util.*;

/**
 * 
 */
public class GameEngine implements Publisher {

    /**
     * Default constructor
     */
    public GameEngine() {
    }

    /**
     * 
     */
    private static GameEngine instance;

    /**
     * 
     */
    private GameSettings settings;

    /**
     * 
     */
    private GameState state;

    /**
     * 
     */
    private ArrayList<Subscriber> subscribers;

    /**
     * 
     */
    private ActivityLogBuilder activityLogBuilder;

    /**
     * 
     */
    public boolean isGameOver;





    /**
     * @param settings
     */
    private GameEngine(GameSettings settings) {
        // TODO implement here
    }

    /**
     * @return
     */
    public static GameEngine Instance() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public GameSettings getSettings() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public GameState getState() {
        // TODO implement here
        return null;
    }

    /**
     * @param settings
     */
    public void setSettings(GameSettings settings) {
        // TODO implement here
    }

    /**
     * 
     */
    public void onTurnStart() {
        // TODO implement here
    }

    /**
     * 
     */
    public void onTurnEnd() {
        // TODO implement here
    }

    /**
     * 
     */
    public void onGameOver() {
        // TODO implement here
    }

    /**
     * @param s
     */
    public void subscribe(Subscriber s) {
        // TODO implement Publisher.subscribe() here
    }

    /**
     * @param s
     */
    public void unsubscribe(Subscriber s) {
        // TODO implement Publisher.unsubscribe() here
    }

    /**
     * 
     */
    public void notifySubscribers() {
        // TODO implement Publisher.notififySubscribers() here
    }

}