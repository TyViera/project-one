package com.travelport.projectone.jpa;

import com.travelport.projectone.entities.PurchaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  PurchaseDetailsRepository extends JpaRepository<PurchaseDetails, Integer>{}
