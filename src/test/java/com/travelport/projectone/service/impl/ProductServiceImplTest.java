package com.travelport.projectone.service.impl;

import com.travelport.projectone.entity.Product;
import com.travelport.projectone.jpa.ProductJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    ProductJpaRepository productDao;

    @Test
    @DisplayName("Given new product When save product Then return product")
    void testSaveSuccess() {
        // Given (setup)
        var productToSend = Mockito.mock(Product.class);

        Mockito.when(productDao.save(eq(productToSend))).thenReturn(productToSend);

        // When (do actions)
        var result = productService.save(productToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isPresent());

        Mockito.verify(productDao).save(eq(productToSend));
    }

    @Test
    @DisplayName("Given null product When save product Then return empty")
    void testSaveNullProductReturnEmpty() {
        // Given (setup)
        Product productToSend = null;

        // When (do actions)
        var result = productService.save(productToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(productDao, Mockito.never()).save(any());
        Mockito.verify(productDao, Mockito.never()).existsById(any());
    }

    @Test
    @DisplayName("Given new product When save product error Then return empty")
    void testSaveErrorReturnEmpty() {
        // Given (setup)
        var productToSend = Mockito.mock(Product.class);

        Mockito.when(productDao.save(eq(productToSend))).thenThrow(new MockitoException("Error"));

        // When (do actions)
        var result = productService.save(productToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(productDao).save(eq(productToSend));
    }

    @Test
    @DisplayName("Given a product does not exist When update product Then return empty")
    void testUpdateNonExistingProductReturnsEmpty() {
        // Given (setup)
        var idToSend = 1;
        var productToSend = new Product();
        productToSend.setId(idToSend);

        Mockito.when(productDao.findById(eq(idToSend))).thenReturn(Optional.empty());

        // When (do actions)
        var result = productService.update(idToSend, productToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(productDao, Mockito.never()).save(eq(productToSend));
    }

    @Test
    @DisplayName("Given a product exists When update product with all properties Then return product")
    void testUpdateExistingProductWithAllPropertiesReturnsProduct() {
        // Given (setup)
        var idToSend = 1;

        var existingProduct = new Product();
        existingProduct.setId(idToSend);
        existingProduct.setName("Old name");

        var productToSend = new Product();
        productToSend.setId(idToSend);
        productToSend.setName("New name");
        productToSend.setTimesSold(2);

        Mockito.when(productDao.findById(eq(idToSend))).thenReturn(Optional.of(existingProduct));
        Mockito.when(productDao.save(eq(productToSend))).thenReturn(productToSend);

        // When (do actions)
        var result = productService.update(idToSend, productToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(productToSend.getId(), result.get().getId());
        assertEquals(productToSend.getName(), result.get().getName());
        assertEquals(productToSend.getTimesSold(), result.get().getTimesSold());

        Mockito.verify(productDao).findById(eq(idToSend));
        Mockito.verify(productDao).save(eq(productToSend));
    }

    @Test
    @DisplayName("Given a product exists When update product with no properties Then return product")
    void testUpdateExistingProductWithNoPropertiesReturnsProduct() {
        // Given (setup)
        var idToSend = 1;

        var existingProduct = new Product();
        existingProduct.setId(idToSend);
        existingProduct.setName("Old name");
        existingProduct.setTimesSold(0);

        var productToSend = new Product();
        productToSend.setTimesSold(null);

        var updatedProduct = new Product();
        updatedProduct.setId(existingProduct.getId());
        updatedProduct.setName(existingProduct.getName());
        updatedProduct.setTimesSold(existingProduct.getTimesSold());

        Mockito.when(productDao.findById(eq(idToSend))).thenReturn(Optional.of(existingProduct));
        Mockito.when(productDao.save(eq(updatedProduct))).thenReturn(updatedProduct);

        // When (do actions)
        var result = productService.update(idToSend, productToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(updatedProduct.getId(), result.get().getId());
        assertEquals(updatedProduct.getName(), result.get().getName());
        assertEquals(updatedProduct.getTimesSold(), result.get().getTimesSold());

        Mockito.verify(productDao).findById(eq(idToSend));
        Mockito.verify(productDao).save(eq(updatedProduct));
    }

    @Test
    @DisplayName("Given a product exists When delete by id Then return true")
    void testDeleteExistingProductReturnsTrue() {
        // Given (setup)
        var idToSend = 1;

        Mockito.when(productDao.existsById(eq(idToSend))).thenReturn(true);

        // When (do actions)
        var result = productService.delete(idToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result);

        Mockito.verify(productDao).deleteById(eq(idToSend));
        Mockito.verify(productDao).existsById(eq(idToSend));
    }

    @Test
    @DisplayName("Given a product does not exists When delete by id Then return false")
    void testDeleteNonExistingProductReturnsFalse() {
        // Given (setup)
        var idToSend = 1;

        Mockito.when(productDao.existsById(eq(idToSend))).thenReturn(false);

        // When (do actions)
        var result = productService.delete(idToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertFalse(result);

        Mockito.verify(productDao, Mockito.never()).deleteById(eq(idToSend));
        Mockito.verify(productDao).existsById(eq(idToSend));
    }

    @Test
    @DisplayName("Given empty database with no orderBy When get all products Then return empty list")
    void testFindAllWithNoOrderReturnEmpty() {
        // Given
        String orderBy = null;

        // When
        var result = productService.findAll(orderBy);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(productDao).findAll();
        Mockito.verify(productDao, Mockito.never()).findAllByOrderByTimesSoldDesc();
    }

    @Test
    @DisplayName("Given empty database with orderBy timesSold When get all products Then return empty list")
    void testFindAllWithValidOrderReturnEmpty() {
        // Given
        String orderBy = "timesSold";

        // When
        var result = productService.findAll(orderBy);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(productDao).findAllByOrderByTimesSoldDesc();
        Mockito.verify(productDao, Mockito.never()).findAll();
    }

    @Test
    @DisplayName("Given empty database with invalid orderBy value When get all products Then return empty list")
    void testFindAllWithInvalidOrderReturnEmpty() {
        // Given
        String orderBy = "asdf";

        // When
        var result = productService.findAll(orderBy);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(productDao).findAll();
        Mockito.verify(productDao, Mockito.never()).findAllByOrderByTimesSoldDesc();
    }

    @Test
    @DisplayName("Given two products exists and no orderBy When get all products Then return size 2 list ordered by id")
    void testFindAllWithNoOrderReturnResults() {
        // Given
        String orderBy = null;

        var product1 = new Product();
        product1.setId(1);

        var product2 = new Product();
        product2.setId(2);

        var productList = new ArrayList<Product>();
        productList.add(product1);
        productList.add(product2);

        Mockito.when(productDao.findAll()).thenReturn(productList);

        // When
        var result = productService.findAll(orderBy);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(1, result.getFirst().getId());
        assertEquals(2, result.getLast().getId());
        assertTrue(result.getFirst().getId() < result.getLast().getId());

        Mockito.verify(productDao).findAll();
        Mockito.verify(productDao, Mockito.never()).findAllByOrderByTimesSoldDesc();
    }

    @Test
    @DisplayName("Given two products exists and orderBy timesSold When get all products Then return size 2 list ordered by timesSold")
    void testFindAllWithValidOrderReturnResults() {
        // Given
        String orderBy = "timesSold";

        var product1 = new Product();
        product1.setId(1);
        product1.setTimesSold(5);

        var product2 = new Product();
        product2.setId(2);
        product2.setTimesSold(8);

        var productList = new ArrayList<Product>();
        productList.add(product2);
        productList.add(product1);

        Mockito.when(productDao.findAllByOrderByTimesSoldDesc()).thenReturn(productList);

        // When
        var result = productService.findAll(orderBy);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(2, result.getFirst().getId());
        assertEquals(1, result.getLast().getId());
        assertTrue(result.getFirst().getTimesSold() > result.getLast().getTimesSold());

        Mockito.verify(productDao).findAllByOrderByTimesSoldDesc();
        Mockito.verify(productDao, Mockito.never()).findAll();
    }

    @Test
    @DisplayName("Given two products exists and invalid orderBy value When get all products Then return size 2 list ordered by id")
    void testFindAllWithInvalidOrderReturnResults() {
        // Given
        String orderBy = "asdf";

        var product1 = new Product();
        product1.setId(1);
        product1.setTimesSold(5);

        var product2 = new Product();
        product2.setId(2);
        product2.setTimesSold(8);

        var productList = new ArrayList<Product>();
        productList.add(product1);
        productList.add(product2);

        Mockito.when(productDao.findAll()).thenReturn(productList);

        // When
        var result = productService.findAll(orderBy);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(1, result.getFirst().getId());
        assertEquals(2, result.getLast().getId());
        assertTrue(result.getFirst().getId() < result.getLast().getId());

        Mockito.verify(productDao).findAll();
        Mockito.verify(productDao, Mockito.never()).findAllByOrderByTimesSoldDesc();
    }

    @Test
    @DisplayName("Given product with id 1 does not exist When get product by id Then return empty")
    void testFindByIdNoResults() {
        // Given
        var idToSend = 1;

        // When
        var result = productService.findById(idToSend);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(productDao).findById(eq(idToSend));
    }

    @Test
    @DisplayName("Given product with id 1 exists When get product by id Then return product")
    void testFindByIdWithResults() {
        // Given
        var idToSend = 1;
        var product = new Product();
        product.setId(idToSend);


        Mockito.when(productDao.findById(eq(idToSend))).thenReturn(Optional.of(product));

        // When
        var result = productService.findById(idToSend);

        // Then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());

        Mockito.verify(productDao).findById(eq(idToSend));
    }

    @Test
    @DisplayName("Given null id When get product by id Then return empty")
    void testFindByIdWithNullParams() {
        // Given
        Integer idToSend = null;

        // When
        var result = productService.findById(idToSend);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(productDao).findById(any());
    }
}
