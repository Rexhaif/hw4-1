package org.hse.petrov.hw4.db;

import org.hse.petrov.hw4.objects.Location;
import org.hse.petrov.hw4.objects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTableIfNotExists() {
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS 'user_data' ('id' INTEGER PRIMARY KEY AUTOINCREMENT,'ip' TEXT, 'browser' TEXT, 'gender' BOOLEAN, 'age' INTEGER)"
            );
            statement.closeOnCompletion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAll(List<User> locations) {
        try {

            PreparedStatement prep = this.connection.prepareStatement("INSERT INTO 'user_data' ('ip', 'browser', 'gender', 'age') VALUES (?, ?, ?, ?)");
            locations.forEach(user -> {
                try {
                    prep.setString(1, user.getIpAddress());
                    prep.setString(2, user.getBrowser());
                    prep.setBoolean(3, user.getSex());
                    prep.setInt(4, user.getAge());
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

    public List<User> readAll() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM 'user_data'");
            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(
                        new User(
                                resultSet.getString("ip"),
                                resultSet.getString("browser"),
                                resultSet.getBoolean("gender"),
                                resultSet.getInt("age")
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
