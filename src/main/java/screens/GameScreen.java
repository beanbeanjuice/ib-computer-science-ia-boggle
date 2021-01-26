package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import utilities.gamecreation.Game;
import utilities.screens.ApplicationScreen;

public class GameScreen implements ApplicationScreen {

    private Game game;
    private Scene gameScreen;
    static TextField letterBuild;
    static TextField scoreAmount;
    static TextField timeAmount;
    static Button[][] boardButtons;

    @Override
    public Scene display() {
        game = new Game(this);

        //////////////
        // Top Menu //
        //////////////

        // THE "SCORE" PORTION OF THE TOP MENU
        HBox score = new HBox(20); // Creates a new horizontal box
        Label scoreLabel = new Label("Score"); // Creates a new label with the text "score" shown on it.
        scoreLabel.setFont(new Font(20)); // Makes the font of the label size 20.
        scoreAmount = new TextField(""); // Creates a new empty text field.
        scoreAmount.setEditable(false); // Makes the text field not editable.
        scoreAmount.setFont(new Font(20));
        scoreAmount.setMaxWidth(100); // Makes the maximum width of the text field 100 pixels.
        score.getChildren().addAll(scoreLabel, scoreAmount); // Adds the label and text field to the score HBox.
        score.setAlignment(Pos.CENTER); // Centers the HBox.

        // THE "TIME" PORTION OF THE TOP MENU
        HBox timeLeft = new HBox(20);
        Label timeLabel = new Label("Time Left");
        timeLabel.setFont(new Font(20));
        timeAmount = new TextField("");
        timeAmount.setEditable(false);
        timeAmount.setFont(new Font(20));
        timeAmount.setMaxWidth(100);
        timeLeft.getChildren().addAll(timeLabel, timeAmount);
        timeLeft.setAlignment(Pos.CENTER);

        // CREATION OF THE TOPMENU TO THEN BE PUT INTO THE OUTER BORDERPANE
        BorderPane topMenu = new BorderPane();
        topMenu.setLeft(score);
        topMenu.setRight(timeLeft);
        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////

        ////////////////
        // Right Menu //
        ////////////////
        VBox rightMenu = new VBox(20);
        Label totalWords = new Label("Total Words");
        totalWords.setFont(new Font(20));
        Label wordsOutOfTotal = new Label(game.getInputWords().size() + "/" + game.getPossibleWords().size()); // Input Words/Possible Words
        Button testButton = new Button("Reset"); // DEBUGGING - Reset Button
        testButton.setOnAction(e -> {
            Main.saveGame();
            Main.setWindow(new StartScreen());
        }); // Lambda Statement to run the code within to display the startscreen if the reset button is clicked.
        rightMenu.getChildren().addAll(totalWords, wordsOutOfTotal, testButton);
        rightMenu.setAlignment(Pos.CENTER);

        ///////////////
        // Left Menu //
        ///////////////
        VBox leftMenu = new VBox(20);
        Label previousWordsLabel = new Label("Previous words");
        previousWordsLabel.setFont(new Font(20));
        ListView<String> previousWords = new ListView<>(); // Shows the inputWords arrayList from the "Game" class.
        previousWords.setMaxWidth(175);
        previousWords.getItems().addAll(game.getInputWords()); // Should be INPUT WORDS instead. Make sure to change this eventually.
        leftMenu.getChildren().addAll(previousWordsLabel, previousWords);
        leftMenu.setAlignment(Pos.CENTER);

        /////////////////
        // Bottom Menu //
        /////////////////
        GridPane bottomMenu = new GridPane();
        Label currentWord = new Label("Current Word:");
        currentWord.setFont(new Font(20));

        letterBuild = new TextField("");
        letterBuild.setFont(new Font(20));
        letterBuild.setEditable(false);

        /*
        Eventually, it should be a mouse click and drag and let go situation.
        The user should not have to click each individual letter.
        The user should not have to click submit at the end.
        */

        // TODO: Eventually remove the submit button
        Button submit = new Button("Submit");
        submit.setOnAction(e -> { // Lambda statement for what the button should do.
            if (game.compareWord()) {
                previousWords.getItems().setAll(game.getInputWords());
            }
            wordsOutOfTotal.setText(game.getInputWords().size() + "/" + game.getPossibleWords().size());
            letterBuild.setText("");
            game.resetCharacterBuild(this); // Resets the characterbuilder and board after each submission.

        });
        bottomMenu.setPadding(new Insets(8, 8, 8, 8)); // Padding from the sides of the window.
        bottomMenu.setVgap(8); // Vertical Gap
        bottomMenu.setHgap(8); // Horizontal Gap
        GridPane.setConstraints(currentWord, 0, 0); // Column, Row
        GridPane.setConstraints(letterBuild, 1, 0);
        GridPane.setConstraints(submit, 0, 1);
        bottomMenu.getChildren().addAll(currentWord, letterBuild, submit);
        bottomMenu.setAlignment(Pos.CENTER);

        /////////////////
        // Center Menu //
        /////////////////
        boardButtons = setButtons(new Button[4][4], game.getBoard());
        GridPane boardLayout = createGridPane(new GridPane(), boardButtons);
        boardLayout.setAlignment(Pos.CENTER);
        // TODO: Currently, accidental button presses are present because the buttons are not far enough apart.
        boardLayout.setVgap(20);
        boardLayout.setHgap(20);

        BorderPane borderPane = new BorderPane();

        // PUTS THE PREVIOUS BOXES/PANES INTO A NEW PANE ALTOGETHER.
        borderPane.setTop(topMenu);
        borderPane.setRight(rightMenu);
        borderPane.setCenter(boardLayout);
        borderPane.setLeft(leftMenu);
        borderPane.setBottom(bottomMenu);
        gameScreen = new Scene(borderPane, 1000, 600);

        // MOUSE DRAGGED STUFF
        gameScreen.addEventFilter(MouseEvent.DRAG_DETECTED, event -> gameScreen.startFullDrag());
        gameScreen.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (game.compareWord()) {
                previousWords.getItems().setAll(game.getInputWords());
            }
            wordsOutOfTotal.setText(game.getInputWords().size() + "/" + game.getPossibleWords().size());
            letterBuild.setText("");
            game.resetCharacterBuild(this); // Resets the characterbuilder and board after each submission.
        });
        // TODO: Button dragging is a little wonky.
        // TODO: Detect that when the user lets go after a drag, submit the word.

        return gameScreen;
    }

    // SETS UP THE BUTTONS TO BE PLACED IN THE 2D GRIDPANE ARRAY
    private Button[][] setButtons(Button[][] boardButtons, String[][] board) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                // Makes a 2D array of Buttons
                // Allows for expandability (4x4, 6x6, 8x8, etc in the future)
                boardButtons[i][j] = new Button(board[i][j]);
                int finalI = i;
                int finalJ = j;

                boardButtons[i][j].setOnMouseDragEntered(event -> {
                    String letter = game.getBoard()[finalI][finalJ];

                    if (game.compareSpots(finalI, finalJ)) {
                        game.addCharacterBuild(letter, finalI, finalJ);

                        // Set colors according to rules of boggle. Sets it blue if it works, white if not.
                        boardButtons[finalI][finalJ].setStyle("-fx-background-color: #ADD8E6");
                    } else if (game.getHasPopped()) {
                        boardButtons[game.getPoppedCharacter().getX()][game.getPoppedCharacter().getY()].setStyle("-fx-background-color: #D3D3D3");
                        game.setHasPopped(false);
                    }

                    letterBuild.setText(game.getCharacterBuild()); // Sets the "output" to the current String build
                });

                // BUTTON LOOK CUSTOMIZATION
                boardButtons[i][j].setFont(new Font(24));

                // Sets every button to a fixed size to keep everything even.
                boardButtons[i][j].setMaxWidth(80);
                boardButtons[i][j].setMinWidth(80);
                boardButtons[i][j].setMaxHeight(80);
                boardButtons[i][j].setMinHeight(80);

                // Sets the background color of the button to a neutral color.
                boardButtons[i][j].setStyle("-fx-background-color: #D3D3D3");
            }
        }

        // Returns the board of buttons
        return boardButtons;
    }

    // CREATES A GRIDPANE OF BUTTONS
    private GridPane createGridPane(GridPane boardLayout, Button[][] boardButtons) {
        boardLayout.setPadding(new Insets(8, 8, 8, 8));
        boardLayout.setVgap(8);
        boardLayout.setHgap(8);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                GridPane.setConstraints(boardButtons[i][j], j, i);
                boardLayout.getChildren().add(boardButtons[i][j]);
            }
        }

        return boardLayout;
    }

    // RESETS THE BUTTON COLORS ONCE SUBMITTED
    public void resetAllButtonColors() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boardButtons[i][j].setStyle("-fx-background-color: #D3D3D3");
            }
        }
    }

    // GETS THE SCORE FROM THE "GAME" CLASS
    public void setScoreAmount(int score) {
        scoreAmount.setText(Integer.toString(score));
    }

    // GETS THE REMAINING TIME FROM THE "GAME" CLASS
    public void setTimeAmount(int time) {
        timeAmount.setText(Integer.toString(time));
    }

    @Override
    public String getName() {
        return "GameScreen";
    }

    @Override
    public Game getGame() {
        return game;
    }


}
