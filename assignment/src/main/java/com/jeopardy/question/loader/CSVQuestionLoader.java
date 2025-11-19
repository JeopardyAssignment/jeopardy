package com.jeopardy.question.loader;
import java.util.ArrayList;
import com.jeopardy.question.Question;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * 
 */
public class CSVQuestionLoader implements QuestionLoader {

    /**
     * Default constructor
     */
    public CSVQuestionLoader() {
    }

    /**
     * @param filename 
     * @return
     */
    public ArrayList<Question> load(String filename) {
        ArrayList<Question> questions = new ArrayList<>();
        // TODO implement QuestionLoader.load() here
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String firstLine = reader.readLine();

            if (firstLine != null && !firstLine.toLowerCase().contains("category")) {
                String[] parts = firstLine.split(",");
                if (parts.length >= 8) {
                    Question question = new Question();
                    question.setCategory(parts[0]);
                    try { question.setValue(Integer.parseInt(parts[1])); } catch (Exception e) {}
                    question.setQuestion(parts[2]);
                    String[] answers = {parts[3], parts[4], parts[5], parts[6]};
                    question.setOptions(answers);
                    question.setCorrectAnswer(parts[7]);
                    questions.add(question);
                }
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 8) continue;
                Question question = new Question();
                question.setCategory(parts[0]);
                try { question.setValue(Integer.parseInt(parts[1])); } catch (Exception e) {}
                question.setQuestion(parts[2]);
                String[] answers = {parts[3], parts[4], parts[5], parts[6]};
                question.setOptions(answers);
                question.setCorrectAnswer(parts[7]);
                questions.add(question);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
            e.printStackTrace();
        }
        return questions;
    }

}