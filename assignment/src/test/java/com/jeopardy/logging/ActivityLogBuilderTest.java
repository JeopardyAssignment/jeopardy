package com.jeopardy.logging;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.jeopardy.utils.ActivityType;
import com.jeopardy.question.Question;
import com.jeopardy.game.Player;
import java.time.Instant;

/**
 * Unit tests for the ActivityLogBuilder class.
 * 
 * These tests verify that the ActivityLogBuilder correctly:
 * - Sets individual properties through its fluent interface
 * - Creates ActivityLog objects with all set properties
 * - Resets its state to allow reuse
 * - Maintains method chaining functionality
 */
public class ActivityLogBuilderTest {
    
    private ActivityLogBuilder builder;
    private Question mockQuestion;
    private Player mockPlayer;

    /**
     * Set up test fixtures before each test method.
     */
    @Before
    public void setUp() {
        builder = new ActivityLogBuilder();
        
        // Create a mock Question
        mockQuestion = new Question();
        
        // Create a mock Player
        mockPlayer = new Player("Player 1");
    }

    /**
     * Test that the builder correctly sets and retrieves the case ID.
     */
    @Test
    public void testSetCaseId() {
        String caseId = "case-001";
        ActivityLog log = builder
            .setCaseId(caseId)
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setTimestamp()
            .createActivityLog();
        
        Assert.assertEquals("Case ID should match", caseId, log.caseId);
    }

    /**
     * Test that the builder correctly sets the player ID from a Player object.
     */
    @Test
    public void testSetPlayerId() {
        ActivityLog log = builder
            .setPlayerId(mockPlayer)
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setTimestamp()
            .createActivityLog();
        
        Assert.assertNotNull("Player ID should not be null", log.playerId);
    }

    /**
     * Test that the builder correctly sets the activity type.
     */
    @Test
    public void testSetActivity() {
        ActivityType activity = ActivityType.ANSWER_QUESTION;
        ActivityLog log = builder
            .setActivity(activity)
            .setTimestamp()
            .createActivityLog();
        
        Assert.assertEquals("Activity type should match", activity, log.activity);
    }

    /**
     * Test that the builder correctly sets the question category.
     */
    @Test
    public void testSetCategory() {
        String category = "Science";
        ActivityLog log = builder
            .setCategory(category)
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setTimestamp()
            .createActivityLog();
        
        Assert.assertEquals("Category should match", category, log.category);
    }

    /**
     * Test that the builder correctly sets the question value.
     */
    @Test
    public void testSetQuestionValue() {
        int value = 200;
        ActivityLog log = builder
            .setQuestionValue(value)
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setTimestamp()
            .createActivityLog();
        
        Assert.assertEquals("Question value should match", value, log.questionValue);
    }

    /**
     * Test that the builder correctly sets the answer given.
     */
    @Test
    public void testSetAnswerGiven() {
        String answer = "DNA";
        ActivityLog log = builder
            .setAnswerGiven(answer)
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setTimestamp()
            .createActivityLog();
        
        Assert.assertEquals("Answer given should match", answer, log.answerGiven);
    }

    /**
     * Test that the builder correctly sets the result.
     */
    @Test
    public void testSetResult() {
        String result = "Correct";
        ActivityLog log = builder
            .setResult(result)
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setTimestamp()
            .createActivityLog();
        
        Assert.assertEquals("Result should match", result, log.result);
    }

    /**
     * Test that the builder correctly sets the score after play.
     */
    @Test
    public void testSetScoreAfterPlay() {
        int score = 1200;
        ActivityLog log = builder
            .setScoreAfterPlay(score)
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setTimestamp()
            .createActivityLog();
        
        Assert.assertEquals("Score after play should match", score, log.scoreAfterPlay);
    }

    /**
     * Test that the builder correctly sets the turn number.
     */
    @Test
    public void testSetTurn() {
        int turn = 5;
        ActivityLog log = builder
            .setTurn(turn)
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setTimestamp()
            .createActivityLog();
        
        Assert.assertEquals("Turn should match", turn, log.turn);
    }

    /**
     * Test that the builder correctly sets the Question object.
     */
    @Test
    public void testSetQuestion() {
        ActivityLog log = builder
            .setQuestion(mockQuestion)
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setTimestamp()
            .createActivityLog();
        
        Assert.assertEquals("Question should match", mockQuestion, log.question);
    }

    /**
     * Test that the builder correctly sets the timestamp to current time.
     */
    @Test
    public void testSetTimestamp() {
        Instant before = Instant.now();
        ActivityLog log = builder
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setTimestamp()
            .createActivityLog();
        Instant after = Instant.now();
        
        Assert.assertNotNull("Timestamp should not be null", log.timestamp);
        Assert.assertTrue("Timestamp should be recent", 
            log.timestamp.isAfter(before.minusSeconds(1)) && 
            log.timestamp.isBefore(after.plusSeconds(1)));
    }

    /**
     * Test that method chaining works correctly with multiple setters.
     */
    @Test
    public void testMethodChaining() {
        ActivityLog log = builder
            .setCaseId("case-001")
            .setPlayerId(mockPlayer)
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setCategory("Science")
            .setQuestionValue(300)
            .setAnswerGiven("Mitochondria")
            .setResult("Correct")
            .setScoreAfterPlay(1500)
            .setQuestion(mockQuestion)
            .setTurn(3)
            .setTimestamp()
            .createActivityLog();
        
        Assert.assertEquals("Case ID should match", "case-001", log.caseId);
        Assert.assertEquals("Activity should match", ActivityType.ANSWER_QUESTION, log.activity);
        Assert.assertEquals("Category should match", "Science", log.category);
        Assert.assertEquals("Question value should match", 300, log.questionValue);
        Assert.assertEquals("Answer should match", "Mitochondria", log.answerGiven);
        Assert.assertEquals("Result should match", "Correct", log.result);
        Assert.assertEquals("Score should match", 1500, log.scoreAfterPlay);
        Assert.assertEquals("Turn should match", 3, log.turn);
        Assert.assertEquals("Question should match", mockQuestion, log.question);
    }

    /**
     * Test that reset clears all builder fields to their default values.
     */
    @Test
    public void testReset() {
        // Set all fields
        builder.setCaseId("case-001")
               .setPlayerId(mockPlayer)
               .setActivity(ActivityType.ANSWER_QUESTION)
               .setCategory("Science")
               .setQuestionValue(200)
               .setAnswerGiven("DNA")
               .setResult("Correct")
               .setScoreAfterPlay(1200)
               .setQuestion(mockQuestion)
               .setTurn(2)
               .setTimestamp();
        
        // Reset the builder
        builder.reset();
        
        // Create a new log after reset
        ActivityLog log = builder
            .setActivity(ActivityType.START_GAME)
            .setTimestamp()
            .createActivityLog();
        
        // Verify that most fields are cleared (null or 0)
        Assert.assertNull("Case ID should be null after reset", log.caseId);
        Assert.assertNull("Player ID should be null after reset", log.playerId);
        Assert.assertEquals("Question value should be 0 after reset", 0, log.questionValue);
        Assert.assertNull("Answer given should be null after reset", log.answerGiven);
        Assert.assertNull("Result should be null after reset", log.result);
        Assert.assertEquals("Score should be 0 after reset", 0, log.scoreAfterPlay);
        Assert.assertNull("Question should be null after reset", log.question);
        Assert.assertEquals("Turn should be 0 after reset", 0, log.turn);
    }

    /**
     * Test that the builder can be reused after reset.
     */
    @Test
    public void testBuilderReusability() {
        // First use
        ActivityLog log1 = builder
            .setCaseId("case-001")
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setTimestamp()
            .createActivityLog();
        Assert.assertEquals("First log case ID", "case-001", log1.caseId);
        
        // Reset
        builder.reset();
        
        // Second use
        ActivityLog log2 = builder
            .setCaseId("case-002")
            .setActivity(ActivityType.SELECT_CATEGORY)
            .setTimestamp()
            .createActivityLog();
        Assert.assertEquals("Second log case ID", "case-002", log2.caseId);
        Assert.assertEquals("Second log activity", ActivityType.SELECT_CATEGORY, log2.activity);
    }

    /**
     * Test creating a complete and realistic ActivityLog.
     */
    @Test
    public void testCreateCompleteActivityLog() {
        ActivityLog log = builder
            .setCaseId("game-2025-11-07")
            .setPlayerId(mockPlayer)
            .setActivity(ActivityType.ANSWER_QUESTION)
            .setCategory("History")
            .setQuestionValue(400)
            .setAnswerGiven("1776")
            .setResult("Correct")
            .setScoreAfterPlay(2400)
            .setQuestion(mockQuestion)
            .setTurn(8)
            .setTimestamp()
            .createActivityLog();
        
        // Verify all fields are correctly set
        Assert.assertNotNull("ActivityLog should not be null", log);
        Assert.assertEquals("Case ID", "game-2025-11-07", log.caseId);
        Assert.assertEquals("Activity", ActivityType.ANSWER_QUESTION, log.activity);
        Assert.assertEquals("Category", "History", log.category);
        Assert.assertEquals("Question value", 400, log.questionValue);
        Assert.assertEquals("Answer given", "1776", log.answerGiven);
        Assert.assertEquals("Result", "Correct", log.result);
        Assert.assertEquals("Score after play", 2400, log.scoreAfterPlay);
        Assert.assertEquals("Turn", 8, log.turn);
        Assert.assertNotNull("Timestamp", log.timestamp);
        Assert.assertEquals("Question", mockQuestion, log.question);
    }
}
