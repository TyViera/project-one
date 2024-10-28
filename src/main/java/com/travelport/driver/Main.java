package com.travelport.driver;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        var db = new DB();
        Scanner in = new Scanner(System.in);
        try(
                var conn = db.connect();
                var statementCreate = conn.createStatement();
                var statementInsert = conn.createStatement();
                var statementSelect = conn.createStatement();
        ){
            var resultTableClients = statementCreate.execute("CREATE TABLE clients (nif VARCHAR(10), name VARCHAR(250), address VARCHAR(255))");
            System.out.println("Result of table creation: " + resultTableClients);
            var resultTableProducts = statementCreate.execute("CREATE TABLE products (code INT, name VARCHAR(250))");
            System.out.println("Result of table creation: " + resultTableProducts);
            var resultTablePurchases = statementCreate.execute("CREATE TABLE purchases (id INT, client_nif VARCHAR(10), product_code INT, quantity INT)");
            System.out.println("Result of table creation: " + resultTablePurchases);

            var insertResultClients = statementInsert.executeUpdate("INSERT INTO clients (nif, name, address) VALUES" +
            "('123456789', 'Juan Pérez', 'Calle Falsa 123, Madrid')," +
            "('987654321', 'Ana García', 'Avenida Siempreviva 742, Barcelona')," +
            "('456789123', 'Carlos Gómez', 'Calle Luna 456, Valencia');");
            System.out.println("Result of insertion: " + insertResultClients);

            var insertResultProducts = statementInsert.executeUpdate("INSERT INTO products (code, name) VALUES" +
                    "(101, 'Laptop Dell XPS 13')," +
                    "(102, 'Smartphone Samsung Galaxy S21')," +
                    "(103, 'Televisor LG OLED 55');");
            System.out.println("Result of insertion: " + insertResultProducts);

            var insertResultPurchases = statementInsert.executeUpdate("INSERT INTO purchases (id, client_nif, product_code, quantity) VALUES" +
                    "(1, '123456789', 101, 10), " +
                    "(2, '987654321', 102, 7), " +
                    "(3, '456789123', 103, 1), " +
                    "(4, '123456789', 102, 2);");
            System.out.println("Result of insertion: " + insertResultPurchases);

//            var selectResult = statementSelect.executeQuery("SELECT nif, name, address FROM clients");
//            while(selectResult.next()){
//               var nif = selectResult.getString("nif");
//               var name = selectResult.getString("name");
//               var address = selectResult.getString("address");
//               System.out.println("I've found:" + nif + ", " + name + ", " + address);
//            }
//            System.out.println("Result of table select: " + selectResult);
        }

    }
}
