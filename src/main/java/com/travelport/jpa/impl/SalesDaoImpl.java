package com.travelport.jpa.impl;

import com.travelport.entities.Sale;
import com.travelport.jpa.SalesDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class SalesDaoImpl implements SalesDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Sale sale) {
        em.persist(sale);
        em.flush(); // Para evitar error 500, ya que antes de crear un SaleDetail necesito persistir la Sale para que inmediatamente esté disponible su id autoGenerado para que SaleDetail lo pueda usar.
    }

    @Override
    public void update(Sale sale) {
        em.merge(sale);
    }

    @Override
    public List<Sale> findByClientNif(String nif) {
        TypedQuery<Sale> query = em.createQuery(
                "SELECT s FROM Sale s WHERE s.client.nif = :nif", Sale.class
        );
        query.setParameter("nif", nif);
        return query.getResultList();
    }
}
