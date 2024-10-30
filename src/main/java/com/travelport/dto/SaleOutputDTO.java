package com.travelport.dto;

import java.util.List;

public class SaleOutputDTO {

    private Integer saleId;
    private List<SaleDetailOutputDTO> products;

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public List<SaleDetailOutputDTO> getProducts() {
        return products;
    }

    public void setProducts(List<SaleDetailOutputDTO> products) {
        this.products = products;
    }
}
