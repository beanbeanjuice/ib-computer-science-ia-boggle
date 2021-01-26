package screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.Main;
import utilities.screens.ApplicationScreen;

import java.sql.SQLException;

public class MySQLConnectionErrorScreen implements ApplicationScreen {

    private Scene mySQLConnectionErrorScreen;

    @Override
    public Scene display() {

        Button retry = new Button("Retry Database Connection");

        retry.setOnAction(e -> {
            try {
                Main.startMySQLConnection();
                Main.setWindow(new StartScreen());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        javafx.scene.control.Label label = new Label("Database ERROR");
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, retry);
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
