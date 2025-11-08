package com.jeopardy.report.format;

import java.util.*;
import java.io.*;

import com.jeopardy.logging.ActivityLog;

/**
 * CSVReportFormat generates activity reports in CSV (Comma-Separated Values) format.
 * 
 * This format is useful for:
 * - Importing data into spreadsheet applications (Excel, Google Sheets, etc.)
 * - Data analysis and processing
 * - Integration with other systems
 * 
 * The CSV file includes a header row and one row per activity log entry.
 * Header: Case_ID, Player_ID, Activity, Timestamp, Category, Question_Value, Answer_Given, Result, Score_After_Play
 */
public class CSVReportFormat implements ReportFormat {

    /**
     * Default constructor for CSVReportFormat.
     */
    public CSVReportFormat() {
    }

    /**
     * Generates a CSV report from activity log data and writes it to a file.
     * 
     * The report includes:
     * - A header row with column names
     * - One row per ActivityLog entry with all relevant data
     * - Proper escaping and formatting for CSV compatibility
     * 
     * The file is saved to output/ directory with a timestamp in the filename.
     * 
     * @param data an ArrayList of ActivityLog entries to include in the report
     */
    @Override
    public void generate(ArrayList<ActivityLog> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("No activity log data to generate CSV report");
            return;
        }

        String filename = generateFilename("csv");
        
        try (FileWriter writer = new FileWriter(filename)) {
            // Write header
            writer.write("Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play\n");
            
            // Write data rows
            for (ActivityLog log : data) {
                writer.write(log.toCSVString());
            }
            
            System.out.println("CSV report generated: " + filename);
        } catch (IOException e) {
            System.err.println("Error generating CSV report: " + e.getMessage());
            e.printStackTrace();
        }
    }

}