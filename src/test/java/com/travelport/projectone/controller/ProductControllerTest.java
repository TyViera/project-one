package com.travelport.projectone.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.travelport.projectone.config.DatabaseConfig;
import com.travelport.projectone.config.JpaRepositoryConfig;
import com.travelport.projectone.config.WebAppConfig;
import com.travelport.projectone.config.WebAppInitializer;
import com.travelport.projectone.persistence.ProductDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebAppConfig.class,
        WebAppInitializer.class,
        JpaRepositoryConfig.class,
        DatabaseConfig.class,
        ProductController.class,
        ProductDao.class
})
class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws SQLException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        executeInitialData();
    }

    @Test
    void postProductTest() throws Exception {
        mockMvc.perform(post("/product")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .content("""
            {
              "name": "Laptop",
              "code": 102
            }
            """))
                .andExpect(status().isOk());
    }

    @Test
    void getProductTest() throws Exception {
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void getProductByCodeTest() throws Exception {
        mockMvc.perform(get("/product/{code}", 101))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Smartphone"));
    }

    @Test
    void patchProductTest() throws Exception {
        mockMvc.perform(patch("/product/{code}", 101)
                        .header("Content-Type", "application/json")
                        .content("""
            {
              "name": "Tablet"
            }
            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tablet"));
    }

    @Test
    void deleteProductTest() throws Exception {
        mockMvc.perform(delete("/product/{code}", 101))
                .andExpect(status().isNoContent());
    }

    private void executeInitialData() throws SQLException {
        var conn = dataSource.getConnection();
        var statement = conn.prepareStatement("INSERT INTO products (code, name) VALUES (?, ?)");
        statement.setInt(1, 101);
        statement.setString(2, "Smartphone");

        statement.executeUpdate();
        conn.close();
    }
}
