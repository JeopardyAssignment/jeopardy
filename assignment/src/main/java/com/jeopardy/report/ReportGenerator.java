package com.jeopardy.report;

import com.jeopardy.logging.observer.Subscriber;
import com.jeopardy.report.format.ReportFormat;
import com.jeopardy.logging.ActivityLog;

import java.util.*;
import java.io.File;

/**
 * ReportGenerator is a Subscriber that collects ActivityLog entries and generates reports.
 * 
 * This class implements the Subscriber interface from the Observer pattern, allowing it to
 * be registered with Publishers (like Player) to receive notifications of game activities.
 * 
 * Key features:
 * - Receives activity notifications through the Observer pattern
 * - Accumulates ActivityLog entries in a collection
 * - Supports multiple report formats through the Strategy pattern
 * - Generates reports on demand with the specified format
 * 
 * Usage:
 * <pre>
 * ReportGenerator reporter = new ReportGenerator();
 * reporter.setFormat(new CSVReportFormat());
 * publisher.subscribe(reporter);  // Subscribe to activities
 * // Activities are recorded as they happen...
 * reporter.createReport();  // Generate report when ready
 * </pre>
 */
public class ReportGenerator implements Subscriber {

    /**
     * The current report format strategy for generating reports.
     */
    private ReportFormat format;

    /**
     * List of all activity logs collected so far.
     */
    private ArrayList<ActivityLog> data;

    /**
     * Default constructor that initializes the activity log collection.
     */
    public ReportGenerator() {
        this.data = new ArrayList<>();
    }

    /**
     * Sets the report format to be used when generating reports.
     * 
     * This method allows switching between different report formats
     * (CSV, TXT, PDF, DOCX) without changing the ReportGenerator itself,
     * implementing the Strategy design pattern.
     * 
     * @param r the ReportFormat implementation to use for generating reports
     */
    public ReportGenerator setFormat(ReportFormat r) {
        this.format = r;
        return this;
    }

    /**
     * Generates a report using the currently set format.
     * 
     * Calls the generate() method on the current ReportFormat with all
     * collected activity logs. The report is generated and saved according
     * to the format's implementation.
     * 
     * If no format has been set, this method will throw a NullPointerException.
     * Ensure setFormat() is called before calling this method.
     * 
     * @throws NullPointerException if no format has been set via setFormat()
     */
    public void createReport() {
        ensureOutputDirectory();
        if (format == null) {
            throw new NullPointerException("Report format must be set before creating a report");
        }
        this.format.generate(data);
    }

    /**
     * Ensures the output directory exists, creating it if necessary.
     * This prevents FileNotFoundException when saving report files.
     */
    private void ensureOutputDirectory() {
        File outputDir = new File("output");
        if (!outputDir.exists()) {
            if (outputDir.mkdirs()) {
                System.out.println("Created output directory: output/");
            } else {
                System.err.println("Failed to create output directory");
            }
        }
    }

    /**
     * Updates this subscriber with a new activity log entry.
     * 
     * This method is called by Publishers whenever an activity occurs in the game.
     * The activity log is added to the internal collection for later report generation.
     * 
     * This implements the Subscriber interface from the Observer pattern.
     * 
     * @param activity the ActivityLog entry representing a game activity
     */
    @Override
    public void update(ActivityLog activity) {
        if (activity != null) {
            this.data.add(activity);
        }
    }

    /**
     * Returns the number of activity logs currently collected.
     * 
     * @return the size of the activity log collection
     */
    public int getActivityCount() {
        return this.data.size();
    }

    /**
     * Clears all collected activity logs.
     * 
     * This can be useful for starting a fresh collection without creating
     * a new ReportGenerator instance.
     */
    public void clearData() {
        this.data.clear();
    }

}