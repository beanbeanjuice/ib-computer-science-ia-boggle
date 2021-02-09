package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import utilities.gamecreation.PreviousGame;
import utilities.handlers.sql.LoginHandler;
import utilities.screens.ApplicationScreen;

public class LoginScreen implements ApplicationScreen {

    private Scene loginScreen;

    @Override
    public Scene display() {
        // "Login" button - self explanatory
        Button login = new Button("Login");

        Button signup = new Button("Sign Up");

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        usernameField.setPromptText("username");
        usernameField.setEditable(true);

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("password");
        passwordField.setEditable(true);

        Label errorLabel = new Label();
        errorLabel.setVisible(false);
        errorLabel.setId("error-label");

        // Self explanatory label
        Label title = new Label("Welcome");
        title.setId("title");

        VBox layout = new VBox(20);
        HBox usernameHBOX = new HBox(5);
        HBox passwordHBOX = new HBox(5);

        usernameHBOX.getChildren().addAll(usernameLabel, usernameField);
        passwordHBOX.getChildren().addAll(passwordLabel, passwordField);
        usernameHBOX.setAlignment(Pos.CENTER);
        passwordHBOX.setAlignment(Pos.CENTER);

        HBox buttonHBOX = new HBox(5);
        buttonHBOX.getChildren().addAll(login, signup);
        buttonHBOX.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, usernameHBOX, passwordHBOX, errorLabel, buttonHBOX);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 80, 10, 80));
        // Put the GridPane in a ScrollPane
        // Put the ScrollPane in a VBox

        login.setOnAction(e -> {
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
        });

        signup.setOnAction(e -> {
            String username = usernameField.getText().toLowerCase();

            if (username.contains(" ")) {
                errorLabel.setVisible(true);
                errorLabel.setText("Usernames cannot contain spaces.");
                return;
            }

            String password = passwordField.getText();
            LoginHandler.SIGNUP_INFORMATION result = Main.getLoginHandler().addToDatabase(username, password);

            if (result.equals(LoginHandler.SIGNUP_INFORMATION.SUCCESSFUL_SIGNUP)) {
                System.out.println(result.getMessage());
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
                System.out.println(result.getMessage());
                errorLabel.setVisible(true);
                errorLabel.setText(result.getMessage());
            }
        });



        loginScreen = new Scene(layout, 1000, 600);

        // CSS
        loginScreen.getStylesheets().add("css/main-style.css");

        return loginScreen;
    }

    @Override
    public String getName() {
        return "LoginScreen";
    }
}
