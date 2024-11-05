package com.travelport;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        var db = new DB();
        Scanner in = new Scanner(System.in);
        try (
                var conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project_one", "postgres", "");
        ) {
            conn.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
