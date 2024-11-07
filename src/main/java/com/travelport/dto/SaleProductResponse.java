package com.travelport.dto;

import com.travelport.entities.Product;

public class SaleProductResponse {
    private Integer id;
    private String code;
    private String name;
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setFromProduct(Product product, Integer quantity){
        this.id=product.getId();
        this.code=product.getCode();
        this.name=product.getName();
        this.quantity=quantity;
    }
}
