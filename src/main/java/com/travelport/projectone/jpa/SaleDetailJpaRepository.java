package com.travelport.projectone.jpa;

import com.travelport.projectone.entities.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleDetailJpaRepository extends JpaRepository<SaleDetail, SaleDetail.SaleDetailPK> {

}
