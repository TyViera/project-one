package com.travelport.projectone.persistence.impl;

import com.travelport.projectone.dto.ReportResponse;
import com.travelport.projectone.dto.SaleResponse;
import com.travelport.projectone.entities.Sale;
import com.travelport.projectone.entities.SaleDetail;
import com.travelport.projectone.persistence.SaleDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class SaleDaoImpl implements SaleDao {
    @PersistenceContext // JPA
    private EntityManager entityManager;

    private final Map<Integer, Sale> cache;

    public SaleDaoImpl() {
        this.cache = new HashMap<>();
    }

    @Override
    public Sale saveSale(Sale sale) {
        entityManager.persist(sale);
        return sale;
    }

    @Override
    public SaleDetail saveSaleDetail(SaleDetail saleDetail) {
        entityManager.persist(saleDetail);
        return saleDetail;
    }

    @Override
    public List<Sale> findAll(Integer clientId) {
        String jpql = "SELECT s FROM Sale s WHERE s.client.id = :clientId";
        return entityManager.createQuery(jpql, Sale.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }

    @Override
    public List<ReportResponse> getIncomeReport() {
        String jpql = "SELECT sd.product.id, SUM(sd.quantity) AS quantity " +
                "FROM SaleDetail sd " +
                "GROUP BY sd.product.id " +
                "ORDER BY quantity DESC";
        return entityManager.createQuery(jpql, ReportResponse.class)
                .getResultList();
    }

    @Override
    public List<SaleDetail> findSaleDetailsBySaleId(Integer saleId) {
        String jpql = "SELECT sd FROM SaleDetail sd WHERE sd.sale.id = :saleId";
        return entityManager.createQuery(jpql, SaleDetail.class)
                .setParameter("saleId", saleId)
                .getResultList();
    }
}
