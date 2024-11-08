package com.travelport.projectone.controller;

import com.travelport.projectone.config.DatabaseConfig;
import com.travelport.projectone.config.WebAppConfig;
import com.travelport.projectone.config.WebAppInitializer;

import com.travelport.projectone.persistence.impl.ClientDaoImpl;
import com.travelport.projectone.persistence.impl.ProductDaoImpl;
import com.travelport.projectone.persistence.impl.SaleDaoImpl;
import com.travelport.projectone.service.impl.SaleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebAppConfig.class,
        WebAppInitializer.class,
        DatabaseConfig.class,
        SaleController.class,
        SaleServiceImpl.class,
        ProductDaoImpl.class,
        ClientDaoImpl.class,
        SaleDaoImpl.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SaleControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetPastSales() throws Exception {
        executeInitialData();

        String expected = "[{\"saleId\":1,\"products\":[{\"id\":1,\"code\":\"JJK2342\",\"name\":\"IPear\",\"quantity\":2},{\"id\":2,\"code\":\"HGTY67\",\"name\":\"Digital Camera\",\"quantity\":3}]}]";

        MvcResult result = mockMvc.perform(get("/sales/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected.trim(), content.trim());
    }


    private void executeInitialData() {
        try (var conn = dataSource.getConnection()) {

            try (var createClientsTable = conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS `clients` (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    nif VARCHAR(20) NOT NULL UNIQUE,
                    address VARCHAR(255) NOT NULL
                )
            """)) {
                createClientsTable.executeUpdate();
            }

            try (var createProductsTable = conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS `products` (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    code VARCHAR(50) NOT NULL UNIQUE
                )
            """)) {
                createProductsTable.executeUpdate();
            }

            try (var createTableSales = conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS `sales` (
                    id INT NOT NULL AUTO_INCREMENT,
                    client_id INT NOT NULL,
                    sell_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    PRIMARY KEY (id),
                    FOREIGN KEY (client_id) REFERENCES clients(id)
                )
            """)) {
                createTableSales.executeUpdate();
            }

            try (var createTableSaleDetails = conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS `sales_det` (
                    sale_id INT NOT NULL,
                    product_id INT NOT NULL,
                    quantity INT NOT NULL,
                    PRIMARY KEY (sale_id, product_id),
                    FOREIGN KEY (sale_id) REFERENCES sales(id) ON DELETE CASCADE,
                    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT
                )
            """)) {
                createTableSaleDetails.executeUpdate();
            }

            try (var deleteTableContents = conn.createStatement()) {
                deleteTableContents.execute("DELETE FROM sales_det");
                deleteTableContents.execute("DELETE FROM sales");
                deleteTableContents.execute("DELETE FROM clients");
                deleteTableContents.execute("DELETE FROM products");
            }

            try (var resetTableIncrement = conn.createStatement()) {
                resetTableIncrement.execute("ALTER TABLE products ALTER COLUMN id RESTART WITH 1;");
                resetTableIncrement.execute("ALTER TABLE sales ALTER COLUMN id RESTART WITH 1;");
            }

            try (var insertProducts = conn.prepareStatement("INSERT INTO products (name, code) VALUES (?, ?)")) {
                Object[][] products = {
                        {"IPear", "JJK2342"},
                        {"Digital Camera", "HGTY67"}
                };
                for (Object[] product : products) {
                    insertProducts.setString(1, (String) product[0]);
                    insertProducts.setString(2, (String) product[1]);
                    insertProducts.executeUpdate();
                }
            }

            try (var insertClients = conn.prepareStatement("INSERT INTO clients (nif, name, address) VALUES (?, ?, ?)")) {
                Object[][] clients = {
                        {"23412123S", "Pepe Botella", "Crt. Barcelona 5"},
                        {"76546745F", "Juana de Arco", "Snt. Francia Street"}
                };
                for (Object[] client : clients) {
                    insertClients.setString(1, (String) client[0]);
                    insertClients.setString(2, (String) client[1]);
                    insertClients.setString(3, (String) client[2]);
                    insertClients.executeUpdate();
                }
            }

            try (var insertNewSale = conn.prepareStatement("INSERT INTO sales (client_id, sell_date) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                insertNewSale.setInt(1, 1);
                insertNewSale.setTimestamp(2, Timestamp.valueOf("2023-01-15 10:30:00"));

                insertNewSale.executeUpdate();

                ResultSet rs = insertNewSale.getGeneratedKeys();
                int saleId = 0;
                if (rs.next()) {
                    saleId = rs.getInt(1);
                }

                try (var insertSaleDetailStmt = conn.prepareStatement("INSERT INTO sales_det (sale_id, product_id, quantity) VALUES (?, ?, ?)")) {
                    insertSaleDetailStmt.setInt(1, saleId);
                    insertSaleDetailStmt.setInt(2, 1);
                    insertSaleDetailStmt.setInt(3, 2);
                    insertSaleDetailStmt.executeUpdate();

                    insertSaleDetailStmt.setInt(1, saleId);
                    insertSaleDetailStmt.setInt(2, 2);
                    insertSaleDetailStmt.setInt(3, 3);
                    insertSaleDetailStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error ejecutando el script SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
