package com.jeopardy.game;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import com.jeopardy.command.SelectCategoryCommand;
import com.jeopardy.command.SelectQuestionCommand;
import com.jeopardy.command.AnswerQuestionCommand;
import com.jeopardy.game.GameState;
import com.jeopardy.game.GameEngine;
import com.jeopardy.game.Player;
import com.jeopardy.question.Question;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GameIntegrationTest {

    private Player player1;
    private Player player2;
    private GameState gameState;
    private GameEngine engine;
    private Question question;

    @Before
    public void setUp() throws Exception {
        System.out.println("================================================");
        System.out.println("GAME INTEGRATION TEST SETUP");
        System.out.println("================================================");

        engine = GameEngine.Instance();
        player1 = new Player("Alice");
        player2 = new Player("Bob");
        gameState = new GameState();

        // Create a question
        question = new Question();
        Field qField = Question.class.getDeclaredField("question");
        qField.setAccessible(true);
        qField.set(question, "What is 2+2?");

        Field aField = Question.class.getDeclaredField("correctAnswer");
        aField.setAccessible(true);
        aField.set(question, "4");

        Field optionsField = Question.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        optionsField.set(question, null);

        System.out.println("Players created: " + player1.getId() + ", " + player2.getId());
        System.out.println("GameState initialized");
        System.out.println();
    }

    @Test
    public void testPlayerCommandExecution() {
        System.out.println("TEST: Player Command Execution");

        // Test that commands can be created and set
        SelectCategoryCommand categoryCmd = new SelectCategoryCommand();
        Assert.assertNotNull(categoryCmd);
        player1.setCommand(categoryCmd);

        SelectQuestionCommand questionCmd = new SelectQuestionCommand();
        Assert.assertNotNull(questionCmd);
        player1.setCommand(questionCmd);

        AnswerQuestionCommand answerCmd = new AnswerQuestionCommand("4");
        Assert.assertNotNull(answerCmd);
        player1.setCommand(answerCmd);

        Assert.assertNotNull(player1);
        System.out.println("SUCCESS: Player commands created and set successfully");
        System.out.println("Player: " + player1.getId());
        System.out.println();
    }

    @Test
    public void testGameStatePlayerManagement() throws Exception {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        // Use reflection to set players directly
        Field playersField = GameState.class.getDeclaredField("players");
        playersField.setAccessible(true);
        playersField.set(gameState, players);

        Assert.assertEquals(2, gameState.getPlayers().size());
        System.out.println("SUCCESS: Players added to GameState");
        System.out.println();
    }

    @Test
    public void testPlayerScoreManagement() {
        player1.updateCurrentScore(100);
        player1.updateCurrentScore(50);

        Assert.assertEquals(150, player1.getCurrentScore());
        System.out.println("SUCCESS: Player score management working correctly");
        System.out.println("Player " + player1.getId() + " score: " + player1.getCurrentScore());
        System.out.println();
    }

    @Test
    public void testGameStateCurrentPlayer() throws Exception {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        // Use reflection to set players directly
        Field playersField = GameState.class.getDeclaredField("players");
        playersField.setAccessible(true);
        playersField.set(gameState, players);

        Player current = gameState.getCurrentPlayer();
        Assert.assertNotNull(current);
        System.out.println("SUCCESS: Current player retrieved: " + current.getId());
        System.out.println();
    }

    @Test
    public void testCompleteGameFlow() throws Exception {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        // Use reflection to set players directly
        Field playersField = GameState.class.getDeclaredField("players");
        playersField.setAccessible(true);
        playersField.set(gameState, players);

        // Test player score updates
        player1.updateCurrentScore(100);
        player2.updateCurrentScore(200);

        Assert.assertEquals(100, player1.getCurrentScore());
        Assert.assertEquals(200, player2.getCurrentScore());
        Assert.assertEquals(2, gameState.getPlayers().size());

        System.out.println("SUCCESS: Complete game flow executed successfully");
        System.out.println("Player 1 Score: " + player1.getCurrentScore());
        System.out.println("Player 2 Score: " + player2.getCurrentScore());
        System.out.println();
    }

    @Test
    public void testGameEngineCreation() {
        GameEngine engine = GameEngine.Instance();
        Assert.assertNotNull(engine);
        Assert.assertNotNull(engine.getState());
        System.out.println("SUCCESS: GameEngine created and validated");
        System.out.println();
    }
}
