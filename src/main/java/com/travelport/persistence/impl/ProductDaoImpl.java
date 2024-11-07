package com.travelport.persistence.impl;

import com.travelport.entities.Product;
import com.travelport.persistence.ProductDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Transactional
public class ProductDaoImpl implements ProductDao {
    @PersistenceContext //JPA
    private EntityManager entityManager;

    private  final Map<Integer, Product> cache;

    public ProductDaoImpl(){
        cache = new HashMap<>();
    }

    @Override
    public Product save(Product product) {
        entityManager.persist(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        var foundProduct =cache.computeIfAbsent(id, x -> entityManager.find(Product.class, x));
        return Optional.ofNullable(foundProduct);
    }

    @Override
    public void update(Product product) {
        entityManager.merge(product);
    }

    @Override
    public Optional<Integer> deleteById(Integer id) {
        return findById(id).map(
                product -> {
                    entityManager.remove(product);
                    return id;
                }
        );
    }
}
