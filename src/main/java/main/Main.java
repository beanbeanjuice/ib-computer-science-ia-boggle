package main;

import javafx.application.Application;
import javafx.stage.Stage;
import screens.MySQLConnectionErrorScreen;
import screens.StartScreen;
import utilities.boxes.ConfirmationBox;
import utilities.gamecreation.Game;
import utilities.gamecreation.PreviousGame;
import utilities.handlers.BoardHandler;
import utilities.handlers.DictionaryHandler;
import utilities.handlers.DistributionHandler;
import utilities.handlers.sql.GameFileHandler;
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
    private static final String TITLE_FONT = "Roboto-BoldItalic.ttf";
    private static final String BUTTON_FONT = "Roboto-Regular.ttf";
    private static final String TEXT_FONT = "Roboto-MediumItalic.ttf";

    private static boolean allowIncorrect;
    private static boolean ignoreTimeLimit;
    private static double timeLimit;

    private static String url = "jdbc:mysql://beanbeanjuice.cscakjg7lpmu.us-east-2.rds.amazonaws.com:3306/Boggle?useSSL=false";
    private static String port = "3306";
    private static String username = "admin";
    private static String password = "ETd7a4tpLx6qGZfmqBimEoDYh6ghEh";

    private static DictionaryHandler dictionaryHandler;
    private static DistributionHandler distributionHandler;
    private static BoardHandler boardHandler;
    private static SettingsHandler settingsHandler;

    public static void main(String[] args) {
        dictionaryHandler = new DictionaryHandler();
        distributionHandler = new DistributionHandler();
        boardHandler = new BoardHandler(distributionHandler);
        gameFileHandler = new GameFileHandler();
        settingsHandler = new SettingsHandler();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

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

            timeLimit = settingsHandler.getFromDataBase().getNum();
            int bit = settingsHandler.getFromDataBase().getBit();

            ignoreTimeLimit = bit != 0;

        } catch (SQLException e) { // This will throw an error if the database is unreachable.
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

                saveGame();
            }

            System.out.println("Closed Program");
            window.close();
        }
    }

    public static void saveGame() {
        Game game = currentScreen.getGame();
        PreviousGame previousGame = game.save();
        boolean successfulSave = gameFileHandler.addToDatabase(previousGame);
        while (!successfulSave) {
            try {
                Thread.sleep(500);
                successfulSave = gameFileHandler.addToDatabase(currentScreen.getGame().save());
            } catch (InterruptedException ignored) {}
        }
        setWindow(new StartScreen());
        game.getTimer().stop();
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

    // Gets the filename for the title font
    public static String getTitleFont() {
        return TITLE_FONT;
    }

    // Gets the filename for the button font
    public static String getButtonFont() {
        return BUTTON_FONT;
    }

    // Gets the filename for the text font
    public static String getTextFont() {
        return TEXT_FONT;
    }

}
