package screens;

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

        gameID.setPrefWidth(60);
        score.setPrefWidth(100);
        timeTaken.setPrefWidth(100);
        timeAllowed.setPrefWidth(100);
        wordsFound.setPrefWidth(100);
        totalWords.setPrefWidth(100);

        table.getColumns().addAll(gameID, score, timeTaken, timeAllowed, wordsFound, totalWords);

        for (PreviousGame previousGame : previousGames) {
            table.getItems().add(previousGame);
        }

        // Self explanatory label
        Label label = new Label("Previous Games");
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, table, goBack);
        layout.setAlignment(Pos.CENTER);
        // Put the GridPane in a ScrollPane
        // Put the ScrollPane in a VBox

        previousGamesScreen = new Scene(layout, 1000, 600);

        return previousGamesScreen;
    }

    @Override
    public String getName() {
        return "PreviousGamesScreen";
    }
}
