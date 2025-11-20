package com.jeopardy;

import com.jeopardy.game.Player;
import com.jeopardy.logging.ActivityLog;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.question.Question;
import com.jeopardy.report.ReportGenerator;
import com.jeopardy.report.format.DOCXReportFormat;
import com.jeopardy.utils.ActivityType;

/**
 * Client is the main entry point for the Jeopardy game application.
 */
public class Client {

    /**
     * Main entry point for the application.
     * @param args command line arguments (not currently used)
     */
    public static void main(String[] args) {
        // Create a sample question
        Question q = new Question();

        // Create first activity log for Player 1
        ActivityLog log = new ActivityLogBuilder()
            .setQuestion(q)
            .setActivity(ActivityType.SELECT_PLAYER_COUNT)
            .setTurn(1)
            .setTimestamp()
            .setPlayerId(new Player("Player 1"))
            .setScoreAfterPlay(400)
            .setCaseId("GAME-01")
            .setCategory("Design Patterns")
            .setQuestionValue(100)
            .setAnswerGiven("A")
            .createActivityLog();

        // Create second activity log for Player 2
        ActivityLog log2 = new ActivityLogBuilder()
            .setQuestion(q)
            .setActivity(ActivityType.SELECT_PLAYER_COUNT)
            .setTurn(2)
            .setTimestamp()
            .setPlayerId(new Player("Player 2"))
            .setScoreAfterPlay(400)
            .setCaseId("GAME-01")
            .setCategory("Design Patterns")
            .setQuestionValue(200)
            .setAnswerGiven("A")
            .createActivityLog();

        // Set up report generator with DOCX format
        ReportGenerator generator = new ReportGenerator().setFormat(new DOCXReportFormat());

        // Add activity logs to the generator
        generator.update(log);
        generator.update(log2);

        // Generate the report file
        generator.createReport();

        // Optional: Print summary to console (currently commented out)
        // System.out.println(log.toSummaryString());
    }

}
