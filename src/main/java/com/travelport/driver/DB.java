package com.travelport.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    public Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:dbF");
    }
}
