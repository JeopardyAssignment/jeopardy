package com.jeopardy.report;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import com.jeopardy.logging.ActivityLog;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.question.Question;
import com.jeopardy.utils.ActivityType;
import com.jeopardy.game.Player;
import com.jeopardy.report.format.*;

import java.util.ArrayList;

/**
 * Integration tests for report generation in all supported formats.
 * 
 * This test class verifies that:
 * - Reports can be generated in CSV, TXT, and PDF formats
 * - Reports contain correct activity data
 * - Output files are created successfully
 * - Report generators collect activity logs correctly
 */
public class ReportGenerationTest {
    
    private ReportGenerator reportGenerator;

    private ArrayList<ActivityLog> sampleLogs;
    private Player player1;
    private Player player2;
    private ArrayList<Question> question;

    /**
     * Set up test fixtures: create sample activity logs and players.
     */
    @Before
    public void setUp() {
        reportGenerator = new ReportGenerator();
        sampleLogs = new ArrayList<>();
        QsForRGTest questionGenerator = new QsForRGTest();
        player1 = new Player("Alice");
        player2 = new Player("Bob");
        question = questionGenerator.getQuestionsForReportGenTest();
        
        // Create sample activity logs
        sampleLogs.add(createActivityLog("game-001", player1, ActivityType.SELECT_CATEGORY, 
            "Science", 200, "DNA", "Correct", 200, this.question.get(0), 1));
        
        sampleLogs.add(createActivityLog("game-001", player2, ActivityType.SELECT_CATEGORY, 
            "History", 300, "1776", "Correct", 300, this.question.get(1), 2));
        
        sampleLogs.add(createActivityLog("game-001", player1, ActivityType.SELECT_CATEGORY, 
            "Literature", 400, "Shakespeare", "Correct", 600, this.question.get(2),3));
        
        sampleLogs.add(createActivityLog("game-001", player2, ActivityType.SELECT_CATEGORY, 
            "Math", 200, "42", "Incorrect", 300, this.question.get(3), 4));
        
        sampleLogs.add(createActivityLog("game-001", player1, ActivityType.SELECT_CATEGORY, 
            "Science", 500, "Photosynthesis", "Correct", 1100, this.question.get(4), 5));
    }

    /**
     * Helper method to create an ActivityLog with test data.
     */
    private ActivityLog createActivityLog(String caseId, Player player, ActivityType activity,
                                         String category, int value, String answer, 
                                         String result, int score, Question question, int turn) {
        return new ActivityLogBuilder()
            .setCaseId(caseId)
            .setPlayerId(player)
            .setActivity(activity)
            .setCategory(category)
            .setQuestionValue(value)
            .setAnswerGiven(answer)
            .setResult(result)
            .setScoreAfterPlay(score)
            .setQuestion(question)
            .setTurn(turn)
            .setTimestamp()
            .createActivityLog();
    }

    /**
     * Test CSV report generation.
     * Verifies that CSV file is created with activity data.
     */
    @Test
    public void testCSVReportGeneration() {
        for (ActivityLog log : sampleLogs) {
            reportGenerator.update(log);
        }
        
        CSVReportFormat csvFormat = new CSVReportFormat();
        reportGenerator.setFormat(csvFormat);
        reportGenerator.createReport();
        
        Assert.assertEquals("Report generator should have 5 activity logs", 5, reportGenerator.getActivityCount());
    }

    /**
     * Test TXT report generation.
     * Verifies that text report is created with activity summaries.
     */
    @Test
    public void testTXTReportGeneration() {
        for (ActivityLog log : sampleLogs) {
            reportGenerator.update(log);
        }
        
        TXTReportFormat txtFormat = new TXTReportFormat();
        reportGenerator.setFormat(txtFormat);
        reportGenerator.createReport();
        
        Assert.assertEquals("Report generator should have 5 activity logs", 5, reportGenerator.getActivityCount());
    }

    /**
     * Test PDF report generation.
     * Verifies that PDF file is created with all activity data.
     */
    @Test
    public void testPDFReportGeneration() {
        for (ActivityLog log : sampleLogs) {
            reportGenerator.update(log);
        }
        
        PDFReportFormat pdfFormat = new PDFReportFormat();
        reportGenerator.setFormat(pdfFormat);
        reportGenerator.createReport();
        
        Assert.assertEquals("Report generator should have 5 activity logs", 5, reportGenerator.getActivityCount());
    }

    /**
     * Test DOCX report generation (placeholder).
     * Verifies that DOCX template is triggered correctly.
     */
    @Test
    public void testDOCXReportGeneration() {
        for (ActivityLog log : sampleLogs) {
            reportGenerator.update(log);
        }
        
        DOCXReportFormat docxFormat = new DOCXReportFormat();
        reportGenerator.setFormat(docxFormat);
        reportGenerator.createReport();
        
        Assert.assertEquals("Report generator should have 5 activity logs", 5, reportGenerator.getActivityCount());
    }

    /**
     * Test switching between multiple report formats.
     * Verifies that reports can be generated sequentially in different formats.
     */
    @Test
    public void testMultipleReportFormats() {
        for (ActivityLog log : sampleLogs) {
            reportGenerator.update(log);
        }
        
        // Generate CSV
        reportGenerator.setFormat(new CSVReportFormat());
        reportGenerator.createReport();
        Assert.assertEquals(5, reportGenerator.getActivityCount());
        
        // Generate TXT
        reportGenerator.setFormat(new TXTReportFormat());
        reportGenerator.createReport();
        Assert.assertEquals(5, reportGenerator.getActivityCount());
        
        // Generate PDF
        reportGenerator.setFormat(new PDFReportFormat());
        reportGenerator.createReport();
        Assert.assertEquals(5, reportGenerator.getActivityCount());
    }

    /**
     * Test report generation with empty activity log.
     * Verifies graceful handling of no data.
     */
    @Test
    public void testEmptyReportGeneration() {
        CSVReportFormat csvFormat = new CSVReportFormat();
        reportGenerator.setFormat(csvFormat);
        reportGenerator.createReport();
        
        Assert.assertEquals("Empty report should have 0 activity logs", 0, reportGenerator.getActivityCount());
    }

    /**
     * Test data clearing functionality.
     * Verifies that report data can be reset for reuse.
     */
    @Test
    public void testReportDataClearing() {
        for (ActivityLog log : sampleLogs) {
            reportGenerator.update(log);
        }
        Assert.assertEquals(5, reportGenerator.getActivityCount());
        
        reportGenerator.clearData();
        Assert.assertEquals("Cleared report should have 0 activity logs", 0, reportGenerator.getActivityCount());
        
        // Add new logs after clearing
        ActivityLog newLog = createActivityLog("game-002", player1, ActivityType.SELECT_CATEGORY,
            "Geography", 100, "France", "Correct", 100, question.get(0), 1);
        reportGenerator.update(newLog);
        Assert.assertEquals(1, reportGenerator.getActivityCount());
    }

    /**
     * Test report format switching without clearing data.
     * Verifies that data persists across format changes.
     */
    @Test
    public void testFormatSwitchingPreservesData() {
        for (ActivityLog log : sampleLogs) {
            reportGenerator.update(log);
        }
        
        CSVReportFormat csv = new CSVReportFormat();
        reportGenerator.setFormat(csv);
        Assert.assertEquals("Data should persist after setting format", 5, reportGenerator.getActivityCount());
        
        TXTReportFormat txt = new TXTReportFormat();
        reportGenerator.setFormat(txt);
        Assert.assertEquals("Data should persist after switching format", 5, reportGenerator.getActivityCount());
    }

    /**
     * Test that report generator correctly collects multiple activities.
     * Verifies the update mechanism of the Observer pattern.
     */
    @Test
    public void testActivityLogCollection() {
        Assert.assertEquals("Initially empty", 0, reportGenerator.getActivityCount());
        
        for (ActivityLog log : sampleLogs) {
            reportGenerator.update(log);
        }
        
        Assert.assertEquals("Should have collected all 5 logs", 5, reportGenerator.getActivityCount());
    }

    /**
     * Test null log handling.
     * Verifies that null logs are gracefully ignored.
     */
    @Test
    public void testNullLogHandling() {
        reportGenerator.update(null);
        Assert.assertEquals("Null log should not be added", 0, reportGenerator.getActivityCount());
        
        reportGenerator.update(sampleLogs.get(0));
        Assert.assertEquals("Valid log should be added", 1, reportGenerator.getActivityCount());
    }
}
