package com.travelport.jpa.impl;

import com.travelport.entities.Sale;
import com.travelport.jpa.SalesDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
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
}
