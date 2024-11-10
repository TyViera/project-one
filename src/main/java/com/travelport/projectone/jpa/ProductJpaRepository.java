package com.travelport.projectone.jpa;

import com.travelport.projectone.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Integer> {

    public List<Product> findAllDesc();
}
