package com.jeopardy.logging.observer;

import com.jeopardy.logging.ActivityLog;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface Subscriber {



    /**
     * @param activity
     */
    public void update(ActivityLog activity);

}