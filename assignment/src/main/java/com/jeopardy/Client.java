package com.jeopardy;

import com.jeopardy.game.Player;
import com.jeopardy.logging.ActivityLog;
import com.jeopardy.logging.ActivityLogBuilder;
import com.jeopardy.question.Question;
import com.jeopardy.report.ReportGenerator;
import com.jeopardy.report.format.CSVReportFormat;
import com.jeopardy.report.format.PDFReportFormat;
import com.jeopardy.report.format.TXTReportFormat;
import com.jeopardy.utils.ActivityType;

/**
 * Hello world!
 *
 */
public class Client 
{
    public static void main( String[] args )
    {
        Question q = new Question();
        ActivityLog log = 
        new ActivityLogBuilder()
        .setQuestion(q)
        .setActivity(ActivityType.SELECT_PLAYER_COUNT)
        .setTurn(1)
        .setTimestamp()
        .setPlayerId(new Player("Player 1"))
        .setScoreAfterPlay(400)
        .setCaseId("GAME-01")
        .setCategory("Design Patterns")
        .setQuestionValue(100)
        .createActivityLog();
        
        ActivityLog log2 = 
        new ActivityLogBuilder()
        .setQuestion(q)
        .setActivity(ActivityType.SELECT_PLAYER_COUNT)
        .setTurn(1)
        .setTimestamp()
        .setPlayerId(new Player("Player 2"))
        .setScoreAfterPlay(400)
        .setCaseId("GAME-01")
        .setCategory("Design Patterns")
        .setQuestionValue(200)
        .createActivityLog();

        ReportGenerator generator = new ReportGenerator().setFormat(new CSVReportFormat());
        generator.update(log);
        generator.update(log2);

        generator.createReport();
        
        System.out.println(log.toSummaryString());
    }
}
