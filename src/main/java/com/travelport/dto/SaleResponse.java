package com.travelport.dto;

import java.util.List;

public class SaleResponse {
    private Integer saleId;
    private List<SaleProductResponse> products;

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public List<SaleProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<SaleProductResponse> products) {
        this.products = products;
    }
}
