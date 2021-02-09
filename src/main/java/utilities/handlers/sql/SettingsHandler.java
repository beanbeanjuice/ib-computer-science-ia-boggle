package utilities.handlers.sql;

import main.Main;
import utilities.handlers.Setting;

import java.sql.*;

public class SettingsHandler {

    public boolean updateSettings(Setting setting) {

        double num = setting.getNum();
        int bit = setting.getBit();

        Connection connection = Main.getSQLServer().getConnection();

        // MySQL Syntax for updating data.
        String arguments = "UPDATE Settings SET TimeLimit = " + num +
                ", IgnoreTimeLimit = " + bit + " WHERE Username = '"
                + Main.getUsername() + "';";

        try {
            PreparedStatement statement = connection.prepareStatement(arguments);

            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean createSettings(String username) {
        Connection connection = Main.getSQLServer().getConnection();

        // MD5 is used to insert into the database as an MD5 hash
        String arguments = "INSERT INTO Settings " +
                "(Username, TimeLimit, IgnoreTimeLimit) " +
                "VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(arguments);
            statement.setString(1, username.toLowerCase());
            statement.setDouble(2, 5);
            statement.setInt(3, 0);
            statement.execute();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public Setting getFromDataBase(String username) {
        Setting setting = new Setting(5, 0);
        Connection connection = Main.getSQLServer().getConnection();
        String arguments = "SELECT * FROM Settings WHERE Username = '" + username + "';";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(arguments);

            while (resultSet.next()) {
                double num = resultSet.getDouble(2);
                int bit = resultSet.getInt(3);

                setting.setNum(num);
                setting.setBit(bit);
            }
        } catch (SQLException e) {
            System.out.println("Error saving settings. Using default settings.");
        }

        return setting;
    }

}
