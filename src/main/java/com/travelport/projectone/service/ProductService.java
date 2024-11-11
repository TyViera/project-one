package com.travelport.projectone.service;

import com.travelport.projectone.entities.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product>findAll();

    Optional<Product> findById(Integer id);

    void delete(Integer id);

    Optional<Product> save(Product product);

    Optional<Product> update(Integer id, Product product);
}
