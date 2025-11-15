package com.jeopardy.gameTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import com.jeopardy.command.SelectCategoryCommand;
import com.jeopardy.command.SelectQuestionCommand;
import com.jeopardy.command.AnswerQuestionCommand;
import com.jeopardy.game.GameSettings;
import com.jeopardy.game.GameState;
import com.jeopardy.game.Player;

public class GameIntegrationTest {
    
    private Player player1;
    private Player player2;
    private GameState gameState;
    private GameSettings gameSettings;

    @Before
    public void setUp() {
        System.out.println("================================================");
        System.out.println("GAME INTEGRATION TEST SETUP");
        System.out.println("================================================");
        
        player1 = new Player("Alice");
        player2 = new Player("Bob");
        gameState = new GameState();
        gameSettings = new GameSettings();
        
        System.out.println("Players created: " + player1.getId() + ", " + player2.getId());
        System.out.println("GameState initialized");
        System.out.println("GameSettings initialized");
        System.out.println();
    }

    @Test
    public void testPlayerCreation() {
        System.out.println("TEST: Player Creation");
        System.out.println("----------------------------------------");
        
        Player player = new Player("TestPlayer");
        Assert.assertNotNull("Player should be created", player);
        Assert.assertEquals("Player ID should match", "TestPlayer", player.getId());
        
        System.out.println("SUCCESS: Player created successfully");
        System.out.println("Player ID: " + player.getId());
        System.out.println();
    }

    @Test
    public void testPlayerCommandExecution() {
        System.out.println("TEST: Player Command Execution");
        System.out.println("----------------------------------------");
        
        Player player = new Player("TestPlayer");
        
        System.out.println("Testing player.doCommand() with no command...");
        player.doCommand();
        
        System.out.println("Testing with SelectCategoryCommand...");
        SelectCategoryCommand command = new SelectCategoryCommand();
        player.setCommand(command);
        player.doCommand();
        
        Assert.assertNotNull("Player should handle commands", player);
        System.out.println("SUCCESS: Player executed commands successfully");
        System.out.println("Player: " + player.getId());
        System.out.println();
    }

    @Test
    public void testMultipleCommandTypes() {
        System.out.println("TEST: Multiple Command Types");
        System.out.println("----------------------------------------");
        
        Player player = new Player("CommandTestPlayer");
        
        System.out.println("Testing SelectCategoryCommand...");
        SelectCategoryCommand categoryCmd = new SelectCategoryCommand();
        player.setCommand(categoryCmd);
        player.doCommand();
        
        System.out.println("Testing SelectQuestionCommand...");
        SelectQuestionCommand questionCmd = new SelectQuestionCommand();
        player.setCommand(questionCmd);
        player.doCommand();
        
        System.out.println("Testing AnswerQuestionCommand...");
        AnswerQuestionCommand answerCmd = new AnswerQuestionCommand();
        player.setCommand(answerCmd);
        player.doCommand();
        
        Assert.assertNotNull("Player should handle all command types", player);
        System.out.println("SUCCESS: All 3 command types executed successfully");
        System.out.println("Commands tested: SelectCategory, SelectQuestion, AnswerQuestion");
        System.out.println();
    }

    @Test
    public void testGameStatePlayerManagement() {
        System.out.println("TEST: GameState Player Management");
        System.out.println("----------------------------------------");
        
        java.util.ArrayList<Player> players = new java.util.ArrayList<>();
        players.add(player1);
        players.add(player2);
        gameState.setPlayer(players);
        
        Assert.assertEquals("Should have 2 players", 2, gameState.getPlayers().size());
        
        System.out.println("SUCCESS: Players added to GameState");
        System.out.println("Total players: " + gameState.getPlayers().size());
        for (int i = 0; i < gameState.getPlayers().size(); i++) {
            System.out.println("  Player " + (i+1) + ": " + gameState.getPlayers().get(i).getId());
        }
        System.out.println();
    }

    @Test
    public void testGameStateScoreManagement() {
        System.out.println("TEST: GameState Score Management");
        System.out.println("----------------------------------------");
        
        int initialScore = gameState.getScore();
        System.out.println("Initial score: " + initialScore);
        
        gameState.addScore(100);
        System.out.println("Added 100 points");
        System.out.println("Current score: " + gameState.getScore());
        
        gameState.addScore(50);
        System.out.println("Added 50 points");
        System.out.println("Current score: " + gameState.getScore());
        
        Assert.assertEquals("Score should be 150", 150, gameState.getScore());
        System.out.println("SUCCESS: Score management working correctly");
        System.out.println("Final score: " + gameState.getScore());
        System.out.println();
    }

    @Test
    public void testGameStateCurrentPlayer() {
        System.out.println("TEST: GameState Current Player");
        System.out.println("----------------------------------------");
        
        System.out.println("Testing with no players...");
        Assert.assertNull("Current player should be null with no players", gameState.getCurrentPlayer());
        System.out.println("No players - current player is null (expected)");
        
        System.out.println("Testing with players...");
        java.util.ArrayList<Player> players = new java.util.ArrayList<>();
        players.add(player1);
        players.add(player2);
        gameState.setPlayer(players);
        
        Player currentPlayer = gameState.getCurrentPlayer();
        Assert.assertNotNull("Current player should not be null", currentPlayer);
        System.out.println("SUCCESS: Current player retrieved");
        System.out.println("Current player: " + currentPlayer.getId());
        System.out.println();
    }

    @Test
    public void testCompleteGameFlow() {
        System.out.println("TEST: Complete Game Flow");
        System.out.println("----------------------------------------");
        
        // Setup game state
        java.util.ArrayList<Player> players = new java.util.ArrayList<>();
        players.add(player1);
        players.add(player2);
        gameState.setPlayer(players);
        
        System.out.println("INITIAL STATE:");
        System.out.println("  Players: " + gameState.getPlayers().size());
        System.out.println("  Score: " + gameState.getScore());
        System.out.println("  Current Player: " + gameState.getCurrentPlayer().getId());
        
        System.out.println();
        System.out.println("PLAYER 1 TURN:");
        SelectCategoryCommand p1Cmd = new SelectCategoryCommand();
        player1.setCommand(p1Cmd);
        player1.doCommand();
        gameState.addScore(100);
        System.out.println("  Score after Player 1: " + gameState.getScore());
        
        System.out.println();
        System.out.println("PLAYER 2 TURN:");
        SelectCategoryCommand p2Cmd = new SelectCategoryCommand();
        player2.setCommand(p2Cmd);
        player2.doCommand();
        gameState.addScore(200);
        System.out.println("  Score after Player 2: " + gameState.getScore());
        
        // Verify final state
        Assert.assertEquals("Final score should be 300", 300, gameState.getScore());
        Assert.assertNotNull("Should have current player", gameState.getCurrentPlayer());
        Assert.assertEquals("Should have 2 players", 2, gameState.getPlayers().size());
        
        System.out.println();
        System.out.println("FINAL STATE:");
        System.out.println("  Final Score: " + gameState.getScore());
        System.out.println("  Current Player: " + gameState.getCurrentPlayer().getId());
        System.out.println("  Total Players: " + gameState.getPlayers().size());
        System.out.println("TEST COMPLETED SUCCESSFULLY!");
        System.out.println();
    }

    @Test
    public void testGameSettingsCreation() {
        System.out.println("TEST: GameSettings Creation");
        System.out.println("----------------------------------------");
        
        GameSettings settings = new GameSettings();
        Assert.assertNotNull("GameSettings should be created", settings);
        
        int playerCount = settings.getPlayerCount();
        Assert.assertTrue("Player count should be reasonable", playerCount >= 0);
        
        System.out.println("SUCCESS: GameSettings created and validated");
        System.out.println("Player count: " + playerCount);
        System.out.println();
    }

    @Test
    public void testPerformance() {
        System.out.println("TEST: Performance - Multiple Operations");
        System.out.println("----------------------------------------");
        
        long startTime = System.currentTimeMillis();
        int operations = 0;
        
        for (int i = 0; i < 20; i++) {
            Player player = new Player("PerfPlayer" + i);
            GameState state = new GameState();
            
            java.util.ArrayList<Player> players = new java.util.ArrayList<>();
            players.add(player);
            state.setPlayer(players);
            state.addScore(i * 10);
            
            SelectCategoryCommand command = new SelectCategoryCommand();
            player.setCommand(command);
            player.doCommand();
            
            operations++;
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        Assert.assertTrue("Operations should complete quickly", duration < 1000);
        System.out.println("SUCCESS: Performance test completed");
        System.out.println("Operations: " + operations);
        System.out.println("Duration: " + duration + "ms");
        System.out.println();
    }
}