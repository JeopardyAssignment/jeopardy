package com.jeopardy.question;

import com.jeopardy.question.loader.CSVQuestionLoader;
import com.jeopardy.question.loader.JSONQuestionLoader;
import com.jeopardy.question.loader.XMLQuestionLoader;
import com.jeopardy.utils.GameConstants;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Integration tests for all loaders (CSV, JSON, XML), Question, and QuestionService.
 *
 * Verifies:
 * - Each loader loads questions from sample files
 * - Question model stores and retrieves data correctly
 * - QuestionService manages questions and queries
 */
public class LoaderAndServiceIntegrationTest {
    private String csvFile = GameConstants.DATA_DIRECTORY.resolve(String.format("sample_game_CSV.csv")).toString();
    private String jsonFile = GameConstants.DATA_DIRECTORY.resolve(String.format("sample_game_JSON.json")).toString();;
    private String xmlFile = GameConstants.DATA_DIRECTORY.resolve(String.format("sample_game_XML.xml")).toString();;

    
    
    @Test
    public void testCSVLoader() {
        CSVQuestionLoader loader = new CSVQuestionLoader();
        ArrayList<Question> questions = loader.load(csvFile);
        Assert.assertNotNull(questions);
        Assert.assertFalse(questions.isEmpty());
        Question q = questions.get(0);
        Assert.assertNotNull(q.getCategory());
        Assert.assertNotNull(q.getQuestion());
        Assert.assertTrue(q.getValue() > 0);
        Assert.assertNotNull(q.getOptions());
        Assert.assertNotNull(q.getCorrectAnswer());
    }

    @Test
    public void testJSONLoader() {
        JSONQuestionLoader loader = new JSONQuestionLoader();
        ArrayList<Question> questions = loader.load(jsonFile);
        Assert.assertNotNull(questions);
        Assert.assertFalse(questions.isEmpty());
        Question q = questions.get(0);
        Assert.assertNotNull(q.getCategory());
        Assert.assertNotNull(q.getQuestion());
        Assert.assertTrue(q.getValue() > 0);
        Assert.assertNotNull(q.getOptions());
        Assert.assertNotNull(q.getCorrectAnswer());
    }

    @Test
    public void testXMLLoader() {
        XMLQuestionLoader loader = new XMLQuestionLoader();
        ArrayList<Question> questions = loader.load(xmlFile);
        Assert.assertNotNull(questions);
        Assert.assertFalse(questions.isEmpty());
        Question q = questions.get(0);
        Assert.assertNotNull(q.getCategory());
        Assert.assertNotNull(q.getQuestion());
        Assert.assertTrue(q.getValue() > 0);
        Assert.assertNotNull(q.getOptions());
        Assert.assertNotNull(q.getCorrectAnswer());
    }

    @Test
    public void testQuestionModel() {
        Question q = new Question();
        q.setCategory("Science");
        q.setQuestion("What is H2O?");
        q.setValue(100);
        ArrayList<String> opts = new ArrayList<>();
        opts.add("Water"); opts.add("Oxygen"); opts.add("Hydrogen"); opts.add("Carbon Dioxide");
        q.setOptions(opts);
        q.setCorrectAnswer("Water");
        Assert.assertEquals("Science", q.getCategory());
        Assert.assertEquals("What is H2O?", q.getQuestion());
        Assert.assertEquals(100, q.getValue());
        Assert.assertEquals("Water", q.getCorrectAnswer());
        Assert.assertEquals(4, q.getOptions().size());
    }

    @Test
    public void testQuestionService() {
        // Use CSV loader for sample data
        CSVQuestionLoader loader = new CSVQuestionLoader();
        QuestionService service = new QuestionService();
        service.setQuestions(loader, csvFile);
        ArrayList<Question> questions = service.getQuestions();;
        Assert.assertEquals(questions.size(), service.getQuestions().size());
        // Test getQuestionsByCategory
        String category = questions.get(0).getCategory();
        ArrayList<Question> byCat = service.getQuestionsByCategory(category);
        Assert.assertFalse(byCat.isEmpty());
        // Test getAnsweredQuestions and getUnansweredQuestions
        int total = questions.size();
        questions.get(0).setIsAnswered(true);
        Assert.assertEquals(1, service.getAnsweredQuestions().size());
        Assert.assertEquals(total - 1, service.getUnansweredQuestions().size());
    }
}
