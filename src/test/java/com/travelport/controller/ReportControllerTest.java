package com.travelport.controller;

import com.travelport.config.DatabaseConfig;
import com.travelport.config.WebAppConfig;
import com.travelport.config.WebAppInitializer;
import com.travelport.persistence.impl.ReportDaoImpl;
import com.travelport.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import javax.sql.DataSource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebAppConfig.class,
        WebAppInitializer.class,
        DatabaseConfig.class,
        ReportController.class,
        ReportService.class,
        ReportDaoImpl.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS) // Solución a que las clases de test corren bien individualmente pero fallan al correr juntas con mvn clean install
class ReportControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private DataSource dataSource;

    MockMvc mockMvc;

    @Test
    void incomeReport() throws Exception {
        loadInitialData();
        var expectedResponse = """
                [
                    {
                        "productCode": 1,
                        "productName": "Laptop",
                        "totalQuantity": 10
                    }
                ]
                """;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(get("/reports/products")
                .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    private void loadInitialData() throws Exception {
        try (var conn = dataSource.getConnection()) {

            // Create Tables
            try (var createTableProducts = conn.prepareStatement("""
            CREATE TABLE IF NOT EXISTS products (
                code int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(25)
            )
        """)) {
                createTableProducts.executeUpdate();
            }

            try (var createTableClients = conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS clients (
                    nif varchar(255) NOT NULL PRIMARY KEY,
                    name varchar(255) DEFAULT NULL,
                    address varchar(255) DEFAULT NULL
                )
             """)) {
                createTableClients.executeUpdate();
            }

            try (var createTableSales = conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS sales_cab (
                    id int NOT NULL AUTO_INCREMENT,
                    client_nif varchar(255),
                    sell_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    PRIMARY KEY (id),
                    FOREIGN KEY (client_nif) REFERENCES clients (nif)
                )
             """)) {
                createTableSales.executeUpdate();
            }

            try (var createTableSaleDetails = conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS sales_det (
                    sale_id int NOT NULL,
                    product_code int NOT NULL,
                    quantity int NOT NULL,
                    PRIMARY KEY (sale_id, product_code),
                    FOREIGN KEY (sale_id) REFERENCES sales_cab (id),
                    FOREIGN KEY (product_code) REFERENCES products (code)
                )
             """)) {
                createTableSaleDetails.executeUpdate();
            }

            // Clear Tables
            try (var deleteStmt = conn.prepareStatement("DELETE FROM clients")) {
                deleteStmt.executeUpdate();
            }
            try (var deleteStmt = conn.prepareStatement("DELETE FROM products")) {
                deleteStmt.executeUpdate();
            }
            try (var deleteStmt = conn.prepareStatement("DELETE FROM sales_cab")) {
                deleteStmt.executeUpdate();
            }
            try (var deleteStmt = conn.prepareStatement("DELETE FROM sales_det")) {
                deleteStmt.executeUpdate();
            }

            // Insert Products
            try (var stmt = conn.prepareStatement("Insert into products (name) values (?)")) {
                stmt.setString(1, "Laptop");
                stmt.executeUpdate();
            }

            // Insert Clients
            try (var stmt = conn.prepareStatement("Insert into clients (nif, name, address) values (?, ?, ?)")) {
                stmt.setString(1, "12345678A");
                stmt.setString(2, "Pepe");
                stmt.setString(3, "Main St 123");
                stmt.executeUpdate();
            }

            // Insert Sale
            try (var stmt = conn.prepareStatement("Insert into sales_cab (client_nif) VALUES (?)")) {
                stmt.setString(1, "12345678A");
                stmt.executeUpdate();
            }

            // Insert Sale Details
            try (var stmt = conn.prepareStatement("Insert into sales_det (sale_id, product_code, quantity) VALUES (?, ?, ?)")) {
                stmt.setInt(1, 1);
                stmt.setInt(2, 1);
                stmt.setInt(3, 10);
                stmt.executeUpdate();
            }

        }
    }
}