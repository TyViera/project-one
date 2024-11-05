package com.travelport.projectone.dto;

import java.util.List;

public class SaleRequestDTO {
  private String clientId;
  private List<ProductRequestDTO> products;

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public List<ProductRequestDTO> getProducts() {
    return products;
  }

  public void setProducts(List<ProductRequestDTO> products) {
    this.products = products;
  }

  public Boolean areProductsQuantityValid(){
    boolean validProducts=true;
    for(ProductRequestDTO product: products){
      if(!product.isQuantityValid()) validProducts=false;
    }
    return validProducts;
  }

}