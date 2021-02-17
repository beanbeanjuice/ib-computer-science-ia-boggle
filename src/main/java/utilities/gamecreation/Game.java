package utilities.gamecreation;

import main.Main;
import screens.GameScreen;
import utilities.handlers.BoardCharacter;

import java.util.ArrayList;
import java.util.Stack;

public class Game {

    // THINGS TO SAVE
    private String[][] board; // The 4x4 Boggle Board
    private ArrayList<String> possibleWords; // Keeps the list of possible words that can be made from the boggle board.
    private ArrayList<String> inputWords; // Keeps the list of correct words input
    private int score; // Keeps the score
    private int timeLeft = 0;

    // THINGS NEEDED TO FUNCTION
    private String characterBuilder = ""; // Creates the "string" that will be built from.
    private boolean[][] spotsTaken; // Keeps the 2D array of spots taken.
    private boolean wordStarted; // Checks if a word is "being created"
    private Stack<BoardCharacter> characters; // A stack of the "CharacterHandler" class.
    private boolean hasPopped = false;
    private final Timer timer;
    private final GameScreen gameScreen;
    private BoardCharacter poppedCharacter;

    public Game(GameScreen gameScreen) {
        board = Main.getBoardHandler().randomize(); // Creates a new board.
        inputWords = new ArrayList<>(); // Instantiates the inputWords ArrayList
        characters = new Stack<>(); // Instantiates the characters Stack
        spotsTaken = new boolean[4][4]; // Instantiates the spotsTaken 2D array.
        possibleWords = new Search().getPossibleWords(this); // Generates the possible words and saves it.
        timer = new Timer(this);
        timer.start();
        this.gameScreen = gameScreen;
    }

    public PreviousGame save() {
        return new PreviousGame(Main.getGameFileHandler().getFromDataBase().size() + 1, board, score, (Main.getTimeLimit() * 60) - timeLeft, Main.getTimeLimit() * 60, inputWords.size(), possibleWords.size(), inputWords, possibleWords);
    }

    // METHOD FOR INCREASING SCORE AS PER THE RULES OF BOGGLE
    private void addScore(String word) {
        switch (word.length()) {
            case 3:
            case 4:
                score += 1;
                break;
            case 5: score += 2;
                break;
            case 6: score += 3;
                break;
            case 7: score += 4;
                break;
            default: score += 11;
                break;
        }

        gameScreen.setScoreAmount(score);
    }

    // BOARD GETTER
    public String[][] getBoard() {
        return board;
    }

    // POSSIBLE WORDS GETTER
    public ArrayList<String> getPossibleWords() {
        return possibleWords;
    }

    // INPUT WORDS GETTER
    public ArrayList<String> getInputWords() {
        return inputWords;
    }

    // GETS CURRENT WORD BEING "BUILT"
    public String getCharacterBuild() {
        return characterBuilder;
    }

    // ADDS A LETTER TO THE WORD BEING BUILD
    public void addCharacterBuild(String string, int x, int y) {
        characterBuilder += string; // Adds the "string" character to the "characterbuilder" string.
        if (!wordStarted) { // If the word has not started being built, then start it.
            wordStarted = true;
        }
        characters.push(new BoardCharacter(x, y, string)); // Push a new board character onto the stack.
        spotsTaken[x][y] = true; // Make sure that spot is taken.
    }

    // MAKES SURE THAT THE BUTTON CHOSEN CAN BE CHOSEN AS PER BOGGLE RULES
    public boolean compareSpots(int x, int y) {

        if (!wordStarted) { // If the word has not yet started to be built, return true, allowing the word to start being made.
            return true;
        }

        if (!spotsTaken[x][y]) { // Makes sure the user can only choose boxes that are up, left, down, right, and diagonal to itself by 1 place.
            return Math.sqrt(((x - characters.peek().getX())
                    * (x - characters.peek().getX()))
                    + ((y - characters.peek().getY())
                    * (y - characters.peek().getY()))) < 2;
        }

        int currentX = characters.peek().getX();
        int currentY = characters.peek().getY();

        /* Prevents the user from moving their mouse around inside a single button
        and thus allowing the same button to be chosen. */
        if (x == currentX && y == currentY) {
            return false;
        }

        poppedCharacter = characters.pop();
        currentX = characters.peek().getX();
        currentY = characters.peek().getY();

        if (x == currentX && y == currentY) {
            hasPopped = true;
            // Remove the last character from the string
            //spotsTaken[currentX][currentY] = false;
            spotsTaken[poppedCharacter.getX()][poppedCharacter.getY()] = false;
            characterBuilder = removeLastCharacter(characterBuilder);
            if (characters.empty()) { // If there are no more characters to pop, the word has no longer "started".
                wordStarted = false;
            }
            return false;
        }

        // If the above if statement fails, add the character back to the stack.
        characters.push(poppedCharacter);
        return false;
    }

    public boolean getHasPopped() {
        return hasPopped;
    }

    public void setHasPopped(boolean input) {
        hasPopped = input;
    }

    // MAKING SURE THE WORD CAN BE CHOSEN
    public boolean compareWord() {
        // If the world being built is in the dictionary, and is not already in the input words ArrayList, add it.
        if (possibleWords.contains(characterBuilder) && !inputWords.contains(characterBuilder)) {
            inputWords.add(characterBuilder);
            addScore(characterBuilder);
            return true;
        }
        return false;
    }

    // Resets the entire process, allowing for another word to be input.
    public void resetCharacterBuild(GameScreen gameScreen) {
        characterBuilder = "";
        spotsTaken = new boolean[4][4];
        wordStarted = false;
        characters = new Stack<>();
        gameScreen.resetAllButtonColors();
    }

    // REMOVES THE LAST CHARACTER IN THE STRING (works with the Stack)
    private String removeLastCharacter(String string) {
        if (string != null && string.length() > 0) {
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }

    // Method for getting the current score
    public int getScore() {
        return score;
    }

    // Sets the time left
    public void setTimeLeft(int time) {
        timeLeft = time;
        gameScreen.setTimeAmount(time);
    }

    // Gets the time left
    public int getTimeLeft() {
        return timeLeft;
    }

    // Gets the current timer.
    public Timer getTimer() {
        return timer;
    }

    // Gets the popped character.
    public BoardCharacter getPoppedCharacter() {
        return poppedCharacter;
    }

    // Gets the size of the current stack
    public int getStackSize() {
        return characters.size();
    }

}
