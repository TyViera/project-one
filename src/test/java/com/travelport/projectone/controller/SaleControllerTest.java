package com.travelport.projectone.controller;

import com.travelport.projectone.config.DatabaseConfig;
import com.travelport.projectone.config.JpaRepositoryConfig;
import com.travelport.projectone.config.WebAppConfig;
import com.travelport.projectone.config.WebAppInitializer;
import com.travelport.projectone.entity.Sale;
import com.travelport.projectone.jpa.ClientJpaRepository;
import com.travelport.projectone.service.impl.SaleServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebAppConfig.class,
        WebAppInitializer.class,
        JpaRepositoryConfig.class,
        DatabaseConfig.class,
        SaleController.class,
        SaleServiceImpl.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class SaleControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    MockMvc mockMvc;

    private void executeInitialData() throws SQLException {
        try (var conn = dataSource.getConnection()) {
            resetAutoIncrementValues(conn);

            insertClients(conn);
            insertProducts(conn);
            insertSales(conn);
            insertSalesProducts(conn);
        }
    }

    @BeforeEach
    public void setup() throws SQLException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        clearData();
    }

    private void resetAutoIncrementValues(Connection conn) throws SQLException {
        try (var statement = conn.createStatement()) {
            statement.execute("ALTER TABLE sales ALTER COLUMN id RESTART WITH 1");
        }
        try (var statement = conn.createStatement()) {
            statement.execute("ALTER TABLE products ALTER COLUMN id RESTART WITH 1");
        }
    }

    private void insertClients(Connection conn) throws SQLException {
        try (var statementClient = conn.prepareStatement("INSERT INTO clients (nif, name, address) VALUES (?, ?, ?)")) {
            statementClient.setString(1, "1");
            statementClient.setString(2, "Client name");
            statementClient.setString(3, "Client address");
            statementClient.executeUpdate();
        }
    }

    private void insertProducts(Connection conn) throws SQLException {
        try (var statementProduct1 = conn.prepareStatement("INSERT INTO products (name, times_sold) VALUES (?, 0)")) {
            statementProduct1.setString(1, "Product name 1");
            statementProduct1.executeUpdate();
        }

        try (var statementProduct2 = conn.prepareStatement("INSERT INTO products (name, times_sold) VALUES (?, 0)")) {
            statementProduct2.setString(1, "Product name 2");
            statementProduct2.executeUpdate();
        }
    }

    private void insertSales(Connection conn) throws SQLException {
        try (var statementSale = conn.prepareStatement("INSERT INTO sales (client_nif) VALUES (?)")) {
            statementSale.setString(1, "1");
            statementSale.executeUpdate();
        }
    }

    private void insertSalesProducts(Connection conn) throws SQLException {
        try (var statementSaleProduct1 = conn.prepareStatement("INSERT INTO sales_products (sale_id, product_id, amount) VALUES (?, ?, ?)")) {
            statementSaleProduct1.setInt(1, 1);
            statementSaleProduct1.setInt(2, 1);
            statementSaleProduct1.setInt(3, 3);
            statementSaleProduct1.executeUpdate();

        }

        try (var statementSaleProduct2 = conn.prepareStatement("INSERT INTO sales_products (sale_id, product_id, amount) VALUES (?, ?, ?)")) {
            statementSaleProduct2.setInt(1, 1);
            statementSaleProduct2.setInt(2, 2);
            statementSaleProduct2.setInt(3, 2);
            statementSaleProduct2.executeUpdate();
        }
    }

    private void clearData() throws SQLException {
        try (var conn = dataSource.getConnection()) {
            try (var statement = conn.createStatement()) {
                statement.execute("DELETE FROM sales_products");
            }
            try (var statement = conn.createStatement()) {
                statement.execute("DELETE FROM sales");
            }
            try (var statement = conn.createStatement()) {
                statement.execute("DELETE FROM products");
            }
            try (var statement = conn.createStatement()) {
                statement.execute("DELETE FROM clients");
            }
        }
    }

    @Test
    @Transactional
    @DisplayName("Given new sale When post Then return ok")
    void test_postSale_success() throws Exception {
        // Given (set up)
        var conn = dataSource.getConnection();
        resetAutoIncrementValues(conn);
        insertClients(conn);
        insertProducts(conn);
        conn.close();

        var newSale = """
                {
                    "clientNif": "1",
                    "productList": [
                        {
                            "productId": 1,
                            "amount": 4
                        }
                    ]
                }
                """;

        // When (do actions)
        mockMvc.perform(post("/sales")
                        .content(newSale)
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @DisplayName("Given new sale with non existing client When post Then return unprocessable entity error")
    void test_postSale_nonExistingClient_unprocessableEntityError() throws Exception {
        // Given (set up)
        var conn = dataSource.getConnection();
        insertProducts(conn);
        conn.close();

        var newSale = """
                {
                    "clientNif": "1",
                    "productList": [
                        {
                            "productId": 1,
                            "amount": 4
                        }
                    ]
                }
                """;

        // When (do actions)
        mockMvc.perform(post("/sales")
                        .content(newSale)
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    @DisplayName("Given new sale with non existing product When post Then return unprocessable entity error")
    void test_postSale_nonExistingProduct_unprocessableEntityError() throws Exception {
        // Given (set up)
        var conn = dataSource.getConnection();
        insertClients(conn);
        conn.close();

        var newSale = """
                {
                    "clientNif": "1",
                    "productList": [
                        {
                            "productId": 1,
                            "amount": 4
                        }
                    ]
                }
                """;

        // When (do actions)
        mockMvc.perform(post("/sales")
                        .content(newSale)
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    @DisplayName("Given new sale with invalid product amount (0 or less) When post Then return unprocessable entity error")
    void test_postSale_invalidProductAmount_unprocessableEntityError() throws Exception {
        // Given (set up)
        executeInitialData();

        var newSale = """
                {
                    "clientNif": "1",
                    "productList": [
                        {
                            "productId": 1,
                            "amount": 0
                        }
                    ]
                }
                """;

        // When (do actions)
        mockMvc.perform(post("/sales")
                        .content(newSale)
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    @DisplayName("Given empty database When get Then return not found error")
    void test_getSalesByClientId_notFoundError() throws Exception {
        // When (do actions)
        mockMvc.perform(get("/sales/{id}", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @DisplayName("Given client has made a purchase When get Then return ok")
    void test_getSalesByClientId_success() throws Exception {
        // Given (set up)
        executeInitialData();

        // When (do actions)
        mockMvc.perform(get("/sales/{id}", "1"))
                .andExpect(status().isOk());
    }
}
