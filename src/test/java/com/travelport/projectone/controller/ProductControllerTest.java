package com.travelport.projectone.controller;

import com.travelport.projectone.config.DatabaseConfig;
import com.travelport.projectone.config.WebAppConfig;
import com.travelport.projectone.config.WebAppInitializer;
import com.travelport.projectone.persistence.impl.ProductDaoImpl;
import com.travelport.projectone.service.impl.ProductServiceImpl;
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

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebAppConfig.class,
        WebAppInitializer.class,
        DatabaseConfig.class,
        ProductController.class,
        ProductServiceImpl.class,
        ProductDaoImpl.class,
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductControllerTest {

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
    public void testGetAllProducts() throws Exception {
        executeInitialData();
        String expected = """
                [{"id":1,"code":"HHJ868","name":"Laptop"},{"id":2,"code":"NBM234","name":"Smartphone"},{"id":3,"code":"7878879F","name":"Electric guitar"}]""";

        MvcResult result = mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected, content);
    }

    @Test
    public void testPostProduct() throws Exception {
        String expected = """
                {"id":1,"code":"GY567","name":"Iphone X"}""";

        MvcResult result = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                {"code":"GY567","name":"Iphone X"}
                """))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected.trim(), content.trim());
    }

    @Test
    public void testFindProductById_Success() throws Exception {
        executeInitialData();
        String expected = """
                {"id":2,"code":"NBM234","name":"Smartphone"}""";

        MvcResult result = mockMvc.perform(get("/products/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected.trim(), content.trim());
    }

    @Test
    public void testFindProductById_Failure() throws Exception {
        String expected = "";

        MvcResult result = mockMvc.perform(get("/products/20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected.trim(), content.trim());
    }

    @Test
    public void testUpdateProductById_Success() throws Exception {
        executeInitialData();
        String expected = """
                {"id":3,"code":"7878879F","name":"Guitar Updated"}""";

        MvcResult result = mockMvc.perform(patch("/products/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                {"name":"Guitar Updated","code":"7878879F"}
                """))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected.trim(), content.trim());
    }

    @Test
    public void testDeleteProductById_Success() throws Exception {
        String expected = "";

        MvcResult result = mockMvc.perform(delete("/products/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected, content);
    }

    private void executeInitialData() throws SQLException {
        var conn = dataSource.getConnection();

        try {
            var clearClientsStatement = conn.prepareStatement("DELETE FROM products");
            clearClientsStatement.executeUpdate();

            var resetSequenceStatement = conn.prepareStatement("ALTER TABLE products ALTER COLUMN id RESTART WITH 1");
            resetSequenceStatement.executeUpdate();

            var statement = conn.prepareStatement("INSERT INTO products (name, code) VALUES (?, ?)");

            statement.setString(1, "Laptop");
            statement.setString(2, "HHJ868");
            statement.executeUpdate();

            statement.setString(1, "Smartphone");
            statement.setString(2, "NBM234");
            statement.executeUpdate();

            statement.setString(1, "Electric guitar");
            statement.setString(2, "7878879F");
            statement.executeUpdate();
        } finally {
            conn.close();
        }
    }
}
