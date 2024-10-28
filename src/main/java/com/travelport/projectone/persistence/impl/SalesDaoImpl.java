package com.travelport.projectone.persistence.impl;

import com.travelport.projectone.entities.Sales;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

public class SalesDaoImpl {
    @PersistenceContext // JPA
    private EntityManager entityManager;

    public void save(Sales sales) {
        entityManager.persist(sales);
    }

    public List<Sales> list() {
        return entityManager.createQuery("SELECT s FROM Sales s", Sales.class).getResultList();
    }

    public void update(Sales sales) {
        entityManager.merge(sales);
    }

    public Optional<Integer> deleteById(Integer id) {
        Sales sales = entityManager.find(Sales.class, id);
        if (sales != null) {
            entityManager.remove(sales);
            return Optional.of(id);
        }
        return Optional.empty();
    }
}
