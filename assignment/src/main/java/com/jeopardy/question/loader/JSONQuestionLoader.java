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
 * 
 */
public class JSONQuestionLoader implements QuestionLoader {

    /**
     * Default constructor
     */
    public JSONQuestionLoader() {
    }

    /**
     * @param filename 
     * @return
     */
    public ArrayList<Question> load(String filename) {
        // TODO implement QuestionLoader.load() here
        ArrayList<Question> questions = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filename)) {
            Object obj = parser.parse(reader);
            JSONArray questionArray = (org.json.simple.JSONArray) obj;
            for (Object qObj : questionArray) {
                JSONObject qJson = (JSONObject) qObj;
                Question question = new Question();
                question.setCategory((String) qJson.get("Category"));

                Object valueObj = qJson.get("Value");
                if (valueObj instanceof Number) {
                    question.setValue(((Number) valueObj).intValue());
                }

                question.setQuestion((String) qJson.get("Question"));


                Object optionsObj = qJson.get("Options");
                ArrayList<String> optionsList = new ArrayList<>();
                if (optionsObj instanceof JSONObject) {
                    JSONObject opts = (JSONObject) optionsObj;

                    String[] keys = new String[]{"A", "B", "C", "D"};
                    for (String k : keys) {
                        Object val = opts.get(k);
                        if (val != null) optionsList.add(((String) val).trim());
                    }
                } else if (optionsObj instanceof JSONArray) {
                    JSONArray opts = (JSONArray) optionsObj;
                    for (Object o : opts) {
                        if (o != null) optionsList.add(((String) o).trim());
                    }
                }

                if (!optionsList.isEmpty()) {
                    String[] optionsArray = optionsList.toArray(new String[optionsList.size()]);
                    question.setOptions(optionsArray);
                }


                Object corr = qJson.get("CorrectAnswer");
                if (corr == null) corr = qJson.get("correctAnswer");
                if (corr != null) question.setCorrectAnswer(((String) corr).trim());

                questions.add(question);
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error reading or parsing file: " + filename);
            e.printStackTrace();
        }
        
        return questions;
    }

}