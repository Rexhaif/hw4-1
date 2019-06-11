package org.hse.petrov.hw4.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private Connection connection;

    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:database.sqlitedb");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.out.println("Connection cannot be closed");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
