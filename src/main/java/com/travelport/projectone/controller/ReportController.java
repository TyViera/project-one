package com.travelport.projectone.controller;

import com.travelport.projectone.dto.IncomeReportDTO;
import com.travelport.projectone.jpa.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/income-report")
public class ReportController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @GetMapping
    public List<IncomeReportDTO> getIncomeReport(@RequestParam("productId") int productId) {
        return purchaseRepository.getProductSalesReport(productId);
    }
}