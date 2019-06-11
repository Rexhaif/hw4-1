package org.hse.petrov.hw4.db;

import org.hse.petrov.hw4.objects.Event;
import org.hse.petrov.hw4.objects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    private Connection connection;

    public EventDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTableIfNotExists() {
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS 'user_logs' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'ip' TEXT, 'timestamp' DATETIME, 'site' TEXT, 'size' INTEGER, 'code' INTEGER, 'browser' TEXT)"
            );
            statement.closeOnCompletion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAll(List<Event> events) {
        try {

            PreparedStatement prep = this.connection.prepareStatement("INSERT INTO 'user_logs' ('ip', 'timestamp', 'site', 'size', 'code', 'browser') VALUES (?, ?, ?, ?, ?, ?)");
            events.forEach(event -> {
                try {
                    prep.setString(1, event.getIpAddress());
                    prep.setTimestamp(2, event.getTimestamp());
                    prep.setString(3, event.getUrl());
                    prep.setInt(4, event.getPageSize());
                    prep.setInt(5, event.getStatusCode());
                    prep.setString(6, event.getUserAgent());
                    prep.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            prep.executeBatch();
            prep.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Event> readAll() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM 'user_logs'");
            List<Event> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(
                        new Event(
                                resultSet.getString("ip"),
                                resultSet.getTimestamp("timestamp").getTime(),
                                resultSet.getString("site"),
                                resultSet.getInt("size"),
                                resultSet.getInt("code"),
                                resultSet.getString("browser")
                        )
                );
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
