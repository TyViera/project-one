package com.travelport.projectone.jpa;

import com.travelport.projectone.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleJpaRepository extends JpaRepository<Sale, Integer> {

  List<Sale> findByClientId(String clientId);

  @Query("SELECT SUM(sp.quantity) FROM Sale s JOIN s.saleProducts sp WHERE sp.product.id = :productId")
  int countByProductId(@Param("productId") int productId);
}
