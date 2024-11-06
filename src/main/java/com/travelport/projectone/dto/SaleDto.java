package com.travelport.projectone.dto;

import java.util.List;

public class SaleDto {

    private Integer saleId;
    private List<ProductDto> productList;

    public SaleDto(Integer saleId, List<ProductDto> productList) {
        this.saleId = saleId;
        this.productList = productList;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public List<ProductDto> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDto> productList) {
        this.productList = productList;
    }

}
