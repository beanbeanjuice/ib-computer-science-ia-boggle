package screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.Main;
import utilities.screens.ApplicationScreen;

public class RulesScreen implements ApplicationScreen {

    private Scene rulesScreen;

    @Override
    public Scene display() {
        Button goBack = new Button("Go Back");
        goBack.setOnAction(e -> Main.setWindow(new StartScreen()));

        Label label = new Label("Rules");
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, goBack);
        layout.setAlignment(Pos.CENTER);

        rulesScreen = new Scene(layout, 1000, 600);

        return rulesScreen;
    }

    @Override
    public String getName() {
        return "RulesScreen";
    }
}
