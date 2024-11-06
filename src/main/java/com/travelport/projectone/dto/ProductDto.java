package com.travelport.projectone.dto;

public class ProductDto {

    private Integer productId;
    private Integer amount;

    public ProductDto() {}

    public ProductDto(Integer productId, Integer amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
