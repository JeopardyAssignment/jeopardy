package com.jeopardy.question;

import com.jeopardy.question.loader.CSVQuestionLoader;
import com.jeopardy.question.loader.JSONQuestionLoader;
import com.jeopardy.question.loader.XMLQuestionLoader;
import com.jeopardy.utils.GameConstants;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Integration test that exercises all loaders, the Question model, and QuestionService.
 */
public class QuestionTest {

    @Test
    public void loadersAndServiceIntegration() {

        CSVQuestionLoader csvLoader = new CSVQuestionLoader();
        ArrayList<Question> csvQuestions = csvLoader.load(
            GameConstants.DATA_DIRECTORY.resolve("sample_game_CSV.csv").toString()
        );
        assertNotNull("CSV result should not be null", csvQuestions);
        assertTrue("CSV should load at least one question", csvQuestions.size() > 0);
        Question csvFirst = csvQuestions.get(0);
        assertEquals("First CSV question category", "Variables & Data Types", csvFirst.getCategory());
        assertNotNull(csvFirst.getQuestion());
        assertNotNull(csvFirst.getOptions());
        assertNotNull(csvFirst.getCorrectAnswer());


        JSONQuestionLoader jsonLoader = new JSONQuestionLoader();
        ArrayList<Question> jsonQuestions = jsonLoader.load(
            GameConstants.DATA_DIRECTORY.resolve("sample_game_JSON.json").toString()
        );
        assertNotNull("JSON result should not be null", jsonQuestions);
        assertTrue("JSON should load at least one question", jsonQuestions.size() > 0);
        Question jsonFirst = jsonQuestions.get(0);
        assertEquals("Variables & Data Types", jsonFirst.getCategory());
        assertEquals(100, jsonFirst.getValue());
        assertNotNull(jsonFirst.getOptions());
        assertEquals("A", jsonFirst.getCorrectAnswer());


        XMLQuestionLoader xmlLoader = new XMLQuestionLoader();
        ArrayList<Question> xmlQuestions = xmlLoader.load(
            GameConstants.DATA_DIRECTORY.resolve("sample_game_XML.xml").toString()
        );
        assertNotNull("XML result should not be null", xmlQuestions);
        assertTrue("XML should load at least one question", xmlQuestions.size() > 0);
        Question xmlFirst = xmlQuestions.get(0);
        assertEquals("Variables & Data Types", xmlFirst.getCategory());
        assertTrue(xmlFirst.getValue() > 0);
        assertNotNull(xmlFirst.getOptions());
        assertNotNull(xmlFirst.getCorrectAnswer());


        // Test case sensitivity with separate Question instances (evaluate can only be called once per question)
        Question q1 = new Question();
        q1.setCorrectAnswer("B");
        assertTrue(q1.evaluate("B"));

        Question q2 = new Question();
        q2.setCorrectAnswer("B");
        assertTrue(q2.evaluate("b"));

        Question q3 = new Question();
        q3.setCorrectAnswer("B");
        assertFalse(q3.evaluate("A"));


        QuestionService service = new QuestionService();
        service.setQuestions(jsonLoader, GameConstants.DATA_DIRECTORY.resolve("sample_game_JSON.json").toString());
        ArrayList<Question> serviceList = service.getQuestions();
        assertNotNull(serviceList);
        assertTrue(serviceList.size() > 0);
        assertEquals(serviceList.size(), service.getUnansweredQuestions().size());
        assertEquals(0, service.getAnsweredQuestions().size());
    }
}
