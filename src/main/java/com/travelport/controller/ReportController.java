package com.travelport.controller;

import com.travelport.dto.ProductReportOutputDTO;
import com.travelport.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController (ReportService reportSerive) {
        this.reportService = reportSerive;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductReportOutputDTO>> incomeReport() {
        List<ProductReportOutputDTO> report = reportService.getProductsReport();
        return ResponseEntity.ok(report);
    }
}
