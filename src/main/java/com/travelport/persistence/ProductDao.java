package com.travelport.persistence;

import com.travelport.entities.Product;

import java.util.Optional;
import java.util.List;

public interface ProductDao {
    Product save(Product product);

    List<Product> findAll();

    Optional<Product> findById(Integer id);

    void update(Product client);

    Optional<Integer> deleteById(Integer id);
}
