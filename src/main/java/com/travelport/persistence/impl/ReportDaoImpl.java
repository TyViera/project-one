package com.travelport.persistence.impl;

import com.travelport.dto.ProductReportOutputDTO;
import com.travelport.persistence.ReportDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportDaoImpl implements ReportDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductReportOutputDTO> getProductReport() {
        TypedQuery<ProductReportOutputDTO> query = em.createQuery(
                "SELECT new com.travelport.dto.ProductReportOutputDTO(sd.product.code, sd.product.name, SUM(sd.quantity)) "
                        + "FROM SaleDetail sd "
                        + "GROUP BY sd.product.code, sd.product.name "
                        + "ORDER BY SUM(sd.quantity) DESC",
                ProductReportOutputDTO.class
        );

        return query.getResultList();
    }
}
