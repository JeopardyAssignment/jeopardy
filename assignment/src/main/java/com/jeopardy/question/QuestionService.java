package com.jeopardy.question;

import com.jeopardy.question.loader.QuestionLoader;

import java.util.*;

/**
 * 
 */
public class QuestionService {

    /**
     * Default constructor
     */
    public QuestionService() {
    }

    /**
     * 
     */
    private ArrayList<Question> questions;






    /**
     * @return
     */
    public ArrayList<Question> getQuestions() {
        if (this.questions == null) return new ArrayList<>();
        return new ArrayList<>(this.questions);
    }

    /**
     * @return
     */
    public ArrayList<Question> getAnsweredQuestions() {
        ArrayList<Question> answered = new ArrayList<>();
        if (this.questions == null) return answered;
        for (Question q : this.questions) {
            if (q.getIsAnswered()) answered.add(q);
        }
        return answered;
    }

    /**
     * @return
     */
    public ArrayList<Question> getUnansweredQuestions() {
        ArrayList<Question> unanswered = new ArrayList<>();
        if (this.questions == null) return unanswered;
        for (Question q : this.questions) {
            if (!q.getIsAnswered()) unanswered.add(q);
        }
        return unanswered;
    }

    /**
     * @param loader 
     * @return
     */
    public void setQuestions(QuestionLoader loader, String filename) {
        if (loader == null) {
            this.questions = new ArrayList<>();
            return;
        }

        try {
            ArrayList<Question> loaded = loader.load(filename);
            if (loaded == null) loaded = new ArrayList<>();
            this.questions = new ArrayList<>(loaded);
        } catch (Exception e) {
            System.out.println("Error loading questions from: " + filename);
            e.printStackTrace();
            this.questions = new ArrayList<>();
        }
        
    }

}