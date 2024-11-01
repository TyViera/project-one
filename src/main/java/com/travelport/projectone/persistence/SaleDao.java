
package com.travelport.projectone.persistence;

import com.travelport.projectone.dto.SaleResponse;
import com.travelport.projectone.entities.Sale;
import com.travelport.projectone.entities.SaleDetail;

import java.util.List;

public interface SaleDao {
    Sale saveSale(Sale sale);

    SaleDetail saveSaleDetail(SaleDetail saleDetail);

    List<Sale> findAll(Integer clientId);

    Object getIncomeReport();

    List<SaleDetail> findSaleDetailsBySaleId(Integer saleId);
}