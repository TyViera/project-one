package com.travelport.persistence;

import com.travelport.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends AbstractEntityDao<Product, Integer> {

    void save(Product product);

    List<Product> list();

    Optional<Product> findById(Integer id);

    void update(Product product);

    void delete(Integer code);
}
