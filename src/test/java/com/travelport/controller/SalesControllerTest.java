package com.travelport.controller;

import com.travelport.config.DatabaseConfig;
import com.travelport.config.WebAppConfig;
import com.travelport.config.WebAppInitializer;
import com.travelport.persistence.impl.ClientDaoImpl;
import com.travelport.persistence.impl.ProductDaoImpl;
import com.travelport.persistence.impl.SalesDaoImpl;
import com.travelport.service.ClientService;
import com.travelport.service.ProductService;
import com.travelport.service.SalesService;
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

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.Statement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebAppConfig.class,
        WebAppInitializer.class,
        DatabaseConfig.class,
        SalesController.class,
        SalesService.class,
        SalesDaoImpl.class,
        ProductService.class,
        ProductDaoImpl.class,
        ClientService.class,
        ClientDaoImpl.class,
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS) // Solución a que las clases de test corren bien individualmente, pero fallan al correr juntas con mvn clean install
class SalesControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private DataSource dataSource;

    MockMvc mockMvc;

    @Test
    void postSale_WithValidNif_ShouldCreateSaleAndReturnSaleJson() throws Exception {
        loadInitialData(true);

        // Build SaleDTO JSON
        var saleDTOJson = """
                {
                    "nif": "12345678A",
                    "products" : [
                        {
                            "productCode": 1,
                            "quantity": 5
                        },
                        {
                            "productCode": 2,
                            "quantity": 3
                        }
                    ]
                }
                """;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(post("/sales")
                .contentType("application/json")
                .content(saleDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.client.nif").value("12345678A"))
                .andExpect(jsonPath("$.saleDetails").isArray())
                .andExpect(jsonPath("$.saleDetails[0].product.code").value(1))
                .andExpect(jsonPath("$.saleDetails[0].quantity").value(5))
                .andExpect(jsonPath("$.saleDetails[1].product.code").value(2))
                .andExpect(jsonPath("$.saleDetails[1].quantity").value(3));
    }

    @Test
    void postSale_WithInvalidNif_ShouldReturnNotFound() throws Exception {
        loadInitialData(true);

        // Build SaleDTO JSON
        var saleDTOJson = """
                {
                    "nif": "00000000A",
                    "products" : [
                        {
                            "productCode": 1,
                            "quantity": 5
                        },
                        {
                            "productCode": 2,
                            "quantity": 3
                        }
                    ]
                }
                """;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(post("/sales")
                        .contentType("application/json")
                        .content(saleDTOJson))
                .andExpect(status().isNotFound());

    }

    @Test
    void postSales_WithNullQuantity_ShouldReturnNotFound() throws Exception {
        loadInitialData(true);

        // Build SaleDTO JSON
        var saleDTOJson = """
                {
                    "nif": "12345678A",
                    "products" : [
                        {
                            "productCode": 1,
                            "quantity": null
                        },
                        {
                            "productCode": 2,
                            "quantity": 3
                        }
                    ]
                }
                """;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(post("/sales")
                .contentType("application/json")
                .content(saleDTOJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void postSales_WithZeroQuantity_ShouldReturnNotFound() throws Exception {
        loadInitialData(true);

        // Build SaleDTO JSON
        var saleDTOJson = """
                {
                    "nif": "12345678A",
                    "products" : [
                        {
                            "productCode": 1,
                            "quantity": 0
                        },
                        {
                            "productCode": 2,
                            "quantity": 3
                        }
                    ]
                }
                """;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(post("/sales")
                        .contentType("application/json")
                        .content(saleDTOJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void postSales_WithDecimalQuantity_ShouldReturnNotFound() throws Exception {
        loadInitialData(true);

        // Build SaleDTO JSON
        var saleDTOJson = """
                {
                    "nif": "12345678A",
                    "products" : [
                        {
                            "productCode": 1,
                            "quantity": 1.3
                        },
                        {
                            "productCode": 2,
                            "quantity": 3
                        }
                    ]
                }
                """;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(post("/sales")
                        .contentType("application/json")
                        .content(saleDTOJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void postSales_WithInvalidProductId_ShouldReturnNotFound() throws Exception {
        loadInitialData(true);

        // Build SaleDTO JSON
        var saleDTOJson = """
                {
                    "nif": "12345678A",
                    "products" : [
                        {
                            "productCode": 1,
                            "quantity": 1
                        },
                        {
                            "productCode": 34,
                            "quantity": 3
                        }
                    ]
                }
                """;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(post("/sales")
                        .contentType("application/json")
                        .content(saleDTOJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void getSalesByClient_WithExistingSales_ShouldReturnClientSales() throws Exception {
        loadInitialData(true);

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(get("/sales/{nif}", "12345678A")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nif").value("12345678A"))
                .andExpect(jsonPath("$.sales").isArray())
                .andExpect(jsonPath("$.sales[0].saleId").value(1))
                .andExpect(jsonPath("$.sales[0].products[0].code").value(1))
                .andExpect(jsonPath("$.sales[0].products[0].quantity").value(2))
                .andExpect(jsonPath("$.sales[0].products[1].code").value(2))
                .andExpect(jsonPath("$.sales[0].products[1].quantity").value(3));
    }

    @Test
    void getSalesByClient_WithoutSalesRelatedToThisClient_ShouldReturnNotFound() throws Exception {
        loadInitialData(false);

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(get("/sales/{nif}", "12345678A"))
                .andExpect(status().isNotFound());
    }


    private void loadInitialData(boolean includeSales) throws Exception {
        try (var conn = dataSource.getConnection()) {

            // Create Tables
            try (var createTableProducts = conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS products (
                    code INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(25)
                )
        """)) {
                createTableProducts.executeUpdate();
            }

            try (var createTableClients = conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS clients (
                    nif VARCHAR(255) NOT NULL PRIMARY KEY,
                    name VARCHAR(255) DEFAULT NULL,
                    address VARCHAR(255) DEFAULT NULL
                )
         """)) {
                createTableClients.executeUpdate();
            }

            try (var createTableSales = conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS sales_cab (
                    id INT NOT NULL AUTO_INCREMENT,
                    client_nif VARCHAR(255),
                    sell_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    PRIMARY KEY (id),
                    FOREIGN KEY (client_nif) REFERENCES clients (nif)
                )
         """)) {
                createTableSales.executeUpdate();
            }

            try (var createTableSaleDetails = conn.prepareStatement("""
            CREATE TABLE IF NOT EXISTS sales_det (
                sale_id INT NOT NULL,
                product_code INT NOT NULL,
                quantity INT NOT NULL,
                PRIMARY KEY (sale_id, product_code),
                FOREIGN KEY (sale_id) REFERENCES sales_cab (id),
                FOREIGN KEY (product_code) REFERENCES products (code)
            )
         """)) {
                createTableSaleDetails.executeUpdate();
            }

            // Clear Tables
            try (var deleteStmt = conn.createStatement()) {
                deleteStmt.execute("DELETE FROM sales_det");
                deleteStmt.execute("DELETE FROM sales_cab");
                deleteStmt.execute("DELETE FROM clients");
                deleteStmt.execute("DELETE FROM products");
            }

            // Reset AUTO_INCREMENT counters
            try (var resetStmt = conn.createStatement()) {
                resetStmt.execute("ALTER TABLE products ALTER COLUMN code RESTART WITH 1");
                resetStmt.execute("ALTER TABLE sales_cab ALTER COLUMN id RESTART WITH 1");
            }

            // Insert Products
            try (var stmt = conn.prepareStatement("INSERT INTO products (name) VALUES (?)")) {
                String[] productNames = {"Laptop", "Phone"};
                for (String name : productNames) {
                    stmt.setString(1, name);
                    stmt.executeUpdate();
                }
            }

            // Insert Clients
            try (var stmt = conn.prepareStatement("INSERT INTO clients (nif, name, address) VALUES (?, ?, ?)")) {
                Object[][] clients = {
                        {"12345678A", "John Doe", "Main St 123"},
                        {"98765432B", "Jane Smith", "Second St 456"}
                };
                for (Object[] client : clients) {
                    stmt.setString(1, (String) client[0]);
                    stmt.setString(2, (String) client[1]);
                    stmt.setString(3, (String) client[2]);
                    stmt.executeUpdate();
                }
            }
            if (includeSales) {
                // Insert Sale
                try (var insertSaleStmt = conn.prepareStatement("INSERT INTO sales_cab (client_nif) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
                    insertSaleStmt.setString(1, "12345678A");
                    insertSaleStmt.executeUpdate();

                    // Get generated sale_id
                    ResultSet rs = insertSaleStmt.getGeneratedKeys();
                    int saleId = 0;
                    if (rs.next()) {
                        saleId = rs.getInt(1);
                    }

                    // Insert Sale Details
                    try (var insertSaleDetailStmt = conn.prepareStatement("INSERT INTO sales_det (sale_id, product_code, quantity) VALUES (?, ?, ?)")) {
                        insertSaleDetailStmt.setInt(1, saleId);
                        insertSaleDetailStmt.setInt(2, 1); // product code 1
                        insertSaleDetailStmt.setInt(3, 2); // quantity 2
                        insertSaleDetailStmt.executeUpdate();

                        insertSaleDetailStmt.setInt(1, saleId);
                        insertSaleDetailStmt.setInt(2, 2); // product code 2
                        insertSaleDetailStmt.setInt(3, 3); // quantity 3
                        insertSaleDetailStmt.executeUpdate();
                    }
                }
            }
        }
    }
}