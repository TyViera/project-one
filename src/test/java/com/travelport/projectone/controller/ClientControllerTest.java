package com.travelport.projectone.controller;

import com.travelport.projectone.config.DatabaseConfig;
import com.travelport.projectone.config.WebAppConfig;
import com.travelport.projectone.config.WebAppInitializer;
import com.travelport.projectone.persistence.impl.ClientDaoImpl;
import com.travelport.projectone.service.impl.ClientServiceImpl;
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
        ClientController.class,
        ClientServiceImpl.class,
        ClientDaoImpl.class,
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    private DataSource dataSource;

    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllClients() throws Exception{
        executeInitialData();
        String expected= """
                [{"id":1,"nif":"2434224335S","name":"Carlos Martinez Test","address":"Calle Mayor, 10, Madrid, Spain"},{"id":2,"nif":"334567891K","name":"Ana Gomez Test","address":"Calle de la Libertad, 15, Barcelona, Spain"},{"id":3,"nif":"334567891Z","name":"Juan Magan Test","address":"Calle de la Libertad, 17, Barcelona, Spain"}]""";
        MvcResult result = mockMvc.perform(get("/clients")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected.trim(), content.trim());
    }

    @Test
    public void testPostClient() throws Exception {
        String expected = """
        {"id":4,"nif":"445434534G","name":"Pablo Manuel","address":"Calle Mayor, 9, Zaragoza, Spain"}
        """;

        MvcResult result = mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                {"nif":"445434534G","name":"Pablo Manuel","address":"Calle Mayor, 9, Zaragoza, Spain"}
                """))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected.trim(), content.trim());
    }

    @Test
    public void testFindById_Success() throws Exception {
        executeInitialData();
        String expected= """
                {"id":2,"nif":"334567891K","name":"Ana Gomez Test","address":"Calle de la Libertad, 15, Barcelona, Spain"}""";
        MvcResult result = mockMvc.perform(get("/clients/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected.trim(), content.trim());
    }

    @Test
    public void testFindById_Failure() throws Exception {
        String expected= "";
        MvcResult result = mockMvc.perform(get("/clients/5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected.trim(), content.trim());

    }

    @Test
    public void testFindById_NotFound() throws Exception {
        String expected = "";

        MvcResult result = mockMvc.perform(get("/clients/4")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())  // Espera un 404 Not Found
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected, content);

    }

    @Test
    public void testUpdateClientById_Success() throws Exception {
        String expected= """
                {"id":3,"nif":"334567891Z","name":"Carlos Updated Manuel","address":"Calle Mayor, 9, Madrid, Spain"}""";
        MvcResult result = mockMvc.perform(patch("/clients/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                {"name":"Carlos Updated Manuel","address":"Calle Mayor, 9, Madrid, Spain"}
                """))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected.trim(), content.trim());
    }

    @Test
    public void testDeleteClientById_Success() throws Exception {
        String expected= "";

        MvcResult result = mockMvc.perform(delete("/clients/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(expected, content);
    }

    private void executeInitialData() throws SQLException {
        var conn = dataSource.getConnection();
        //clear table
        var clearSalesDetStatement = conn.prepareStatement("DELETE FROM clients");
        clearSalesDetStatement.executeUpdate();

        var resetSequenceStatement = conn.prepareStatement("ALTER TABLE clients ALTER COLUMN id RESTART WITH 1");
        resetSequenceStatement.executeUpdate();
        //inserts
        var statement = conn.prepareStatement("INSERT INTO clients (name, nif, address) VALUES (?, ?, ?)");

        statement.setString(1, "Carlos Martinez Test");
        statement.setString(2, "2434224335S");
        statement.setString(3, "Calle Mayor, 10, Madrid, Spain");
        statement.executeUpdate();

        statement.setString(1, "Ana Gomez Test");
        statement.setString(2, "334567891K");
        statement.setString(3, "Calle de la Libertad, 15, Barcelona, Spain");
        statement.executeUpdate();

        statement.setString(1, "Juan Magan Test");
        statement.setString(2, "334567891Z");
        statement.setString(3, "Calle de la Libertad, 17, Barcelona, Spain");
        statement.executeUpdate();
    }
}
