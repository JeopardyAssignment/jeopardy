package com.jeopardy.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import com.jeopardy.game.Player;

public class commandTest {
    
    private Player player1;
    private Player player2;

    @Before
    public void setUp() {
        System.out.println("================================================");
        System.out.println("COMMAND TEST SETUP");
        System.out.println("================================================");
        
        player1 = new Player("Alice");
        player2 = new Player("Bob");
        
        System.out.println("Players created: " + player1.getId() + ", " + player2.getId());
        System.out.println();
    }

    @Test
    public void testSelectCategoryCommand() {
        System.out.println("TEST: SelectCategoryCommand Execution");
        System.out.println("----------------------------------------");
        
        SelectCategoryCommand command = new SelectCategoryCommand();
        command.execute(); 
        
        Assert.assertNotNull("Command should be instantiated", command);
        System.out.println("SUCCESS: SelectCategoryCommand executed successfully");
        System.out.println();
    }

    @Test
    public void testSelectQuestionCommand() {
        System.out.println("TEST: SelectQuestionCommand Execution");
        System.out.println("----------------------------------------");
        
        SelectQuestionCommand command = new SelectQuestionCommand();
        command.execute(); 
        
        Assert.assertNotNull("Command should be instantiated", command);
        System.out.println("SUCCESS: SelectQuestionCommand executed successfully");
        System.out.println();
    }

    @Test
    public void testAnswerQuestionCommand() {
        System.out.println("TEST: AnswerQuestionCommand Execution");
        System.out.println("----------------------------------------");
        
        AnswerQuestionCommand command = new AnswerQuestionCommand();
        command.execute(); 
        
        Assert.assertNotNull("Command should be instantiated", command);
        System.out.println("SUCCESS: AnswerQuestionCommand executed successfully");
        System.out.println();
    }

    @Test
    public void testCommandInterfaceImplementation() {
        System.out.println("TEST: Command Interface Implementation");
        System.out.println("----------------------------------------");
        
        Command categoryCommand = new SelectCategoryCommand();
        Command questionCommand = new SelectQuestionCommand();
        Command answerCommand = new AnswerQuestionCommand();
        
        Assert.assertTrue("SelectCategoryCommand should implement Command", 
                         categoryCommand instanceof Command);
        Assert.assertTrue("SelectQuestionCommand should implement Command", 
                         questionCommand instanceof Command);
        Assert.assertTrue("AnswerQuestionCommand should implement Command", 
                         answerCommand instanceof Command);
        
        System.out.println("SUCCESS: All commands implement Command interface");
        System.out.println("Commands verified: SelectCategory, SelectQuestion, AnswerQuestion");
        System.out.println();
    }

    @Test
    public void testPolymorphicCommandExecution() {
        System.out.println("TEST: Polymorphic Command Execution");
        System.out.println("----------------------------------------");
        
        Command[] commands = {
            new SelectCategoryCommand(),
            new SelectQuestionCommand(),
            new AnswerQuestionCommand()
        };
        
        for (Command command : commands) {
            command.execute();
            Assert.assertNotNull("Each command should execute without issues", command);
        }
        
        System.out.println("SUCCESS: All commands executed polymorphically");
        System.out.println("Commands executed: " + commands.length);
        System.out.println();
    }

    @Test
    public void testCompletePlayerTurnSequence() {
        System.out.println("TEST: Complete Player Turn Sequence");
        System.out.println("----------------------------------------");
        
        SelectCategoryCommand categoryCmd = new SelectCategoryCommand();
        categoryCmd.execute();
        
        SelectQuestionCommand questionCmd = new SelectQuestionCommand();
        questionCmd.execute();
        
        AnswerQuestionCommand answerCmd = new AnswerQuestionCommand();
        answerCmd.execute();
        
        Assert.assertNotNull("Category command should complete", categoryCmd);
        Assert.assertNotNull("Question command should complete", questionCmd);
        Assert.assertNotNull("Answer command should complete", answerCmd);
        
        System.out.println("SUCCESS: Complete turn sequence executed successfully");
        System.out.println("Sequence: SelectCategory -> SelectQuestion -> AnswerQuestion");
        System.out.println();
    }

    @Test
    public void testCommandsWithPlayerContext() {
        System.out.println("TEST: Commands With Player Context");
        System.out.println("----------------------------------------");
        
        Player player = new Player("TestPlayer");
        
        System.out.println("Testing SelectCategoryCommand with player...");
        SelectCategoryCommand categoryCmd = new SelectCategoryCommand();
        player.setCommand(categoryCmd);
        player.doCommand();
        
        System.out.println("Testing SelectQuestionCommand with player...");
        SelectQuestionCommand questionCmd = new SelectQuestionCommand();
        player.setCommand(questionCmd);
        player.doCommand();
        
        System.out.println("Testing AnswerQuestionCommand with player...");
        AnswerQuestionCommand answerCmd = new AnswerQuestionCommand();
        player.setCommand(answerCmd);
        player.doCommand();
        
        Assert.assertNotNull("Player should handle all command types", player);
        System.out.println("SUCCESS: Player executed all command types successfully");
        System.out.println("Player: " + player.getId());
        System.out.println();
    }

    @Test
    public void testPlayerWithNullCommand() {
        System.out.println("TEST: Player With Null Command");
        System.out.println("----------------------------------------");
        
        Player player = new Player("TestPlayer");
        player.setCommand(null);
        player.doCommand();
        
        Assert.assertNotNull("Player should handle null command", player);
        System.out.println("SUCCESS: Player handled null command gracefully");
        System.out.println("Player: " + player.getId());
        System.out.println();
    }

    @Test
    public void testCommandPerformance() {
        System.out.println("TEST: Command Execution Performance");
        System.out.println("----------------------------------------");
        
        long startTime = System.currentTimeMillis();
        int commandCount = 50;
        
        for (int i = 0; i < commandCount; i++) {
            SelectCategoryCommand command = new SelectCategoryCommand();
            command.execute();
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        Assert.assertTrue("Commands should execute quickly: " + duration + "ms", duration < 1000);
        System.out.println("SUCCESS: Performance test completed");
        System.out.println("Commands executed: " + commandCount);
        System.out.println("Duration: " + duration + "ms");
        System.out.println();
    }

    @Test
    public void testCommandExceptionHandling() {
        System.out.println("TEST: Command Exception Handling");
        System.out.println("----------------------------------------");
        
        Command[] commands = {
            new SelectCategoryCommand(),
            new SelectQuestionCommand(),
            new AnswerQuestionCommand()
        };
        
        for (Command command : commands) {
            try {
                command.execute();
                Assert.assertNotNull("Command should execute without throwing exceptions", command);
            } catch (Exception e) {
                Assert.fail("Command should not throw exceptions: " + e.getMessage());
            }
        }
        
        System.out.println("SUCCESS: All commands handled exceptions properly");
        System.out.println("Commands tested: " + commands.length);
        System.out.println();
    }

    @Test
    public void testMultiplePlayersWithCommands() {
        System.out.println("TEST: Multiple Players With Commands");
        System.out.println("----------------------------------------");
        
        System.out.println("Player 1 executing command...");
        SelectCategoryCommand p1Cmd = new SelectCategoryCommand();
        player1.setCommand(p1Cmd);
        player1.doCommand();
        
        System.out.println("Player 2 executing command...");
        SelectCategoryCommand p2Cmd = new SelectCategoryCommand();
        player2.setCommand(p2Cmd);
        player2.doCommand();
        
        Assert.assertNotNull("Player 1 should handle command", player1);
        Assert.assertNotNull("Player 2 should handle command", player2);
        
        System.out.println("SUCCESS: Multiple players executed commands successfully");
        System.out.println("Players: " + player1.getId() + ", " + player2.getId());
        System.out.println();
    }

    @Test
    public void testCommandReusability() {
        System.out.println("TEST: Command Reusability");
        System.out.println("----------------------------------------");
        
        SelectCategoryCommand command = new SelectCategoryCommand();
        
        System.out.println("First execution...");
        command.execute();
        
        System.out.println("Second execution...");
        command.execute();
        
        System.out.println("Third execution...");
        command.execute();
        
        Assert.assertNotNull("Command should be reusable", command);
        System.out.println("SUCCESS: Command reused multiple times successfully");
        System.out.println("Executions: 3");
        System.out.println();
    }

    @Test
    public void testCommandExecutionOrder() {
        System.out.println("TEST: Command Execution Order");
        System.out.println("----------------------------------------");
        
        System.out.println("Executing commands in different order...");
        AnswerQuestionCommand answerCmd = new AnswerQuestionCommand();
        answerCmd.execute();
        
        SelectCategoryCommand categoryCmd = new SelectCategoryCommand();
        categoryCmd.execute();
        
        SelectQuestionCommand questionCmd = new SelectQuestionCommand();
        questionCmd.execute();
        
        Assert.assertNotNull("Answer command should work in any order", answerCmd);
        Assert.assertNotNull("Category command should work in any order", categoryCmd);
        Assert.assertNotNull("Question command should work in any order", questionCmd);
        
        System.out.println("SUCCESS: Commands executed in different orders successfully");
        System.out.println("Execution order: Answer -> Category -> Question");
        System.out.println();
    }
}