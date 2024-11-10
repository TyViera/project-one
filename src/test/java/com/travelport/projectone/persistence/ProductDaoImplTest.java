package com.travelport.projectone.persistence;


import com.travelport.projectone.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductDaoImplTest {

    @InjectMocks
    private ProductDaoImpl productDao;

    @Mock
    private EntityManager entityManager;

    @BeforeEach
    void initEachTest() {
        System.out.println("Starting a test...");
    }

    @AfterEach
    void afterEachTest() {
        System.out.println("Cleaning up after test...");
    }


    @Test
    void saveProductTest() {
        Product product = new Product();
        product.setCode(101);
        product.setName("Product A");

        productDao.save(product);

        Mockito.verify(entityManager).persist(product);

        System.out.println("saveProductTest - Product saved: Code: " + product.getCode() + ", Name: " + product.getName());
    }

    @Test
    void listProductsTest() {
        String jpql = "FROM Product";
        var mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery(eq(jpql), eq(Product.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of(new Product(), new Product()));

        List<Product> result = productDao.list();

        assertNotNull(result);
        assertEquals(2, result.size());
        Mockito.verify(entityManager).createQuery(eq(jpql), eq(Product.class));

        System.out.println("listProductsTest - Number of products returned: " + result.size());
    }

    @Test
    void getProductByCodeTest() {
        int code = 101;
        Product product = new Product();
        Mockito.when(entityManager.find(Product.class, code)).thenReturn(product);

        Optional<Product> result = productDao.getProductByCode(code);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        Mockito.verify(entityManager).find(Product.class, code);

        System.out.println("getProductByCodeTest - Product retrieved with code: " + code);
    }

    @Test
    void updateProductTest() {
        Product product = new Product();
        product.setCode(101);
        product.setName("Updated Product");

        productDao.update(product);

        Mockito.verify(entityManager).merge(product);

        System.out.println("updateProductTest - Product updated with Code: " + product.getCode() + ", Name: " + product.getName());
    }

    @Test
    void deleteProductByCodeTest() {
        int code = 101;
        Product product = new Product();
        Mockito.when(entityManager.find(Product.class, code)).thenReturn(product);

        Optional<Integer> result = productDao.deleteByCode(code);

        assertTrue(result.isPresent());
        assertEquals(code, result.get());
        Mockito.verify(entityManager).remove(product);

        System.out.println("deleteProductByCodeTest - Product with code " + code + " deleted.");
    }
}