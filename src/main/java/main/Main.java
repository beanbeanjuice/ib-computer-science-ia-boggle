package main;

import javafx.application.Application;
import javafx.stage.Stage;
import screens.MySQLConnectionErrorScreen;
import screens.StartScreen;
import utilities.boxes.ConfirmationBox;
import utilities.filehandlers.GameFileHandler;
import utilities.screens.ApplicationScreen;
import utilities.sql.SQLServer;

import java.sql.SQLException;

public class Main extends Application {

    private static ApplicationScreen currentScreen;
    private static Stage window;
    private static GameFileHandler gameFileHandler;
    private static SQLServer sqlServer;

    private static final int BUTTON_WIDTH = 175;
    private static final int BUTTON_TEXT_SIZE = 18;

    private static boolean allowIncorrect;
    private static boolean ignoreTimeLimit;
    private static double timeLimit;

    private static String url = "jdbc:mysql://beanbeanjuice.cscakjg7lpmu.us-east-2.rds.amazonaws.com:3306/Boggle?useSSL=false";
    private static String port = "3306";
    private static String username = "admin";
    private static String password = "ETd7a4tpLx6qGZfmqBimEoDYh6ghEh";

    public static void main(String[] args) {
        // TODO: new DictionaryHandler();
        // TODO: Get game time from settings file
        timeLimit = 5;
        // TODO: Get if should ignore time limit from game file
        ignoreTimeLimit = false;
        // TODO: Get if should ignore incorrect
        allowIncorrect = false;

        // TODO: Maybe get the settings from the database?
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;
        setWindow(new StartScreen());
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        System.out.println("Attempting Connection to the Database...");
        try {
            startMySQLConnection();
            System.out.println("Successfully connected to the database.");

        } catch (SQLException e) {
            window.setScene(new MySQLConnectionErrorScreen().display());
        }

        window.show();

    }

    public static void closeProgram() {
        boolean answer = new ConfirmationBox("Confirmation", "Are you sure you want to exit?").display();
        if (answer) {
            // Save everything
            if (currentScreen.getName().equals("GameScreen")) {
                // Save Game Stuff

                //boolean successfulSave = gameFileHandler.addToDatabase(currentScreen.getGame().save());
                //while (!successfulSave) {
                //    successfulSave = gameFileHandler.addToDatabase(currentScreen.getGame().save());
                //}
            }

            System.out.println("Closed Program");
            window.close();
        }
    }

    /* ===========================================================================
    MYSQL CONNECTION
    =========================================================================== */

    public static void startMySQLConnection() throws SQLException {
        sqlServer = new SQLServer(url, port, username, password);
    }

    /* ===========================================================================
    GETTERS AND SETTERS
    =========================================================================== */

    // Gets the button width. Keeps things consistent.
    public static int getButtonWidth() {
        return BUTTON_WIDTH;
    }

    // Gets the button height. Keeps things consistent.
    public static int getButtonTextSize() {
        return BUTTON_TEXT_SIZE;
    }

    // Sets the current window. Custom interface created to house all of the screens.
    public static void setWindow(ApplicationScreen screen) {
        currentScreen = screen;
        window.setScene(screen.display());
    }

    // Gets which screen is currently being displayed.
    public static ApplicationScreen getCurrentScreen() {
        return currentScreen;
    }

    // Gets the game time limit.
    public static double getTimeLimit() {
        return timeLimit;
    }

    // Sets the game time limit.
    public static void setTimeLimit(double limit) {
        timeLimit = limit;
    }

    // Gets if the game should ignore incorrect words
    public static boolean getAllowIncorrect() {
        return allowIncorrect;
    }

    // Sets if the game should allow incorrect words
    public static void setAllowIncorrect(boolean answer) {
        allowIncorrect = answer;
    }

    // Gets if the game should ignore the time limit
    public static boolean getIgnoreTimeLimit() {
        return ignoreTimeLimit;
    }

    // Sets if the game should ignore the time limit
    public static void setIgnoreTimeLimit(boolean answer) {
        ignoreTimeLimit = answer;
    }

}
