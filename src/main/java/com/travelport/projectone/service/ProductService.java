package com.travelport.projectone.service;

import java.util.List;

import com.travelport.projectone.entities.Product;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product updateProduct(int id, Product updatedProduct);
    void deleteProduct(int id);
    Product getProductById(int id);
}
