package com.travelport.projectone.jpa;

import com.travelport.projectone.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleJpaRepository extends JpaRepository<Sale, Integer> {

    public List<Sale> findAllByClientNif(String clientNif);

}
