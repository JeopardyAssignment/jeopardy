package com.jeopardy.report.format;

import java.io.*;
import java.util.*;

import com.jeopardy.logging.ActivityLog;

/**
 * 
 */
public interface ReportFormat {

    /**
     * @param data
     */
    public void generate(ArrayList<ActivityLog> data);

}