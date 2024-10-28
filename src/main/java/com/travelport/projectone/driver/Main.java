package com.travelport.projectone.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        // Using SQLServer
        try (var connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=SmallStore",
                "sa", "1234asdf!#")) {
            System.out.println("Connected to SQLServer");
        }
    }
}