package com.travelport.projectone.dto;

import java.util.List;

public class SaleRequest {
  private String clientId;
  private List<ProductRequest> products;

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public List<ProductRequest> getProducts() {
    return products;
  }

  public void setProducts(List<ProductRequest> products) {
    this.products = products;
  }

  public Boolean areProductsQuantityValid(){
    boolean validProducts=true;
    for(ProductRequest product: products){
      if(!product.isQuantityValid()) validProducts=false;
    }
    return validProducts;
  }

}