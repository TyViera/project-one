package com.travelport.projectone.service;

import com.travelport.projectone.entities.Sale;

import java.util.List;

public interface SaleService {
  Sale createSale(Sale sale);
  List<Sale> getClientSales(String clientId);
  int findAllSalesOfProduct(int productId);
}
