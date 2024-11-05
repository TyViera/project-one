package com.travelport.projectone.service;

import com.travelport.projectone.entities.Sale;
import com.travelport.projectone.entities.SaleProduct;
import com.travelport.projectone.jpa.SaleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

  private final SaleJpaRepository saleRepository;

  public SaleServiceImpl(SaleJpaRepository saleRepository) {
    this.saleRepository = saleRepository;
  }

  @Override
  @Transactional
  public Sale createSale(Sale sale) {
    Sale savedSale = saleRepository.save(sale);

    for (SaleProduct product : savedSale.getSaleProducts()) {
      product.setSale(savedSale);
    }

    return savedSale;
  }

  @Override
  public List<Sale> getClientSales(String clientId) {
    return saleRepository.findByClientId(clientId);
  }

  @Override
  public int findAllSalesOfProduct(int productId) {
    return saleRepository.countByProductId(productId);
  }
}
