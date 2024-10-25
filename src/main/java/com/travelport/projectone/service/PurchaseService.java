package com.travelport.projectone.service;

import com.travelport.projectone.entities.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {

    List<Purchase> getPurchases();

    Optional<Purchase> getPurchaseById(int id);
}
