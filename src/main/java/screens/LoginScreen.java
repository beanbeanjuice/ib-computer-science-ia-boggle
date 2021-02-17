package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import utilities.handlers.sql.LoginHandler;
import utilities.screens.ApplicationScreen;

public class LoginScreen implements ApplicationScreen {

    private Scene loginScreen;

    @Override
    public Scene display() {
        int buttonWidth = Main.getButtonWidth() - 50;
        // "Login" button - self explanatory
        Button login = new Button("Login");
        login.setMinWidth(buttonWidth);
        login.setMaxWidth(buttonWidth);

        Button signup = new Button("Sign Up");
        signup.setMinWidth(buttonWidth);
        signup.setMaxWidth(buttonWidth);

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        usernameField.setPromptText("username");
        usernameField.setEditable(true);
        usernameLabel.setId("text");

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("password");
        passwordField.setEditable(true);
        passwordLabel.setId("text");

        Label errorLabel = new Label();
        errorLabel.setVisible(false);
        errorLabel.setId("error-label");

        // Self explanatory label
        Label title = new Label("Welcome");
        title.setId("title");

        VBox layout = new VBox(20);
        VBox labelVBOX = new VBox(10);
        VBox fieldVBOX = new VBox(10);

        labelVBOX.getChildren().addAll(usernameLabel, passwordLabel);
        fieldVBOX.getChildren().addAll(usernameField, passwordField);
        labelVBOX.setAlignment(Pos.CENTER);
        fieldVBOX.setAlignment(Pos.CENTER);

        HBox comboHBOX = new HBox(5);
        HBox buttonHBOX = new HBox(5);
        comboHBOX.getChildren().addAll(labelVBOX, fieldVBOX);
        buttonHBOX.getChildren().addAll(login, signup);
        comboHBOX.setAlignment(Pos.CENTER);
        buttonHBOX.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, comboHBOX, errorLabel, buttonHBOX);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 80, 10, 80));
        // Put the GridPane in a ScrollPane
        // Put the ScrollPane in a VBox

        login.setOnAction(e -> {
            login(usernameField, passwordField, errorLabel);
        });

        signup.setOnAction(e -> {
            if (passwordField.getText().length() < 8) {
                errorLabel.setVisible(true);
                errorLabel.setText("Password must be 8 or more characters.");
            } else if (usernameField.getText().length() < 3) {

                errorLabel.setVisible(true);
                errorLabel.setText("Your username must be 3 or more characters.");
            } else {
                signup(usernameField, passwordField, errorLabel);
            }
        });

        loginScreen = new Scene(layout, 1000, 600);

        // Checks if the ENTER key is pressed
        loginScreen.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                login(usernameField, passwordField, errorLabel);
            }
        });

        // CSS
        loginScreen.getStylesheets().add("css/main-style.css");

        return loginScreen;
    }

    private void login(TextField usernameField, PasswordField passwordField, Label errorLabel) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        LoginHandler.LOGIN_INFORMATION result = Main.getLoginHandler().checkDatabase(username, password);

        if (result.equals(LoginHandler.LOGIN_INFORMATION.SUCCESSFUL_LOGIN)) {
            Main.setUsername(username);
            Main.updateSettings();
            Main.setWindow(new StartScreen());
        } else {
            errorLabel.setVisible(true);
            errorLabel.setText(result.getMessage());
        }
    }

    private void signup(TextField usernameField, PasswordField passwordField, Label errorLabel) {
        String username = usernameField.getText().toLowerCase();

        if (username.contains(" ")) {
            errorLabel.setVisible(true);
            errorLabel.setText("Usernames cannot contain spaces.");
            return;
        }

        String password = passwordField.getText();
        LoginHandler.SIGNUP_INFORMATION result = Main.getLoginHandler().addToDatabase(username, password);

        if (result.equals(LoginHandler.SIGNUP_INFORMATION.SUCCESSFUL_SIGNUP)) {
            boolean complete = Main.getSettingsHandler().createSettings(username);
            errorLabel.setVisible(true);
            if (complete) {
                errorLabel.setText(result.getMessage());
                Main.updateSettings();
                Main.setWindow(new StartScreen());
            } else {
                errorLabel.setText("Unable to create an account. Please try again.");
            }
        } else {
            errorLabel.setVisible(true);
            errorLabel.setText(result.getMessage());
        }
    }

    @Override
    public String getName() {
        return "LoginScreen";
    }
}
