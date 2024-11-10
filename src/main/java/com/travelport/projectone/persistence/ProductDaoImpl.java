package com.travelport.projectone.persistence;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void save(Product product) {
        entityManager.persist(product);
    }

    @Override
    public List<Product> list() {
        //JPQL
        return entityManager.createQuery("FROM Product", Product.class).getResultList();
    }

    @Override
    public Optional<Product> getProductByCode(int code) {
        return Optional.ofNullable(entityManager.find(Product.class, code));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Product product) {
        entityManager.merge(product);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Optional<Integer> deleteByCode(int code) {
        return getProductByCode(code).map(product -> {entityManager.remove(product);return code;});
    }


}
