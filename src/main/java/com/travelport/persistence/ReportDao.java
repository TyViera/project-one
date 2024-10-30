package com.travelport.persistence;

import com.travelport.dto.ProductReportOutputDTO;

import java.util.List;

public interface ReportDao {

    List<ProductReportOutputDTO> getProductReport();
}
