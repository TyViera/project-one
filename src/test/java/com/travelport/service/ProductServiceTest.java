package com.travelport.service;

import com.travelport.entities.Product;
import com.travelport.persistence.ProductDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;

    @Test
    void saveProductWithValidProduct_ShouldCallDaoSave() {
        Product product = new Product();
        product.setCode(1);
        product.setName("testProduct");

        productService.saveProduct(product);

        verify(productDao, times(1)).save(product);
    }

    @Test
    void saveProductWithInvalidProduct_ShouldThrowException() {
        Product product = null;

        assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(product));

    }

    @Test
    void findAll_ShouldReturnListOfProducts() {
        Product product1 = new Product();
        product1.setCode(1);
        product1.setName("testProduct1");

        Product product2 = new Product();
        product2.setCode(2);
        product2.setName("testProduct2");

        List<Product> products = Arrays.asList(product1, product2);
        when(productDao.list()).thenReturn(products);

        List<Product> returnedProducts = productService.findAll();

        assertNotNull(returnedProducts);
        assertEquals(2, returnedProducts.size());
        assertEquals(products, returnedProducts);
        verify(productDao, times(1)).list();
    }

    @Test
    void findProductById_ProductExists_ShouldReturnProduct() {
        Product product = new Product();
        product.setCode(1);
        product.setName("testProduct");

        when(productDao.findById(product.getCode())).thenReturn(Optional.of(product));

        Optional<Product> returnedProduct = productService.findProductById(product.getCode());

        assertTrue(returnedProduct.isPresent());
        assertEquals(product, returnedProduct.get());
        verify(productDao, times(1)).findById(product.getCode());
    }

    @Test
    void findProductById_ProductDoesNotExist_ShouldThrowException() {
        var id = 37;
        when(productDao.findById(id)).thenReturn(Optional.empty());

        Optional<Product> returnedProduct = productService.findProductById(id);

        assertFalse(returnedProduct.isPresent());
        verify(productDao, times(1)).findById(id);
    }

    @Test
    void updateProduct_WithValidProduct_ShouldCallDaoUpdate() {
        Product product = new Product();
        product.setCode(1);
        product.setName("testProduct");

        productService.updateProduct(product);

        verify(productDao, times(1)).update(product);
    }

    @Test
    void updateProduct_WithNullProduct_ShouldThrowException() {
        Product product = null;

        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(product));
    }

    @Test
    void deleteProductById() {
        Product product = new Product();
        product.setCode(1);
        product.setName("testProduct");

        productService.deleteProduct(product.getCode());

        verify(productDao, times(1)).delete(product.getCode());
    }
}