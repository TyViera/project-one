package com.travelport.projectone.service;

import com.travelport.projectone.dto.SaleProductResponseDTO;
import com.travelport.projectone.entities.Sale;
import com.travelport.projectone.entities.SaleProduct;
import com.travelport.projectone.jpa.SaleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    List<Sale> sales = saleRepository.findAll();
    int totalSales = 0;

    for (Sale sale : sales) {
      for (SaleProduct saleProduct : sale.getSaleProducts()) {
        if (saleProduct.getProductId() == productId) {
          totalSales += saleProduct.getQuantity();
        }
      }
    }
    return totalSales;
  }

  @Override
  public List<SaleProductResponseDTO> generateIncomeReport(int productId) {
    List<Sale> sales = saleRepository.findAll();
    Map<Integer, Integer> productSalesCount = new HashMap<>();

    for (Sale sale : sales) {
      for (SaleProduct sp : sale.getSaleProducts()) {
        if (sp.getProductId() == productId) {
          productSalesCount.put(productId, productSalesCount.getOrDefault(productId, 0) + sp.getQuantity());
        }
      }
    }

    List<SaleProductResponseDTO> responseList = new ArrayList<>();
    if (productSalesCount.containsKey(productId)) {
      SaleProductResponseDTO dto = new SaleProductResponseDTO();
      dto.setProductId(productId);
      dto.setQuantity(productSalesCount.get(productId));
      responseList.add(dto);
    }
    return responseList;
  }
}
