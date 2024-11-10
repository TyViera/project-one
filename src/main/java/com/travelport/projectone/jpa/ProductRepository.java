package com.travelport.projectone.jpa;

import com.travelport.projectone.dto.ProductSalesDTO;
import com.travelport.projectone.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> { }
