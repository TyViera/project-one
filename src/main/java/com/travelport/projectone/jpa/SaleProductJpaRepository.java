package com.travelport.projectone.jpa;

import com.travelport.projectone.entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleProductJpaRepository extends JpaRepository<SaleProduct, Integer> {

    public List<SaleProduct> findAllBySaleId(Integer saleId);

}
