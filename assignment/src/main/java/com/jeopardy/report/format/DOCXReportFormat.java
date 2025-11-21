package com.jeopardy.report.format;

import java.util.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.xwpf.usermodel.*;

import com.jeopardy.logging.ActivityLog;

/**
 * DOCXReportFormat generates activity reports in DOCX (Microsoft Word) format using Apache POI.
 * 
 * This format is useful for:
 * - Professional reports with formatting
 * - Integration with Microsoft Office suite
 * - Easy editing and customization
 * - Distribution to non-technical users
 * - Document-ready output
 */
public class DOCXReportFormat implements ReportFormat {

    /**
     * Default constructor for DOCXReportFormat.
     */
    public DOCXReportFormat() {
    }

    /**
     * Generates a DOCX report from activity log data using Apache POI.
     * 
     * The report includes:
     * - Professional title and metadata
     * - Case ID and player list
     * - Detailed activity summaries with turn-by-turn breakdown
     * - Final scores
     * - Generation timestamp
     * 
     * @param data an ArrayList of ActivityLog entries to include in the report
     */
    @Override
    public void generate(ArrayList<ActivityLog> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("No activity log data to generate DOCX report");
            return;
        }

        String filename = generateFilename("docx");
        
        try (XWPFDocument document = new XWPFDocument()) {
            // Add title
            XWPFParagraph title = document.createParagraph();
            XWPFRun titleRun = title.createRun();
            titleRun.setText("JEOPARDY PROGRAMMING GAME REPORT");
            titleRun.setBold(true);
            titleRun.setFontSize(18);
            
            // Add case ID
            String caseId = data.get(0).getCaseId() != null ? data.get(0).getCaseId() : "UNKNOWN";
            XWPFParagraph caseInfo = document.createParagraph();
            caseInfo.createRun().setText("Case ID: " + caseId);
            
            // Extract unique players
            Set<String> players = new LinkedHashSet<>();
            for (ActivityLog log : data) {
                if (log.getPlayerId() != null && log.getPlayerId() != "System") {
                    players.add(log.getPlayerId());
                }
            }
            
            if (!players.isEmpty()) {
                XWPFParagraph playerInfo = document.createParagraph();
                playerInfo.createRun().setText("Players: " + String.join(", ", players));
                
                // Add blank line
                document.createParagraph();
            }
            
            // Add gameplay summary section
            XWPFParagraph summaryTitle = document.createParagraph();
            XWPFRun summaryRun = summaryTitle.createRun();
            summaryRun.setText("Gameplay Summary:");
            summaryRun.setBold(true);
            summaryRun.setFontSize(12);
            
            // Add activity logs with same format as PDF
            for (ActivityLog log : data) {
                String summary = log.toSummaryString();
                if (summary != null) {
                    String[] lines = summary.split("\n");
                
                    for (String line : lines) {
                        if (line.trim().isEmpty()) continue;
                        
                        XWPFParagraph activityPara = document.createParagraph();
                        activityPara.createRun().setText(line);
                    }
                    
                    // Add spacing between activity blocks
                    document.createParagraph();
                }
            }
            
            // Add final scores section
            XWPFParagraph scoresTitle = document.createParagraph();
            XWPFRun scoresRun = scoresTitle.createRun();
            scoresRun.setText("Final Scores:");
            scoresRun.setBold(true);
            scoresRun.setFontSize(12);
            
            // Extract final scores
            Map<String, Integer> finalScores = new LinkedHashMap<>();
            for (ActivityLog log : data) {
                if (log.getPlayerId() != null && log.getPlayerId() != "System") {
                    finalScores.put(log.getPlayerId(), log.getScoreAfterPlay());
                }
            }

            for (Map.Entry<String, Integer> entry : finalScores.entrySet()) {
                XWPFParagraph score = document.createParagraph();
                score.createRun().setText(entry.getKey() + ": " + entry.getValue());
            }
            
            // Add footer with timestamp
            XWPFParagraph footer = document.createParagraph();
            footer.setAlignment(org.apache.poi.xwpf.usermodel.ParagraphAlignment.LEFT);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            footer.createRun().setText("Generated: " + now.format(formatter));
            
            // Save document
            try (FileOutputStream fos = new FileOutputStream(filename)) {
                document.write(fos);
                System.out.println("DOCX report generated: " + filename);
            }
            
        } catch (IOException e) {
            System.err.println("Error generating DOCX report: " + e.getMessage());
            e.printStackTrace();
        }
    }

}