package com.travelport.projectone.service;

import com.travelport.projectone.dto.SaleProductResponseDTO;
import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Sale;
import com.travelport.projectone.entities.SaleProduct;
import com.travelport.projectone.jpa.SaleJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

  @InjectMocks
  private SaleServiceImpl saleService;

  @Mock
  private SaleJpaRepository saleRepository;

  private Sale sale;
  private Client client;

  @BeforeEach
  void setUp() {
    client = new Client();
    client.setId("34828462F");

    SaleProduct saleProduct = new SaleProduct();
    saleProduct.setProductId(1);
    saleProduct.setQuantity(5);

    sale = new Sale();
    sale.setId(1);
    sale.setClient(client);
    sale.setSaleProducts(Collections.singletonList(saleProduct));
  }

  @Test
  void testCreateSale() {
    when(saleRepository.save(any(Sale.class))).thenReturn(sale);

    Sale createdSale = saleService.createSale(sale);

    assertNotNull(createdSale);
    assertEquals("34828462F", createdSale.getClient().getId());
    assertEquals(1, createdSale.getSaleProducts().size());
    assertEquals(5, createdSale.getSaleProducts().get(0).getQuantity());
    verify(saleRepository, times(1)).save(sale);
  }

  @Test
  void testGetClientSales() {
    when(saleRepository.findByClientId("34828462F")).thenReturn(Collections.singletonList(sale));

    List<Sale> clientSales = saleService.getClientSales("34828462F");

    assertNotNull(clientSales);
    assertEquals(1, clientSales.size());
    assertEquals("34828462F", clientSales.get(0).getClient().getId());
    verify(saleRepository, times(1)).findByClientId("34828462F");
  }

  @Test
  void testFindAllSalesOfProduct() {
    SaleProduct saleProduct2 = new SaleProduct();
    saleProduct2.setProductId(1);
    saleProduct2.setQuantity(10);

    Sale sale2 = new Sale();
    sale2.setId(2);
    sale2.setClient(client);
    sale2.setSaleProducts(Collections.singletonList(saleProduct2));

    when(saleRepository.findAll()).thenReturn(Arrays.asList(sale, sale2));

    int totalSales = saleService.findAllSalesOfProduct(1);

    assertEquals(15, totalSales);
    verify(saleRepository, times(1)).findAll();
  }

  @Test
  void testGenerateIncomeReport() {
    SaleProduct saleProduct2 = new SaleProduct();
    saleProduct2.setProductId(1);
    saleProduct2.setQuantity(10);

    Sale sale2 = new Sale();
    sale2.setId(2);
    sale2.setClient(client);
    sale2.setSaleProducts(Collections.singletonList(saleProduct2));

    when(saleRepository.findAll()).thenReturn(Arrays.asList(sale, sale2));

    List<SaleProductResponseDTO> incomeReport = saleService.generateIncomeReport(1);

    assertNotNull(incomeReport);
    assertEquals(1, incomeReport.size());
    assertEquals(1, incomeReport.getFirst().getProductId());
    assertEquals(15, incomeReport.getFirst().getQuantity());
    verify(saleRepository, times(1)).findAll();
  }
}
