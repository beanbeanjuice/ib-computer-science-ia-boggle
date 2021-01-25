package utilities.boxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

public class ConfirmationBox {

    private String title;
    private String message;
    private boolean answer;

    public ConfirmationBox(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public boolean display() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); // Block input events from other windows in the application until this one is taken care of
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        int buttonWidth = Main.getButtonWidth();
        int buttonTextSize = Main.getButtonTextSize();

        // Create two buttons
        Button yesButton = new Button("Yes");
        yesButton.setMaxWidth(buttonWidth);
        yesButton.setMinWidth(buttonWidth);
        yesButton.setFont(new Font(buttonTextSize));

        Button noButton = new Button("No");
        noButton.setMaxWidth(buttonWidth);
        noButton.setMinWidth(buttonWidth);
        noButton.setFont(new Font(buttonTextSize));

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER); // Centers everything
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); // Display it, but needs to be closed before it can be returned to the original window.

        return answer;
    }

    public String getName() {
        return "ConfirmationBox";
    }
}
