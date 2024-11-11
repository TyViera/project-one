package com.travelport.projectone.dto;

import  java.util.List;

public class SalesPrDto {

    private Integer id;
    private List<ProductIpDto> products;

    public SalesPrDto (Integer id, List<ProductIpDto> products) {
        this.id = id;
        this.products = products;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public List<ProductIpDto> getProducts() { return products; }
    public void setProducts(List<ProductIpDto> products) { this.products = products; }
}
