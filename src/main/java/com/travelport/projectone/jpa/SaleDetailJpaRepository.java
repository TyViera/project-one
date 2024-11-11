package com.travelport.projectone.jpa;

import com.travelport.projectone.entities.SaleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SaleDetailJpaRepository extends JpaRepository<SaleDetails, Integer> {

    public List<SaleDetails> findAllBySaleId(Integer saleId);
}
