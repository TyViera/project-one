package com.travelport.projectone.service;

import com.travelport.entities.Product;
import com.travelport.persistence.ProductDao;
import com.travelport.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductDao productDao;

    @Test
    void findAllProducts() {
        // Set up - given
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setCode("P001");

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setCode("P002");

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        when(productDao.findAll()).thenReturn(products);

        // Do something - when
        List<Product> result = productService.findAll();

        // Asserts - then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(products, result);
        verify(productDao, times(1)).findAll();
    }

    @Test
    void findProductById_ProductExists() {
        // Set up - given
        int productId = 1;
        Product product = new Product();
        product.setId(productId);
        product.setName("Product 1");

        when(productDao.findById(productId)).thenReturn(Optional.of(product));

        // Do something - when
        Optional<Product> result = productService.findById(productId);

        // Asserts - then
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productDao, times(1)).findById(productId);
    }

    @Test
    void findProductById_ProductDoesNotExist() {
        // Set up - given
        int productId = 1;
        when(productDao.findById(productId)).thenReturn(Optional.empty());

        // Do something - when
        Optional<Product> result = productService.findById(productId);

        // Asserts - then
        assertFalse(result.isPresent());
        verify(productDao, times(1)).findById(productId);
    }

    @Test
    void deleteProductById() {
        // Set up - given
        int productId = 1;

        when(productDao.deleteById(productId)).thenReturn(Optional.of(productId));

        // Do something - when
        Optional<Integer> result = productService.deleteById(productId);

        // Asserts - then
        assertTrue(result.isPresent());
        assertEquals(productId, result.get());
        verify(productDao, times(1)).deleteById(productId);
    }

    @Test
    void deleteProductById_ProductDoesNotExist() {
        // Set up - given
        int productId = 1;

        when(productDao.deleteById(productId)).thenReturn(Optional.empty());

        // Do something - when
        Optional<Integer> result = productService.deleteById(productId);

        // Asserts - then
        assertFalse(result.isPresent());
        verify(productDao, times(1)).deleteById(productId);
    }

    @Test
    void saveProduct() {
        // Set up - given
        Product product = new Product();
        product.setName("Product 1");

        when(productDao.save(product)).thenReturn(product);

        // Do something - when
        Product result = productService.save(product);

        // Asserts - then
        assertNotNull(result);
        assertEquals(product, result);
        verify(productDao, times(1)).save(product);
    }

    @Test
    void updateProduct_ProductExists() {
        // Set up - given
        int productId = 1;
        Product productToUpdate = new Product();
        productToUpdate.setName("Updated Product");
        productToUpdate.setCode("P001");

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Original Product");
        existingProduct.setCode("P000");

        when(productDao.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Do something - when
        Product result = productService.update(productId, productToUpdate);

        // Asserts - then
        assertNotNull(result);
        assertEquals(productToUpdate.getName(), result.getName());
        assertEquals(productToUpdate.getCode(), result.getCode());
        verify(productDao, times(1)).findById(productId);
        verify(productDao, times(1)).update(existingProduct);
    }

    @Test
    void updateProduct_ProductDoesNotExist() {
        // Set up - given
        int productId = 1;
        Product productToUpdate = new Product();
        productToUpdate.setName("Updated Product");

        when(productDao.findById(productId)).thenReturn(Optional.empty());

        // Do something - when
        Product result = productService.update(productId, productToUpdate);

        // Asserts - then
        assertNull(result);
        verify(productDao, times(1)).findById(productId);
        verify(productDao, times(0)).update(any(Product.class));
    }

}
