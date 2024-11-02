package com.travelport.persistence.impl;

import com.travelport.persistence.AbstractEntityDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@Transactional
public abstract class AbstractEntityDaoImpl<E, K> implements AbstractEntityDao<E, K> {

    @PersistenceContext
    private EntityManager em;

    private final Class<E> persistentClass;

    @SuppressWarnings("unchecked")
    protected AbstractEntityDaoImpl() {
        this.persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void save(E entity) {
        em.persist(entity);
    }

    @Override
    public List<E> list() {
        var criteriaBuilder = em.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(persistentClass);
        var root = query.from(persistentClass);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<E> findById(K id) {
        E entity = em.find(persistentClass, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public void update(E entity) {
        em.merge(entity);
    }

    @Override
    public void delete(K id) {
        em.remove(em.find(persistentClass, id));
    }
}
