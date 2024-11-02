package com.travelport.controller;

import com.travelport.config.DatabaseConfig;
import com.travelport.config.WebAppConfig;
import com.travelport.config.WebAppInitializer;
import com.travelport.persistence.impl.ClientDaoImpl;
import com.travelport.service.ClientService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebAppConfig.class,
        WebAppInitializer.class,
        DatabaseConfig.class,
        ClientController.class,
        ClientService.class,
        ClientDaoImpl.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS) // Solución a que las clases de test corren bien individualmente pero fallan al correr juntas con mvn clean install
class ClientControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private DataSource dataSource;

    MockMvc mockMvc;


    @Test
    void getClients_ShouldReturnJsonWithClientsAndOkStatus() throws Exception {
        // Set up
        loadInitialData();

        // Expected JSON response
        String expectedJson = """
        [
            {
                "nif": "12345678A",
                "name": "John Doe",
                "address": "Main St 123"
            },
            {
                "nif": "98765432B",
                "name": "Jane Doe",
                "address": "Main St 456"
            }
        ]
        """;


        // Do
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(get("/clients")
                .header("Accept", "application/json")
        ).andExpect(status().isOk()).andExpect(content().json(expectedJson));
    }

    @Test
    void getClientByNif_WithExistingNif_ShouldReturn1Client() throws Exception {
        loadInitialData();
        var nifToFind = "12345678A";

        String expectedJson = """
                {
                    "nif": "12345678A",
                    "name": "John Doe",
                    "address": "Main St 123"
                }
                """;

        // DO
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(get("/clients/{nif}", nifToFind)
                .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void getClientByNif_WithNonExistingNif_ShouldReturn404() throws Exception {
        loadInitialData();
        var nifToFind = "00000000Z";

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(get("/clients/{nif}", nifToFind)
                .header("Accept", "application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void postClient_ShouldReturnJsonWithClientAndOkStatus() throws Exception {
        loadInitialData();

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(post("/clients")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .content("""
                        {
                            "nif": "49831717L",
                            "name": "Dr Max",
                            "address": "Main St"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "nif": "49831717L",
                            "name": "Dr Max",
                            "address": "Main St"
                        }
                """));
    }

    @Test
    void updateClient_ExistingClient_ShouldUpdateClientAndReturn200AndTheClient() throws Exception {
        loadInitialData();
        var nifToUpdate = "12345678A";

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(patch("/clients/{nif}", nifToUpdate)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .content("""
                        {
                            "name": "Max Dron",
                            "address": "Russia St"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                       {
                          "nif": "12345678A",
                          "name": "Max Dron",
                          "address": "Russia St"
                        }     
                """));

    }

    @Test
    void updateClient_NonExistingClient_ShouldReturn404() throws Exception {
        loadInitialData();
        var nifToUpdate = "00000000Z";

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(patch("/clients/{nif}", nifToUpdate)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .content("""
                         {
                            "name": "Max Dron",
                            "address": "Russia St"
                        }
                        """))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteClient_shouldDeleteClientAndReturnNoContent() throws Exception {
        loadInitialData();
        var nifToDelete = "12345678A";

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        mockMvc.perform(delete("/clients/{nif}", nifToDelete))
                .andExpect(status().isNoContent());
    }

    private void loadInitialData() throws SQLException {
        try (var conn = dataSource.getConnection()) {

            // Asegurarse de que la tabla exista
            try (var createStmt = conn.prepareStatement("""
            CREATE TABLE IF NOT EXISTS clients (
                nif VARCHAR(255) PRIMARY KEY,
                name VARCHAR(255),
                address VARCHAR(255)
            )
        """)) {
                createStmt.executeUpdate();
            }

            // Eliminar datos existentes
            try (var deleteStmt = conn.prepareStatement("DELETE FROM clients")) {
                deleteStmt.executeUpdate();
            }

            // Insertar datos iniciales
            try (var stmt = conn.prepareStatement("INSERT INTO clients(nif, name, address) VALUES (?, ?, ?)")) {
                Object[][] initialData = {
                        {"12345678A", "John Doe", "Main St 123"},
                        {"98765432B", "Jane Doe", "Main St 456"}
                };

                for (Object[] clientData : initialData) {
                    stmt.setString(1, (String) clientData[0]);
                    stmt.setString(2, (String) clientData[1]);
                    stmt.setString(3, (String) clientData[2]);
                    stmt.executeUpdate();
                }
            }
        }
    }



}