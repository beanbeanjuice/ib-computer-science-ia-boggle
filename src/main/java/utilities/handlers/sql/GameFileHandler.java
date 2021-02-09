package utilities.handlers.sql;

import main.Main;
import utilities.gamecreation.PreviousGame;

import java.sql.*;
import java.util.ArrayList;

public class GameFileHandler {

    public boolean addToDatabase(PreviousGame previousGame) {

        Connection connection = Main.getSQLServer().getConnection();

        // MySQL Syntax for inserting data into a table.
        // The "?" represents variables to be input using the statement.set() method.
        // "?" is MySQL Syntax.
        String arguments = "INSERT INTO GameData " +
                "(Username, GameID, Score, BoardCharacters, TimeTaken, TimeAllowed, " +
                "WordsFound, TotalWords, FoundWordsStringList, TotalWordsStringList) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Prepares a statement to be executed on the connection from the SQLServer object.
            PreparedStatement statement = connection.prepareStatement(arguments);

            // ParameterIndex is the column number on the actual MySQL database.
            statement.setString(1, Main.getUsername().toLowerCase());
            statement.setInt(2, previousGame.getGameID());
            statement.setInt(3, previousGame.getScore());
            statement.setString(4, previousGame.getBoard());
            statement.setDouble(5, previousGame.getTimeTaken());
            statement.setDouble(6, previousGame.getTotalTime());
            statement.setInt(7, previousGame.getWordsFound());
            statement.setInt(8, previousGame.getTotalWords());
            statement.setString(9, previousGame.getFoundWordsStringList());
            statement.setString(10, previousGame.getTotalWordsStringList());

            // Executes the statement
            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public ArrayList<PreviousGame> getFromDataBase() {
        ArrayList<PreviousGame> previousGames = new ArrayList<>();
        Connection connection = Main.getSQLServer().getConnection();
        String arguments = "SELECT * FROM GameData WHERE Username = '" + Main.getUsername().toLowerCase() + "';";

        try {
            /*
            Since we are retrieving the data now, we no longer
            want to "prepare" the data. This is why we use
            Statement instead of PreparedStatement to get
            the results immediately.
             */
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(arguments);

            while (resultSet.next()) {
                // ColumnIndex coincides with the column number on the database.
                int gameID = resultSet.getInt(2);
                int score = resultSet.getInt(3);
                String board = resultSet.getString(4);
                double timeTaken = resultSet.getDouble(5);
                double totalTime = resultSet.getDouble(6);
                int wordsFound = resultSet.getInt(7);
                int totalWords = resultSet.getInt(8);
                String wordsFoundStringList = resultSet.getString(9);
                String totalWordsStringList = resultSet.getString(10);

                // This creates a new "PreviousGame" object for each row on the database.
                previousGames.add(new PreviousGame(gameID, board, score, timeTaken,
                        totalTime, wordsFound, totalWords,
                        wordsFoundStringList, totalWordsStringList));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving previous game data.");
        }

        return previousGames;
    }

    // Retrieves a specific previous game.
    // Used when double clicking a row in the PreviousGames screen.
    public PreviousGame getPreviousGame(int gameID) {
        for (PreviousGame previousGame : getFromDataBase()) {
            if (previousGame.getGameID() == gameID) {
                return previousGame;
            }
        }
        return null;
    }

}
