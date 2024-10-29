package com.travelport.dto;

import java.util.List;

public class SaleResponseDTO {

    private Integer saleId;
    private List<SaleDetailResponseDTO> products;

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public List<SaleDetailResponseDTO> getProducts() {
        return products;
    }

    public void setProducts(List<SaleDetailResponseDTO> products) {
        this.products = products;
    }
}
