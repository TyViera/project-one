package com.travelport.projectone.persistence.impl;

import com.travelport.projectone.entities.Sales;
import com.travelport.projectone.persistence.SalesDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class SalesDaoImpl implements SalesDao {
    @PersistenceContext // JPA
    private EntityManager entityManager;

    @Override
    public void save(Sales sales) {
        entityManager.persist(sales);
    }

    @Override
    public List<Sales> list() {
        return entityManager.createQuery("SELECT s FROM Sales s", Sales.class).getResultList();
    }

    @Override
    public Optional<Sales> getSalesById(Integer id) {
        return Optional.ofNullable(entityManager.find(Sales.class, id));
    }

    @Override
    public void update(Sales sales) {
        entityManager.merge(sales);
    }

    @Override
    public Optional<Integer> deleteById(Integer id) {
        Sales sales = entityManager.find(Sales.class, id);
        if (sales != null) {
            entityManager.remove(sales);
            return Optional.of(id);
        }
        return Optional.empty();
    }
}
