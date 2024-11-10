package com.travelport.projectone.dto;

import java.util.List;

public class SaleRequest {
    private Integer clientId;
    private List<ProductRequest> products;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequest> products) {
        this.products = products;
    }

    public Boolean areProductsQuantityValid(){
        Boolean validProducts=true;
        for(ProductRequest product: products){
            if(!product.isQuantityValid()) validProducts=false;
        }
        return validProducts;
    }

}
