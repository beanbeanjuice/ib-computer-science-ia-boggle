package utilities.boxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

public class SettingsBox {

    private String title;
    private String message;

    public SettingsBox(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public void display() {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); // Block input events from other windows in the application
        window.setTitle(title);
        window.setMinWidth(250);

        // Allow incorrect words to be input
        CheckBox allowIncorrect = new CheckBox("Allow Incorrect Words");
        allowIncorrect.setSelected(Main.getAllowIncorrect());

        // Time Settings
        Label time = new Label("Time (in Minutes):");
        ComboBox<String> timeInput = new ComboBox<>();
        timeInput.getItems().addAll(
                "6.0",
                "5.0",
                "4.0",
                "3.0",
                "2.0",
                "1.0"
        );
        timeInput.setEditable(true);
        timeInput.getSelectionModel().select(Double.toString(Main.getTimeLimit()));


        // Allow for Input after Time Ends
        CheckBox ignoreTimeLimit = new CheckBox("Ignore Time Limit");
        ignoreTimeLimit.setSelected(Main.getIgnoreTimeLimit());

        Button saveButton = new Button("Save Settings");
        saveButton.setOnAction(e -> {
            String number = timeInput.getValue();

            if (checkDouble(timeInput, number)) {
                Main.setTimeLimit(Double.parseDouble(timeInput.getValue()));

                Main.setIgnoreTimeLimit(ignoreTimeLimit.isSelected());
                Main.setAllowIncorrect(allowIncorrect.isSelected());
                window.close();
            } else {
                // TODO: Eventually show an error message that says "Only numbers are accepted."
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(time, timeInput, allowIncorrect, ignoreTimeLimit, saveButton);
        layout.setAlignment(Pos.CENTER); // Centers everything

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); // Display it, but needs to be closed before it can be returned to the original window.
    }

    // Makes sure the string can be converted to a double.
    private static boolean checkDouble(ComboBox<String> input, String number) {
        try {
            double num = Double.parseDouble(input.getValue());
            System.out.println(num); // Debugging
            // Eventually also check if the time is not above 10 minutes.
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
