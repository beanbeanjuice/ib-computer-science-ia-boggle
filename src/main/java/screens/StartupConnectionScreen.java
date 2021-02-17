package screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import utilities.handlers.FileHandler;
import utilities.screens.ApplicationScreen;

public class StartupConnectionScreen implements ApplicationScreen {

    private Scene startupConnectionScreen;

    @Override
    public Scene display() {

        // Image from https://in.pinterest.com/pin/347410558745467884/
        Image image = new Image(FileHandler.getResourceAsFile("LoadingScreen/loading.gif").toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Label title = new Label("Connecting to the database...");
        title.setId("title");
        title.setFont(new Font(Main.getTitleSize()));

        // FULL VBOX
        VBox layout = new VBox(20);
        layout.getChildren().addAll(title, imageView);
        layout.setAlignment(Pos.CENTER);

        startupConnectionScreen = new Scene(layout, 1000, 600);

        // CSS
        startupConnectionScreen.getStylesheets().add("css/main-style.css");

        return startupConnectionScreen;
    }

    @Override
    public String getName() {
        return "StartupConnectionScreen";
    }
}
