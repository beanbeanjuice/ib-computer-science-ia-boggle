package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.Main;
import utilities.gamecreation.PreviousGame;
import utilities.screens.ApplicationScreen;

import java.util.ArrayList;

public class PreviousGamesScreen implements ApplicationScreen {

    private Scene previousGamesScreen;
    private ArrayList<PreviousGame> previousGames;
    public String name = "PreviousGamesScreen";
    private TableView table = new TableView();

    @Override
    public Scene display() {
        // Getting the previous games from the MySQL database.
        previousGames = Main.getGameFileHandler().getFromDataBase();

        // "Go Back" button - self explanatory
        Button goBack = new Button("Go Back");
        goBack.setOnAction(e -> Main.setWindow(new StartScreen()));

        // Creating the Table of Game Data
        table.setEditable(true);

        TableColumn gameID = new TableColumn("Game ID");
        TableColumn score = new TableColumn("Score");
        TableColumn timeTaken = new TableColumn("Time Taken");
        TableColumn timeAllowed = new TableColumn("Time Allowed");
        TableColumn wordsFound = new TableColumn("Words Found");
        TableColumn totalWords = new TableColumn("Total Words");

        gameID.setCellValueFactory(new PropertyValueFactory<>("gameID"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        timeTaken.setCellValueFactory(new PropertyValueFactory<>("timeTaken"));
        timeAllowed.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        wordsFound.setCellValueFactory(new PropertyValueFactory<>("wordsFound"));
        totalWords.setCellValueFactory(new PropertyValueFactory<>("totalWords"));

        gameID.setId("table-text");
        score.setId("table-text");
        timeTaken.setId("table-text");
        timeAllowed.setId("table-text");
        wordsFound.setId("table-text");
        totalWords.setId("table-text");

        // Prevent the table from being reordered.
        gameID.impl_setReorderable(false);
        score.impl_setReorderable(false);
        timeTaken.impl_setReorderable(false);
        timeAllowed.impl_setReorderable(false);
        wordsFound.impl_setReorderable(false);
        totalWords.impl_setReorderable(false);

        gameID.setPrefWidth(100);
        score.setPrefWidth(90);
        timeTaken.setPrefWidth(120);
        timeAllowed.setPrefWidth(120);
        wordsFound.setPrefWidth(120);
        totalWords.setPrefWidth(120);

        table.getColumns().addAll(gameID, score, timeTaken, timeAllowed, wordsFound, totalWords);
        table.setId("table-text");

        for (PreviousGame previousGame : previousGames) {
            table.getItems().add(previousGame);
        }

        // Self explanatory label
        Label label = new Label("Previous Games");
        label.setId("title");
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(label, table, goBack);
        layout.setAlignment(Pos.CENTER);
        // Put the GridPane in a ScrollPane
        // Put the ScrollPane in a VBox

        previousGamesScreen = new Scene(layout, 1000, 600);

        // CSS
        previousGamesScreen.getStylesheets().add("css/previous-games-style.css");

        return previousGamesScreen;
    }

    @Override
    public String getName() {
        return "PreviousGamesScreen";
    }
}
