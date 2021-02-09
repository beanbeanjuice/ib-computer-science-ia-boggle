package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import utilities.screens.ApplicationScreen;

public class ChangePasswordScreen implements ApplicationScreen {

    private Scene changePasswordScreen;

    @Override
    public Scene display() {
        int buttonWidth = Main.getButtonWidth();

        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.setMinWidth(buttonWidth);
        changePasswordButton.setMaxWidth(buttonWidth);

        Button cancelButton = new Button("Cancel");
        cancelButton.setMinWidth(buttonWidth);
        cancelButton.setMaxWidth(buttonWidth);
        cancelButton.setOnAction(e -> {
            Main.setWindow(new StartScreen());
        });

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("password");
        passwordField.setEditable(true);
        passwordLabel.setId("text");

        Label passwordConfirmLabel = new Label("Confirm Password");
        PasswordField passwordConfirmField = new PasswordField();
        passwordConfirmField.setPromptText("password");
        passwordConfirmField.setEditable(true);
        passwordConfirmLabel.setId("text");

        Label errorLabel = new Label();
        errorLabel.setVisible(false);
        errorLabel.setId("error-label");

        // Self explanatory label
        Label title = new Label("Change Password");
        title.setId("title");

        VBox layout = new VBox(20);
        VBox labelVBOX = new VBox(10);
        VBox fieldVBOX = new VBox(10);
        HBox buttonHBOX = new HBox(5);
        HBox comboHBOX = new HBox(5);

        labelVBOX.getChildren().addAll(passwordLabel, passwordConfirmLabel);
        fieldVBOX.getChildren().addAll(passwordField, passwordConfirmField);
        buttonHBOX.getChildren().addAll(changePasswordButton, cancelButton);
        labelVBOX.setAlignment(Pos.BASELINE_CENTER);
        fieldVBOX.setAlignment(Pos.BASELINE_CENTER);
        comboHBOX.getChildren().addAll(labelVBOX, fieldVBOX);
        comboHBOX.setAlignment(Pos.CENTER);
        buttonHBOX.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(title, comboHBOX, errorLabel, buttonHBOX);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 80, 10, 80));
        // Put the GridPane in a ScrollPane
        // Put the ScrollPane in a VBox

        changePasswordButton.setOnAction(e -> {
            if (checkFields(passwordField, passwordConfirmField)) {
                if (passwordField.getText().length() < 8) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Password must be 8 more more characters.");
                } else {
                    boolean result = Main.getLoginHandler().updatePassword(passwordConfirmField.getText());

                    if (result) {
                        Main.setWindow(new StartScreen());
                    } else {
                        errorLabel.setVisible(true);
                        errorLabel.setText("There was an unknown error. Unable to change password.");
                    }
                }
            } else {
                errorLabel.setVisible(true);
                errorLabel.setText("Passwords do not match.");
            }
        });

        changePasswordScreen = new Scene(layout, 1000, 600);

        changePasswordScreen.getStylesheets().add("css/main-style.css");

        return changePasswordScreen;
    }

    private boolean checkFields(PasswordField field1, PasswordField field2) {
        return field1.getText().equals(field2.getText());
    }

    @Override
    public String getName() {
        return "ChangePasswordScreen";
    }
}
