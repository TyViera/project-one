package com.travelport.projectone.dto;

public class ProductIpDto {

    private Integer code_product;
    private Integer quantity;

    public ProductIpDto(Integer code_product, Integer quantity) {
        this.code_product = code_product;
        this.quantity = quantity;
    }

    public Integer getCode_product() { return code_product; }
    public void setCode_product(Integer code_product) { this.code_product = code_product; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Boolean validQuantity() {
        if (quantity >= 1) {
            return true;
        } else {
            return false;
        }
    }
}
