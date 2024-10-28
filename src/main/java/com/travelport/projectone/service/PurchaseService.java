package com.travelport.projectone.service;

import com.travelport.projectone.entities.Purchase;
import java.util.List;
import java.util.Optional;

public interface PurchaseService {

    List<Purchase> getPurchase();

    Optional<Purchase> findById(Integer id);

    void deleteById(Integer id);

    Purchase save(Purchase purchase);

    Purchase update(Integer id, Purchase purchase);
}
