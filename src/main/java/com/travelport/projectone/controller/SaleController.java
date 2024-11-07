package com.travelport.projectone.controller;

import com.travelport.projectone.dto.ProductRequestDTO;
import com.travelport.projectone.dto.SaleProductResponseDTO;
import com.travelport.projectone.dto.SaleRequestDTO;
import com.travelport.projectone.dto.SaleResponseDTO;
import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Sale;
import com.travelport.projectone.entities.SaleProduct;
import com.travelport.projectone.jpa.ClientJpaRepository;
import com.travelport.projectone.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

  private final SaleService saleService;
  private final ClientJpaRepository clientRepository;

  public SaleController(SaleService saleService, ClientJpaRepository clientRepository) {
    this.saleService = saleService;
    this.clientRepository = clientRepository;
  }

  @PostMapping
  public ResponseEntity<SaleResponseDTO> createSale(@Valid @RequestBody SaleRequestDTO saleRequest) {
    Client client = clientRepository.findById(saleRequest.getClientId()).orElseGet(() -> {
      Client newClient = new Client();
      newClient.setId(saleRequest.getClientId());
      return clientRepository.save(newClient);
    });

    if (saleRequest.getProducts().isEmpty() || !saleRequest.areProductsQuantityValid()) {
      return ResponseEntity.badRequest().body(null);
    }

    Sale sale = new Sale();
    sale.setClient(client);
    sale.setSaleProducts(convertToSaleProducts(saleRequest.getProducts()));
    sale.setSaleDate(LocalDateTime.now());

    Sale savedSale = saleService.createSale(sale);
    SaleResponseDTO response = mapToSaleResponse(savedSale);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/client/{clientId}")
  public ResponseEntity<List<SaleResponseDTO>> getClientSales(@PathVariable String clientId) {
    List<Sale> sales = saleService.getClientSales(clientId);
    List<SaleResponseDTO> saleResponses = new ArrayList<>();

    for (Sale sale : sales) {
      saleResponses.add(mapToSaleResponse(sale));
    }
    return new ResponseEntity<>(saleResponses, HttpStatus.OK);
  }

  @GetMapping("/income-report/{productId}")
  public ResponseEntity<List<SaleProductResponseDTO>> getIncomeReport(@PathVariable int productId) {
    List<SaleProductResponseDTO> report = saleService.generateIncomeReport(productId);
    return new ResponseEntity<>(report, HttpStatus.OK);
  }

  private List<SaleProduct> convertToSaleProducts(List<ProductRequestDTO> productRequests) {
    List<SaleProduct> saleProducts = new ArrayList<>();
    for (ProductRequestDTO productRequest : productRequests) {
      SaleProduct saleProduct = new SaleProduct();
      saleProduct.setProductId(productRequest.getId());
      saleProduct.setQuantity(productRequest.getQuantity());
      saleProducts.add(saleProduct);
    }
    return saleProducts;
  }

  private SaleResponseDTO mapToSaleResponse(Sale sale) {
    SaleResponseDTO response = new SaleResponseDTO();
    response.setId(sale.getId());
    response.setClientId(sale.getClient().getId());
    response.setSaleDate(sale.getSaleDate());

    List<SaleProductResponseDTO> productResponses = new ArrayList<>();
    for (SaleProduct sp : sale.getSaleProducts()) {
      SaleProductResponseDTO productResponse = new SaleProductResponseDTO();
      productResponse.setProductId(sp.getProductId());
      productResponse.setQuantity(sp.getQuantity());
      productResponses.add(productResponse);
    }
    response.setSaleProducts(productResponses);
    return response;
  }
}
