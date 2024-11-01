
package com.travelport.projectone.service;

import com.travelport.projectone.dto.SaleRequest;
import com.travelport.projectone.dto.SaleResponse;
import com.travelport.projectone.entities.Sale;
import com.travelport.projectone.entities.SaleDetail;

import java.util.List;

public interface SaleService {
    public String incomeReport(Integer productId);

    public Sale saveSale(SaleRequest saleRequest);

    public List<SaleResponse> findAll(Integer clientId);

    public Object getIncomeReport();
}