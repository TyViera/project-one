package com.travelport.projectone.persistence.impl;

import com.travelport.projectone.entities.Product;
import com.travelport.projectone.persistence.ProductDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

//@Component
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    private EntityManager entityManager;
    private final Class<Product> productClass;

    @SuppressWarnings("unchecked")
    protected ProductDaoImpl() {
        this.productClass = (Class<Product>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void save(Product product) {entityManager.persist(product);}

    @Override
    public List<Product> list() {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(productClass);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Optional<Product> getProductById(Integer code) {
        return Optional.ofNullable(entityManager.find(Product.class, code));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Product product) { entityManager.merge(product); }

    @Override
    public Optional<Integer> deleteById(Integer code) {
        Product product = entityManager.find(Product.class, code);
        if (product != null) {
            entityManager.remove(product);
            return Optional.of(code);
        }
        return Optional.empty();
    }
}
