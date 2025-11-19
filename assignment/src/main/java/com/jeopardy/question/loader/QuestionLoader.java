package com.jeopardy.question.loader;
import com.jeopardy.question.Question;
import java.util.ArrayList;

/**
 * 
 */
public interface QuestionLoader {

    /**
     * @param filename 
     * @return
     */
    public ArrayList<Question> load(String filename);

}