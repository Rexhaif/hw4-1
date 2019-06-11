package org.hse.petrov.hw4.db;

import org.hse.petrov.hw4.objects.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO {

    private Connection conn;

    public LocationDAO(Connection conn) {
        this.conn = conn;
    }

    public void createTableIfNotExists() {
        try {
            Statement statement = this.conn.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS 'ip_data' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'ip' TEXT, 'region' TEXT)"
            );
            statement.closeOnCompletion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAll(List<Location> locations) {
        try {

            PreparedStatement prep = conn.prepareStatement("INSERT INTO 'ip_data' ('ip', 'region') VALUES (?, ?)");
            locations.forEach(location -> {
                try {
                    prep.setString(1, location.getIpAddress());
                    prep.setString(2, location.getRegion());
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

    public List<Location> readAll() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM 'ip_data'");
            List<Location> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(
                        new Location(
                                resultSet.getString("ip"),
                                resultSet.getString("region")
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
