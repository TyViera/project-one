package com.travelport.projectone.controller;

import com.travelport.projectone.dto.ProductRequest;
import com.travelport.projectone.dto.SaleRequest;
import com.travelport.projectone.entities.Sale;
import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.SaleProduct;
import com.travelport.projectone.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

  private final SaleService saleService;

  public SaleController(SaleService saleService) {
    this.saleService = saleService;
  }

  @PostMapping
  public ResponseEntity<Sale> createSale(@Validated @RequestBody SaleRequest saleRequest) {
    if (!saleRequest.areProductsQuantityValid()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    Sale sale = new Sale();
    sale.setSaleDate(LocalDateTime.now());

    Client client = new Client();
    client.setId(saleRequest.getClientId());
    sale.setClient(client);

    for (ProductRequest productRequest : saleRequest.getProducts()) {
      SaleProduct saleProduct = new SaleProduct();
      saleProduct.setProductId(productRequest.getId());
      saleProduct.setQuantity(productRequest.getQuantity());
      saleProduct.setSale(sale);
      sale.getSaleProducts().add(saleProduct);
    }

    Sale createdSale = saleService.createSale(sale);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdSale);
  }

  @GetMapping("/client/{clientId}")
  public ResponseEntity<List<Sale>> getClientSales(@PathVariable String clientId) {
    List<Sale> clientSales = saleService.getClientSales(clientId);
    return ResponseEntity.ok(clientSales);
  }

  @GetMapping("/product/{productId}")
  public ResponseEntity<Integer> findSalesOfAllProducts(@PathVariable int productId) {
    int totalQuantity = saleService.findAllSalesOfProduct(productId);
    return ResponseEntity.ok(totalQuantity);
  }
}
