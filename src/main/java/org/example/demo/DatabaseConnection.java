package org.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection databaseLink;

    public void shutdown() throws SQLException {
        if (databaseLink != null) {
            databaseLink.close();
        }
    }
    public Connection getConnection(){
        String databaseName = "desks-cards";
        String databaseUser = "root";
        String databasePassword = "root";
        final String url = "jdbc:mysql://localhost:3333/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseLink;
    }


}
