package org.hse.petrov.hw4.db;

import jdk.internal.util.xml.impl.Pair;
import org.hse.petrov.hw4.objects.Event;
import org.hse.petrov.hw4.objects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Analytics {

    private Connection connection;

    public Analytics(Connection connection) {
        this.connection = connection;
    }

    public void initAnalysticsTables() {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS reg_ip AS\n" +
                    "SELECT *  FROM user_logs s_1 LEFT JOIN ip_data s_2 ON s_1.ip = s_2.ip");
            statement.execute("CREATE TABLE IF NOT EXISTS user_type AS\n" +
                    "SELECT * FROM user_logs\n" +
                    "LEFT JOIN user_data\n" +
                    "ON user_logs.ip = user_data.ip");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getMostActiveUsers(int days) {
        try (Statement statement = this.connection.createStatement()) {

            ResultSet results = statement.executeQuery("SELECT * FROM user_data\n" +
                    "WHERE ip IN (SELECT ip, COUNT(ip) as cnt FROM user_logs WHERE" +
                    " julianday(’now’) - julianday(" + days + ") <= A GROUP BY ip ORDER BY cnt DESC LIMIT 10)");

            List<User> result = new ArrayList<>();
            while (results.next()) {
                result.add(
                        new User(
                                results.getString("ip"),
                                results.getString("browser"),
                                results.getBoolean("gender"),
                                results.getInt("age")
                        )
                );
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<String> getRegions() {
        try (Statement statement = this.connection.createStatement()){

            ResultSet rs1 = statement.executeQuery("SELECT COUNT(*) FROM user_logs");
            int numInst = rs1.getInt(1);
            ResultSet rs2 = statement.executeQuery("SELECT COUNT(DISTINCT region) FROM ip_data");
            int numReg = rs2.getInt(1);

            rs1.close();
            rs2.close();

            double av = (numInst + 0.0) / numReg;

            ResultSet rs3 = statement.executeQuery("SELECT DISTINCT region FROM ip_data " +
                    "WHERE region IN " +
                    "(SELECT region,  as cnt FROM reg_ip WHERE COUNT(region) > "+ av + " GROUP BY region)");

            List<String> regions = new ArrayList<>();
            while (rs3.next()) {
                regions.add(rs3.getString(1));
            }


            return regions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<String, Integer> getGenderedViews(Timestamp day) {
        return null;
    }

    public List<String> getMostPopularSites(int years, int days) {
        return null;
    }

    public List<String> getLentaRuRegions(int days) {
        return null;
    }

    public void addNewEvent(Event event) {

    }

    public void removeOld(Timestamp than) {

    }

    public void updateTLD(Timestamp dayAfter) {

    }
}
