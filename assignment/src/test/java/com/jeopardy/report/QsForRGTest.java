package com.jeopardy.report;
import java.util.ArrayList;
import com.jeopardy.question.Question;

public class QsForRGTest {
    public QsForRGTest() {
    }

    public ArrayList<Question> getQuestionsForReportGenTest() {
        ArrayList<Question> questions = new ArrayList<>();

        Question q1 = new Question();
        q1.setCategory("Science");
        q1.setQuestion("This is a sample question for testing. 1");
        q1.setValue(200);
        q1.setCorrectAnswer("A");
        questions.add(q1);

        Question q2 = new Question();
        q2.setCategory("History");
        q2.setQuestion("This is a sample question for testing. 2");
        q2.setValue(300);
        q2.setCorrectAnswer("B");
        questions.add(q2);

        Question q3 = new Question();
        q3.setCategory("Geography");
        q3.setQuestion("This is a sample question for testing. 3");
        q3.setValue(400);
        q3.setCorrectAnswer("C");
        questions.add(q3);

        Question q4 = new Question();
        q4.setCategory("Literature");
        q4.setQuestion("This is a sample question for testing. 4");
        q4.setValue(500);
        q4.setCorrectAnswer("D");
        questions.add(q4);

        Question q5 = new Question();
        q5.setCategory("Math");
        q5.setQuestion("This is a sample question for testing. 5");
        q5.setValue(600);
        q5.setCorrectAnswer("A");
        questions.add(q5);
        

        return questions;
    }
    
}
