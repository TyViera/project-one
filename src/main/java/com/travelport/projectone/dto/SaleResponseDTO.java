package com.travelport.projectone.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SaleResponseDTO {
  private int id;
  private String clientId;
  private LocalDateTime saleDate;
  private List<SaleProductResponseDTO> saleProducts;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public LocalDateTime getSaleDate() {
    return saleDate;
  }

  public void setSaleDate(LocalDateTime saleDate) {
    this.saleDate = saleDate;
  }

  public List<SaleProductResponseDTO> getSaleProducts() {
    return saleProducts;
  }

  public void setSaleProducts(List<SaleProductResponseDTO> saleProducts) {
    this.saleProducts = saleProducts;
  }
}
