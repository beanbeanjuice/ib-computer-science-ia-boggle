package screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import utilities.screens.ApplicationScreen;

public class StartScreen implements ApplicationScreen {

    @Override
    public Scene display() {
        // TITLE
        Label label1 = new Label("Boggle: The Game");
        label1.setFont(new Font(50));

        // START GAME BUTTON
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> GameLauncher.setWindow(GameScreen.display(), GameScreen.name));
        startButton.setMaxWidth(GameLauncher.buttonWidth);
        startButton.setMinWidth(GameLauncher.buttonWidth);
        startButton.setFont(new Font(GameLauncher.buttonTextSize));

        // RULES
        Button rulesButton = new Button("Rules");
        rulesButton.setOnAction(e -> GameLauncher.setWindow(RulesScreen.display(), RulesScreen.name));
        rulesButton.setMaxWidth(GameLauncher.buttonWidth);
        rulesButton.setMinWidth(GameLauncher.buttonWidth);
        rulesButton.setFont(new Font(GameLauncher.buttonTextSize));

        // PREVIOUS GAMES
        Button previousGamesButton = new Button("Previous Games");
        previousGamesButton.setOnAction(e -> GameLauncher.setWindow(PreviousGamesScreen.display(), PreviousGamesScreen.name));
        previousGamesButton.setMaxWidth(GameLauncher.buttonWidth);
        previousGamesButton.setMinWidth(GameLauncher.buttonWidth);
        previousGamesButton.setFont(new Font(GameLauncher.buttonTextSize));

        // SETTINGS
        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> SettingsBox.display("Settings", "Boggle Settings"));
        settingsButton.setMaxWidth(GameLauncher.buttonWidth);
        settingsButton.setMinWidth(GameLauncher.buttonWidth);
        settingsButton.setFont(new Font(GameLauncher.buttonTextSize));

        // HELP
        Button helpButton = new Button("Help");
        helpButton.setOnAction(e -> GameLauncher.setWindow(HelpScreen.display(), HelpScreen.name));
        helpButton.setMaxWidth(GameLauncher.buttonWidth);
        helpButton.setMinWidth(GameLauncher.buttonWidth);
        helpButton.setFont(new Font(GameLauncher.buttonTextSize));

        // CLOSE BUTTON
        Button closeButton = new Button("Save and Quit");
        closeButton.setOnAction(e -> GameLauncher.closeProgram());
        closeButton.setMaxWidth(GameLauncher.buttonWidth);
        closeButton.setMinWidth(GameLauncher.buttonWidth);
        closeButton.setFont(new Font(GameLauncher.buttonTextSize));

        // Layout 1 - children are laid out in a vertical column
        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(label1, startButton, rulesButton, previousGamesButton, settingsButton, closeButton);
        layout1.setAlignment(Pos.CENTER);
        startScene = new Scene(layout1, 700, 700);

        return startScene;
    }

    @Override
    public String getName() {
        return null;
    }
}
