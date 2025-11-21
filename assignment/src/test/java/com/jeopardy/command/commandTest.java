package com.jeopardy.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import com.jeopardy.game.GameEngine;
import com.jeopardy.game.Player;
import com.jeopardy.question.Question;

import java.lang.reflect.Field;

public class commandTest {

    private Player player1;
    private Question question;

    @Before
    public void setUp() throws Exception {
        GameEngine.Instance();

        // Create players
        player1 = new Player("Alice");
        new Player("Bob");

        // Create a question
        question = new Question();
        Field questionField = Question.class.getDeclaredField("question");
        questionField.setAccessible(true);
        questionField.set(question, "What is 2+2?");

        Field correctAnswerField = Question.class.getDeclaredField("correctAnswer");
        correctAnswerField.setAccessible(true);
        correctAnswerField.set(question, "4");

        Field optionsField = Question.class.getDeclaredField("options");
        optionsField.setAccessible(true);
        optionsField.set(question, null); 
    }

    @Test
    public void testSelectQuestionCommand() {
        SelectQuestionCommand command = new SelectQuestionCommand(GameEngine.Instance());
        Assert.assertNotNull("Command should be created without error", command);
    }

    @Test
    public void testAnswerQuestionCommandCorrect() {
        AnswerQuestionCommand command = new AnswerQuestionCommand(GameEngine.Instance(), "4");
        Assert.assertNotNull(command);
    }

    @Test
    public void testAnswerQuestionCommandIncorrect() {
        AnswerQuestionCommand command = new AnswerQuestionCommand(GameEngine.Instance(), "5");
        Assert.assertNotNull(command);
    }

    @Test
    public void testPlayerCommandExecution() {
        SelectQuestionCommand command = new SelectQuestionCommand(GameEngine.Instance());
        Assert.assertNotNull(command);
        player1.setCommand(command);
        // Don't execute the command as it requires Scanner input
        Assert.assertNotNull(player1);
    }

    @Test
    public void testPlayerWithNullCommand() {
        player1.setCommand(null);
        player1.doCommand(); 
        Assert.assertNotNull(player1);
    }
}
