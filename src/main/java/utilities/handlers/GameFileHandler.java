package utilities.handlers;

import main.Main;
import utilities.gamecreation.PreviousGame;

import java.sql.*;
import java.util.ArrayList;

public class GameFileHandler {

    public boolean addToDatabase(PreviousGame game) {

        System.out.println("tried");
        Connection connection = Main.getSQLServer().getConnection();
        String arguments = "INSERT INTO GameData " +
                "(GameID, Score, BoardCharacters, TimeTaken, TimeAllowed, WordsFound, TotalWords) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(arguments);
            statement.setInt(1, game.getGameID());
            statement.setInt(2, game.getScore());
            statement.setString(3, game.getBoard());
            statement.setDouble(4, game.getTimeTaken());
            statement.setDouble(5, game.getTotalTime());
            statement.setInt(6, game.getWordsFound());
            statement.setInt(7, game.getTotalWords());

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

                previousGames.add(new PreviousGame(gameID, board, score, timeTaken, totalTime, wordsFound, totalWords));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving previous game data.");
            // TODO: Show the user there was an error.
        }

        return previousGames;
    }

}
