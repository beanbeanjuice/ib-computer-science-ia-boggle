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
        VBox score = new VBox(5); // Creates a new horizontal box
        Label scoreLabel = new Label("Score"); // Creates a new label with the text "score" shown on it.
        scoreLabel.setId("text");
        scoreAmount = new TextField(""); // Creates a new empty text field.
        scoreAmount.setEditable(false); // Makes the text field not editable.
        scoreAmount.setId("game-box");
        scoreAmount.setMaxWidth(100); // Makes the maximum width of the text field 100 pixels.
        score.getChildren().addAll(scoreLabel, scoreAmount); // Adds the label and text field to the score HBox.
        score.setAlignment(Pos.CENTER); // Centers the HBox.
        score.setPadding(new Insets(0, 0, 0, 40));

        // THE "TIME" PORTION OF THE TOP MENU
        VBox timeLeft = new VBox(5);
        Label timeLabel = new Label("Time Left");
        timeLabel.setId("text");
        timeLabel.setFont(new Font(20));
        timeAmount = new TextField("");
        timeAmount.setEditable(false);
        timeAmount.setId("game-box");
        timeAmount.setMaxWidth(100);
        timeLeft.getChildren().addAll(timeLabel, timeAmount);
        timeLeft.setAlignment(Pos.CENTER);
        timeLeft.setPadding(new Insets(0, 40, 0, 0));

        // CREATION OF THE TOPMENU TO THEN BE PUT INTO THE OUTER BORDERPANE
        BorderPane topMenu = new BorderPane();
        topMenu.setLeft(score);
        topMenu.setRight(timeLeft);
        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////

        ////////////////
        // Right Menu //
        ////////////////
        VBox rightMenu = new VBox(5);
        Label totalWords = new Label("Total Words");
        totalWords.setId("text");
        Label wordsOutOfTotal = new Label(game.getInputWords().size() + "/" + game.getPossibleWords().size()); // Input Words/Possible Words
        wordsOutOfTotal.setId("text");
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            Main.saveGame();
            Main.setWindow(new StartScreen());
        }); // Lambda Statement to run the code within to display the startscreen if the reset button is clicked.
        rightMenu.getChildren().addAll(totalWords, wordsOutOfTotal, quitButton);
        rightMenu.setAlignment(Pos.CENTER);

        ///////////////
        // Left Menu //
        ///////////////
        VBox leftMenu = new VBox(20);
        Label previousWordsLabel = new Label("Previous words");
        previousWordsLabel.setId("text");
        ListView<String> previousWords = new ListView<>(); // Shows the inputWords arrayList from the "Game" class.
        previousWords.setMaxWidth(175);
        previousWords.getItems().addAll(game.getInputWords()); // Should be INPUT WORDS instead. Make sure to change this eventually.
        leftMenu.getChildren().addAll(previousWordsLabel, previousWords);
        leftMenu.setAlignment(Pos.CENTER);
        leftMenu.setPadding(new Insets(20, 0, 0, 0));

        /////////////////
        // Center Menu //
        /////////////////
        boardButtons = setButtons(new Button[4][4], game.getBoard());
        GridPane boardLayout = createGridPane(new GridPane(), boardButtons);
        boardLayout.setAlignment(Pos.CENTER);
        boardLayout.setVgap(20);
        boardLayout.setHgap(20);

        ///////////////////////
        // Middle Combo Menu //
        ///////////////////////
        VBox middleCombo = new VBox(20);

        letterBuild = new TextField("");
        letterBuild.setId("text");
        letterBuild.setEditable(false);
        letterBuild.setMaxWidth(200);

        middleCombo.getChildren().addAll(boardLayout, letterBuild);
        middleCombo.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();

        // PUTS THE PREVIOUS BOXES/PANES INTO A NEW PANE ALTOGETHER.
        borderPane.setTop(topMenu);
        borderPane.setRight(rightMenu);
        borderPane.setCenter(middleCombo);
        borderPane.setLeft(leftMenu);
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        gameScreen = new Scene(borderPane, 1000, 600);

        // MOUSE DRAGGED STUFF
        gameScreen.addEventFilter(MouseEvent.DRAG_DETECTED, event -> gameScreen.startFullDrag());
        gameScreen.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (game.compareWord()) {
                previousWords.getItems().setAll(game.getInputWords());
                previousWords.scrollTo(game.getInputWords().size() - 1);
            }
            wordsOutOfTotal.setText(game.getInputWords().size() + "/" + game.getPossibleWords().size());
            letterBuild.setText("");
            game.resetCharacterBuild(this); // Resets the characterbuilder and board after each submission.
            if(game.getInputWords().size() == game.getPossibleWords().size()) {
                Main.saveGame();
                Main.setWindow(new GameEndScreen());
            }
        });

        // CSS
        gameScreen.getStylesheets().add("css/main-style.css");

        return gameScreen;
    }

    // SETS UP THE BUTTONS TO BE PLACED IN THE 2D GRIDPANE ARRAY
    private Button[][] setButtons(Button[][] boardButtons, String[][] board) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                // Makes a 2D array of Buttons
                // Allows for expandability (4x4, 6x6, 8x8, etc in the future)
                boardButtons[i][j] = new Button(board[i][j]);

                // Gets the style for "button-board" from CSS.
                boardButtons[i][j].setId("button-board");

                // This is required so that the button hitboxes are circular.
                boardButtons[i][j].setPickOnBounds(false);
                int finalI = i;
                int finalJ = j;

                boardButtons[i][j].setOnMouseDragEntered(event -> {
                    String letter = game.getBoard()[finalI][finalJ];

                    if (game.compareSpots(finalI, finalJ)) {
                        game.addCharacterBuild(letter, finalI, finalJ);

                        // Set colors according to rules of boggle. Sets it blue if it works, white if not.
                        boardButtons[finalI][finalJ].setStyle("-fx-background-color: " + getColorHex(game.getStackSize()));
                    } else if (game.getHasPopped()) {
                        boardButtons[game.getPoppedCharacter().getX()][game.getPoppedCharacter().getY()].setStyle("-fx-background-color: "
                                + getColorHex(0));
                        game.setHasPopped(false);
                    }

                    letterBuild.setText(game.getCharacterBuild()); // Sets the "output" to the current String build
                });

                // Sets the background color of the button to a neutral color.
                boardButtons[i][j].setStyle("-fx-background-color: " + getColorHex(0));
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
                boardButtons[i][j].setStyle("-fx-background-color: " + getColorHex(0));
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

    public String getColorHex(int amount) {
        switch (amount) {
            case 1: return "#bdf1ff";
            case 2: return "#b0eeff";
            case 3: return "#a1ebff";
            case 4: return "#91e8ff";
            case 5: return "#80e4ff";
            case 6: return "#70e1ff";
            case 7: return "#61deff";
            case 8: return "#4cd2f5";
            case 9: return "#3bc0e3";
            case 10: return "#2cafd1";
            case 11: return "#20a7c9";
            case 12: return "#1d99b8";
            case 13: return "#158fad";
            case 14: return "#0e809c";
            case 15: return "#087e9c";
            case 16: return "#036a85";
            default: return "#D3D3D3";
        }
    }


}
