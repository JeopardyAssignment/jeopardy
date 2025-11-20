package com.jeopardy.question.loader;
import java.util.ArrayList;
import com.jeopardy.question.Question;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * 
 */
public class XMLQuestionLoader implements QuestionLoader {

    /**
     * Default constructor
     */
    public XMLQuestionLoader() {
    }

    /**
     * @param filename 
     * @return
     */
    @Override
    public ArrayList<Question> load(String filename) {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(filename);

            NodeList questionNodes = doc.getElementsByTagName("QuestionItem");
            for (int i = 0; i < questionNodes.getLength(); i++) {
                Node questionNode = questionNodes.item(i);
                if (questionNode.getNodeType() != Node.ELEMENT_NODE) continue;

                Element questionElement = (Element) questionNode;
                Question question = new Question();
                NodeList catNodes = questionElement.getElementsByTagName("Category");
                question.setCategory(catNodes.item(0).getTextContent().trim());

                NodeList valueNodes = questionElement.getElementsByTagName("Value");

                try {
                    question.setValue(Integer.parseInt(valueNodes.item(0).getTextContent().trim()));
                } catch (NumberFormatException n) {
                }
                
                NodeList textNodes = questionElement.getElementsByTagName("QuestionText");
                question.setQuestion(textNodes.item(0).getTextContent().trim());
                
                String[] optionsArray;
                ArrayList<String> optionsList = new ArrayList<>();
                NodeList optionsNodes = questionElement.getElementsByTagName("Options");

                Element optionsElement = (Element) optionsNodes.item(0);
                NodeList optionChildren = optionsElement.getChildNodes();
                for (int j = 0; j < optionChildren.getLength(); j++) {
                    Node optNode = optionChildren.item(j);
                    if (optNode.getNodeType() != Node.ELEMENT_NODE) continue;
                    String text = optNode.getTextContent();
                    
                    if (text != null) optionsList.add(text.trim());
                }
                
                if (!optionsList.isEmpty()) {
                    optionsArray = optionsList.toArray(new String[4]);
                    question.setOptions(optionsArray);
                }
                NodeList correctNodes = questionElement.getElementsByTagName("CorrectAnswer");
                if (correctNodes.getLength() > 0 && correctNodes.item(0) != null) {
                    question.setCorrectAnswer(correctNodes.item(0).getTextContent().trim());
                }

                questions.add(question);
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + filename);
            e.printStackTrace();
        }

        return questions;
    }

}