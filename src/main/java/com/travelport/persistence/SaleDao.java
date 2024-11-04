package com.travelport.persistence;

import com.travelport.dto.ReportResponse;
import com.travelport.entities.Sale;
import com.travelport.entities.SaleDetail;

import java.util.List;

public interface SaleDao {
    Sale saveSale(Sale sale);

    SaleDetail saveSaleDetail(SaleDetail saleDetail);

    List<Sale> findAll(Integer clientId);

    List<ReportResponse> getIncomeReport();

    List<SaleDetail> findSaleDetailsBySaleId(Integer saleId);
}