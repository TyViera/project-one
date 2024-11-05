package com.travelport.projectone.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.travelport.projectone.config.DatabaseConfig;
import com.travelport.projectone.config.JpaRepositoryConfig;
import com.travelport.projectone.config.WebAppConfig;
import com.travelport.projectone.config.WebAppInitializer;
import com.travelport.projectone.jpa.ProductJpaRepository;
import com.travelport.projectone.service.ProductServiceImpl;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    WebAppConfig.class,
    WebAppInitializer.class,
    JpaRepositoryConfig.class,
    DatabaseConfig.class,
    ProductController.class,
    ProductJpaRepository.class,
    ProductServiceImpl.class
})
public class ProductControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private DataSource dataSource;

  private MockMvc mockMvc;
  private int createdProductId;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void postProductTest() throws Exception {
    MvcResult result = mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "name": "ACOTAR"
                    }
                """))
        .andExpect(status().isOk())
        .andReturn();

    try (var conn = dataSource.getConnection()) {
      var statement = conn.prepareStatement("SELECT * FROM product WHERE name = ?");
      statement.setString(1, "ACOTAR");
      ResultSet resultSet = statement.executeQuery();
      assertTrue(resultSet.next());
      createdProductId = resultSet.getInt("id");
      assertEquals("ACOTAR", resultSet.getString("name"));
    }
  }

  @Test
  void getAllProductsTest() throws Exception {
    MvcResult result = mockMvc.perform(get("/products")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    assertTrue(content.startsWith("[") && content.endsWith("]"));
  }

  @Test
  void getProductByIdTest() throws Exception {
    postProductTest();

    MvcResult result = mockMvc.perform(get("/products/{id}", createdProductId)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    assertTrue(content.contains("\"name\":\"ACOTAR\""));
  }

  @Test
  void updateProductTest() throws Exception {
    postProductTest();

    mockMvc.perform(patch("/products/{id}", createdProductId)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "name": "ACOMAF"
                    }
                """))
        .andExpect(status().isOk());

    try (var conn = dataSource.getConnection()) {
      var statement = conn.prepareStatement("SELECT * FROM product WHERE id = ?");
      statement.setInt(1, createdProductId);
      ResultSet resultSet = statement.executeQuery();
      assertTrue(resultSet.next());
      assertEquals("ACOMAF", resultSet.getString("name"));
    }
  }

  @Test
  void deleteProductTest() throws Exception {
    postProductTest();

    mockMvc.perform(delete("/products/{id}", createdProductId)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    try (var conn = dataSource.getConnection()) {
      var statement = conn.prepareStatement("SELECT * FROM product WHERE id = ?");
      statement.setInt(1, createdProductId);
      ResultSet resultSet = statement.executeQuery();
      assertFalse(resultSet.next());
    }
  }
}
