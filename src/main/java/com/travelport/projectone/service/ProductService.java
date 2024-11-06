package com.travelport.projectone.service;

import com.travelport.projectone.entity.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> save(@RequestBody Product product);

    Optional<Product> update(@PathVariable("id") Integer id, @RequestBody Product product);

    Boolean delete(@PathVariable("id") Integer id);

    List<Product> findAll(String orderBy);

    Optional<Product> findById(@PathVariable("id") Integer id);

}
