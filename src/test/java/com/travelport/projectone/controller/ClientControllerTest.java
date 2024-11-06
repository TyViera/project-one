package com.travelport.projectone.controller;

import com.travelport.projectone.config.DatabaseConfig;
import com.travelport.projectone.config.JpaRepositoryConfig;
import com.travelport.projectone.config.WebAppConfig;
import com.travelport.projectone.config.WebAppInitializer;
import com.travelport.projectone.entity.Client;
import com.travelport.projectone.service.impl.ClientServiceImpl;
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
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebAppConfig.class,
        WebAppInitializer.class,
        JpaRepositoryConfig.class,
        DatabaseConfig.class,
        ClientController.class,
        ClientServiceImpl.class,
        Client.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ClientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    MockMvc mockMvc;
    String sampleUserId = "123";

    private void executeInitialData() throws SQLException {
        try (var conn = dataSource.getConnection()) {
            try (var statement = conn.prepareStatement("INSERT INTO clients VALUES (?, ?, ?)")) {
                statement.setString(1, sampleUserId);
                statement.setString(2, "John Doe");
                statement.setString(3, "Barcelona");

                statement.executeUpdate();
            }
        }
    }

    @BeforeEach
    public void setup() throws SQLException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        clearData();
    }

    public void clearData() throws SQLException {
        try (var conn = dataSource.getConnection()) {
            var statement = conn.prepareStatement("DELETE FROM clients");
            statement.execute();
        }
    }

    @Test
    @Transactional
    @DisplayName("Given new user When post Then return ok")
    void test_post_success() throws Exception {
        // When (do actions)
        mockMvc.perform(post("/clients")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .content("""
                        {
                            "nif": "123",
                            "name": "carlos",
                            "address": "barcelona"
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @DisplayName("Given id already exists When post Return error unprocessable entity")
    void test_post_idAlreadyExists_error() throws Exception {
        // Given (set up)
        executeInitialData();

        // When (do actions)
        mockMvc.perform(post("/clients")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .content("""
                        {
                            "nif": "123",
                            "name": "carlos",
                            "address": "barcelona"
                        }
                        """))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    @DisplayName("Given existing user When patch Then return ok")
    void test_update_success() throws Exception {
        executeInitialData();

        // When (do actions)
        mockMvc.perform(patch("/clients/{id}", sampleUserId)
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
    @DisplayName("Given empty database When patch Then return not found error")
    void test_update_notFoundError() throws Exception {
         // When (do actions)
        mockMvc.perform(patch("/clients/{id}", sampleUserId)
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
    @Transactional
    @DisplayName("Given empty database When delete Then return error not found")
    void test_delete_notFoundError() throws Exception {
        System.out.println("starting test_delete_notFoundError");
        clearData();

        // When (do actions)
        System.out.println("About to DELETE");
        mockMvc.perform(delete("/clients/{id}", sampleUserId))
                .andExpect(status().isNotFound());

        System.out.println("End test_delete_notFoundError");
    }

    @Test
    @Transactional
    @DisplayName("Given client exists in database When delete Then return ok")
    void test_delete_success() throws Exception {
        // Given (set up)
        executeInitialData();

        // When (do actions)
        mockMvc.perform(delete("/clients/{id}", sampleUserId))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @DisplayName("When get Then return ok")
    void test_get_success() throws Exception {
        // When (do actions)
        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @DisplayName("Given empty database When get by id Then return error not found")
    void test_getById_notFoundError() throws Exception {
        // When (do actions)
        mockMvc.perform(get("/clients/{id}", sampleUserId))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @DisplayName("Given client exists in database When get by id Then return ok")
    void test_getById_success() throws Exception {
        // Given (set up)
        executeInitialData();

        // When (do actions)
        mockMvc.perform(get("/clients/{id}", sampleUserId))
                .andExpect(status().isOk());
    }
}
