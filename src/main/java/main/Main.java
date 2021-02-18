package main;

import javafx.application.Application;
import javafx.stage.Stage;
import screens.LoginScreen;
import screens.MySQLConnectionErrorScreen;
import screens.StartupConnectionScreen;
import utilities.boxes.ConfirmationBox;
import utilities.gamecreation.Game;
import utilities.gamecreation.PreviousGame;
import utilities.handlers.BoardHandler;
import utilities.handlers.DictionaryHandler;
import utilities.handlers.DistributionHandler;
import utilities.handlers.sql.GameFileHandler;
import utilities.handlers.sql.LoginHandler;
import utilities.handlers.sql.SettingsHandler;
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
    private static final int TITLE_SIZE = 24;

    private static String username;

    private static boolean ignoreTimeLimit;
    private static double timeLimit;

    private static final String SQL_URL = "jdbc:mysql://beanbeanjuice.cscakjg7lpmu.us-east-2.rds.amazonaws.com:3306/Boggle?useSSL=false";
    private static final String SQL_PORT = "3306";
    private static final String SQL_USERNAME = "admin";
    private static final String SQL_PASSWORD = "ETd7a4tpLx6qGZfmqBimEoDYh6ghEh";

    private static DictionaryHandler dictionaryHandler;
    private static DistributionHandler distributionHandler;
    private static BoardHandler boardHandler;
    private static SettingsHandler settingsHandler;
    private static LoginHandler loginHandler;

    public static void main(String[] args) {
        // Instantiating all objects that are needed for the game to run
        dictionaryHandler = new DictionaryHandler();
        distributionHandler = new DistributionHandler();
        boardHandler = new BoardHandler(distributionHandler);
        gameFileHandler = new GameFileHandler();
        settingsHandler = new SettingsHandler();
        loginHandler = new LoginHandler();

        launch(args);
    }

    // Code that is run to start the GUI.
    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        window.setTitle("Boggle: The Game");
        setWindow(new StartupConnectionScreen());
        window.show();

        // Consumes the "X" button or Mac/Linux equivalent.
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        // Starts the MySQL Exception
        startMySQLConnection();
    }

    // Sets the current window. Custom interface created to house all of the screens.
    public static void setWindow(ApplicationScreen screen) {
        currentScreen = screen;
        window.setScene(screen.display());
    }

    public static void closeProgram() {
        boolean answer = new ConfirmationBox("Confirmation",
                "Are you sure you want to exit?").display();
        if (answer) {
            // Save everything
            if (currentScreen.getName().equals("GameScreen")) {
                // Save Game Stuff
                saveGame();
            }
            System.out.println("Closed Program");
            window.close();
        }
    }

    // Saves all aspects of the current game.
    public static void saveGame() {
        Game game = currentScreen.getGame();
        PreviousGame previousGame = game.save();
        boolean successfulSave = gameFileHandler.addToDatabase(previousGame);
        while (!successfulSave) {
            try {
                Thread.sleep(500); // Needed so the computer doesn't freeze
                successfulSave = gameFileHandler.addToDatabase(currentScreen.getGame().save());
            } catch (InterruptedException ignored) {}
        }
        game.getTimer().stop(); // Stops the timer if it is active.
    }

    /* ===========================================================================
    MYSQL CONNECTION
    =========================================================================== */

    public static void startMySQLConnection() {
        sqlServer = new SQLServer(SQL_URL, SQL_PORT, SQL_USERNAME, SQL_PASSWORD);
    }

    /* ===========================================================================
    GETTERS AND SETTERS
    =========================================================================== */

    // Updates the settings
    public static void updateSettings() {
        timeLimit = settingsHandler.getFromDataBase(username).getNum();
        int bit = settingsHandler.getFromDataBase(username).getBit();
        ignoreTimeLimit = bit != 0;
    }

    // Gets the button width. Keeps things consistent.
    public static int getButtonWidth() {
        return BUTTON_WIDTH;
    }

    // Gets the button height. Keeps things consistent.
    public static int getButtonTextSize() {
        return BUTTON_TEXT_SIZE;
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

    // Gets if the game should ignore the time limit
    public static boolean getIgnoreTimeLimit() {
        return ignoreTimeLimit;
    }

    // Sets if the game should ignore the time limit
    public static void setIgnoreTimeLimit(boolean answer) {
        ignoreTimeLimit = answer;
    }

    // Gets the dictionary handler.
    public static DictionaryHandler getDictionaryHandler() {
        return dictionaryHandler;
    }

    // Gets the Boggle distribution.
    public static DistributionHandler getDistributionHandler() {
        return distributionHandler;
    }

    // Gets the board handler
    public static BoardHandler getBoardHandler() {
        return boardHandler;
    }

    // Gets the SQLServer Object
    public static SQLServer getSQLServer() {
        return sqlServer;
    }

    // Gets the SQL Game File Handler
    public static GameFileHandler getGameFileHandler() {
        return gameFileHandler;
    }

    // Gets the title size
    public static int getTitleSize() {
        return TITLE_SIZE;
    }

    // Gets the settings handler
    public static SettingsHandler getSettingsHandler() {
        return settingsHandler;
    }

    // Gets the current username
    public static String getUsername() {
        return username;
    }

    // Sets the current username
    public static void setUsername(String name) {
        username = name;
    }

    // Gets the login handler
    public static LoginHandler getLoginHandler() {
        return loginHandler;
    }

}
