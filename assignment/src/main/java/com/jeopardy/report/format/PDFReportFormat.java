package com.jeopardy.report.format;

import java.util.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.jeopardy.logging.ActivityLog;

/**
 * PDFReportFormat generates activity reports in PDF (Portable Document Format) using Apache PDFBox.
 * 
 * This format is useful for:
 * - Professional report distribution
 * - Archival and long-term storage
 * - Print-friendly documentation
 * - Sharing across different platforms
 * 
 * The PDF includes:
 * - Professional header with title and metadata
 * - Case ID and player list
 * - Detailed activity logs with formatting
 * - Final scores summary
 */
public class PDFReportFormat implements ReportFormat {

    private static final float MARGIN = 50;
    private static final float PAGE_HEIGHT = 792;
    private static final float LINE_HEIGHT = 12;
    private float currentY = PAGE_HEIGHT - MARGIN;

    /**
     * Default constructor for PDFReportFormat.
     */
    public PDFReportFormat() {
    }

    /**
     * Generates a PDF report from activity log data using Apache PDFBox.
     * 
     * The PDF includes:
     * - Professional title and header
     * - Case ID and player information
     * - Detailed activity summaries
     * - Final scores table
     * 
     * @param data an ArrayList of ActivityLog entries to include in the report
     */
    @Override
    public void generate(ArrayList<ActivityLog> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("No activity log data to generate PDF report");
            return;
        }

        String filename = generateFilename("pdf");
        PDDocument document = new PDDocument();
        
        try {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            
            currentY = PAGE_HEIGHT - MARGIN;
            
            // Write header
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
            contentStream.newLineAtOffset(MARGIN, currentY);
            contentStream.showText("JEOPARDY PROGRAMMING GAME REPORT");
            contentStream.endText();
            currentY -= LINE_HEIGHT * 2;
            
            // Write metadata
            String caseId = data.get(0).caseId != null ? data.get(0).caseId : "UNKNOWN";
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 11);
            contentStream.newLineAtOffset(MARGIN, currentY);
            contentStream.showText("Case ID: " + caseId);
            contentStream.endText();
            currentY -= LINE_HEIGHT * 1.5f;
            
            // Write player list
            Set<String> players = new LinkedHashSet<>();
            for (ActivityLog log : data) {
                if (log.playerId != null) {
                    players.add(log.playerId);
                }
            }
            
            if (!players.isEmpty()) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 11);
                contentStream.newLineAtOffset(MARGIN, currentY);
                contentStream.showText("Players: " + String.join(", ", players));
                contentStream.endText();
                currentY -= LINE_HEIGHT * 2;
            }
            
            // Write gameplay summary section
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(MARGIN, currentY);
            contentStream.showText("Gameplay Summary:");
            contentStream.endText();
            currentY -= LINE_HEIGHT * 1.5f;
            
            // Write activity logs
            for (ActivityLog log : data) {
                // Check if we need a new page
                if (currentY < MARGIN + LINE_HEIGHT * 5) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    currentY = PAGE_HEIGHT - MARGIN;
                }
                
                // Write activity summary
                String summary = log.toSummaryString();
                String[] lines = summary.split("\n");
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                
                for (String line : lines) {
                    if (line.trim().isEmpty()) continue;
                    
                    contentStream.newLineAtOffset(MARGIN, currentY);
                    contentStream.showText(truncateLineIfNeeded(line));
                    currentY -= LINE_HEIGHT;
                }
                
                contentStream.endText();
                currentY -= LINE_HEIGHT * 0.5f;
            }
            
            // Check if we need a new page for scores
            if (currentY < MARGIN + LINE_HEIGHT * 5) {
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                currentY = PAGE_HEIGHT - MARGIN;
            }
            
            // Write final scores section
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(MARGIN, currentY);
            contentStream.showText("Final Scores:");
            contentStream.endText();
            currentY -= LINE_HEIGHT * 1.5f;
            
            // Write scores
            Map<String, Integer> finalScores = new LinkedHashMap<>();
            for (ActivityLog log : data) {
                finalScores.put(log.playerId, log.scoreAfterPlay);
            }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 11);
            
            for (Map.Entry<String, Integer> entry : finalScores.entrySet()) {
                contentStream.newLineAtOffset(MARGIN, currentY);
                contentStream.showText(entry.getKey() + ": " + entry.getValue());
                currentY -= LINE_HEIGHT;
            }
            
            contentStream.endText();
            
            // Add footer with generation timestamp
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 9);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            contentStream.newLineAtOffset(MARGIN, MARGIN - 10);
            contentStream.showText("Generated: " + now.format(formatter));
            contentStream.endText();
            
            contentStream.close();
            
            // Save the document
            document.save(filename);
            System.out.println("PDF report generated: " + filename);
            
        } catch (IOException e) {
            System.err.println("Error generating PDF report: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                System.err.println("Error closing PDF document: " + e.getMessage());
            }
        }
    }

    /**
     * Truncates a line if it exceeds the page width to prevent overflow.
     * 
     * @param line the text line to truncate
     * @return the truncated line or original if it fits
     */
    private String truncateLineIfNeeded(String line) {
        final int MAX_CHARS = 90;
        if (line.length() > MAX_CHARS) {
            return line.substring(0, MAX_CHARS) + "...";
        }
        return line;
    }

}