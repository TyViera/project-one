//package com.travelport.persistence.impl;
//
//import com.travelport.entities.Sales;
//import com.travelport.persistence.SalesDao;
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
//public class SalesDaoImpl implements SalesDao {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    private final Map<Integer, Sales> cache;
//
//    public SalesDaoImpl(){
//        cache = new HashMap<>();
//    }
//
//    @Override
//    public List<Sales> list() {
//        return entityManager.createQuery("FROM Products", Sales.class).getResultList();
//    }
//
//    @Override
//    public Optional<Sales> getSaleById(Integer id) {
//        var sale = cache.computeIfAbsent(id, x -> entityManager.find(Sales.class, x));
//        return Optional.ofNullable(sale);
//    }
//
//    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public void update(Sales sales) {
//        entityManager.merge(sales);
//    }
//
//    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Optional<Integer> deleteById(Integer id) {
//        return getSaleById(id)
//                .map(
//                        sale -> {
//                            entityManager.remove(sale);
//                            return id;
//                        });
//    }
//
//    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public void save(Sales sale) {
//        if (sale.getId() != null) {
//            entityManager.persist(sale);
//        }
//    }
//}
