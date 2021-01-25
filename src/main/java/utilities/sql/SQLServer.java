package utilities.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServer {

    Connection connection;

    public SQLServer(String url, String port, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection() {
        return connection;
    }

}
