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
        String arguments = "UPDATE Settings SET TimeLimit = " + num + ", IgnoreTimeLimit = " + bit + " WHERE ID = 1;";

        try {
            PreparedStatement statement = connection.prepareStatement(arguments);

            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Setting getFromDataBase() {
        Setting setting = new Setting(5, 0);
        Connection connection = Main.getSQLServer().getConnection();
        String arguments = "SELECT * FROM Settings";

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
