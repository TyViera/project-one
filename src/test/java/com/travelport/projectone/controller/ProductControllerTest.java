package com.travelport.projectone.controller;

import com.travelport.projectone.config.DatabaseConfig;
import com.travelport.projectone.config.JpaRepositoryConfig;
import com.travelport.projectone.config.WebAppConfig;
import com.travelport.projectone.config.WebAppInitializer;
import com.travelport.projectone.entity.Product;
import com.travelport.projectone.service.impl.ClientServiceImpl;
import com.travelport.projectone.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebAppConfig.class,
        WebAppInitializer.class,
        JpaRepositoryConfig.class,
        DatabaseConfig.class,
        ProductController.class,
        ProductServiceImpl.class,
        Product.class
})
@Transactional
class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    MockMvc mockMvc;

    private void executeInitialData() throws SQLException {
        var conn = dataSource.getConnection();

        conn.createStatement().execute("ALTER TABLE products ALTER COLUMN id RESTART WITH 1");

        var statement = conn.prepareStatement("INSERT INTO products VALUES (DEFAULT, ?, DEFAULT)");
        statement.setString(1, "T-Shirt");

        statement.executeUpdate();

        conn.close();
    }

    @BeforeEach
    public void setup() throws SQLException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        clearData();
    }

    public void clearData() throws SQLException {
        var conn = dataSource.getConnection();
        var statement = conn.prepareStatement("DELETE FROM products");
        statement.execute();
    }

    @Test
    @Transactional
    @Rollback
    @Timeout(4)
    @DisplayName("Given new product When post Then return ok")
    void test_post_success() throws Exception {
        // When (do actions)
        mockMvc.perform(post("/products")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .content("""
                        {
                            "name": "laptop"
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback
    @Timeout(4)
    @DisplayName("Given new product When post with bad format Then return bad request error")
    void test_post_badRequestError() throws Exception {
        // When (do actions)
        mockMvc.perform(post("/products")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .content("""
                        {
                            "name": "laptop",
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    @Timeout(4)
    @DisplayName("Given existing product When patch Then return ok")
    void test_update_success() throws Exception {
        executeInitialData();

        // When (do actions)
        mockMvc.perform(patch("/products/{id}", 1)
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .content("""
                        {
                            "name": "new name"
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback
    @Timeout(4)
    @DisplayName("Given empty database When patch Then return not found error")
    void test_update_notFoundError() throws Exception {
        // When (do actions)
        mockMvc.perform(patch("/products/{id}", 1)
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .content("""
                        {
                            "name": "new name"
                        }
                        """))
                .andExpect(status().isNotFound());
    }

    @Test
    @Disabled
    @Transactional
    @Rollback
    @Timeout(4)
    @DisplayName("Given empty database When delete Then return error not found")
    void test_delete_notFoundError() throws Exception {
        System.out.println("starting test_delete_notFoundError");
        clearData();

        // When (do actions)
        System.out.println("About to DELETE");
        mockMvc.perform(delete("/products/{id}", 1))
                .andExpect(status().isNotFound());

        System.out.println("End test_delete_notFoundError");
    }

    @Test
    @Disabled
    @Transactional
    @Rollback
    @Timeout(4)
    @DisplayName("Given client exists in database When delete Then return ok")
    void test_delete_success() throws Exception {
        // Given (set up)
        executeInitialData();

        // When (do actions)
        mockMvc.perform(delete("/products/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback
    @Timeout(4)
    @DisplayName("When get Then return ok")
    void test_get_success() throws Exception {
        // When (do actions)
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback
    @Timeout(4)
    @DisplayName("Given empty database When get by id Then return error not found")
    void test_getById_notFoundError() throws Exception {
        // When (do actions)
        mockMvc.perform(get("/products/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Rollback
    @Timeout(4)
    @DisplayName("Given product exists in database When get by id Then return ok")
    void test_getById_success() throws Exception {
        // Given (set up)
        executeInitialData();

        // When (do actions)
        mockMvc.perform(get("/products/{id}", 1))
                .andExpect(status().isOk());
    }
}
