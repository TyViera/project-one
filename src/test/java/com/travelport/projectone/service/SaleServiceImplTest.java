package com.travelport.projectone.service;

import com.travelport.projectone.dto.*;
import com.travelport.projectone.entities.*;
import com.travelport.projectone.persistence.*;
import com.travelport.projectone.service.impl.SaleServiceImpl;
import com.travelport.projectone.entities.Sale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceImplTest {

    @InjectMocks
    private SaleServiceImpl saleService;

    @Mock
    private SaleDao saleDao;

    @Mock
    private ProductDao productDao;

    @Mock
    private ClientDao clientDao;

    @Test
    void saveSale_ValidRequest() {
        // Set up - given
        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setClientId(1);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setId(1);
        productRequest.setQuantity(2);
        saleRequest.setProducts(List.of(productRequest));

        Client client = new Client();
        client.setId(1);

        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);
        sale.setClient(client);
        sale.setSellDate(new Timestamp(System.currentTimeMillis()));

        when(clientDao.findById(1)).thenReturn(Optional.of(client));
        when(productDao.findById(1)).thenReturn(Optional.of(product));
        when(saleDao.saveSale(any(Sale.class))).thenReturn(sale);

        // Do something - when
        Sale result = saleService.saveSale(saleRequest);

        // Asserts - then
        assertNotNull(result);
        assertEquals(client, result.getClient());
        verify(saleDao, times(1)).saveSale(any(Sale.class));
        verify(saleDao, times(1)).saveSaleDetail(any(SaleDetail.class));
    }

    @Test
    void saveSale_InvalidProductQuantity() {
        // Set up - given
        SaleRequest saleRequest = mock(SaleRequest.class);
        when(saleRequest.areProductsQuantityValid()).thenReturn(false);

        // Do somwthing / Assert
        assertThrows(IllegalArgumentException.class, () -> saleService.saveSale(saleRequest));
    }

    @Test
    void saveSale_ClientNotFound() {
        // Set up - given
        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setClientId(1);

        lenient().when(clientDao.findById(1)).thenReturn(Optional.empty());// to solve: Please remove unnecessary stubbings or use 'lenient' strictness.

        assertThrows(NullPointerException.class, () -> saleService.saveSale(saleRequest));
    }


    @Test
    void saveSale_ProductNotFound() {
        // Set up - given
        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setClientId(1);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setId(1);
        productRequest.setQuantity(2);
        saleRequest.setProducts(List.of(productRequest));

        Client client = new Client();
        client.setId(1);

        when(clientDao.findById(1)).thenReturn(Optional.of(client));
        when(productDao.findById(1)).thenReturn(Optional.empty());

        // Assert - Then
        assertThrows(IllegalArgumentException.class, () -> saleService.saveSale(saleRequest));
    }

    @Test
    void getIncomeReport() {
        // Set up - given
        ReportResponse reportResponse = new ReportResponse();
        reportResponse.setId(1);

        Product product = new Product();
        product.setId(1);
        product.setName("Product 1");

        when(saleDao.getIncomeReport()).thenReturn(List.of(reportResponse));
        when(productDao.findById(reportResponse.getId())).thenReturn(Optional.of(product));

        // Do something - when
        List<ReportResponse> result = saleService.getIncomeReport();

        // Asserts - then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(product.getName(), result.get(0).getName());
        verify(saleDao, times(1)).getIncomeReport();
        verify(productDao, times(1)).findById(reportResponse.getId());
    }
}
