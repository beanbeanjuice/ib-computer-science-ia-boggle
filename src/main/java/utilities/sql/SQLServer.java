package utilities.sql;

import javafx.application.Platform;
import main.Main;
import screens.LoginScreen;
import screens.MySQLConnectionErrorScreen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SQLServer implements Runnable {

    private Connection connection;
    boolean connected;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread thread;
    private String url;
    private String port;
    private String username;
    private String password;

    // Constructor
    public SQLServer(String url, String port, String username, String password) {
        // Gets a connection using the JDBC plugin.
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
        connected = false;
        thread = new Thread(this);
        thread.start();
    }

    // Gets the actual server connection from this specific object.
    // Needed in case multiple MySQL Servers are needed (Amazon AWS free-tier limitation)
    public Connection getConnection() {
        return connection;
    }

    private void stop() {
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        while (running.get()) {

            try {
                connection = DriverManager.getConnection(url, username, password);
                stop();
                /* Needed so a thread error
                is not produced. */
                Platform.runLater(() -> {
                    Main.setWindow(new LoginScreen());
                });
            } catch (SQLException e) {
                stop();
                Platform.runLater(() -> {
                    Main.setWindow(new MySQLConnectionErrorScreen());
                });
            }
        }
    }

}
