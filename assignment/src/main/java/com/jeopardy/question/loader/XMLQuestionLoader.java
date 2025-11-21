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
 * XMLQuestionLoader loads questions from XML (Extensible Markup Language) files.
 *
 * This class implements the QuestionLoader interface and provides functionality
 * to parse XML files containing Jeopardy questions.
 *
 * Expected XML format:
 * <pre>
 * &lt;Questions&gt;
 *   &lt;QuestionItem&gt;
 *     &lt;Category&gt;Science&lt;/Category&gt;
 *     &lt;Value&gt;100&lt;/Value&gt;
 *     &lt;QuestionText&gt;What is H2O?&lt;/QuestionText&gt;
 *     &lt;Options&gt;
 *       &lt;Option&gt;Water&lt;/Option&gt;
 *       &lt;Option&gt;Hydrogen&lt;/Option&gt;
 *       &lt;Option&gt;Oxygen&lt;/Option&gt;
 *       &lt;Option&gt;Salt&lt;/Option&gt;
 *     &lt;/Options&gt;
 *     &lt;CorrectAnswer&gt;A&lt;/CorrectAnswer&gt;
 *   &lt;/QuestionItem&gt;
 * &lt;/Questions&gt;
 * </pre>
 *
 * Features:
 * - Uses DOM parser for XML processing
 * - Extracts questions from QuestionItem elements
 * - Automatically labels options as A, B, C, D
 * - Handles malformed XML gracefully
 * - Robust error handling for parsing issues
 */
public class XMLQuestionLoader implements QuestionLoader {

    /**
     * Constructs a new XMLQuestionLoader.
     */
    public XMLQuestionLoader() {
    }

    /**
     * Loads questions from an XML file.
     *
     * Parses the XML file using DOM parser, extracting all QuestionItem elements
     * and converting them to Question objects. Each QuestionItem should contain:
     * - Category: the question category
     * - Value: the point value
     * - QuestionText: the question text
     * - Options: container element with multiple Option child elements
     * - CorrectAnswer: the correct answer (A, B, C, or D)
     *
     * @param filename the path to the XML file containing questions
     * @return an ArrayList of Question objects parsed from the file
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
                if (questionNode.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                Element questionElement = (Element) questionNode;
                Question question = parseQuestionFromElement(questionElement);
                questions.add(question);
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + filename);
            e.printStackTrace();
        }

        return questions;
    }

    /**
     * Parses a single QuestionItem XML element into a Question object.
     *
     * @param questionElement the XML Element representing a question
     * @return a Question object populated with data from the XML
     */
    private Question parseQuestionFromElement(Element questionElement) {
        Question question = new Question();

        // Parse category
        NodeList catNodes = questionElement.getElementsByTagName("Category");
        if (catNodes.getLength() > 0) {
            question.setCategory(catNodes.item(0).getTextContent().trim());
        }

        // Parse value
        NodeList valueNodes = questionElement.getElementsByTagName("Value");
        if (valueNodes.getLength() > 0) {
            try {
                question.setValue(Integer.parseInt(valueNodes.item(0).getTextContent().trim()));
            } catch (NumberFormatException e) {
                // If value parsing fails, default to 0
            }
        }

        // Parse question text
        NodeList textNodes = questionElement.getElementsByTagName("QuestionText");
        if (textNodes.getLength() > 0) {
            question.setQuestion(textNodes.item(0).getTextContent().trim());
        }

        // Parse options
        parseOptions(questionElement, question);

        // Parse correct answer
        NodeList correctNodes = questionElement.getElementsByTagName("CorrectAnswer");
        if (correctNodes.getLength() > 0 && correctNodes.item(0) != null) {
            question.setCorrectAnswer(correctNodes.item(0).getTextContent().trim());
        }

        return question;
    }

    /**
     * Parses the Options element and its child Option elements, setting them on the Question.
     *
     * @param questionElement the XML Element containing the Options element
     * @param question the Question object to set options on
     */
    private void parseOptions(Element questionElement, Question question) {
        ArrayList<String> optionsList = new ArrayList<>();
        NodeList optionsNodes = questionElement.getElementsByTagName("Options");

        if (optionsNodes.getLength() > 0) {
            Element optionsElement = (Element) optionsNodes.item(0);
            NodeList optionChildren = optionsElement.getChildNodes();

            for (int j = 0; j < optionChildren.getLength(); j++) {
                Node optNode = optionChildren.item(j);
                if (optNode.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                String text = optNode.getTextContent();
                if (text != null) {
                    optionsList.add(text.trim());
                }
            }
        }

        if (!optionsList.isEmpty()) {
            String[] optionsArray = optionsList.toArray(new String[0]);
            question.setOptions(optionsArray);
        }
    }

}
