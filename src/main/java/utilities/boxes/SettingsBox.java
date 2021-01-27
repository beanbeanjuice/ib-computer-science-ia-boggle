package utilities.boxes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import utilities.handlers.Setting;

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

        // Time Settings
        Label time = new Label("Time (in Minutes):");
        time.setId("text");

        Label timeInfo = new Label("1 minute or above. 10 minutes or less.");
        timeInfo.setId("text-no-bold");
        timeInfo.setFont(new Font(10));

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
        timeInput.setId("text-no-bold");
        timeInput.getSelectionModel().select(Double.toString(Main.getTimeLimit()));


        // Allow for Input after Time Ends
        CheckBox ignoreTimeLimit = new CheckBox("Ignore Time Limit");
        ignoreTimeLimit.setId("text");
        ignoreTimeLimit.setSelected(Main.getIgnoreTimeLimit());

        Button saveButton = new Button("Save Settings");
        saveButton.setOnAction(e -> {

            if (checkDouble(timeInput)) {
                double num = Double.parseDouble(timeInput.getValue());

                if (checkTime(num)) {

                    boolean isSelected = ignoreTimeLimit.isSelected();
                    int bit = 0;

                    // MySQL does not truly support booleans
                    // 1s and 0s are to be used instead.

                    if (isSelected) {
                        bit = 1;
                    }

                    Main.setTimeLimit(num);
                    Main.setIgnoreTimeLimit(isSelected);
                    Main.getSettingsHandler().updateSettings(new Setting(num, bit));
                    window.close();
                }
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(time, timeInfo, timeInput, ignoreTimeLimit, saveButton);
        layout.setAlignment(Pos.CENTER); // Centers everything
        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout);

        // CSS
        scene.getStylesheets().add("css/main-style.css");

        window.setScene(scene);
        window.showAndWait(); // Display it, but needs to be closed before it can be returned to the original window.
    }

    // Makes sure the string can be converted to a double
    private boolean checkDouble(ComboBox<String> input) {
        try {
            double num = Double.parseDouble(input.getValue());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean checkTime(double number) {
        return !(number > 10) && !(number < 1);
    }

}
