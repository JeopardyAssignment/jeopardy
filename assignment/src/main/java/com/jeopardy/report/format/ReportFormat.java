package com.jeopardy.report.format;

import java.util.*;

import com.jeopardy.logging.ActivityLog;

/**
 * ReportFormat is an interface that defines the contract for generating reports in different formats.
 * 
 * This interface implements the Strategy design pattern, allowing different report formats
 * to be used interchangeably without modifying the ReportGenerator class.
 * 
 * Implementations should:
 * - Accept a list of ActivityLog entries
 * - Generate a report in their specific format
 * - Output or save the report as appropriate for their format
 * 
 * Supported formats:
 * - CSV (Comma-Separated Values)
 * - TXT (Plain Text)
 * - DOCX (Microsoft Word)
 * - PDF (Portable Document Format)
 */
public interface ReportFormat {
    /**
     * Generates a report based on the provided activity log data.
     * 
     * The implementation should process the entire list of ActivityLog entries
     * and generate a formatted report appropriate for the specific format.
     * 
     * @param data an ArrayList of ActivityLog entries containing game activity information
     */
    public void generate(ArrayList<ActivityLog> data);

    /**
     * Generates a timestamped filename for the report.
     * 
     * @param format the file extension (e.g., "csv", "txt", "pdf", "docx")
     * @return a filename with the format: output/jeopardy_report_YYYYMMDD_HHmmss.{format}
     */
    public default String generateFilename(String format) {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return "output/jeopardy_report_" + now.format(formatter) + "." + format;
    }

}