package screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import utilities.screens.ApplicationScreen;

public class RulesScreen implements ApplicationScreen {

    private Scene rulesScreen;

    @Override
    public Scene display() {
        Button goBack = new Button("Go Back");
        goBack.setFont(new Font(12));
        goBack.setOnAction(e -> Main.setWindow(new StartScreen()));

        Label label = new Label("Rules");
        label.setFont(new Font(Main.getTitleSize()));

        // VBOX FOR RULES
        VBox rules = new VBox(1);
        Label rule1 = new Label("1. Minimum of 3 letters.");
        rule1.setFont(new Font(Main.getTitleSize() - 4));
        Label rule2 = new Label("2. Letters must be touching. (Horizontally, Vertically, or Diagonally)");
        rule2.setFont(new Font(Main.getTitleSize() - 4));
        rules.getChildren().addAll(rule1, rule2);
        rules.setAlignment(Pos.CENTER);

        // TODO: Implement example gifs/pictures.

        // FULL VBOX
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, rules, goBack);
        layout.setAlignment(Pos.CENTER);

        rulesScreen = new Scene(layout, 1000, 600);

        return rulesScreen;
    }

    @Override
    public String getName() {
        return "RulesScreen";
    }
}
