
package com.travelport.projectone.jpa;

import com.travelport.projectone.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleJpaRepository extends JpaRepository<Sale, Integer> {
}