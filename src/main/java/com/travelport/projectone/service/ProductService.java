package com.travelport.projectone.service;

import com.travelport.projectone.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getProduct();

    Optional<Product> findByCode(Integer code);

    void deleteByCode(Integer code);

    Product save(Product product);

    Product update(Integer code, Product product);

    List<Product> incomeReport();
}
