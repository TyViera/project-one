//package com.travelport.persistence.impl;
//
//import com.travelport.entities.Product;
//import com.travelport.persistence.ProductDao;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Isolation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Component
//@Transactional
//public class ProductDaoImpl implements ProductDao{
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    private final Map<Integer, Product> cache;
//
//    public ProductDaoImpl(){
//        cache = new HashMap<>();
//    }
//
//    @Override
//    public List<Product> list() {
//        return entityManager.createQuery("FROM Products", Product.class).getResultList();
//    }
//
//    @Override
//    public Optional<Product> getProductById(Integer id) {
//        var product = cache.computeIfAbsent(id, x -> entityManager.find(Product.class, x));
//        return Optional.ofNullable(product);
//    }
//
//    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public void update(Product product) {
//        entityManager.merge(product);
//    }
//
//    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Optional<Integer> deleteById(Integer id) {
//        return getProductById(id)
//            .map(
//                product -> {
//                    entityManager.remove(product);
//                    return id;
//                });
//    }
//
//    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public void save(Product product) {
//        if (product.getId() != null) {
//            entityManager.persist(product);
//        }
//    }
//}
