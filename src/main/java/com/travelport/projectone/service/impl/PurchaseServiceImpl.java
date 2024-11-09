package com.travelport.projectone.service.impl;

import com.travelport.projectone.entities.Purchase;
import com.travelport.projectone.persistence.PurchaseDao;
import com.travelport.projectone.service.PurchaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseDao purchaseDao;

    public PurchaseServiceImpl(PurchaseDao purchaseDao) { this.purchaseDao = purchaseDao; }

    @Override
    public List<Purchase> getPurchase() {
        return purchaseDao.list();
    }

    @Override
    public Optional<Purchase> findById(Integer id) {
        return purchaseDao.getPurchaseById(id);
    }

    @Override
    public void deleteById(Integer id) {
        purchaseDao.deleteById(id);
    }

    @Override
    public Purchase save(Purchase purchase) {
        purchaseDao.save(purchase);
        return purchase;
    }

    @Override
    public Purchase update(Integer id, Purchase purchase) {
        var fpurchase = purchaseDao.getPurchaseById(id);
        if (fpurchase.isEmpty()) {
            return null;
        }
        if (purchase.getId() == null) {
            return null;
        }
        purchaseDao.save(purchase);
        return purchase;
    }
}
