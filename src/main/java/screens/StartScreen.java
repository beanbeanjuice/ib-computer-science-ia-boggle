package screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import utilities.boxes.SettingsBox;
import utilities.screens.ApplicationScreen;

public class StartScreen implements ApplicationScreen {

    private Scene startScene;

    @Override
    public Scene display() {
        int buttonWidth = Main.getButtonWidth();
        int buttonTextSize = Main.getButtonTextSize();
        // TITLE
        Label label1 = new Label("Boggle: The Game");
        //label1.setFont(fontHandler.getFont(Main.getTitleFont(), 50));
        label1.setId("title");

        // START GAME BUTTON
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> Main.setWindow(new GameScreen()));
        startButton.setMaxWidth(buttonWidth);
        startButton.setMinWidth(buttonWidth);

        // RULES
        Button rulesButton = new Button("Rules");
        rulesButton.setOnAction(e -> Main.setWindow(new RulesScreen()));
        rulesButton.setMaxWidth(buttonWidth);
        rulesButton.setMinWidth(buttonWidth);

        // PREVIOUS GAMES
        Button previousGamesButton = new Button("Previous Games");
        previousGamesButton.setOnAction(e -> Main.setWindow(new PreviousGamesScreen()));
        previousGamesButton.setMaxWidth(buttonWidth);
        previousGamesButton.setMinWidth(buttonWidth);

        // SETTINGS
        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> new SettingsBox("Settings", "Boggle Settings").display());
        settingsButton.setMaxWidth(buttonWidth);
        settingsButton.setMinWidth(buttonWidth);

        // HELP
        Button helpButton = new Button("Help");
        helpButton.setOnAction(e -> Main.setWindow(new HelpScreen()));
        helpButton.setMaxWidth(buttonWidth);
        helpButton.setMinWidth(buttonWidth);

        // CLOSE BUTTON
        Button closeButton = new Button("Save and Quit");
        closeButton.setOnAction(e -> Main.closeProgram());
        closeButton.setMaxWidth(buttonWidth);
        closeButton.setMinWidth(buttonWidth);

        // LOGOUT BUTTON
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> Main.setWindow(new LoginScreen()));
        logoutButton.setMaxWidth(buttonWidth);
        logoutButton.setMinWidth(buttonWidth);

        // COMBINATION OF START AND PREVIOUS GAMES
        HBox combo1 = new HBox(5);
        combo1.getChildren().addAll(startButton, previousGamesButton);
        combo1.setAlignment(Pos.CENTER);

        // COMBINATION OF HELP AND RULES BUTTON
        HBox combo2 = new HBox(5);
        combo2.getChildren().addAll(rulesButton, helpButton);
        combo2.setAlignment(Pos.CENTER);

        // COMBINATION OF SETTINGS AND LOGOUT BUTTON
        HBox combo3 = new HBox(5);
        combo3.getChildren().addAll(settingsButton, logoutButton);
        combo3.setAlignment(Pos.CENTER);

        // Layout 1 - children are laid out in a vertical column
        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(label1, combo1, combo2, combo3, closeButton);
        layout1.setAlignment(Pos.CENTER);
        startScene = new Scene(layout1, 700, 700);

        // CSS
        startScene.getStylesheets().add("css/main-style.css");

        return startScene;
    }

    @Override
    public String getName() {
        return "startScene";
    }
}
