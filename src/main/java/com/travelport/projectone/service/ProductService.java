package com.travelport.projectone.service;

import com.travelport.projectone.entities.Product;

import java.util.Optional;

public interface ProductService {

    Optional<Product> getProductById(Integer id);

    Product save(Product client);

    Product update(Integer id, Product client);

    void deleteById(Integer id);
}
