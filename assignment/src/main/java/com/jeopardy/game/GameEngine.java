package com.jeopardy.game;


import com.jeopardy.game.GameState;
import com.jeopardy.logging.observer.Publisher;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.logging.observer.Subscriber;
import com.jeopardy.question.Question;
import com.jeopardy.logging.ActivityLog; 
import com.jeopardy.utils.ActivityType;

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
    private Question currentQuestion;





    /**
     * @param settings
     */
    private GameEngine(GameSettings settings) {
          this.settings = settings;
        this.state = new GameState();
        this.subscribers = new ArrayList<>();
        this.activityLogBuilder = new ActivityLogBuilder();
        this.isGameOver = false;
    }

    /**
     * @return
     */
    public static GameEngine Instance() {
        if (instance == null) {
            instance = new GameEngine(new GameSettings());
        }
        return instance;
    }

    /**
     * @return
     */
    public GameSettings getSettings() {
        return this.settings;
    }

    /**
     * @return
     */
    public GameState getState() {
         return this.state;
    }

    /**
     * @param settings
     */
    public void setSettings(GameSettings settings) {
         this.settings = settings;
    }

    /**
     * 
     */
    public void onTurnStart() {
        if (!isGameOver && state != null) {
            state.getCurrentTurn();
            notifySubscribers();
        }
    }


    /**
     * 
     */
    public void onTurnEnd() {
         if (!isGameOver) {
            notifySubscribers();
        }
    }
    /**
     * 
     */
    public void onGameOver() {
        this.isGameOver = true;
        notifySubscribers();
    }
    

    /**
     * @param s
     */
    public void subscribe(Subscriber s) {
        if (s != null && !subscribers.contains(s)) {
            subscribers.add(s);
        }
    }

    /**
     * @param s
     */
    public void unsubscribe(Subscriber s) {
       if (s != null) {
            subscribers.remove(s);
        }
    }
    /**
     * 
     */
    public void selectQuestion(Question question) {
    this.currentQuestion = question;
    System.out.println("Question selected: " + question.getQuestion());
}

  public void notifySubscribers() {
    for (Subscriber subscriber : subscribers) {
        if (subscriber != null) {
            // Create ActivityLog using the builder
            ActivityLogBuilder builder = new ActivityLogBuilder()
                .setCaseId("GAME001")
                .setTimestamp();

            // Add player info using state.getCurrentPlayer()
            if (state != null && state.getCurrentPlayer() != null) {
                builder.setPlayerId(state.getCurrentPlayer());
            }

            // Set activity type
            builder.setActivity(ActivityType.GAME_UPDATE);

            ActivityLog activity = builder.createActivityLog();
            subscriber.update(activity);
        }
    }
}
}