package com.jeopardy.logging;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.jeopardy.utils.ActivityType;
import com.jeopardy.question.Question;
import java.time.Instant;

/**
 * Unit tests for the ActivityLog class.
 * 
 * These tests verify that the ActivityLog correctly:
 * - Stores and retrieves activity information
 * - Generates summary strings with correct formatting
 * - Generates CSV strings with proper formatting
 * - Handles various activity types and scenarios
 */
public class ActivityLogTest {
    
    private ActivityLog activityLog;
    private Question mockQuestion;
    private Instant testTimestamp;

    /**
     * Set up test fixtures before each test method.
     */
    @Before
    public void setUp() {
        activityLog = new ActivityLog();
        mockQuestion = new Question();
        testTimestamp = Instant.now();
        
        // Set up common test data
        activityLog.setCaseId("case-001");
        activityLog.setPlayerId("player1");
        activityLog.setActivity(ActivityType.ANSWER_QUESTION);
        activityLog.setTimestamp(testTimestamp);
        activityLog.setCategory("Science");
        activityLog.setQuestionValue(200);
        activityLog.setAnswerGiven("A");
        activityLog.setResult("Correct");
        activityLog.setScoreAfterPlay(1200);
        activityLog.setTurn(3);
        activityLog.setQuestion(mockQuestion);
    }

    /**
     * Test that ActivityLog correctly stores the case ID.
     */
    @Test
    public void testCaseIdField() {
        Assert.assertEquals("Case ID should be stored correctly", "case-001", activityLog.getCaseId());
    }

    /**
     * Test that ActivityLog correctly stores the player ID.
     */
    @Test
    public void testPlayerIdField() {
        Assert.assertEquals("Player ID should be stored correctly", "player1", activityLog.getPlayerId());
    }

    /**
     * Test that ActivityLog correctly stores the activity type.
     */
    @Test
    public void testActivityField() {
        Assert.assertEquals("Activity should be stored correctly", 
            ActivityType.ANSWER_QUESTION, activityLog.getActivity());
    }

    /**
     * Test that ActivityLog correctly stores the timestamp.
     */
    @Test
    public void testTimestampField() {
        Assert.assertEquals("Timestamp should be stored correctly", testTimestamp, activityLog.getTimestamp());
    }

    /**
     * Test that ActivityLog correctly stores the category.
     */
    @Test
    public void testCategoryField() {
        Assert.assertEquals("Category should be stored correctly", "Science", activityLog.getCategory());
    }

    /**
     * Test that ActivityLog correctly stores the question value.
     */
    @Test
    public void testQuestionValueField() {
        Assert.assertEquals("Question value should be stored correctly", 200, activityLog.getQuestionValue());
    }

    /**
     * Test that ActivityLog correctly stores the answer given.
     */
    @Test
    public void testAnswerGivenField() {
        Assert.assertEquals("Answer given should be stored correctly", "A", activityLog.getAnswerGiven());
    }

    /**
     * Test that ActivityLog correctly stores the result.
     */
    @Test
    public void testResultField() {
        Assert.assertEquals("Result should be stored correctly", "Correct", activityLog.getResult());
    }

    /**
     * Test that ActivityLog correctly stores the score after play.
     */
    @Test
    public void testScoreAfterPlayField() {
        Assert.assertEquals("Score after play should be stored correctly", 1200, activityLog.getScoreAfterPlay());
    }

    /**
     * Test that ActivityLog correctly stores the turn.
     */
    @Test
    public void testTurnField() {
        Assert.assertEquals("Turn should be stored correctly", 3, activityLog.getTurn());
    }

    /**
     * Test that ActivityLog correctly stores the Question object.
     */
    @Test
    public void testQuestionField() {
        Assert.assertEquals("Question should be stored correctly", mockQuestion, activityLog.getQuestion());
    }

    /**
     * Test that toCSVString() generates proper CSV format.
     */
    @Test
    public void testToCSVStringFormat() {
        String csv = activityLog.toCSVString();
        
        Assert.assertNotNull("CSV string should not be null", csv);
        String[] fields = csv.trim().split(",");
        Assert.assertEquals("CSV should have exactly 9 fields", 9, fields.length);
    }

    /**
     * Test that toCSVString() includes the case ID as first field.
     */
    @Test
    public void testToCSVStringCaseId() {
        String csv = activityLog.toCSVString();
        Assert.assertTrue("CSV should start with case ID", csv.startsWith("case-001"));
    }

    /**
     * Test that toCSVString() includes the player ID as second field.
     */
    @Test
    public void testToCSVStringPlayerId() {
        String csv = activityLog.toCSVString();
        String[] fields = csv.trim().split(",");
        Assert.assertEquals("Second field should be player ID", "player1", fields[1]);
    }

    /**
     * Test that toCSVString() includes the activity type as third field.
     */
    @Test
    public void testToCSVStringActivity() {
        String csv = activityLog.toCSVString();
        String[] fields = csv.trim().split(",");
        Assert.assertEquals("Third field should be activity type", 
            ActivityType.ANSWER_QUESTION.toString(), fields[2]);
    }

    /**
     * Test that toCSVString() includes the question value.
     */
    @Test
    public void testToCSVStringQuestionValue() {
        String csv = activityLog.toCSVString();
        Assert.assertTrue("CSV should include question value 200", csv.contains("200"));
    }

    /**
     * Test that toCSVString() includes the answer given.
     */
    @Test
    public void testToCSVStringAnswer() {
        String csv = activityLog.toCSVString();
        Assert.assertTrue("CSV should include answer 'A'", csv.contains("A"));
    }

    /**
     * Test that toCSVString() includes the result.
     */
    @Test
    public void testToCSVStringResult() {
        String csv = activityLog.toCSVString();
        Assert.assertTrue("CSV should include result 'Correct'", csv.contains("Correct"));
    }

    /**
     * Test that toCSVString() includes the score.
     */
    @Test
    public void testToCSVStringScore() {
        String csv = activityLog.toCSVString();
        Assert.assertTrue("CSV should include score 1200", csv.contains("1200"));
    }

    /**
     * Test that toCSVString() has a newline at the end.
     */
    @Test
    public void testToCSVStringNewline() {
        String csv = activityLog.toCSVString();
        Assert.assertTrue("CSV string should end with newline", csv.endsWith("\n"));
    }

    /**
     * Test toCSVString() with different values.
     */
    @Test
    public void testToCSVStringWithDifferentValues() {
        activityLog.setCaseId("game-123");
        activityLog.setPlayerId("bob");
        activityLog.setActivity(ActivityType.SELECT_CATEGORY);
        activityLog.setCategory("History");
        activityLog.setQuestionValue(300);
        activityLog.setAnswerGiven("B");
        activityLog.setResult("Incorrect");
        activityLog.setScoreAfterPlay(900);
        
        String csv = activityLog.toCSVString();
        
        Assert.assertTrue("CSV should include new case ID", csv.contains("game-123"));
        Assert.assertTrue("CSV should include new player ID", csv.contains("bob"));
        Assert.assertTrue("CSV should include new activity", csv.contains("SELECT_CATEGORY"));
        Assert.assertTrue("CSV should include new category", csv.contains("History"));
        Assert.assertTrue("CSV should include new question value", csv.contains("300"));
        Assert.assertTrue("CSV should include new answer", csv.contains("B"));
        Assert.assertTrue("CSV should include 'Incorrect'", csv.contains("Incorrect"));
        Assert.assertTrue("CSV should include new score", csv.contains("900"));
    }

    /**
     * Test ActivityLog with edge case values.
     */
    @Test
    public void testActivityLogWithEdgeCaseValues() {
        activityLog.setCaseId("");
        activityLog.setPlayerId("");
        activityLog.setQuestionValue(0);
        activityLog.setScoreAfterPlay(0);
        activityLog.setTurn(1);
        
        String csv = activityLog.toCSVString();
        Assert.assertNotNull("CSV should handle empty strings and zero values", csv);
        Assert.assertTrue("CSV should still be valid format", csv.contains(","));
    }

    /**
     * Test ActivityLog with maximum values.
     */
    @Test
    public void testActivityLogWithMaximumValues() {
        activityLog.setQuestionValue(500);
        activityLog.setScoreAfterPlay(Integer.MAX_VALUE);
        activityLog.setTurn(Integer.MAX_VALUE);
        
        String csv = activityLog.toCSVString();
        Assert.assertNotNull("CSV should handle maximum values", csv);
        Assert.assertTrue("CSV should include max int value for score", 
            csv.contains(String.valueOf(Integer.MAX_VALUE)));
    }

}
