package com.example.assignment1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/Movies_db";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }
}
