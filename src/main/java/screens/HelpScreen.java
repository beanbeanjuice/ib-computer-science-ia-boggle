package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import main.Main;
import utilities.handlers.FileHandler;
import utilities.screens.ApplicationScreen;

public class HelpScreen implements ApplicationScreen {

    private Scene helpScreen;

    @Override
    public Scene display() {

        // Video to be used for graphical help
        Media media = new Media(FileHandler.getResourceAsFile("HelpScreen/gamehelp.mp4").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView();
        mediaView.setMediaPlayer(mediaPlayer);

        // Video Properties
        mediaView.setFitHeight(480);
        mediaView.setFitWidth(720);
        mediaView.autosize();
        mediaPlayer.setMute(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        Button goBack = new Button("Go Back");
        goBack.setOnAction(e -> {
            Main.setWindow(new StartScreen());
            mediaPlayer.stop();
        });

        // Scene layout
        Label label = new Label("Help");
        label.setId("title");
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, mediaView, goBack);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));

        helpScreen = new Scene(layout, 1000, 600);

        // CSS
        helpScreen.getStylesheets().add("css/main-style.css");

        return helpScreen;
    }

    @Override
    public String getName() {
        return "HelpScreen";
    }
}
