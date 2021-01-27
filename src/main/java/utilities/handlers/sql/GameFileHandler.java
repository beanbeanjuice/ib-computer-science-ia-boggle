package utilities.handlers.sql;

import main.Main;
import utilities.gamecreation.PreviousGame;

import java.sql.*;
import java.util.ArrayList;

public class GameFileHandler {

    public boolean addToDatabase(PreviousGame previousGame) {

        Connection connection = Main.getSQLServer().getConnection();

        // MySQL Syntax for retrieving data.
        String arguments = "INSERT INTO GameData " +
                "(GameID, Score, BoardCharacters, TimeTaken, TimeAllowed, WordsFound, TotalWords, FoundWordsStringList, TotalWordsStringList) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(arguments);
            statement.setInt(1, previousGame.getGameID());
            statement.setInt(2, previousGame.getScore());
            statement.setString(3, previousGame.getBoard());
            statement.setDouble(4, previousGame.getTimeTaken());
            statement.setDouble(5, previousGame.getTotalTime());
            statement.setInt(6, previousGame.getWordsFound());
            statement.setInt(7, previousGame.getTotalWords());
            statement.setString(8, previousGame.getFoundWordsStringList());
            statement.setString(9, previousGame.getTotalWordsStringList());

            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public ArrayList<PreviousGame> getFromDataBase() {
        ArrayList<PreviousGame> previousGames = new ArrayList<>();
        Connection connection = Main.getSQLServer().getConnection();
        String arguments = "SELECT * FROM GameData";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(arguments);

            while (resultSet.next()) {
                int gameID = resultSet.getInt(1);
                int score = resultSet.getInt(2);
                String board = resultSet.getString(3);
                double timeTaken = resultSet.getDouble(4);
                double totalTime = resultSet.getDouble(5);
                int wordsFound = resultSet.getInt(6);
                int totalWords = resultSet.getInt(7);
                String wordsFoundStringList = resultSet.getString(8);
                String totalWordsStringList = resultSet.getString(9);

                previousGames.add(new PreviousGame(gameID, board, score, timeTaken, totalTime, wordsFound, totalWords, wordsFoundStringList, totalWordsStringList));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving previous game data.");
            // TODO: Show the user there was an error.
        }

        return previousGames;
    }

    public PreviousGame getPreviousGame(int gameID) {
        for (PreviousGame previousGame : getFromDataBase()) {
            if (previousGame.getGameID() == gameID) {
                return previousGame;
            }
        }
        return null;
    }

}
