package com.travelport.projectone.jpa;

import java.util.Optional;

import com.travelport.projectone.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Integer> {}