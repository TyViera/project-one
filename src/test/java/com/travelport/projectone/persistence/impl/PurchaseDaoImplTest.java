package com.travelport.projectone.persistence.impl;

import com.travelport.projectone.entities.Purchase;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class PurchaseDaoImplTest {

    @InjectMocks
    PurchaseDaoImpl purchaseDao;

    @Mock
    EntityManager em;

    @Spy
    HashMap<Integer, Purchase> cache;

    @BeforeAll
    static void beforeAll() { System.out.println("Before All..."); }

    @BeforeEach
    void initEachTest() { System.out.println("Init test..."); }

    @AfterEach
    void afterEachTest() { System.out.println("Cleaning up..."); }

    @ParameterizedTest
    @MethodSource("provideStringsForIsPurchaseExist")
    void givenParamValues_whenIsPurchaseExist_thenReturnAssertValue(Integer id, boolean asserValue) {
        var result = purchaseDao.purchaseExist(id);
        assertEquals(asserValue, result);
    }

    @Test
    void getPurchaseByIdTest() {
        var idToFind = 1;

        ReflectionTestUtils.setField(purchaseDao, "cache", cache);
        Mockito.when(em.find(Purchase.class, idToFind)).thenReturn(new Purchase());

        assertTrue(cache.isEmpty());
        var result = purchaseDao.getPurchaseById(idToFind);
        System.out.println("RESULT: " + result);
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertFalse(cache.isEmpty());

        Mockito.verify(em).find(Purchase.class, idToFind);
    }

    static Stream<Arguments> provideStringsForIsPurchaseExist() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(100, false),
                Arguments.of(1, true),
                Arguments.of(1000000, false)
        );
    }
}
