package com.travelport.projectone.persistence;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    void save(Product product);

    List<Product> list();

    Optional<Product> getProductByCode(int code);

    void update(Product product);

    Optional<Integer> deleteByCode(int code);
}
