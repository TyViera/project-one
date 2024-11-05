package com.travelport.projectone.persistence.impl;

import com.travelport.projectone.entities.Product;
import com.travelport.projectone.persistence.ProductDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
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
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void save(Product product) {em.persist(product);}

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Product> list() {
        var query = em.createQuery("from Product", Product.class);
        return query.getResultList();
    }

    public boolean productExists(Integer code) {
        return (code != null && em.find(Product.class, code) != null);
    }

    @Override
    public Optional<Product> getProductByCode(Integer code) {
        var foundPurchase = cache.computeIfAbsent(code, x -> em.find(Product.class, x));
        return Optional.ofNullable(foundPurchase);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Product product) { em.merge(product); }

    @Override
    public Optional<Integer> deleteByCode(Integer code) {
        return getProductByCode(code)
                .map(
                        product -> {
                            em.remove(product);
                            return code;
                        });
    }

    @Override
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
