package com.jeopardy.logging.observer;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface Publisher {

    /**
     * @param s
     */
    public void subscribe(Subscriber s);

    /**
     * @param s
     */
    public void unsubscribe(Subscriber s);

    /**
     * 
     */
    public void notififySubscribers();

}