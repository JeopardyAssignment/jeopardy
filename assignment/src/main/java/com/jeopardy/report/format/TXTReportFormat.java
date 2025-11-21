package com.jeopardy.report.format;

import java.util.*;
import java.io.*;

import com.jeopardy.logging.ActivityLog;

/**
 * TXTReportFormat generates activity reports in plain text format.
 * 
 * This format is useful for:
 * - Human-readable summaries of game activities
 * - Email reports
 * - Quick visual inspection of game logs
 * - Basic documentation
 * 
 * The text report includes a header, individual activity summaries, and a footer.
 */
public class TXTReportFormat implements ReportFormat {
    /**
     * Generates a plain text report from activity log data and writes it to a file.
     * 
     * The report includes:
     * - A professional header with title and case ID
     * - List of unique players
     * - Complete gameplay summary with turn-by-turn activity
     * - Individual activity summaries for each turn
     * - Final scores for all players
     * 
     * The file is saved to output/ directory with a timestamp in the filename.
     * 
     * @param data an ArrayList of ActivityLog entries to include in the report
     */
    @Override
    public void generate(ArrayList<ActivityLog> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("No activity log data to generate TXT report");
            return;
        }

        String filename = generateFilename("txt");
        
        try (FileWriter writer = new FileWriter(filename)) {
            // Write header
            writer.write("JEOPARDY PROGRAMMING GAME REPORT\n");
            writer.write("================================\n\n");
            
            // Extract and write case ID
            String caseId = data.get(0).caseId != null ? data.get(0).caseId : "UNKNOWN";
            writer.write("Case ID: " + caseId + "\n\n");
            
            // Extract unique players and write them
            Set<String> players = new LinkedHashSet<>();
            for (ActivityLog log : data) {
                if (log.playerId != null) {
                    players.add(log.playerId);
                }
            }
            
            if (!players.isEmpty()) {
                writer.write("Players: " + String.join(", ", players) + "\n\n");
            }
            
            // Write gameplay summary section
            writer.write("Gameplay Summary:\n");
            writer.write("-----------------\n");
            
            for (ActivityLog log : data) {
                String text = log.toSummaryString();
                if (text != null) {
                    writer.write(text);
                    writer.write("\n\n");
                }
            }
            
            // Write final scores
            writer.write("\nFinal Scores:\n");
            Map<String, Integer> finalScores = new LinkedHashMap<>();
            for (ActivityLog log : data) {
                if (log.playerId != null && log.playerId != "System") {
                    finalScores.put(log.playerId, log.scoreAfterPlay);
                }
            }

            for (Map.Entry<String, Integer> entry : finalScores.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            
            System.out.println("TXT report generated: " + filename);
        } catch (IOException e) {
            System.err.println("Error generating TXT report: " + e.getMessage());
            e.printStackTrace();
        }
    }

}