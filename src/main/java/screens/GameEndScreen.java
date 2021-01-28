package screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import utilities.screens.ApplicationScreen;

public class GameEndScreen implements ApplicationScreen {

    private Scene gameEndScreen;

    @Override
    public Scene display() {
        Button goBack = new Button("Back to Start");
        goBack.setFont(new Font(12));
        goBack.setOnAction(e -> Main.setWindow(new StartScreen()));

        Label label = new Label("Game Over");
        label.setId("title");
        label.setFont(new Font(Main.getTitleSize()));

        // VBOX FOR RULES
        VBox rules = new VBox(1);
        Label rule1 = new Label("You have run out of time...");
        Label rule2 = new Label("The game has ended.");
        rule1.setFont(new Font(Main.getTitleSize() - 4));
        rule2.setFont(new Font(Main.getTitleSize() - 4));
        rules.getChildren().addAll(rule1, rule2);
        rules.setAlignment(Pos.CENTER);

        // TODO: Implement example gifs/pictures.

        // FULL VBOX
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, rules, goBack);
        layout.setAlignment(Pos.CENTER);

        gameEndScreen = new Scene(layout, 1000, 600);

        // CSS
        gameEndScreen.getStylesheets().add("css/main-style.css");

        return gameEndScreen;
    }

    @Override
    public String getName() {
        return "GameEndScreen";
    }
}