package screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.Main;
import utilities.screens.ApplicationScreen;

public class PreviousGamesScreen implements ApplicationScreen {

    private Scene previousGamesScreen;

    @Override
    public Scene display() {
        // "Go Back" button - self explanatory
        Button goBack = new Button("Go Back");
        goBack.setOnAction(e -> Main.setWindow(new StartScreen()));

        // Self explanatory label
        Label label = new Label("Previous Games");
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, goBack);
        layout.setAlignment(Pos.CENTER);

        previousGamesScreen = new Scene(layout, 1000, 600);

        return previousGamesScreen;
    }

    @Override
    public String getName() {
        return "PreviousGamesScreen";
    }
}
