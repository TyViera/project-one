package com.travelport.projectone.controller;

import com.travelport.projectone.config.DatabaseConfig;
import com.travelport.projectone.config.WebAppConfig;
import com.travelport.projectone.config.WebAppInitializer;
import com.travelport.projectone.persistence.impl.ProductDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebAppConfig.class,
        WebAppInitializer.class,
        DatabaseConfig.class,
        ProductController.class,
        ProductDaoImpl.class,
})

public class ProductControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DataSource dataSource;

    MockMvc mockMvc;

    @Test
    void getProductsTest() {
    }

    @Test
    void postProduct() {
    }

    @Test
    void getProductByCode() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}
