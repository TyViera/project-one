package com.travelport.driver;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        var db = new DB();
        try(
                var conn = db.connect();
                var statementCreate = conn.createStatement();
                var statementInsert = conn.createStatement();
                var statementSelect = conn.createStatement();
        ){
            var result = statementCreate.execute("CREATE TABLE clients (nif VARCHAR(10), name VARCHAR(250), address VARCHAR(255))");
            System.out.println("Result of table creation: " + result);

            var insertResult = statementInsert.executeUpdate("INSERT INTO clients (nif, name, address) VALUES ('X1234Y','Filip','Mars')");
            System.out.println("Result of insertion: " + insertResult);

            var selectResult = statementSelect.executeQuery("SELECT nif, name, address FROM clients");
            while(selectResult.next()){
               var nif = selectResult.getString("nif");
               var name = selectResult.getString("name");
               var address = selectResult.getString("address");
               System.out.println("I've found:" + nif + ", " + name + ", " + address);
            }
            System.out.println("Result of table select: " + selectResult);
        }

    }
}
