package com.travelport.projectone.dto;

import java.util.List;

public class PurchaseDto {

    private String clientNif;
    private List<ProductDto> productList;

    public PurchaseDto() {}

    public PurchaseDto(String clientNif, List<ProductDto> productList) {
        this.clientNif = clientNif;
        this.productList = productList;
    }

    public String getClientNif() {
        return clientNif;
    }

    public void setClientNif(String clientNif) {
        this.clientNif = clientNif;
    }

    public List<ProductDto> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDto> productList) {
        this.productList = productList;
    }

}
