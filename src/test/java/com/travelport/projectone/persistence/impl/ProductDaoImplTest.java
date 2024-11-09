package com.travelport.projectone.persistence.impl;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Product;
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

@ExtendWith(MockitoExtension.class)
class ProductDaoImplTest {

    @InjectMocks
    ProductDaoImpl productDao;

    @Mock
    EntityManager em;

    @Spy
    HashMap<Integer, Product> cache;

    @BeforeAll
    static void beforeAll() { System.out.println("Before All..."); }

    @BeforeEach
    void initEachTest() { System.out.println("Init test..."); }

    @AfterEach
    void afterEachTest() { System.out.println("Cleaning up..."); }

    @ParameterizedTest
    @MethodSource("provideStringsForIsProductExist")
    void givenParamValues_whenIsProductExists_ThenReturnAssertValue(Integer code, boolean assertValue) {
        var result = productDao.productExists(code);
        assertEquals(assertValue, result);
    }

    @Test
    void getProductByCodeTest() {
        var codeToFind = 101;

        ReflectionTestUtils.setField(productDao, "cache", cache);
        Mockito.when(em.find(Product.class, codeToFind)).thenReturn(new Product());

        assertTrue(cache.isEmpty()); //Before the call, the cache must be empty
        var result = productDao.getProductByCode(codeToFind);
        System.out.println("RESULT: " + result);
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertFalse(cache.isEmpty());

        Mockito.verify(em).find(Product.class, codeToFind);
    }

    static Stream<Arguments> provideStringsForIsProductExist() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(0, false),
                Arguments.of(101, true),
                Arguments.of(100001, false)
        );
    }
}
