
package com.travelport.service;

import com.travelport.dto.ReportResponse;
import com.travelport.dto.SaleRequest;
import com.travelport.dto.SaleResponse;
import com.travelport.entities.Sale;

import java.util.List;

public interface SaleService {
    public Sale saveSale(SaleRequest saleRequest);

    public List<SaleResponse> findAll(Integer clientId);

    public List<ReportResponse> getIncomeReport();
}