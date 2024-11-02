package com.travelport.service;

import com.travelport.dto.ClientSalesOutputDTO;
import com.travelport.dto.SaleDetailOutputDTO;
import com.travelport.dto.SaleOutputDTO;
import com.travelport.entities.Client;
import com.travelport.entities.Product;
import com.travelport.entities.Sale;
import com.travelport.entities.SaleDetail;
import com.travelport.persistence.SalesDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalesServiceTest {

    @InjectMocks
    SalesService salesService;

    @Mock
    SalesDao salesDao;

    @Test
    void getSalesByClientNif_ShouldReturnAListOfSales() {
        Timestamp testTimestamp = new Timestamp(System.currentTimeMillis());
        String nif = "12345678A";

        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setClient(new Client());
        sale1.setSellDate(testTimestamp);
        sale1.setSaleDetails(new ArrayList<>());

        Sale sale2 = new Sale();
        sale2.setId(2);
        sale2.setClient(new Client());
        sale2.setSellDate(testTimestamp);
        sale2.setSaleDetails(new ArrayList<>());

        List<Sale> sales = Arrays.asList(sale1, sale2);

        when(salesDao.findByClientNif(nif)).thenReturn(sales);

        List<Sale> resultSalesList = salesService.getSalesByClientNif(nif);

        assertFalse(resultSalesList.isEmpty());
        assertEquals(2, resultSalesList.size());
        assertEquals(sales, resultSalesList);
        verify(salesDao).findByClientNif(nif);
    }

    @Test
    void getClientSalesDTO_ShouldReturnAClientSalesDTO() {
        // Setup
        String nifToSend = "12345678A";

        Client client = new Client();
        client.setNif(nifToSend);

        Product product1 = new Product();
        product1.setCode(1);
        product1.setName("Product 1");

        Product product2 = new Product();
        product2.setCode(2);
        product2.setName("Product 2");

        SaleDetail saleDetail1 = new SaleDetail();
        saleDetail1.setProduct(product1);
        saleDetail1.setQuantity(5);

        SaleDetail saleDetail2 = new SaleDetail();
        saleDetail2.setProduct(product2);
        saleDetail2.setQuantity(3);

        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setClient(client);
        sale1.setSellDate(new Timestamp(System.currentTimeMillis()));
        sale1.setSaleDetails(Arrays.asList(saleDetail1, saleDetail2));

        List<Sale> sales = List.of(sale1);

        // Simular el comportamient
        when(salesDao.findByClientNif(nifToSend)).thenReturn(sales);

        // Ejecucion
        ClientSalesOutputDTO recievedResult = salesService.getClientSalesDTO(nifToSend);

        // Verificaciones
        assertNotNull(recievedResult);
        assertEquals(nifToSend, recievedResult.getNif());
        assertEquals(1, recievedResult.getSales().size());

        SaleOutputDTO saleOutputDTO = recievedResult.getSales().getFirst();
        assertEquals(1, saleOutputDTO.getSaleId());
        assertEquals(2, saleOutputDTO.getProducts().size());

        SaleDetailOutputDTO detail1 = saleOutputDTO.getProducts().getFirst();
        assertEquals(1, detail1.getCode());
        assertEquals(5, detail1.getQuantity());

        SaleDetailOutputDTO detail2 = saleOutputDTO.getProducts().get(1);
        assertEquals(2, detail2.getCode());
        assertEquals(3, detail2.getQuantity());

        verify(salesDao).findByClientNif(nifToSend);
    }

    @Test
    void saveSale() {
        Sale sale1 = new Sale();
        salesService.saveSale(sale1);
        verify(salesDao).save(sale1);
    }

    @Test
    void updateSale() {
        Sale sale1 = new Sale();
        salesService.updateSale(sale1);
        verify(salesDao).update(sale1);
    }
}