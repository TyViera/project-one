package com.travelport.projectone.service.impl;

import com.travelport.projectone.entities.Purchase;
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

class PurchaseServiceImplTest {

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @Mock
    EntityManager entityManager;

    @Spy
    HashMap<Integer, Purchase> cache;

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
        List<Purchase> purchases = purchaseService.getPurchase();
        assertNotNull(purchases);
        assertTrue(purchases.size() > 0);
    }

    @ParameterizedTest
    @MethodSource("provideIntegerForIsPurchaseExists")
    void findByCode(Integer id, boolean assertedValue) {
        var purchase = purchaseService.findById(id);
        assertNotNull(purchase);
        assertEquals(purchase.isPresent(), assertedValue);
    }

    static Stream<Arguments> provideIntegerForIsPurchaseExists() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("1", false),
                Arguments.of(1, true),
                Arguments.of(1000000001, false)
        );
    }

    @Test
    void deleteByCode() {
        var idToDelete = 1;
        var listBeforeDelete = purchaseService.getPurchase();
        purchaseService.deleteById(idToDelete);
        var listAfterDelete = purchaseService.getPurchase();
        assertTrue(listBeforeDelete.size() > listAfterDelete.size());
    }

    @Test
    void save() {
        var listBeforeSave = purchaseService.getPurchase();
        purchaseService.save(new Purchase());
        var listAfterSave = purchaseService.getPurchase();
        assertTrue(listBeforeSave.size() < listAfterSave.size());
    }

    @Test
    void update() {
    }
}