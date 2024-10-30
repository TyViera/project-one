package com.travelport.projectone.jpa;

import com.travelport.projectone.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseJpaRepository extends JpaRepository<Purchase, Integer> { }
