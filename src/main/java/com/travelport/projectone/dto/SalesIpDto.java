package com.travelport.projectone.dto;

import java.util.List;

public class SalesIpDto {

    private Integer code_client;
    private List<ProductIpDto> products;

    public SalesIpDto(Integer code_client, List<ProductIpDto> products) {
        this.code_client = code_client;
        this.products = products;
    }

    public Integer getCode_client() { return code_client; }
    public void setCode_client(Integer code_client) { this.code_client = code_client; }

    public List<ProductIpDto> getProducts() { return products; }
    public void setProducts(List<ProductIpDto> products) { this.products = products; }

}
