package com.travelport.projectone.service.impl;

import com.travelport.projectone.entities.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    EntityManager entityManager;

    @Spy
    HashMap<Integer, Product> cache;


    @BeforeEach
    void setUp() {
        System.out.println("Init test...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Cleaning up...");
    }

    @Test
    void getProduct() {
        List<Product> products = productService.getProduct();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

    @ParameterizedTest
    @MethodSource("provideIntegerForIsProductExists")
    void findByCode(Integer code, boolean assertedValue) {
        var product = productService.findByCode(code);
        assertNotNull(product);
        assertEquals(product.isPresent(), assertedValue);
    }

    static Stream<Arguments> provideIntegerForIsProductExists() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("101", false),
                Arguments.of(101, true),
                Arguments.of(1000000001, false)
        );
    }

    @Test
    void deleteByCode() {
        var codeToDelete = 101;
        var listBeforeDelete = productService.getProduct();
        productService.deleteByCode(codeToDelete);
        var listAfterDelete = productService.getProduct();
        assertTrue(listBeforeDelete.size() > listAfterDelete.size());
    }

    @Test
    void save() {
        var listBeforeSave = productService.getProduct();
        productService.save(new Product());
        var listAfterSave = productService.getProduct();
        assertTrue(listBeforeSave.size() < listAfterSave.size());
    }

    @Test
    void update() {
    }

    @Test
    void incomeReport() {
        var listProducts = productService.incomeReport();
        assertNotNull(listProducts);
        assertTrue(listProducts.size() > 0);
    }
}