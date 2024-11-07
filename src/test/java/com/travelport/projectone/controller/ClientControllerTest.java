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
import com.travelport.projectone.jpa.ClientJpaRepository;
import com.travelport.projectone.service.ClientServiceImpl;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    WebAppConfig.class,
    WebAppInitializer.class,
    JpaRepositoryConfig.class,
    DatabaseConfig.class,
    ClientController.class,
    ClientJpaRepository.class,
    ClientServiceImpl.class
})
public class ClientControllerTest {

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
  void postClientTest() throws Exception {
    mockMvc.perform(post("/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "id": "87654321B",
                    "name": "Martin",
                    "address": "Av Inventada 2"
                }
            """))
        .andExpect(status().isOk());

    try (var conn = dataSource.getConnection()) {
      var statement = conn.prepareStatement("SELECT * FROM client WHERE id = ?");
      statement.setString(1, "87654321B");
      ResultSet resultSet = statement.executeQuery();
      resultSet.next();
      assertEquals("87654321B", resultSet.getString("id"));
      assertEquals("Martin", resultSet.getString("name"));
      assertEquals("Av Inventada 2", resultSet.getString("address"));
    }
  }

  @Test
  void getAllClientsTest() throws Exception {
    MvcResult result = mockMvc.perform(get("/clients")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    assertTrue(content.startsWith("[") && content.endsWith("]"));
  }

  @Test
  void getClientByIdTest() throws Exception {
    mockMvc.perform(post("/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "id": "87654321B",
                    "name": "Martin",
                    "address": "Av Inventada 2"
                }
            """))
        .andExpect(status().isOk());

    MvcResult result = mockMvc.perform(get("/clients/{clientId}", "87654321B")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    assertTrue(content.contains("\"id\":\"87654321B\""));
    assertTrue(content.contains("\"name\":\"Martin\""));
    assertTrue(content.contains("\"address\":\"Av Inventada 2\""));
  }


  @Test
  void updateClientTest() throws Exception {
    mockMvc.perform(post("/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "id": "87654321B",
                    "name": "Martin",
                    "address": "Av Inventada 2"
                }
            """))
        .andExpect(status().isOk());

    mockMvc.perform(patch("/clients/{clientId}", "87654321B")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "name": "Edu",
                    "address": "Av Inventada 1"
                }
            """))
        .andExpect(status().isOk());

    try (var conn = dataSource.getConnection()) {
      var statement = conn.prepareStatement("SELECT * FROM client WHERE id = ?");
      statement.setString(1, "87654321B");
      ResultSet resultSet = statement.executeQuery();
      resultSet.next();
      assertEquals("Edu", resultSet.getString("name"));
      assertEquals("Av Inventada 1", resultSet.getString("address"));
    }
  }

  @Test
  void deleteClientTest() throws Exception {
    mockMvc.perform(delete("/clients/{clientId}", "45182096E")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    try (var conn = dataSource.getConnection()) {
      var statement = conn.prepareStatement("SELECT * FROM client WHERE id = ?");
      statement.setString(1, "45182096E");
      ResultSet resultSet = statement.executeQuery();
      assertFalse(resultSet.next());
    }
  }
}
