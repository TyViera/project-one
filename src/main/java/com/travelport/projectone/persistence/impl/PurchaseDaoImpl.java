package com.travelport.projectone.persistence.impl;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Purchase;
import com.travelport.projectone.persistence.PurchaseDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class PurchaseDaoImpl implements PurchaseDao {

    @PersistenceContext
    private EntityManager em;

    private final Map<Integer, Purchase> cache;

    public PurchaseDaoImpl() { cache = new HashMap<>(); }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void save(Purchase purchase) { em.persist(purchase); }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Purchase> list() {
        var query = em.createQuery("from Purchase", Purchase.class);
        return query.getResultList();
    }

    public boolean purchaseExist(Integer id) {
        if (id != null) {
            em.find(Purchase.class, id);
        }
        return false;
    }

    @Override
    public Optional<Purchase> getPurchaseById(Integer id) {
        var foundPurchase = cache.computeIfAbsent(id, x -> em.find(Purchase.class, x));
        return Optional.ofNullable(foundPurchase);
    }

    @Override
    public void update(Purchase purchase) {

    }

    @Override
    public Optional<Integer> deleteById(Integer code) {
        return Optional.empty();
    }


}
