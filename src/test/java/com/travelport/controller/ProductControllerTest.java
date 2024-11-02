package com.travelport.controller;

import com.travelport.config.DatabaseConfig;
import com.travelport.config.WebAppConfig;
import com.travelport.config.WebAppInitializer;
import com.travelport.persistence.impl.ClientDaoImpl;
import com.travelport.persistence.impl.ProductDaoImpl;
import com.travelport.service.ClientService;
import com.travelport.service.ProductService;
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

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebAppConfig.class,
        WebAppInitializer.class,
        DatabaseConfig.class,
        ProductController.class,
        ProductService.class,
        ProductDaoImpl.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS) // Solución a que las clases de test corren bien individualmente pero fallan al correr juntas con mvn clean install
class ProductControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private DataSource dataSource;

    MockMvc mockMvc;

    @Test
    void getProducts_ShouldReturnJsonWithProductsAndOkStatus() throws Exception {
        loadInitialData();

        var expectedJson = """
                [
                    {
                     "code": 1,
                     "name": "Laptop"
                     },
                     {
                     "code": 2,
                     "name": "Phone"
                     }
                ]
                """;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(get("/products")
                .header("Accept", "application/json")
                ).andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

    }

    @Test
    void getProductByCode_ValidCode_ReturnProductJsonAndOkStatus() throws Exception {
        loadInitialData();
        var codeToFind = 1;

        var expectedJson = """
                
                {
                 "code": 1,
                 "name": "Laptop"
                 }
                
                """;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(get("/products/{code}", codeToFind)
                .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void getProductByCode_InvalidCode_ShouldReturn404NotFound() throws Exception {
        loadInitialData();
        var codeToFind = 99;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(get("/products/{code}", codeToFind))
                .andExpect(status().isNotFound());
    }

    @Test
    void postProduct_SaveProductAndReturnJsonProductAndOkStatus() throws Exception {
        loadInitialData();

        var productToSave = """
                {
                    "name": "Platano"
                }
                """;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(post("/products")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .content(productToSave))
                .andExpect(status().isOk())
                .andExpect(content().json(productToSave));
    }

    @Test
    void updateProduct_ExistingProduct_ShouldUpdateProductAndReturn200AndJsonOfTheProduct() throws Exception {
        loadInitialData();
        var productCodeToFind = 1;
        var updateData = """
                {
                    "name": "ChangedName"
                }
                """;
        var expectedReturn = """
                {
                    "code": 1,
                    "name": "ChangedName"
                }
                """;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(patch("/products/{code}", productCodeToFind)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .content(updateData))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedReturn));
    }

    @Test
    void updateProduct_NonExistingProduct_ShouldReturn404NotFound() throws Exception {
        loadInitialData();
        var productCodeToFind = 99;
        var updateData = """
                {
                    "name": "ChangedName"
                }
                """;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(patch("/products/{code}", productCodeToFind)
                .header("Content-Type", "application/json")
                .content(updateData))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        loadInitialData();
        var productCodeToDelete = 1;

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(delete("/products/{code}", productCodeToDelete))
                .andExpect(status().isNoContent());
    }

    private void loadInitialData() throws SQLException {
        try (var conn = dataSource.getConnection()) {

            // Asegurarse de que la tabla exista
            try (var createStmt = conn.prepareStatement("""
            CREATE TABLE IF NOT EXISTS products (
                code int AUTO_INCREMENT PRIMARY KEY,
                name varchar(25) DEFAULT NULL
            )
        """)) {
                createStmt.executeUpdate();
            }

            // Eliminar datos existentes
            try (var deleteStmt = conn.prepareStatement("DELETE FROM products")) {
                deleteStmt.executeUpdate();
            }

            // Reiniciar el contador de autoIncrement
            try (var resetStmt = conn.prepareStatement("ALTER TABLE products ALTER COLUMN code RESTART WITH 1")) {
                resetStmt.executeUpdate();
            }

            // Insertar datos iniciales
            try (var stmt = conn.prepareStatement("INSERT INTO products(name) VALUES (?)")) {
                String[] initialData = { "Laptop", "Phone" };

                for (String name : initialData) {
                    stmt.setString(1, name);
                    stmt.executeUpdate();
                }
            }
        }
    }
}