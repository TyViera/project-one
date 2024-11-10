package com.travelport.projectone.dto;

import com.travelport.projectone.dto.ProductSalesDTO;

import java.util.List;

public class SaleRequestDTO {

    private String clientNif;
    private List<ProductSalesDTO> products;

    public String getClientNif() {
        return clientNif;
    }

    public void setClientNif(String clientNif) {
        this.clientNif = clientNif;
    }

    public List<ProductSalesDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSalesDTO> products) {
        this.products = products;
    }
}
