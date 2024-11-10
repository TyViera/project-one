package com.travelport.projectone.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.travelport.projectone.config.DatabaseConfig;
import com.travelport.projectone.config.JpaRepositoryConfig;
import com.travelport.projectone.config.WebAppConfig;
import com.travelport.projectone.config.WebAppInitializer;
import com.travelport.projectone.persistence.ClientDao;
import com.travelport.projectone.entities.Client;
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
        ClientController.class,
        ClientDao.class
})
class ClientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    MockMvc mockMvc;

    @Test
    void postClientTest() throws Exception {
        executeInitialData();

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(post("/client")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .content("""
            {
              "name": "John Doe",
              "address": "123 Main St",
              "nif": "123456789"
            }
            """))
                .andExpect(status().isOk());
    }

    @Test
    void getClientByNifTest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/client/123456789"))
                .andExpect(status().isOk());
    }

    @Test
    void patchClientTest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(patch("/client/123456789")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .content("""
            {
              "name": "Jane Doe",
              "address": "456 Secondary St"
            }
            """))
                .andExpect(status().isOk());
    }

    @Test
    void deleteClientByNifTest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(delete("/client/123456789"))
                .andExpect(status().isNoContent());
    }

    private void executeInitialData() throws SQLException {
        var conn = dataSource.getConnection();
        var statement = conn.prepareStatement("INSERT INTO client (nif, name, address) VALUES (?, ?, ?)");
        statement.setString(1, "123456789");
        statement.setString(2, "John Doe");
        statement.setString(3, "Spain");

        statement.executeUpdate();
    }
}