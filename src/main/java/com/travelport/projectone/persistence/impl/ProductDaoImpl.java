package com.travelport.projectone.persistence.impl;

import com.travelport.projectone.entities.Product;
import com.travelport.projectone.persistence.ProductDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    private EntityManager em;

    private final Map<Integer, Product> cache;

    public ProductDaoImpl() { cache = new HashMap<>();}

    @Override
    @Transactional
    public void save(Product product) {
        try {
            em.persist(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<Product> list() {
        var query = em.createQuery("from Product", Product.class);
        return query.getResultList();
    }

    public boolean productExists(Integer code) {
        return (code != null && em.find(Product.class, code) != null);
    }

    @Override
    @Transactional
    public Optional<Product> getProductByCode(Integer code) {
        var foundPurchase = cache.computeIfAbsent(code, x -> em.find(Product.class, x));
        return Optional.ofNullable(foundPurchase);
    }

    @Override
    @Transactional
    public void update(Product product) { em.merge(product); }

    @Override
    @Transactional
    public void deleteByCode(Integer code) {
        try
        {
            getProductByCode(code).map( product -> {
                em.remove(product);
                return code;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<Product> incomeReport() {
        var query = em.createQuery(
                "SELECT p FROM Product p " +
                        "JOIN Purchase s ON s.product = p " +
                        "GROUP BY p " +
                        "ORDER BY COUNT(s) DESC", Product.class);
        query.setMaxResults(10);
        return query.getResultList();
    }
}
