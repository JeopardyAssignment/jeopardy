package com.jeopardy.question.loader;

import java.util.ArrayList;
import com.jeopardy.question.Question;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import java.io.FileReader;
import java.io.IOException;

/**
 * JSONQuestionLoader loads questions from JSON (JavaScript Object Notation) files.
 *
 * This class implements the QuestionLoader interface and provides functionality
 * to parse JSON files containing Jeopardy questions.
 *
 * Expected JSON format:
 * <pre>
 * [
 *   {
 *     "Category": "Science",
 *     "Value": 100,
 *     "Question": "What is H2O?",
 *     "Options": {"A": "Water", "B": "Hydrogen", "C": "Oxygen", "D": "Salt"},
 *     "CorrectAnswer": "A"
 *   }
 * ]
 * </pre>
 *
 * Alternative formats supported:
 * - Options can be a JSON object with keys A, B, C, D
 * - Options can be a JSON array ["Water", "Hydrogen", "Oxygen", "Salt"]
 * - CorrectAnswer field can be "CorrectAnswer" or "correctAnswer"
 *
 * Features:
 * - Flexible JSON parsing supporting multiple formats
 * - Handles both object and array representations of options
 * - Case-insensitive field name handling for some fields
 * - Robust error handling for malformed JSON
 */
public class JSONQuestionLoader implements QuestionLoader {

    /**
     * Constructs a new JSONQuestionLoader.
     */
    public JSONQuestionLoader() {
    }

    /**
     * Loads questions from a JSON file.
     *
     * Parses the JSON file expecting an array of question objects.
     * Each question object should contain Category, Value, Question, Options, and CorrectAnswer fields.
     *
     * The Options field can be either:
     * - A JSON object with keys A, B, C, D mapping to option text
     * - A JSON array of option strings (automatically labeled A, B, C, D)
     *
     * @param filename the path to the JSON file containing questions
     * @return an ArrayList of Question objects parsed from the file
     */
    @Override
    public ArrayList<Question> load(String filename) {
        ArrayList<Question> questions = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            Object obj = parser.parse(reader);
            JSONArray questionArray = (JSONArray) obj;

            for (Object qObj : questionArray) {
                JSONObject qJson = (JSONObject) qObj;
                Question question = parseQuestionFromJSON(qJson);
                questions.add(question);
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error reading or parsing file: " + filename);
            e.printStackTrace();
        }

        return questions;
    }

    /**
     * Parses a single JSON object into a Question.
     *
     * @param qJson the JSON object representing a question
     * @return a Question object populated with data from the JSON
     */
    private Question parseQuestionFromJSON(JSONObject qJson) {
        Question question = new Question();

        // Parse category
        question.setCategory((String) qJson.get("Category"));

        // Parse value
        Object valueObj = qJson.get("Value");
        if (valueObj instanceof Number) {
            question.setValue(((Number) valueObj).intValue());
        }

        // Parse question text
        question.setQuestion((String) qJson.get("Question"));

        // Parse options (supports both object and array formats)
        parseOptions(qJson, question);

        // Parse correct answer (supports multiple field names)
        Object corr = qJson.get("CorrectAnswer");
        if (corr == null) {
            corr = qJson.get("correctAnswer");
        }
        if (corr != null) {
            question.setCorrectAnswer(((String) corr).trim());
        }

        return question;
    }

    /**
     * Parses the Options field from JSON and sets it on the Question.
     * Supports both object format ({"A": "text", "B": "text"}) and array format (["text1", "text2"]).
     *
     * @param qJson the JSON object containing the Options field
     * @param question the Question object to set options on
     */
    private void parseOptions(JSONObject qJson, Question question) {
        Object optionsObj = qJson.get("Options");
        ArrayList<String> optionsList = new ArrayList<>();

        if (optionsObj instanceof JSONObject) {
            // Options as object: {"A": "text", "B": "text", ...}
            JSONObject opts = (JSONObject) optionsObj;
            String[] keys = {"A", "B", "C", "D"};
            for (String k : keys) {
                Object val = opts.get(k);
                if (val != null) {
                    optionsList.add(((String) val).trim());
                }
            }
        } else if (optionsObj instanceof JSONArray) {
            // Options as array: ["text1", "text2", "text3", "text4"]
            JSONArray opts = (JSONArray) optionsObj;
            for (Object o : opts) {
                if (o != null) {
                    optionsList.add(((String) o).trim());
                }
            }
        }

        if (!optionsList.isEmpty()) {
            String[] optionsArray = optionsList.toArray(new String[0]);
            question.setOptions(optionsArray);
        }
    }

}
