package com.jeopardy.logging.observer;

import com.jeopardy.logging.ActivityLog;

/**
 * 
 */
public interface Subscriber {



    /**
     * @param activity
     */
    public void update(ActivityLog activity);

}