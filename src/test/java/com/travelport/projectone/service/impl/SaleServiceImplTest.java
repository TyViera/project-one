package com.travelport.projectone.service.impl;

import com.travelport.projectone.dto.ProductDto;
import com.travelport.projectone.dto.PurchaseDto;
import com.travelport.projectone.entity.Client;
import com.travelport.projectone.entity.Product;
import com.travelport.projectone.entity.Sale;
import com.travelport.projectone.entity.SaleProduct;
import com.travelport.projectone.jpa.ClientJpaRepository;
import com.travelport.projectone.jpa.ProductJpaRepository;
import com.travelport.projectone.jpa.SaleJpaRepository;
import com.travelport.projectone.jpa.SaleProductJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {

    @InjectMocks
    SaleServiceImpl saleService;

    @Mock
    SaleJpaRepository saleDao;

    @Mock
    ClientJpaRepository clientDao;

    @Mock
    SaleProductJpaRepository saleProductDao;

    @Mock
    ProductJpaRepository productDao;

    @Test
    @DisplayName("Given new sale When save sale Then return sale")
    void testSaveSuccess() {
        // Given (setup)
        var purchaseProducts = new ArrayList<ProductDto>();
        var product1Dto = new ProductDto(1, 2);
        var product2Dto = new ProductDto(2, 4);
        purchaseProducts.add(product1Dto);
        purchaseProducts.add(product2Dto);

        var purchaseToSend = new PurchaseDto("1", purchaseProducts);

        var client = new Client();
        client.setNif("1");

        var sale = new Sale(client);
        sale.setId(1);

        var product1Db = new Product();
        product1Db.setId(1);
        product1Db.setTimesSold(0);

        var product2Db = new Product();
        product2Db.setId(2);
        product2Db.setTimesSold(0);

        // When (do actions)
        Mockito.when(clientDao.findById(eq("1"))).thenReturn(Optional.of(client));
        Mockito.when(saleDao.save(any(Sale.class))).thenReturn(sale);
        Mockito.when(productDao.findById(eq(1))).thenReturn(Optional.of(product1Db));
        Mockito.when(productDao.findById(eq(2))).thenReturn(Optional.of(product2Db));

        var result = saleService.save(purchaseToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getSaleId());
        assertEquals(2, result.get().getProductList().size());
        assertEquals(2, product1Db.getTimesSold());
        assertEquals(4, product2Db.getTimesSold());
        assertEquals(1, result.get().getProductList().getFirst().getProductId());
        assertEquals(2, result.get().getProductList().getLast().getProductId());

        Mockito.verify(clientDao).findById(eq("1"));
        Mockito.verify(saleDao).save(any(Sale.class));
        Mockito.verify(productDao).findById(eq(1));
        Mockito.verify(productDao).findById(eq(2));
        Mockito.verify(productDao, Mockito.times(2)).save(any(Product.class));
        Mockito.verify(saleProductDao, Mockito.times(2)).save(any(SaleProduct.class));
    }

    @Test
    @DisplayName("Given new sale with non existing client When save sale Then return empty")
    void testSaveClientNotFoundReturnEmpty() {
        // Given (setup)
        var purchaseProducts = new ArrayList<ProductDto>();
        var product1Dto = new ProductDto(1, 2);
        var product2Dto = new ProductDto(2, 4);
        purchaseProducts.add(product1Dto);
        purchaseProducts.add(product2Dto);

        var purchaseToSend = new PurchaseDto("1", purchaseProducts);

        // When (do actions)
        Mockito.when(clientDao.findById(eq("1"))).thenReturn(Optional.empty());

        var result = saleService.save(purchaseToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(clientDao).findById(eq("1"));
        Mockito.verify(saleDao, Mockito.never()).save(any());
        Mockito.verify(productDao, Mockito.never()).findById(any());
        Mockito.verify(productDao, Mockito.never()).save(any());
        Mockito.verify(saleProductDao, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Given new sale with non existing product When save sale Then return empty")
    void testSaveProductNotFoundReturnEmpty() {
        // Given (setup)
        var purchaseProducts = new ArrayList<ProductDto>();
        var product1Dto = new ProductDto(1, 2);
        var product2Dto = new ProductDto(2, 4);
        purchaseProducts.add(product1Dto);
        purchaseProducts.add(product2Dto);

        var purchaseToSend = new PurchaseDto("1", purchaseProducts);

        var client = new Client();
        client.setNif("1");

        var sale = new Sale(client);
        sale.setId(1);

        // When (do actions)
        Mockito.when(clientDao.findById(eq("1"))).thenReturn(Optional.of(client));
        Mockito.when(saleDao.save(any(Sale.class))).thenReturn(sale);
        Mockito.when(productDao.findById(anyInt())).thenReturn(Optional.empty());

        var result = saleService.save(purchaseToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(clientDao).findById(eq("1"));
        Mockito.verify(saleDao).save(any(Sale.class));
        Mockito.verify(productDao, Mockito.times(2)).findById(anyInt());
        Mockito.verify(productDao, Mockito.never()).save(any(Product.class));
        Mockito.verify(saleProductDao, Mockito.never()).save(any(SaleProduct.class));
    }

    @Test
    @DisplayName("Given new sale with invalid product amount (0 or less) When save sale Then return empty")
    void testSaveInvalidProductAmountReturnEmpty() {
        // Given (setup)
        var purchaseProducts = new ArrayList<ProductDto>();
        var product1Dto = new ProductDto(1, 0);
        var product2Dto = new ProductDto(2, -1);
        purchaseProducts.add(product1Dto);
        purchaseProducts.add(product2Dto);

        var purchaseToSend = new PurchaseDto("1", purchaseProducts);

        var client = new Client();
        client.setNif("1");

        var sale = new Sale(client);
        sale.setId(1);

        var product1Db = new Product();
        product1Db.setId(1);
        product1Db.setTimesSold(0);

        var product2Db = new Product();
        product2Db.setId(2);
        product2Db.setTimesSold(0);

        // When (do actions)
        Mockito.when(clientDao.findById(eq("1"))).thenReturn(Optional.of(client));
        Mockito.when(saleDao.save(any(Sale.class))).thenReturn(sale);
        Mockito.when(productDao.findById(eq(1))).thenReturn(Optional.of(product1Db));
        Mockito.when(productDao.findById(eq(2))).thenReturn(Optional.of(product2Db));

        var result = saleService.save(purchaseToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(clientDao).findById(eq("1"));
        Mockito.verify(saleDao).save(any(Sale.class));
        Mockito.verify(productDao).findById(eq(1));
        Mockito.verify(productDao).findById(eq(2));
        Mockito.verify(productDao, Mockito.never()).save(any(Product.class));
        Mockito.verify(saleProductDao, Mockito.never()).save(any(SaleProduct.class));
    }

    @Test
    @DisplayName("Given client id does not exist When find sales Then return empty")
    void testFindSalesByClientIdWithNoExistingClientReturnEmpty() {
        // Given (set up)
        var idToSend = "1";

        Mockito.when(clientDao.existsById(eq(idToSend))).thenReturn(false);

        // When (do actions)
        var result = saleService.findSalesByClientId(idToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(clientDao).existsById(eq(idToSend));
        Mockito.verify(saleDao, Mockito.never()).findAllByClientNif(any());
        Mockito.verify(saleProductDao, Mockito.never()).findAllBySaleId(any());
    }

    @Test
    @DisplayName("Given client had not made any purchase When find sales Then return empty list")
    void testFindSalesByClientIdWithClientWithNoPurchasesReturnEmpty() {
        // Given (set up)
        var idToSend = "1";

        Mockito.when(clientDao.existsById(eq(idToSend))).thenReturn(true);
        Mockito.when(saleDao.findAllByClientNif(eq(idToSend))).thenReturn(List.of());

        // When (do actions)
        var result = saleService.findSalesByClientId(idToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertTrue(result.get().getSales().isEmpty());
        assertEquals(idToSend, result.get().getClientNif());

        Mockito.verify(clientDao).existsById(eq(idToSend));
        Mockito.verify(saleDao).findAllByClientNif(eq(idToSend));
        Mockito.verify(saleProductDao, Mockito.never()).findAllBySaleId(any());
    }

    @Test
    @DisplayName("Given client has made two purchases When find sales Then return size 2 list")
    void testFindSalesByClientIdWithClientWithPurchasesReturnSalesList() {
        // Given (set up)
        var idToSend = "1";
        var client = new Client();
        client.setNif(idToSend);

        var product1 = new Product();
        product1.setId(1);
        product1.setName("laptop");

        var product2 = new Product();
        product2.setId(2);
        product2.setName("phone");

        var sale1 = new Sale(client);
        sale1.setId(1);

        var sale1Products = new ArrayList<SaleProduct>();
        sale1Products.add(new SaleProduct(sale1, product1, 5));
        sale1Products.add(new SaleProduct(sale1, product2, 3));

        var sale2 = new Sale(client);
        sale2.setId(2);

        var sale2Products = new ArrayList<SaleProduct>();
        sale2Products.add(new SaleProduct(sale2, product2, 6));

        var clientSalesList = new ArrayList<Sale>();
        clientSalesList.add(sale1);
        clientSalesList.add(sale2);

        Mockito.when(clientDao.existsById(eq(idToSend))).thenReturn(true);
        Mockito.when(saleDao.findAllByClientNif(eq(idToSend))).thenReturn(clientSalesList);
        Mockito.when(saleProductDao.findAllBySaleId(eq(sale1.getId()))).thenReturn(sale1Products);
        Mockito.when(saleProductDao.findAllBySaleId(eq(sale2.getId()))).thenReturn(sale2Products);

        // When (do actions)
        var result = saleService.findSalesByClientId(idToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertFalse(result.get().getSales().isEmpty());
        assertEquals(idToSend, result.get().getClientNif());
        assertEquals(2, result.get().getSales().size());
        assertEquals(2, result.get().getSales().getFirst().getProductList().size());
        assertEquals(1, result.get().getSales().getLast().getProductList().size());

        Mockito.verify(clientDao).existsById(eq(idToSend));
        Mockito.verify(saleDao).findAllByClientNif(eq(idToSend));
        Mockito.verify(saleProductDao, Mockito.times(2)).findAllBySaleId(anyInt());
    }
}