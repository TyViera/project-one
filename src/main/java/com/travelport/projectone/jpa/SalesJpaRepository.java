package com.travelport.projectone.jpa;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Sales;
import com.travelport.projectone.entities.SalesPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesJpaRepository extends JpaRepository<Sales, SalesPk> {
}
