package screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import main.Main;
import utilities.screens.ApplicationScreen;

import java.io.File;

public class HelpScreen implements ApplicationScreen {

    private Scene helpScreen;

    @Override
    public Scene display() {
        // GIF Image to be used for graphical help
        Image image = new Image(new File("src/main/resources/HelpScreen/gamehelp.gif").toURI().toString());
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        // Video to be used for graphical help
        Media media = new Media(new File("src/main/resources/HelpScreen/gamehelp.mp4").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView();
        mediaView.setMediaPlayer(mediaPlayer);

        // Video Properties
        mediaView.setFitHeight(480);
        mediaView.setFitWidth(858);
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
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, mediaView, goBack);
        layout.setAlignment(Pos.CENTER);

        helpScreen = new Scene(layout, 1000, 600);

        return helpScreen;
    }

    @Override
    public String getName() {
        return "HelpScreen";
    }
}
