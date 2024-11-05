package com.travelport.projectone.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.travelport.projectone.entities.Product;
import com.travelport.projectone.jpa.ProductJpaRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @InjectMocks
  private ProductServiceImpl productService;

  @Mock
  private ProductJpaRepository productJpaRepository;

  private Product product;

  @BeforeEach
  void setUp() {
    product = new Product();
    product.setId(1);
    product.setName("ACOTAR");
  }

  @Test
  void testCreateProduct() {
    when(productJpaRepository.save(product)).thenReturn(product);
    assertEquals(product, productService.createProduct(product));
  }

  @Test
  void testGetAllProducts() {
    Product product2 = new Product();
    product2.setId(2);
    product2.setName("TOWER OF GLASS");

    when(productJpaRepository.findAll()).thenReturn(Arrays.asList(product, product2));

    List<Product> products = productService.getAllProducts();
    assertEquals(2, products.size());
    assertEquals("ACOTAR", products.get(0).getName());
    assertEquals("TOWER OF GLASS", products.get(1).getName());
  }

  @Test
  void testGetProductById() {
    when(productJpaRepository.findById(1)).thenReturn(Optional.of(product));

    Product foundProduct = productService.getProductById(1);
    assertEquals("ACOTAR", foundProduct.getName());
  }

  @Test
  void testGetProductByIdNotFound() {
    when(productJpaRepository.findById(99)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
      productService.getProductById(99);
    });

    assertEquals("Product not found with id: 99", exception.getMessage());
  }

  @Test
  void testUpdateProduct() {
    Product updatedProduct = new Product();
    updatedProduct.setName("ACOMAF");

    when(productJpaRepository.findById(1)).thenReturn(Optional.of(product));
    when(productJpaRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Product updated = productService.updateProduct(1, updatedProduct);
    assertEquals("ACOMAF", updated.getName());
  }

  @Test
  void testUpdateProductNotFound() {
    Product updatedProduct = new Product();
    updatedProduct.setName("ACOMAF");

    when(productJpaRepository.findById(99)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
      productService.updateProduct(99, updatedProduct);
    });

    assertEquals("Product not found with id: 99", exception.getMessage());
  }

  @Test
  void testDeleteProduct() {
    doNothing().when(productJpaRepository).deleteById(1);

    assertDoesNotThrow(() -> productService.deleteProduct(1));
    verify(productJpaRepository, times(1)).deleteById(1);
  }

  @Test
  void testDeleteProductNotFound() {
    doThrow(new RuntimeException("Product not found with id: 99")).when(productJpaRepository).deleteById(99);

    Exception exception = assertThrows(RuntimeException.class, () -> {
      productService.deleteProduct(99);
    });

    assertEquals("Product not found with id: 99", exception.getMessage());
  }
}
