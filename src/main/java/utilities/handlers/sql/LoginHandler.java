package utilities.handlers.sql;

import main.Main;
import org.apache.commons.codec.digest.DigestUtils;
import java.sql.*;

public class LoginHandler {

    public SIGNUP_INFORMATION addToDatabase(String username, String password) {

        Connection connection = Main.getSQLServer().getConnection();

        // MD5 is used to insert into the database as an MD5 hash
        String arguments = "INSERT INTO UserData VALUES(?, md5(?));";

        if (checkDatabase(username, password).equals(LOGIN_INFORMATION.NO_USER)) {
            try {
                // Prepares a statement to be executed on the connection from the SQLServer object.
                PreparedStatement statement = connection.prepareStatement(arguments);

                // ParameterIndex is the column number on the actual MySQL database.
                statement.setString(1, username.toLowerCase());
                statement.setString(2, password);

                // Executes the statement
                statement.execute();
                return SIGNUP_INFORMATION.SUCCESSFUL_SIGNUP;
            } catch (SQLException e) {
                return SIGNUP_INFORMATION.CONNECTION_ERROR;
            }
        } else {
            return SIGNUP_INFORMATION.USER_ALREADY_EXISTS;
        }
    }

    public LOGIN_INFORMATION checkDatabase(String username, String password) {
        username = username.toLowerCase();
        Connection connection = Main.getSQLServer().getConnection();
        // Gets the user
        String arguments = "SELECT * FROM UserData WHERE Username = '" + username + "';";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(arguments);

            // If nothing exists, do this
            if (!resultSet.next()) {
                return LOGIN_INFORMATION.NO_USER;
            }

            // Converts the password the user entered into an MD5 string hex
            String digestedPassword = DigestUtils.md5Hex(password);

            // If the MD5 Hash is the same as in the database, allow a login
            if (resultSet.getString(2).equals(digestedPassword)) {
                return LOGIN_INFORMATION.SUCCESSFUL_LOGIN;
            } else {
                return LOGIN_INFORMATION.INCORRECT_PASSWORD;
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return LOGIN_INFORMATION.CONNECTION_ERROR;
        }
    }

    public enum SIGNUP_INFORMATION {
        USER_ALREADY_EXISTS("An account with this username already exists."),
        SUCCESSFUL_SIGNUP("You have been successfully signed up!"),
        CONNECTION_ERROR("Unable to connect to the database.");

        private final String message;

        SIGNUP_INFORMATION(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum LOGIN_INFORMATION {
        NO_USER("There is no account in the database."),
        INCORRECT_PASSWORD("You have entered the incorrect password."),
        SUCCESSFUL_LOGIN("You have been successfully logged in."),
        CONNECTION_ERROR("Unable to connect to the database.");

        private final String message;

        LOGIN_INFORMATION(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}