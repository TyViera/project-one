package com.travelport.service;

import com.travelport.dto.ProductReportOutputDTO;
import com.travelport.persistence.ReportDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @InjectMocks
    ReportService reportService;

    @Mock
    ReportDao reportDao;

    @Test
    void getProductsReport_ShouldReturnAListOfProducts() {
        ProductReportOutputDTO product1 = new ProductReportOutputDTO(1, "Laptop", 30);
        ProductReportOutputDTO product2 = new ProductReportOutputDTO(2, "PC", 20);

        List<ProductReportOutputDTO> productsReportList = Arrays.asList(product1, product2);

        when(reportDao.getProductReport()).thenReturn(productsReportList);

        List<ProductReportOutputDTO> result = reportService.getProductsReport();

        assertNotNull(result);
        assertEquals(productsReportList.size(), result.size());
        assertEquals(productsReportList, result);
        verify(reportDao).getProductReport();
    }
}