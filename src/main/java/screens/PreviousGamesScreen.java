package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.Main;
import utilities.gamecreation.PreviousGame;
import utilities.screens.ApplicationScreen;

import java.util.ArrayList;

public class PreviousGamesScreen implements ApplicationScreen {

    private Scene previousGamesScreen;
    private ArrayList<PreviousGame> previousGames;
    private final TableView<PreviousGame> table = new TableView<>();

    @Override
    public Scene display() {
        // Getting the previous games from the MySQL database.
        previousGames = Main.getGameFileHandler().getFromDataBase();

        // "Go Back" button - self explanatory
        Button goBack = new Button("Go Back");
        goBack.setOnAction(e -> Main.setWindow(new StartScreen()));

        // Creating the Table of Game Data
        table.setEditable(true);

        table.setRowFactory(e -> {
            TableRow<PreviousGame> tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!tableRow.isEmpty())) {
                    PreviousGame previousGame = tableRow.getItem();
                    Main.setWindow(new PreviousGameScreen(previousGame.getGameID()));
                }
            });
            return tableRow;
        });

        TableColumn gameID = new TableColumn("Game ID");
        TableColumn score = new TableColumn("Score");
        TableColumn timeTaken = new TableColumn("Time Taken");
        TableColumn timeAllowed = new TableColumn("Time Allowed");
        TableColumn wordsFound = new TableColumn("Words Found");
        TableColumn totalWords = new TableColumn("Total Words");

        // Reads the variable name from the PreviousGame object.
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

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Self explanatory label
        Label title = new Label("Previous Games");
        title.setId("title");

        Label information = new Label("Double click a row to visit the board.");
        information.setId("information");
        VBox layout = new VBox(20);
        VBox upper = new VBox(5);
        upper.getChildren().addAll(title, information);
        upper.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 80, 10, 80));
        layout.getChildren().addAll(upper, table, goBack);
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
