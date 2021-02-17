package screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import utilities.screens.ApplicationScreen;

public class MySQLConnectionErrorScreen implements ApplicationScreen {

    private Scene mySQLConnectionErrorScreen;

    @Override
    public Scene display() {

        Button retry = new Button("Retry Connection");

        retry.setOnAction(e -> {
            Main.setWindow(new StartupConnectionScreen());
            Main.startMySQLConnection();
        });

        Label label = new Label("Database ERROR");
        label.setId("title");

        Label info = new Label("Please check your internet connection...");
        info.setFont(new Font(Main.getTitleSize() - 4));

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, info, retry);
        layout.setAlignment(Pos.CENTER);

        mySQLConnectionErrorScreen = new Scene(layout, 1000, 600);

        // CSS
        mySQLConnectionErrorScreen.getStylesheets().add("css/main-style.css");

        return mySQLConnectionErrorScreen;
    }

    @Override
    public String getName() {
        return "MySQLConnectionErrorScreen";
    }
}
