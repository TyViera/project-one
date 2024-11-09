package com.travelport.projectone.persistence;

import com.travelport.projectone.entities.Product;
import com.travelport.projectone.entities.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseDao {
    void save(Purchase purchase);

    List<Purchase> list();

    Optional<Purchase> getPurchaseById(Integer id);

    void update(Purchase purchase);

    void deleteById(Integer id);
}
