package com.travelport.projectone.jpa;

import com.travelport.projectone.dto.IncomeReportDTO;
import com.travelport.projectone.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    List<Purchase> findByClientNif(String nif);

//    @Query("SELECT Product.name, SUM(PurchaseDetails.quantity) " +
//            "FROM PurchaseDetails, Product" +
//            "WHERE PurchaseDetails.product = Product.code" +
//            "AND Product.code = :code" +
//            "GROUP BY PurchaseDetails.product")
//    List<ProductSalesDTO> findProductSales(@Param("code") int code);

    @Query("SELECT new com.travelport.projectone.dto.IncomeReportDTO(p.name, SUM(pd.quantity)) " +
            "FROM PurchaseDetails pd " +
            "JOIN pd.product p " +
            "WHERE p.code = :productId " +
            "GROUP BY p.name")
    List<IncomeReportDTO> getProductSalesReport(@Param("productId") int productId);
}
