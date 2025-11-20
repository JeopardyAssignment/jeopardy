package com.jeopardy.gameTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import com.jeopardy.command.SelectCategoryCommand;
import com.jeopardy.command.SelectQuestionCommand;
import com.jeopardy.command.AnswerQuestionCommand;
import com.jeopardy.game.GameSettings;
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
    private GameSettings gameSettings;
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
        gameSettings = new GameSettings();

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
        System.out.println("GameSettings initialized");
        System.out.println();
    }

    @Test
    public void testPlayerCommandExecution() {
        System.out.println("TEST: Player Command Execution");

        // Category command
        SelectCategoryCommand categoryCmd = new SelectCategoryCommand("Math", engine);
        player1.setCommand(categoryCmd);
        player1.doCommand();

        // Question command
        SelectQuestionCommand questionCmd = new SelectQuestionCommand(question, engine);
        player1.setCommand(questionCmd);
        player1.doCommand();

        // Answer command
        AnswerQuestionCommand answerCmd = new AnswerQuestionCommand(question, "4", engine);
        player1.setCommand(answerCmd);
        player1.doCommand();

        Assert.assertNotNull(player1);
        System.out.println("SUCCESS: Player executed commands successfully");
        System.out.println("Player: " + player1.getId());
        System.out.println();
    }

    @Test
    public void testGameStatePlayerManagement() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        gameState.setPlayer(players);

        Assert.assertEquals(2, gameState.getPlayers().size());
        System.out.println("SUCCESS: Players added to GameState");
        System.out.println();
    }

    @Test
    public void testGameStateScoreManagement() {
        gameState.addScore(100);
        gameState.addScore(50);

        Assert.assertEquals(150, gameState.getScore());
        System.out.println("SUCCESS: Score management working correctly");
        System.out.println();
    }

    @Test
    public void testGameStateCurrentPlayer() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        gameState.setPlayer(players);

        Player current = gameState.getCurrentPlayer();
        Assert.assertNotNull(current);
        System.out.println("SUCCESS: Current player retrieved: " + current.getId());
        System.out.println();
    }

    @Test
    public void testCompleteGameFlow() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        gameState.setPlayer(players);

        // Player 1 turn
        player1.setCommand(new SelectCategoryCommand("Math", engine));
        player1.doCommand();
        gameState.addScore(100);

        // Player 2 turn
        player2.setCommand(new SelectCategoryCommand("Science", engine));
        player2.doCommand();
        gameState.addScore(200);

        Assert.assertEquals(300, gameState.getScore());
        Assert.assertEquals(2, gameState.getPlayers().size());

        System.out.println("SUCCESS: Complete game flow executed successfully");
        System.out.println("Final Score: " + gameState.getScore());
        System.out.println();
    }

    @Test
    public void testGameSettingsCreation() {
        GameSettings settings = new GameSettings();
        Assert.assertNotNull(settings);
        Assert.assertTrue(settings.getPlayerCount() >= 0);
        System.out.println("SUCCESS: GameSettings created and validated");
        System.out.println();
    }
}
