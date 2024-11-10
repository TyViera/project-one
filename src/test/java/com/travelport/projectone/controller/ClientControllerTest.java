package com.travelport.projectone.controller;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import com.travelport.projectone.config.WebAppConfig;
import com.travelport.projectone.config.WebAppInitializer;
import com.travelport.projectone.config.DataBaseConfig;
import com.travelport.projectone.config.JpaRepositoryConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.sql.DataSource;
import java.sql.SQLException;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        DataBaseConfig.class,
        JpaRepositoryConfig.class,
        WebAppConfig.class,
        WebAppInitializer.class,
        ClientController.class,
        Client.class,
        ClientService.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ClientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    MockMvc mockMvc;
    String testClientId = "2821";

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testDelete() throws Exception {
        inicialice();

        mockMvc.perform(delete("/clients/{id}", testClientId))
                .andExpect(status().isOk());
    }

    @Test
    void testGetClientByNifTest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/client/123456789"))
                .andExpect(status().isOk());
    }

    private void inicialice() throws SQLException {
        var conn = dataSource.getConnection();

        var clearSalesDetStatement = conn.prepareStatement("DELETE FROM clients");
        clearSalesDetStatement.executeUpdate();

        var resetSequenceStatement = conn.prepareStatement("ALTER TABLE clients ALTER COLUMN id RESTART WITH 1");
        clearSalesDetStatement.executeUpdate();

        var statement = conn.prepareStatement("INSERT INTO clients (name, nif, address) VALUES (?, ?, ?)");

        statement.setString(1, "David Segura");
        statement.setString(2, testClientId);
        statement.setString(3, "Via Augusta, 21");
        statement.executeUpdate();

        statement.setString(1, "Nacho Muñoz");
        statement.setString(2, "49271638T");
        statement.setString(3, "Carrer Margenat, 3");
        statement.executeUpdate();

        statement.setString(1, "Matias Nieto");
        statement.setString(2, "20174937W");
        statement.setString(3, "Carrer Major, 97");
        statement.executeUpdate();
    }
}
