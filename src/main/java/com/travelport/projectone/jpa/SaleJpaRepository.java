package com.travelport.projectone.jpa;

import com.travelport.projectone.entities.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SaleJpaRepository extends JpaRepository<Sales, Integer> {

    public List<Sales> findAllByClientDNI(String clientDNI);
}
