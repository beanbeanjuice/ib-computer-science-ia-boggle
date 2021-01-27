package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import utilities.gamecreation.PreviousGame;
import utilities.screens.ApplicationScreen;

import java.util.ArrayList;

public class PreviousGameScreen implements ApplicationScreen {

    private Scene previousGameScreen;
    private int gameID;
    private PreviousGame previousGame;

    public PreviousGameScreen(int gameID) {
        this.gameID = gameID;
    }

    @Override
    public Scene display() {
        previousGame = Main.getGameFileHandler().getPreviousGame(gameID);

        //////////////
        // Top Menu //
        //////////////

        // THE "SCORE" PORTION OF THE TOP MENU
        VBox score = new VBox(5); // Creates a new horizontal box
        Label scoreLabel = new Label("Score"); // Creates a new label with the text "score" shown on it.
        scoreLabel.setId("text");
        TextField scoreAmount = new TextField(String.valueOf(previousGame.getScore()));
        scoreAmount.setEditable(false); // Makes the text field not editable.
        scoreAmount.setId("game-box");
        scoreAmount.setMaxWidth(100); // Makes the maximum width of the text field 100 pixels.
        score.getChildren().addAll(scoreLabel, scoreAmount); // Adds the label and text field to the score HBox.
        score.setAlignment(Pos.CENTER); // Centers the HBox.

        // THE "TIME" PORTION OF THE TOP MENU
        HBox time = new HBox(5);
        VBox timeLeft = new VBox(5);
        VBox timeRight = new VBox(5);
        Label timeLeftLabel = new Label("Time Used");
        Label timeRightLabel = new Label("Time Allocated");
        timeLeftLabel.setId("text");
        timeRightLabel.setId("text");
        timeLeftLabel.setFont(new Font(20));
        timeRightLabel.setFont(new Font(20));
        TextField timeLeftAmount = new TextField(String.valueOf(previousGame.getTimeTaken()));
        TextField timeRightAmount = new TextField(String.valueOf(previousGame.getTotalTime()));
        timeLeftAmount.setEditable(false);
        timeRightAmount.setEditable(false);
        timeLeftAmount.setId("game-box");
        timeRightAmount.setId("game-box");
        timeLeftAmount.setMaxWidth(100);
        timeRightAmount.setMaxWidth(100);
        timeLeft.getChildren().addAll(timeLeftLabel, timeLeftAmount);
        timeRight.getChildren().addAll(timeRightLabel, timeRightAmount);
        timeLeft.setAlignment(Pos.CENTER);
        timeRight.setAlignment(Pos.CENTER);
        time.getChildren().addAll(timeLeft, timeRight);
        time.setAlignment(Pos.CENTER);
        time.setPadding(new Insets(0, 0, 50, 0));

        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////

        ////////////////
        // Right Menu //
        ////////////////
        VBox rightMenu = new VBox(5);
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            Main.setWindow(new PreviousGamesScreen());
        }); // Lambda Statement to run the code within to display the startscreen if the reset button is clicked.
        backButton.setAlignment(Pos.CENTER);
        rightMenu.getChildren().add(backButton);
        rightMenu.setAlignment(Pos.CENTER);

        ///////////////
        // Left Menu //
        ///////////////
        HBox leftMenu = new HBox(20);
        VBox foundWordsVBox = new VBox(20);
        VBox totalWordsVBox = new VBox(20);

        VBox foundWords = new VBox(5);
        VBox totalWords = new VBox(5);

        Label foundWordsLabel = new Label("Found Words");
        Label foundWordsCount = new Label("(" + previousGame.getWordsFound() + ")");
        foundWordsLabel.setId("text");
        foundWordsCount.setId("text-no-bold");
        foundWordsCount.setFont(new Font(10));

        foundWords.getChildren().addAll(foundWordsLabel, foundWordsCount);
        foundWords.setAlignment(Pos.CENTER);

        Label totalWordsLabel = new Label("Total Words");
        Label totalWordsCount = new Label("(" + previousGame.getTotalWords() + ")");
        totalWordsLabel.setId("text");
        totalWordsCount.setId("text-no-bold");
        totalWordsCount.setFont(new Font(10));

        totalWords.getChildren().addAll(totalWordsLabel, totalWordsCount);
        totalWords.setAlignment(Pos.CENTER);

        ListView<String> foundWordsListView = new ListView<>();
        ListView<String> totalWordsListView = new ListView<>();

        foundWordsListView.getItems().addAll(previousGame.getFoundWordsArrayList());
        totalWordsListView.getItems().addAll(previousGame.getTotalWordsArrayList());

        foundWordsListView.setMaxWidth(175);
        totalWordsListView.setMaxWidth(175);

        foundWordsVBox.getChildren().addAll(foundWords, foundWordsListView);
        totalWordsVBox.getChildren().addAll(totalWords, totalWordsListView);

        foundWordsVBox.setAlignment(Pos.CENTER);
        totalWordsVBox.setAlignment(Pos.CENTER);

        leftMenu.getChildren().addAll(foundWordsVBox, totalWordsVBox);
        leftMenu.setAlignment(Pos.CENTER);

        VBox leftMenuComboBox = new VBox(15);
        leftMenuComboBox.getChildren().addAll(score, leftMenu);
        leftMenuComboBox.setAlignment(Pos.CENTER);

        /////////////////
        // Center Menu //
        /////////////////
        Button[][] boardButtons = setButtons(new Button[4][4], previousGame.getBoard());
        GridPane boardLayout = createGridPane(new GridPane(), boardButtons);
        boardLayout.setAlignment(Pos.CENTER);
        boardLayout.setVgap(20);
        boardLayout.setHgap(20);
        boardLayout.setPadding(new Insets(10, 0, 0, 0));

        VBox boardComboBox = new VBox(30);
        boardComboBox.getChildren().addAll(time, boardLayout);
        boardComboBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();

        // PUTS THE PREVIOUS BOXES/PANES INTO A NEW PANE ALTOGETHER.
        borderPane.setRight(rightMenu);
        borderPane.setCenter(boardComboBox);
        borderPane.setLeft(leftMenuComboBox);
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        previousGameScreen = new Scene(borderPane, 1000, 600);

        // CSS
        previousGameScreen.getStylesheets().add("css/main-style.css");

        return previousGameScreen;
    }

    @Override
    public String getName() {
        return "PreviousGameScreen";
    }

    private Button[][] setButtons(Button[][] boardButtons, String board) {

        int pos = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // Creates a new button in the 2D array
                boardButtons[i][j] = new Button();

                // Gets the style for "button-board" from CSS.
                boardButtons[i][j].setId("button-board");

                boardButtons[i][j].setText(previousGame.getBoardArray()[i][j]);
                boardButtons[i][j].setStyle("-fx-background-color: #D3D3D3");
            }
        }

        return boardButtons;
    }

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
}
