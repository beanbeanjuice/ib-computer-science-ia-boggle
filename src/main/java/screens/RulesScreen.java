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
        label.setId("title");
        label.setFont(new Font(Main.getTitleSize()));

        // VBOX FOR RULES
        VBox rules = new VBox(1);
        Label rule1 = new Label("1. Minimum of 3 letters.");
        Label rule2 = new Label("2. Letters must be touching. (Horizontally, Vertically, or Diagonally)");
        Label rule3 = new Label("3. Multiple meanings of the same spelling do not count. (E.g., \"r-i-p\" only counts once.)");
        Label rule4 = new Label("4. The same word found in a different area does not count.");
        Label rule5 = new Label("5. Full credit is given for both singular and plural forms of words.");
        rule1.setFont(new Font(Main.getTitleSize() - 4));
        rule2.setFont(new Font(Main.getTitleSize() - 4));
        rule3.setFont(new Font(Main.getTitleSize() - 4));
        rule4.setFont(new Font(Main.getTitleSize() - 4));
        rule5.setFont(new Font(Main.getTitleSize() - 4));
        rules.getChildren().addAll(rule1, rule2, rule3, rule4, rule5);
        rules.setAlignment(Pos.CENTER);

        // FULL VBOX
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, rules, goBack);
        layout.setAlignment(Pos.CENTER);

        rulesScreen = new Scene(layout, 1000, 600);

        // CSS
        rulesScreen.getStylesheets().add("css/main-style.css");

        return rulesScreen;
    }

    @Override
    public String getName() {
        return "RulesScreen";
    }
}
