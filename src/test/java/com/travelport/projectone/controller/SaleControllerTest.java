package com.travelport.projectone.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.travelport.projectone.jpa.SaleJpaRepository;
import com.travelport.projectone.service.SaleServiceImpl;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    WebAppConfig.class,
    WebAppInitializer.class,
    JpaRepositoryConfig.class,
    DatabaseConfig.class,
    SaleController.class,
    SaleJpaRepository.class,
    SaleServiceImpl.class
})
public class SaleControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private DataSource dataSource;

  private MockMvc mockMvc;
  private int createdSaleId;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void createSaleTest() throws Exception {
    MvcResult result = mockMvc.perform(post("/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "clientId": "12345678A",
                        "products": [
                            {
                                "id": 2,
                                "quantity": 1
                            }
                        ]
                    }
                """))
        .andExpect(status().isCreated())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    assertTrue(content.contains("\"clientId\":\"12345678A\""), "Response should contain the posted clientId.");

  }

  @Test
  void getClientSalesTest() throws Exception {
    createSaleTest();

    MvcResult result = mockMvc.perform(get("/sales/client/{clientId}", "87654321B")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    assertTrue(content.startsWith("[") && content.endsWith("]"), "Response should be a JSON array.");
    assertTrue(content.contains("\"clientId\":\"87654321B\""), "Response should contain the clientId.");
  }

  @Test
  void getIncomeReportTest() throws Exception {
    createSaleTest();

    MvcResult result = mockMvc.perform(get("/sales/income-report/{productId}", 3)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    assertTrue(content.contains("\"productId\":3"), "Response should contain the correct productId.");
    assertTrue(content.contains("\"quantity\":27"), "Response should contain the correct quantity sold.");
  }

}
