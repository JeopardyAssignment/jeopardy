package com.jeopardy.game;

import com.jeopardy.Client;
import com.jeopardy.question.Question;
import com.jeopardy.question.QuestionService;
import com.jeopardy.question.loader.QuestionLoader;
import com.jeopardy.question.loader.CSVQuestionLoader;
import com.jeopardy.question.loader.JSONQuestionLoader;
import com.jeopardy.question.loader.XMLQuestionLoader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * GameState maintains the current state of a Jeopardy game session.
 *
 * This class tracks all mutable game state including:
 * - Current turn number
 * - List of players participating in the game
 * - Question service for managing game questions
 * - Current category selection
 * - Current question selection
 *
 * The GameState provides methods for:
 * - Accessing and modifying player list
 * - Determining the current player based on turn rotation
 * - Tracking turn progression
 * - Managing category and question selection
 * - Loading questions from various file formats
 */
public class GameState {

    private int currentTurn;
    private ArrayList<Player> players;
    private QuestionService questionService;
    private String currentCategory;
    private Question currentQuestion;
    private final Map<Integer, QuestionLoader> loaderRegistry;

    /**
     * Constructs a new GameState with default initial values.
     * Initializes an empty player list and turn counter at 0.
     * Registers available question loaders for the Strategy pattern.
     */
    public GameState() {
        this.players = new ArrayList<>();
        this.currentTurn = 0;
        
        // Register question loaders (Open/Closed Principle)
        this.loaderRegistry = new HashMap<>();
        this.loaderRegistry.put(1, new CSVQuestionLoader());
        this.loaderRegistry.put(2, new JSONQuestionLoader());
        this.loaderRegistry.put(3, new XMLQuestionLoader());
    }

    /**
     * Gets the current turn number.
     *
     * @return the current turn number
     */
    public int getCurrentTurn() {
        return this.currentTurn;
    }

    /**
     * Gets the list of players in the game.
     *
     * @return an ArrayList of Player objects
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Sets the list of players for the game.
     */
    public void setPlayers(Scanner scanner) {
        // Get the number of players
        int playerCount = 0;

        do {
            System.out.print("How many players (1-4)? ");
            playerCount = scanner.nextInt();
            scanner.nextLine(); // Consume newline after nextInt()

            if (playerCount < 1 || playerCount > 4) {
                System.out.println("[Error]: Invalid number of players. Must be between 1 and 4 (inclusive).");
            }
        } while (playerCount < 1 || playerCount > 4);

        // Set the player names
        System.out.println("Please provide player names.");
        for (int i = 0; i < playerCount; i++) {
            String playerName;
            do {
                System.out.print(String.format("Player %d: ", i + 1));
                playerName = scanner.nextLine();

                if (playerName.trim().isEmpty()) {
                    System.out.println("[Error]: Invalid name. Must be at least 1 character long.");
                }
            } while (playerName.trim().isEmpty());

            // Add player to list
            Player newPlayer = new Player(playerName.trim());
            this.players.add(newPlayer);
        }
    }

    /**
     * Gets the player whose turn it currently is.
     *
     * Uses modulo arithmetic to rotate through players based on turn number.
     * Returns null if no players are in the game.
     *
     * @return the Player object for the current turn, or null if no players exist
     */
    public Player getCurrentPlayer() {
        if (this.players == null || this.players.isEmpty()) {
            return null;
        }
        int playerIndex = this.currentTurn % this.players.size();
        return this.players.get(playerIndex);
    }

    /**
     * Gets the question service associated with this game state.
     *
     * @return the QuestionService instance
     */
    public QuestionService getQuestionService() {
        return this.questionService;
    }

    /**
     * Sets the question service for this game state by loading questions from a file.
     * Uses a registry-based approach to select the appropriate loader, adhering to OCP.
     *
     * @param scanner the Scanner instance to use for input
     */
    public boolean setQuestionService(Scanner scanner) {
        String[] options = {"CSV", "JSON", "XML"};
        int optionIndex = Client.prompt("What file type do you wish to load the game data with? ", options, scanner);

        int arrayIndex = optionIndex - 1;
        String fileName = String.format("data/sample_game_%s.%s", options[arrayIndex], options[arrayIndex].toLowerCase());

        QuestionLoader questionLoader = loaderRegistry.get(optionIndex);

        this.questionService = new QuestionService();
        return this.questionService.setQuestions(questionLoader, fileName);
    }

    /**
     * Advances to the next turn.
     */
    public void nextTurn() {
        this.currentTurn++;
    }

    /**
     * Gets the currently selected category.
     *
     * @return the current category name, or null if no category is selected
     */
    public String getCurrentCategory() {
        return this.currentCategory;
    }

    /**
     * Prompts the user to select a category from available categories.
     * Uses the Client.prompt() method to display options and validate input.
     *
     * @param scanner the Scanner instance to use for input
     * @return the selected category name
     */
    public String setCurrentCategory(Scanner scanner) {
        ArrayList<String> categories = this.questionService.getCategories();
        String[] categoryArray = categories.toArray(new String[0]);

        int categoryIndex = Client.prompt("What category of question? ", categoryArray, scanner);

        String selectedCategory = categoryArray[categoryIndex - 1];
        this.currentCategory = selectedCategory;

        return selectedCategory;
    }

    /**
     * Gets all unique categories from unanswered questions.
     *
     * @return an ArrayList of unique category names from unanswered questions
     */
    public ArrayList<String> getCategories() {
        return this.questionService.getCategories();
    }

    /**
     * Gets the currently selected question.
     *
     * @return the current Question object, or null if no question is selected
     */
    public Question getCurrentQuestion() {
        return this.currentQuestion;
    }

    /**
     * Prompts the user to select a question from available questions in the current category.
     * Uses the Client.prompt() method to display options and validate input.
     *
     * @param scanner the Scanner instance to use for input
     * @return the selected Question object
     */
    public Question setCurrentQuestion(Scanner scanner) {
        ArrayList<Integer> questionValues = this.questionService.getCategoryQuestionValues(this.currentCategory);
        String[] questionValueArray = new String[questionValues.size()];

        for (int i = 0; i < questionValues.size(); i++) {
            questionValueArray[i] = String.valueOf(questionValues.get(i));
        }

        System.out.println(String.format("=== %s's Turn (Score %s) ===", getCurrentPlayer().getId(), getCurrentPlayer().getCurrentScore()));
        int questionValueIndex = Client.prompt("What question value? ", questionValueArray, scanner) - 1;

        Question selectedQuestion = this.questionService.getCategoryQuestionByValue(this.currentCategory, questionValues.get(questionValueIndex));
        this.currentQuestion = selectedQuestion;

        return selectedQuestion;
    }
}
