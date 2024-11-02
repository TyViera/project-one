package com.travelport.service;

import com.travelport.dto.ProductReportOutputDTO;
import com.travelport.persistence.ReportDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReportService {

    private final ReportDao reportDao;

    public ReportService (ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public List<ProductReportOutputDTO> getProductsReport() {
        return reportDao.getProductReport();
    }
}
