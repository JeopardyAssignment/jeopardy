package com.jeopardy.question;

import com.jeopardy.question.loader.QuestionLoader;

import java.util.*;

/**
 * QuestionService manages a collection of Question objects for the Jeopardy game.
 *
 * This service class provides centralized management of questions including:
 * - Loading questions from various file formats using the Strategy pattern
 * - Retrieving all questions, answered questions, or unanswered questions
 * - Maintaining game state by tracking which questions have been answered
 *
 * The service uses QuestionLoader implementations to support multiple file formats
 * (CSV, JSON, XML) without needing to know the specific loading logic.
 *
 * Usage example:
 * <pre>
 * QuestionService service = new QuestionService();
 * service.setQuestions(new JSONQuestionLoader(), "questions.json");
 * ArrayList&lt;Question&gt; unanswered = service.getUnansweredQuestions();
 * </pre>
 */
public class QuestionService {

    private ArrayList<Question> questions;

    /**
     * Constructs a new QuestionService with no questions loaded.
     */
    public QuestionService() {
    }

    /**
     * Gets a copy of all questions managed by this service.
     *
     * Returns a defensive copy to prevent external modification of the internal list.
     *
     * @return an ArrayList containing all questions, or an empty list if no questions are loaded
     */
    public ArrayList<Question> getQuestions() {
        if (this.questions == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(this.questions);
    }

    /**
     * Gets all questions that have been answered.
     *
     * @return an ArrayList containing only questions where isAnswered is true
     */
    public ArrayList<Question> getAnsweredQuestions() {
        ArrayList<Question> answered = new ArrayList<>();
        if (this.questions == null) {
            return answered;
        }
        for (Question q : this.questions) {
            if (q.getIsAnswered()) {
                answered.add(q);
            }
        }
        return answered;
    }

    /**
     * Gets all questions that have not been answered yet.
     *
     * @return an ArrayList containing only questions where isAnswered is false
     */
    public ArrayList<Question> getUnansweredQuestions() {
        ArrayList<Question> unanswered = new ArrayList<>();
        if (this.questions == null) {
            return unanswered;
        }
        for (Question q : this.questions) {
            if (!q.getIsAnswered()) {
                unanswered.add(q);
            }
        }
        return unanswered;
    }

    /**
     * Loads questions from a file using the specified QuestionLoader strategy.
     *
     * This method uses the Strategy pattern to support multiple file formats.
     * The loader parameter determines which format to parse (CSV, JSON, or XML).
     *
     * If loading fails, an empty question list is set and an error is printed.
     *
     * @param loader the QuestionLoader implementation to use for parsing the file
     * @param filename the path to the file containing questions
     */
    public void setQuestions(QuestionLoader loader, String filename) {
        if (loader == null) {
            this.questions = new ArrayList<>();
            return;
        }

        try {
            ArrayList<Question> loaded = loader.load(filename);
            if (loaded == null) {
                loaded = new ArrayList<>();
            }
            this.questions = new ArrayList<>(loaded);
        } catch (Exception e) {
            System.out.println("Error loading questions from: " + filename);
            e.printStackTrace();
            this.questions = new ArrayList<>();
        }
    }

}
