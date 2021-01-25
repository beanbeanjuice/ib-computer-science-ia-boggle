package screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import utilities.boxes.SettingsBox;
import utilities.screens.ApplicationScreen;

public class StartScreen implements ApplicationScreen {

    private Scene startScene;

    @Override
    public Scene display() {
        // TITLE
        Label label1 = new Label("Boggle: The Game");
        label1.setFont(new Font(50));

        int buttonWidth = Main.getButtonWidth();
        int buttonTextSize = Main.getButtonTextSize();

        // START GAME BUTTON
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> Main.setWindow(new GameScreen()));
        startButton.setMaxWidth(buttonWidth);
        startButton.setMinWidth(buttonWidth);
        startButton.setFont(new Font(buttonTextSize));

        // RULES
        Button rulesButton = new Button("Rules");
        rulesButton.setOnAction(e -> Main.setWindow(new RulesScreen()));
        rulesButton.setMaxWidth(buttonWidth);
        rulesButton.setMinWidth(buttonWidth);
        rulesButton.setFont(new Font(buttonTextSize));

        // PREVIOUS GAMES
        Button previousGamesButton = new Button("Previous Games");
        previousGamesButton.setOnAction(e -> Main.setWindow(new PreviousGamesScreen()));
        previousGamesButton.setMaxWidth(buttonWidth);
        previousGamesButton.setMinWidth(buttonWidth);
        previousGamesButton.setFont(new Font(buttonTextSize));

        // SETTINGS
        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> new SettingsBox("Settings", "Boggle Settings").display());
        settingsButton.setMaxWidth(buttonWidth);
        settingsButton.setMinWidth(buttonWidth);
        settingsButton.setFont(new Font(buttonTextSize));

        // HELP
        Button helpButton = new Button("Help");
        helpButton.setOnAction(e -> Main.setWindow(new HelpScreen()));
        helpButton.setMaxWidth(buttonWidth);
        helpButton.setMinWidth(buttonWidth);
        helpButton.setFont(new Font(buttonTextSize));

        // CLOSE BUTTON
        Button closeButton = new Button("Save and Quit");
        closeButton.setOnAction(e -> Main.closeProgram());
        closeButton.setMaxWidth(buttonWidth);
        closeButton.setMinWidth(buttonWidth);
        closeButton.setFont(new Font(buttonTextSize));

        // Layout 1 - children are laid out in a vertical column
        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(label1, startButton, rulesButton, previousGamesButton, settingsButton, closeButton);
        layout1.setAlignment(Pos.CENTER);
        startScene = new Scene(layout1, 700, 700);

        return startScene;
    }

    @Override
    public String getName() {
        return "startScene";
    }
}
