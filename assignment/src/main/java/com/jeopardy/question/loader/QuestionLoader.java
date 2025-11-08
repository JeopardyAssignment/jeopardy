package com.jeopardy.question.loader;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface QuestionLoader {

    /**
     * @param filename 
     * @return
     */
    public boolean load(String filename);

}