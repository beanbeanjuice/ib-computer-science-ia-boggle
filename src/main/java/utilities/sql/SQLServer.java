package utilities.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServer {

    Connection connection;

    public SQLServer(String url, String port, String username, String password) throws SQLException {
        // Gets a connection using the JDBC plugin.
        connection = DriverManager.getConnection(url, username, password);
    }

    // Gets the actual server connection from this specific object.
    // Needed in case multiple MySQL Servers are needed (Amazon AWS free-tier limitation)
    public Connection getConnection() {
        return connection;
    }

}
