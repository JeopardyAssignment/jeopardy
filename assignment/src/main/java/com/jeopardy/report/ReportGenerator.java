package com.jeopardy.report;

import com.jeopardy.logging.observer.Subscriber;
import com.jeopardy.report.format.ReportFormat;
import com.jeopardy.logging.ActivityLog;

import java.util.*;

/**
 * 
 */
public class ReportGenerator implements Subscriber {

    /**
     * Default constructor
     */
    public ReportGenerator() {
    }

    /**
     * 
     */
    private ReportFormat format;

    /**
     * 
     */
    private ArrayList<ActivityLog> data;


    /**
     * @param r
     */
    public void setFormat(ReportFormat r) {
        // TODO implement here
    }

    /**
     * 
     */
    public void createReport() {
        // TODO implement here
    }

    /**
     * @param activity
     */
    public void update(ActivityLog activity) {
        // TODO implement here
    }

}